package account;

import exceptionalMassage.ExceptionalMassage;
import server.communications.Utils;

public class Message {
    private String senderUsername;
    private String contentOfMessage;
    private String chatRoomId;

    private Message(String senderUsername, String contentOfMessage, ChatRoom chatRoom) {
        this.senderUsername = senderUsername;
        this.contentOfMessage = contentOfMessage;
        this.chatRoomId = chatRoom.getChatRoomId();
        chatRoom.addMessage(this);
    }

    public static Message getInstance(String senderUsername, String contentOfMessage, String chatRoomId) throws ExceptionalMassage {
        ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
        if (chatRoom == null)
            throw new ExceptionalMassage("Chatroom not found");
        return new Message(senderUsername, contentOfMessage, chatRoom);
    }

    public static Message convertJsonStringToMessage(String jsonString){
        return (Message) Utils.convertStringToObject(jsonString, "account.Message");
    }
}
