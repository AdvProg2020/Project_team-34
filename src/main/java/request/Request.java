package request;

import java.util.ArrayList;

public abstract class Request {
    private String requestId;
    private static ArrayList<ProductRequest> allRequest = new ArrayList<>();

    public String getRequestId() {
        return requestId;
    }


    public static ArrayList<String> getAllRequestId(){
        ArrayList<String> allId = new ArrayList<>();
        for (ProductRequest requestProduct : allRequest) {
            allId.add(requestProduct.getRequestId());
        }
        return allId;
    }

    public static Request getRequestById(String requestId){
        for (ProductRequest requestProduct : allRequest) {
            if(requestProduct.getRequestId().equals(requestId))
                return requestProduct;
        }
        return null;
    }

    public void removeRequest(){
        allRequest.remove(this);
    }

    public abstract void doRequest();
}
