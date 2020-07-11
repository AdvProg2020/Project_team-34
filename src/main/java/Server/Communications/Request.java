package Server.Communications;

/**
 * @author Aryan Ahadinia
 * @author Soheil Mahdi Zadeh
 * @author Roozbeh PirAyadi
 * @since 0.0.3
 */

public class Request {
    private String token;
    private String function;
    private String inputs;
    private ControllerSource source;

    public Request(String token, String function, String inputs, ControllerSource source) {
        this.token = token;
        this.function = function;
        this.inputs = inputs;
        this.source = source;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public ControllerSource getSource() {
        return source;
    }

    public void setSource(ControllerSource source) {
        this.source = source;
    }
}
