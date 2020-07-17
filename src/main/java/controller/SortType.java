package controller;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public enum SortType {
    BY_AVERAGE_SCORE,
    BY_TIME,
    BY_NUMBER_OF_VIEWS;

    private String printableType;

    static {
        BY_NUMBER_OF_VIEWS.printableType = "by number of views";
        BY_AVERAGE_SCORE.printableType = "by score";
        BY_TIME.printableType = "by time added";
    }

    public String getPrintableType() {
        return printableType;
    }
}
