import USM.*;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void main(String[] args) {
        JFrame jFrame = getFrame();
    }
    static JFrame getFrame() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250, dimension.height/2 - 150, 500, 300);
        return jFrame;
    }
    static USM getProfile(String name) {
        USM profile = new USM(name);
        if (!profile.opened()) {
            profile.create_ssec("date");
            profile.create_ssec("object");
            profile.create_ssec("type");
            profile.create_isec("value");
            profile.to_file();
        }
        return profile;
    }
    static USM newProfile(String name) {
        USM profile = new USM(name, 1);
        profile.create_ssec("date");
        profile.create_ssec("object");
        profile.create_ssec("type");
        profile.create_isec("value");
        profile.to_file();
        return profile;
    }
    void addAchie(USM profile, String date, String object, String type, int value) {
        profile.gets("date").add(date);
        profile.gets("object").add(object);
        profile.gets("type").add(type);
        profile.geti("value").add(value);
        profile.to_file();
    }
}
