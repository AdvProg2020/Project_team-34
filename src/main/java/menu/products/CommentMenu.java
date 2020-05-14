package menu.products;

import account.Customer;
import feedback.Comment;
import menu.menuAbstract.Menu;
import product.Product;

public class CommentMenu extends Menu {
    private Product product;
    public CommentMenu(Menu parentMenu,Product product) {
        super("Comment Menu", parentMenu);
        this.product = product;
        Menu addComment = new Menu("Comment Menu", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String title = null;
                String content = null;
                System.out.println("Enter the title :");
                title = scanner.nextLine();
                System.out.println("Enter the content");
                content = scanner.nextLine();
                controller.getProductController().controlAddCommentToProduct(title,content,product);
                System.out.println("comment added successfully!");
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^Add comment$", addComment);
        menuForShow.add("Add comment");

    }

    @Override
    public void show() {
        System.out.println("Comments : ");
        for (Comment comment : controller.getProductController().controlGetConfirmedComments()) {
            System.out.println(comment);
        }
        super.show();
    }
}
