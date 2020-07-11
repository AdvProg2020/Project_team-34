package server.communications;

/**
 * @author Aryan Ahadinia
 * @author Soheil Mahdi Zadeh
 * @author Roozbeh PirAyadi
 * @since 0.0.3
 */

public class Response {
    private RequestStatus status;
    private String content;

    public Response(RequestStatus status, String content) {
        this.status = status;
        this.content = content;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
