package account;

import java.util.ArrayList;
import java.util.Objects;

public class ChatRoom {
    private static ArrayList<ChatRoom> allChatRooms = new ArrayList<>();
    private static int numberOfChatRoomsCreated = 0;
    private String chatRoomId;
    private ArrayList<Account> joinedAccounts;
    private ArrayList<Message> messages;

    public ChatRoom() {
        this.joinedAccounts = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.chatRoomId = ChatRoom.generateIdentifier();
        numberOfChatRoomsCreated++;
        allChatRooms.add(this);
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

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public static ArrayList<String> getSupportiveChatRoomIdsForSupporter(Supporter supporter){
        ArrayList<String> chatRoomIds = new ArrayList<>();
        for (ChatRoom allChatRoom : allChatRooms) {
            if(allChatRoom.isSupporterInThisChatRoom(supporter)){
                chatRoomIds.add(allChatRoom.getChatRoomId());
            }
        }
        return chatRoomIds;
    }

    public boolean isSupporterInThisChatRoom(Supporter supporter){
        for (Account joinedAccount : joinedAccounts) {
            if(joinedAccount.equals(supporter)){
                return true;
            }
        }
        return false;
    }

    public Customer getCustomerOfChatRoom(){
        for (Account joinedAccount : joinedAccounts) {
            if(joinedAccount instanceof Customer){
                return (Customer)joinedAccount;
            }
        }
        return null;
    }

    public static void removeChatRoom(ChatRoom chatRoom){
        allChatRooms.remove(chatRoom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(chatRoomId, chatRoom.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId);
    }
}
