package menu.products;

import menu.menuAbstract.Menu;

public class CommentMenu extends Menu {
    public CommentMenu(Menu parentMenu) {
        super("Comment Menu", parentMenu);

        Menu addComment = new Menu("Comment Menu", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^Add comment$", addComment);
        menuForShow.add("Add comment");
    }
}
