package request;


import product.Product;

public class ProductRequest extends Request{
    private Product oldProduct;
    private Product newProduct;


    @Override
    public void doRequest() {
        if (oldProduct != null)
            oldProduct.removeProduct();
        newProduct.addProduct();
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
