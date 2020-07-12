package discount;

import account.Account;
import controller.AccountController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Aryan Ahadinia
 * @since 0.0.3
 */

public class PeriodicCodedDiscountGenerator extends Thread {
    private final String filePath;
    private final int percent;
    private final int maxAmount;
    private final long period;
    private long lastTime;
    private boolean lastTry;
    private final boolean continuous;

    public PeriodicCodedDiscountGenerator(boolean continuous) throws FileNotFoundException {
        this.filePath = "src/main/java/PeriodicCodedDiscount.txt";
        Scanner reader = new Scanner(new File(filePath));
        reader.nextLine();
        this.percent = Integer.parseInt(reader.nextLine());
        this.maxAmount = Integer.parseInt(reader.nextLine());
        this.period = Long.parseLong(reader.nextLine());
        this.lastTime = Long.parseLong(reader.nextLine());
        reader.close();
        this.lastTry = false;
        this.continuous = continuous;
    }

    @Override
    public void run() {
        try {
            long timeNow = System.currentTimeMillis();
            if (timeNow - lastTime >= period && continuous) {
                FileWriter writer = new FileWriter(new File(filePath));
                writer.write("Data\n");
                writer.write(percent + "\n");
                writer.write(maxAmount + "\n");
                writer.write(period + "\n");
                writer.write(timeNow + "\n");
                writer.write("percent, max amount, period, lastTime");
                writer.close();
                AccountController.controlCreateRandomCodesForCustomers(Account.getRandomCustomers(), percent, maxAmount);
                lastTime = timeNow;
                lastTry = true;
            } else {
                lastTry = false;
            }
        } catch (IOException e) {
            System.err.println("Error, during writing periodic coded discount info");
        }
        try {
            if (lastTry) {
                Thread.sleep(period);
            } else {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Error, periodic coded discount thread interrupted");
        }
        if (continuous) {
            run();
        }
    }
}
