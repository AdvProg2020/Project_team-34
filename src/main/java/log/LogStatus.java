package log;

public enum LogStatus {
    PENDING,
    PREPARING,
    SENDING,
    DELIVERED;

    public void proceedToNextStep() {

    }
}
