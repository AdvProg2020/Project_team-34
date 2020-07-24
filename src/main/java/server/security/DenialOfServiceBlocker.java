package server.security;

import java.util.*;

/**
 * @author Aryan Ahadinia
 * @since 0.0.3
 */
public class DenialOfServiceBlocker {
    private static final int MAX_CONNECTIONS = 4;
    private static final int MAX_FREQUENCY_ALLOWED = 30;
    private static final int FREQUENCY_CALCULATOR_PERIOD_MILLIS = 10000;
    private static final int BLOCKING_PERIOD = 36000;

    private final ArrayList<String> blockedIp;
    private final HashMap<Date, String> cummonicationRequests;
    private final HashMap<String, Integer> connectionRequests;

    private final Timer blockedIpCleaner;
    private final Timer connectionRequestsCleaner;

    private boolean goToPrint;

    public DenialOfServiceBlocker() {
        this.blockedIp = new ArrayList<>();
        this.cummonicationRequests = new HashMap<>();
        this.connectionRequests = new HashMap<>();
        this.blockedIpCleaner = new Timer();
        this.connectionRequestsCleaner = new Timer();
        this.goToPrint = false;
        new Thread(() -> {
            while (true) {
                if (goToPrint) {
                    System.err.println("A DoS attack was blocked by DenialOfServiceBlocker :)");
                    goToPrint = false;
                }
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void reduceConnection(String ip) {
        if (connectionRequests.containsKey(ip))
            connectionRequests.replace(ip, connectionRequests.get(ip) - 1);
    }

    public boolean getIpPermissionForConnection(String ip) {
        System.out.println(ip);
        if (connectionRequests.containsKey(ip) && connectionRequests.get(ip) >= MAX_CONNECTIONS) {
            return false;
        } else if (connectionRequests.containsKey(ip)) {
            connectionRequests.replace(ip, connectionRequests.get(ip) + 1);
        } else {
            connectionRequests.put(ip, 1);
        }
        return true;
    }

    public boolean getIpPermissionForCommunication(String ip) {
        System.out.println(ip);
        Date requestDate = new Date(System.currentTimeMillis());
        cummonicationRequests.put(requestDate, ip);
        TimerTask connectionRequestCleaningTask = new TimerTask() {
            @Override
            public void run() {
                cummonicationRequests.remove(requestDate);
            }
        };
        connectionRequestsCleaner.schedule(connectionRequestCleaningTask, new Date(System.currentTimeMillis() +
                FREQUENCY_CALCULATOR_PERIOD_MILLIS));
        double frequency = checkConnectionsFrequency(ip);
        System.out.println("FRE: " + frequency);
        if (frequency > MAX_FREQUENCY_ALLOWED || blockedIp.contains(ip)) {
            blockedIp.add(ip);
            TimerTask blockIpCleaningTask = new TimerTask() {
                @Override
                public void run() {
                    blockedIp.remove(ip);
                }
            };
            blockedIpCleaner.schedule(blockIpCleaningTask, new Date(System.currentTimeMillis() + BLOCKING_PERIOD));
            goToPrint = true;
            return false;
        }
        return true;
    }

    private synchronized double checkConnectionsFrequency(String ip) {
        try {
            int c = 0;
            for (Date date : cummonicationRequests.keySet()) {
                if (cummonicationRequests.get(date).equals(ip)) {
                    c++;
                }
            }
            System.out.println("f:" + c);
            return (c * 1000) / (double) FREQUENCY_CALCULATOR_PERIOD_MILLIS;
        } catch (ConcurrentModificationException e) {
            System.out.println("Concurren");
            return 0;
        }
    }
}
