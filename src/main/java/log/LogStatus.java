package log;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */
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

    @Override
    public String toString() {
        if (this == LogStatus.PENDING)
            return "PENDING";
        if (this == LogStatus.PREPARING)
            return "PREPARING";
        if (this == LogStatus.SENDING)
            return "SENDING";
        return "DELIVERED";
    }
}
