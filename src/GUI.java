import USSM.USM.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.*;

public class GUI {

    USM profile;
    String defaultProfileName;
    Achie[] achiesArray;
    JList<Achie> achies ;
    public GUI() {
        // Container container = new Box(0);
        // newProfile("main");
        getDefaultProfile();


    }

    void paintMainWin() {
        achiesArray = Achie.getAchies(profile);
        achies = new JList<Achie>();
        achies.setListData(achiesArray);
        JFrame jFrame = getFrame(JFrame.EXIT_ON_CLOSE);
        // container.add(new JButton());
        JPanel jp = new JPanel(new FlowLayout());
        JButton jbt = new JButton("Добавить достижение");
        jbt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*JDialog newAchieDialog = new JDialog();
                Dimension dimension = new Dimension();
                newAchieDialog.setBounds(dimension.width - 150, dimension.height - 300, 300, 600);
                JPanel addAchiePanel = new JPanel();///////////////////
                JDatePickerImpl dateFld = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()), new DateComponentFormatter());
                Date date = new Date();
                DateFormat dateFormat = SimpleDateFormat.getDateInstance();
                dateFld.setBackground(Color.gray);
                dateFld.getJFormattedTextField().setText(dateFormat.format(date));
                dateFld.getJFormattedTextField().setBackground(Color.gray);
                JTextField objectFld = new JTextField();
                JTextField typeFld = new JTextField();
                JTextField measureFld = new JTextField();
                JTextField countFld = new JFormattedTextField();
                JButton newAchieBtn = new JButton("Создать");
                newAchieBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addAchie(profile, date.getTime(), objectFld.getText(), typeFld.getText(), "",  measureFld.getText(), Long.parseLong(countFld.getText()));
                    }
                });
                JPanel datePanel = new JPanel();
                datePanel.add(dateFld);
                addAchiePanel.add(datePanel);
                JPanel objectPanel = new JPanel();
                objectPanel.add(objectFld);
                addAchiePanel.add(objectPanel);
                addAchiePanel.add(typeFld);
                JPanel measurePanel = new JPanel();
                measurePanel.add(measureFld);
                addAchiePanel.add(measurePanel);
                JPanel countPanel = new JPanel();
                countPanel.add(countFld);
                addAchiePanel.add(countPanel);
                JPanel newAchiePanel = new JPanel();
                newAchiePanel.add(newAchieBtn);
                addAchiePanel.add(newAchiePanel);
                newAchieDialog.setContentPane(addAchiePanel);
                newAchieDialog.setVisible(true);*/

                AddAchieDialog addAchieDialog = new AddAchieDialog(profile);
                Dimension dimension = new Dimension();
                addAchieDialog.setBounds(dimension.width - 150, dimension.height - 300, 300, 600);
                addAchieDialog.setVisible(true);
            }
        });
        achies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        achies.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (achies.getValueIsAdjusting()) {
                    JFrame viewAchie = getFrame(JFrame.HIDE_ON_CLOSE);
                    JPanel achiePanel = new JPanel(new FlowLayout());
                    DateFormat dateFormat = SimpleDateFormat.getDateInstance();
                    achiePanel.add(new JTextArea(dateFormat.format(achiesArray[achies.getSelectedIndex()].getDate())));
                    //viewAchie.setContentPane(achiePanel);
                    achiePanel.add(new JTextArea(achiesArray[achies.getSelectedIndex()].getObject()));
                    //viewAchie.setContentPane(achiePanel);
                    achiePanel.add(new JTextArea(achiesArray[achies.getSelectedIndex()].getType()));
                    //viewAchie.setContentPane(achiePanel);
                    achiePanel.add(new JTextArea(String.valueOf(achiesArray[achies.getSelectedIndex()].getCount()) + " " + achiesArray[achies.getSelectedIndex()].getMeasure()));
                    viewAchie.setContentPane(achiePanel);
                    Thread imgLoading = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        File imgFile = new File("profiles" + File.separator + "res" + File.separator + profile.get_name(), achiesArray[achies.getSelectedIndex()].getPhoto());
                                        System.out.println("File opened");
                                        BufferedImage img = ImageIO.read(imgFile);
                                        System.out.println("Image loaded");
                                        ImageIcon imgIcon = new ImageIcon(img.getScaledInstance(img.getWidth() / 10, img.getHeight() / 10, Image.SCALE_AREA_AVERAGING));
                                        System.out.println("Image resized");
                                        JLabel imgLabel = new JLabel(imgIcon);
                                        viewAchie.add(imgLabel);
                                        System.out.println("Image binded");
                                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                                        Dimension dimension = toolkit.getScreenSize();
                                        viewAchie.repaint(dimension.width / 2 - 250, dimension.height / 2 - 200, 500, 400);

                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    imgLoading.start();
                }

            }
        });

        jp.add(achies);
        jFrame.setContentPane(jp);
        jp.add(jbt);
        jFrame.setContentPane(jp);
        jbt = new JButton("Войти в профиль");
        jp.add(jbt);
        jFrame.setContentPane(jp);
        jbt = new JButton();
        jbt.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jbt.setText("Выйти из программы");
        jp.add(jbt);
        jFrame.setContentPane(jp);
    }
    static JFrame getFrame(int closeOperation) {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(closeOperation);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250, dimension.height/2 - 300, 500, 600);
        return jFrame;
    }


    /** Force creates new profile replacing it if exists
     * @param name name of profile which you want to create **/
    static USM newProfile(String name) {
        USM profile = new USM(name, 1);
        profile.create_isec("date");
        profile.create_ssec("object");
        profile.create_ssec("type");
        profile.create_ssec("measure");
        profile.create_ssec("photo");
        profile.create_isec("value");
        profile.to_file();
        return profile;
    }
    /** Adds an achievement in USSM.USM storage
     * Example :
     * 
     *      USSM.USM prof = newProfile("profile");
     *
     *      addAchie(prof, "2022.08.21, Sport, Push-ups, 100);
     * @param profile a USSM.USM profile, where the achievement will be added
     * @param date Date when was this achievement achieved
     * @param object The subject on which the achievement was completed
     * @param type The type of this achievement
     * @param value Value of this achievement **/
    static void addAchie(USM profile, long date, String object, String type, String photo, String measure, long value) {
        profile.geti("date").add(date);
        profile.gets("object").add(object);
        profile.gets("type").add(type);
        profile.gets("measure").add(measure);
        profile.gets("photo").add(photo);
        profile.geti("count").add(value);
        profile.to_file();
    }
    static private USM getProfile(String name) {
        USM profile = new USM(name);
        if (!profile.opened()) {
            profile.create_isec("date");
            profile.create_ssec("object");
            profile.create_ssec("type");
            profile.create_ssec("measure");
            profile.create_ssec("photo");
            profile.create_isec("value");
            profile.to_file();
        }
        return profile;
    }

    void getDefaultProfile() {
        Path defaultProfilePath = Paths.get("profiles", "default_profile.txt");
        final USM[] defaultProfile = {null};
        final boolean[] pressed = {false};
        try {
            if (!Files.exists(defaultProfilePath)) {
                Files.createFile(defaultProfilePath);
            }
            String defaultProfileName = Files.readString(defaultProfilePath);
            if (defaultProfileName.length() > 0) {
                profile = getProfile(defaultProfileName);
                pressed[0] = true;
            }
        } catch (IOException ignore) {

        }
        if (profile == null) {
            JDialog newProfileDialog = new JDialog();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            newProfileDialog.setBounds(dimension.width / 2 - 150, dimension.height / 2 - 50, 300, 100);
            JTextField profileName = new JTextField("Имя профиля");
            JButton createProfileBtn = new JButton();
            createProfileBtn.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    profile = newProfile(profileName.getText());
                    pressed[0] = true;
                    try {
                        Files.write(defaultProfilePath, profile.get_name().getBytes());
                        newProfileDialog.setVisible(false);
                        paintMainWin();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(profileName);
            panel.add(createProfileBtn);
            newProfileDialog.setContentPane(panel);
            newProfileDialog.setVisible(true);

        } else {
            paintMainWin();
        }
    }
}
