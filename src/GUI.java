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
    /** Get profile by name. If profile is not existing, creates a new
     * @param name name for profile which you want to get **/
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
    /** Force creates new profile replacing it if exists
     * @param name name of profile which you want to create **/
    static USM newProfile(String name) {
        USM profile = new USM(name, 1);
        profile.create_ssec("date");
        profile.create_ssec("object");
        profile.create_ssec("type");
        profile.create_isec("value");
        profile.to_file();
        return profile;
    }
    /** Adds an achievement in USM storage
     * Example :
     * 
     *      USM prof = newProfile("profile");
     *
     *      addAchie(prof, "2022.08.21, Sport, Push-ups, 100);
     * @param profile a USM profile, where the achievement will be added
     * @param date Date when was this achievement achieved
     * @param object The subject on which the achievement was completed
     * @param type The type of this achievement
     * @param value Value of this achievement **/
    void addAchie(USM profile, String date, String object, String type, int value) {
        profile.gets("date").add(date);
        profile.gets("object").add(object);
        profile.gets("type").add(type);
        profile.geti("value").add(value);
        profile.to_file();
    }
}
