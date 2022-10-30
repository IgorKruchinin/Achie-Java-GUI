import USM.USM;

import java.awt.*;

public class Start {
    public static void main(String[] args) {
        // new GUI();
        MainMenu mainMenu = new MainMenu(new USM("2022"));
        Dimension dimension = new Dimension();
        Rectangle rectangle = new Rectangle(dimension.width - 250, dimension.height - 300, 500, 600);
        mainMenu.setBounds(rectangle);
        mainMenu.setVisible(true);
    }
}
