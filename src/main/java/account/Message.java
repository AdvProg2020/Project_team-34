package account;

import exceptionalMassage.ExceptionalMassage;
import server.communications.Utils;

public class Message {
    private String senderUsername;
    private String contentOfMessage;
    private String chatRoomId;

    public Message(String senderUsername, String contentOfMessage, String chatRoomId) throws ExceptionalMassage {
        this.senderUsername = senderUsername;
        this.contentOfMessage = contentOfMessage;
        this.chatRoomId = chatRoomId;
        if(ChatRoom.getChatRoomById(chatRoomId) == null){
            throw new ExceptionalMassage("Invalid Chat Room Id");
        } else {
            ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
            chatRoom.addMessage(this);
        }
    }

    public static Message convertJsonStringToMessage(String jsonString){
        return (Message) Utils.convertStringToObject(jsonString, "account.Message");
    }

}
