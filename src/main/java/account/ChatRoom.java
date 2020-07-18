package account;

import java.util.ArrayList;

public class ChatRoom {
    private static ArrayList<ChatRoom> allChatRooms = new ArrayList<>();
    private static int numberOfChatRoomsCreated = 0;
    private String chatRoomId;
    private ArrayList<Account> joinedAccounts;
    private ArrayList<Message> messages;

    public ChatRoom() {
        this.joinedAccounts = new ArrayList<>();
        this.messages = new ArrayList<>();
        allChatRooms.add(this);
        numberOfChatRoomsCreated++;
        this.chatRoomId = ChatRoom.generateIdentifier();
    }

    public void joinChatRoom(Account account){
        joinedAccounts.add(account);
    }

    public void leaveChatRoom(Account account){
        joinedAccounts.remove(account);
    }

    private static String generateIdentifier() {
        return "T34CR" + String.format("%015d", numberOfChatRoomsCreated + 1);
    }

    public static ChatRoom getChatRoomById(String id){
        for (ChatRoom allChatRoom : allChatRooms) {
            if(allChatRoom.getChatRoomId().equals(id)){
                return allChatRoom;
            }
        }
        return null;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public ArrayList<Account> getJoinedAccounts() {
        return joinedAccounts;
    }

    public static ArrayList<ChatRoom> getAllChatRooms() {
        return allChatRooms;
    }

    public void addMessage(Message message){
        messages.add(message);
    }
}
