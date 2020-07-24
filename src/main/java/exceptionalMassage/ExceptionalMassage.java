package exceptionalMassage;

import server.communications.Utils;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ExceptionalMassage extends Exception {
    public ExceptionalMassage(String message) {
        super(message);
    }

    public static ExceptionalMassage convertJsonStringToExceptionalMessage(String jsonString){
        return (ExceptionalMassage) Utils.convertStringToObject(jsonString, "exceptionalMessage.ExceptionalMessage");
    }
}

