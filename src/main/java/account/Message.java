package account;

import communications.Utils;
import exceptionalMassage.ExceptionalMassage;

public class Message {
    private String senderUsername;
    private String contentOfMessage;
    private String chatRoomId;

    public static Message convertJsonStringToMessage(String jsonString){
        return (Message) Utils.convertStringToObject(jsonString, "account.Message");
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getContentOfMessage() {
        return contentOfMessage;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }
}