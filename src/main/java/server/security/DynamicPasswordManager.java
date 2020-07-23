package server.security;

import account.Account;
import exceptionalMassage.ExceptionalMassage;
import server.MailSender;

import java.util.*;

public class DynamicPasswordManager {
    private static final int MAX_REQUESTS_IN_TIME_PERIOD_HOUR = 5;
    private static final int MAX_REQUEST_TIME_PERIOD = (60 * 60 * 1000);
    private static final int PASSWORD_EXPIRY_PERIOD = 5 * 60 * 1000;

    private final HashMap<String, String> activePasswords;
    private final HashMap<Date, String> requestsHistory;

    private final Timer activePasswordsCleaner;
    private final Timer requestsCleaner;

    public DynamicPasswordManager() {
        this.activePasswords = new HashMap<>();
        this.requestsHistory = new HashMap<>();
        this.activePasswordsCleaner = new Timer();
        this.requestsCleaner = new Timer();
    }

    public void requestPassword(String username) throws ExceptionalMassage {
        Account account = Account.getAccountByUsernameWithinAvailable(username);
        if (account == null)
            throw new ExceptionalMassage("Username is not available. DPM");
        if (!permissionForGettingPassword(username))
            throw new ExceptionalMassage("This account is Temporary blocked for requesting Dynamic password. DPM");
        activePasswords.remove(username);
        Date requestDate = new Date(System.currentTimeMillis());
        requestsHistory.put(requestDate, username);
        TimerTask requestCleaningTask = new TimerTask() {
            @Override
            public void run() {
                requestsHistory.remove(requestDate);
            }
        };
        requestsCleaner.schedule(requestCleaningTask, new Date(System.currentTimeMillis() + MAX_REQUEST_TIME_PERIOD));
        String password = generateRandomPassword();
        activePasswords.put(username, password);
        TimerTask passwordExpiryTask = new TimerTask() {
            @Override
            public void run() {
                if (activePasswords.containsKey(username) && activePasswords.get(username).equals(password)) {
                    activePasswords.remove(username, password);
                }
            }
        };
        activePasswordsCleaner.schedule(passwordExpiryTask, new Date(System.currentTimeMillis() + PASSWORD_EXPIRY_PERIOD));
        String passwordEmail = "Your dynamic password for @" + account.getUserName() + " is " + password + ".\n" +
                "This password is valid for " + PASSWORD_EXPIRY_PERIOD /(int) (60 * 1000) + " minutes.";
        new MailSender(account.getEmail(), "Dynamic Password", passwordEmail).send();
    }

    public boolean authenticatePassword(String username, String password) {
        if (!activePasswords.containsKey(username))
            return false;
        if (activePasswords.get(username).equals(password)) {
            activePasswords.remove(username);
            return true;
        }
        return false;
    }

    private String generateRandomPassword() {
        final String LETTERS_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        int upperBound = LETTERS_SET.length() - 1;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            code.append(LETTERS_SET.charAt(rand.nextInt(upperBound)));
        }
        return code.toString();
    }

    private boolean permissionForGettingPassword(String username) {
        int c = 0;
        for (Date date : requestsHistory.keySet()) {
            if (requestsHistory.get(date).equals(username)) {
                c++;
            }
        }
        int frequency = (c * 60 * 60 * 1000) / MAX_REQUEST_TIME_PERIOD;
        return c <= MAX_REQUESTS_IN_TIME_PERIOD_HOUR;
    }
}