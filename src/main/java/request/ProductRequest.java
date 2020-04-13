package request;


import product.Product;

public class ProductRequest extends Request {
    private Product oldProduct;
    private Product newProduct;
    private static int allCreatedProductRequestNum = 0;

    public ProductRequest() {
        super();
    }


    @Override
    public void doRequest(boolean acceptedOrNot) {
        if (acceptedOrNot) {
            if (oldProduct != null)
                oldProduct.removeProduct();
            newProduct.addProduct();
        }
        this.removeRequest();
    }

    @Override
    public static String generateRequestId() {
        return "T34PR" + String.format("%015d",allCreatedProductRequestNum  + 1);
    }

    @Override
    public String toString() {
        return "RequestProduct{" +
                "oldProduct=" + oldProduct +
                ", newProduct=" + newProduct +
                ", requestId='" + this.getRequestId() + '\'' +
                '}';
    }

}
