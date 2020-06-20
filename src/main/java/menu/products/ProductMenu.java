package menu.products;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Product;

import java.util.regex.Matcher;

public class ProductMenu extends Menu {
    private Product currentProduct;
    public ProductMenu(Menu parentMenu) {
        super("Product Menu", parentMenu);


        menusIn.put("^digest$", new DigestMenu(this));
        menuForShow.add("Digest Menu");

        Menu attributes = new Menu("Attributes", this) {
            @Override
            public void show() {
                try{
                    System.out.println(controller.getProductController().controlGetAttributesOfProduct(currentProduct.getProductId()));
                } catch (ExceptionalMassage ex){
                    System.out.println(ex.getMessage());
                }
            }

            @Override
            public void execute() {

                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^attributes$", attributes);
        menuForShow.add("Attributes");

        Menu compare = new Menu("CompareGMenu" , this) {
            @Override
            public void show() {
                String regex = "^compare ([^\\s]+)";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try {
                        System.out.println(controller.getProductController().controlCompare(currentProduct.getProductId(), matcher.group(1)));
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^compare ([^\\s]+)", compare);
        menuForShow.add("CompareGMenu [productID]");

        menusIn.put("^Comments$", new CommentMenu(this, currentProduct));
        menuForShow.add("Comments");





    }

    @Override
    public void execute(){
        String regex = "^show product ([^\\s]+)$";
        Matcher matcher = getMatcher(command, regex);
        if(matcher.find()){
            if(Product.getProductById(matcher.group(1)) == null){
                System.out.println("No such product");
                parentMenu.show();
                parentMenu.execute();
            }
            currentProduct = Product.getProductById(matcher.group(1));
            ((DigestMenu)menusIn.get("^digest$")).setProduct(currentProduct);
            ((CommentMenu)menusIn.get("^Comments$")).setProduct(currentProduct);
        }
        super.execute();
    }
}
