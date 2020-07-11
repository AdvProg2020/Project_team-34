package Server.Communications;

/**
 * @author Aryan Ahadinia
 * @author Soheil Mahdi Zadeh
 * @author Roozbeh PirAyadi
 * @since 0.0.3
 */

public class Request {
    private final String token;
    private final String function;
    private final String inputs;

    public Request(String token, String function, String inputs) {
        this.token = token;
        this.function = function;
        this.inputs = inputs;
    }

    public String getToken() {
        return token;
    }

    public String getFunction() {
        return function;
    }

    public String getInputs() {
        return inputs;
    }
}
