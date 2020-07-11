package Server.Communications;

/**
 * @author Aryan Ahadinia
 * @author Soheil Mahdi Zadeh
 * @author Roozbeh PirAyadi
 * @since 0.0.3
 */

public class Response {
    private final RequestStatus status;
    private final String content;

    public Response(RequestStatus status, String content) {
        this.status = status;
        this.content = content;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}
