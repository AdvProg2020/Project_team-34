package server.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class DenialOfServiceBlocker {
    private static final int MAX_FREQUENCY_ALLOWED = 10;
    private static final int FREQUENCY_CALCULATOR_PERIOD_MILLIS = 10000;
    private static final int BLOCKING_PERIOD = 3600000;

    private ArrayList<String> blockedIp;
    private HashMap<Date, String> connectionRequests;

    private Timer blockedIpCleaner;
    private Timer connectionRequestsCleaner;

    public boolean getIpPermission(String ip) {
        return false;
    }

    private long checkConnectionsFrequency(String ip) {
        return 0;
    }
}
