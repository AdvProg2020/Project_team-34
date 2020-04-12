package log;

public enum LogStatus {
    PENDING,
    PREPARING,
    SENDING,
    DELIVERED;

    private LogStatus next;

    static {
        PENDING.next = PREPARING;
        PREPARING.next = SENDING;
        SENDING.next = DELIVERED;
        DELIVERED.next = DELIVERED;
    }

    public LogStatus nextStep() {
        return next;
    }
}
