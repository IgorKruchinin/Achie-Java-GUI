import USSM.USM.USM;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.desktop.OpenFilesEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.zip.ZipFile;

public class MainMenu extends JDialog {
    private USM profile;
    private JPanel contentPane;
    private JButton addAchieBtn;
    private JButton buttonCancel;
    private JList<Achie> achiesList;
    private JButton openTermBtn;
    private JButton importBtn;
    private JFileChooser profPackChooser;
    private Rectangle rectangle;
    private  Achie[] achiesArray;

    public MainMenu(USM profile) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(addAchieBtn);
        Dimension dimension = new Dimension();
        rectangle = new Rectangle(dimension.width - 350, dimension.height - 300, 700, 600);

        addAchieBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddAchie();
            }
        });

        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profPackChooser = new JFileChooser() {
                    @Override
                    public void approveSelection() {
                        File selectedFile = getSelectedFile();
                        if (selectedFile.isFile()) {
                            try {
                                USM.from_profile_archive(new ZipFile(selectedFile, ZipFile.OPEN_READ));
                                MainMenu.this.profile = new USM(profile.get_name());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            profPackChooser.cancelSelection();
                            resetListContent();
                            return;
                        } else {
                            super.approveSelection();
                        }
                    }
                };
                profPackChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                profPackChooser.showOpenDialog(MainMenu.this);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        openTermBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOQTerm terminal = new LOQTerm();
                terminal.setBounds(rectangle);
                terminal.setVisible(true);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.profile = profile;
        resetListContent();
        achiesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (achiesList.getValueIsAdjusting()) {
                    // JFrame viewAchie = GUI.getFrame(JFrame.HIDE_ON_CLOSE);
                    JDialog viewAchie = new JDialog(MainMenu.this);
                    //Dimension dimension = new Dimension();
                    //Rectangle rectangle = new Rectangle(dimension.width - 250, dimension.height - 300, 500, 600);
                    viewAchie.setBounds(rectangle);
                    JPanel achiePanel = new JPanel(new FlowLayout());
                    DateFormat dateFormat = SimpleDateFormat.getDateInstance();
                    achiePanel.add(new JTextArea(dateFormat.format(achiesArray[achiesList.getSelectedIndex()].getDate())));
                    //viewAchie.setContentPane(achiePanel);
                    achiePanel.add(new JTextArea(achiesArray[achiesList.getSelectedIndex()].getObject()));
                    //viewAchie.setContentPane(achiePanel);
                    achiePanel.add(new JTextArea(achiesArray[achiesList.getSelectedIndex()].getType()));
                    //viewAchie.setContentPane(achiePanel);
                    achiePanel.add(new JTextArea(String.valueOf(achiesArray[achiesList.getSelectedIndex()].getCount()) + " " + achiesArray[achiesList.getSelectedIndex()].getMeasure()));
                    viewAchie.setContentPane(achiePanel);
                    Thread imgLoading = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        File imgFile = new File("profiles" + File.separator + "res" + File.separator + profile.get_name(), achiesArray[achiesList.getSelectedIndex()].getPhoto());
                                        System.out.println("File opened");
                                        BufferedImage img = ImageIO.read(imgFile);
                                        System.out.println("Image loaded");
                                        double k = (double)img.getHeight() / img.getWidth();
                                        ImageIcon imgIcon = new ImageIcon(img.getScaledInstance(700, (int)(700 * k), Image.SCALE_AREA_AVERAGING));
                                        System.out.println("Image resized");
                                        JLabel imgLabel = new JLabel(imgIcon);
                                        viewAchie.add(imgLabel);
                                        System.out.println("Image binded");
                                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                                        Dimension dimension = toolkit.getScreenSize();
                                        viewAchie.repaint(dimension.width / 2 - 350, dimension.height / 2 - 200, 700, 400);

                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    viewAchie.setVisible(true);
                    imgLoading.start();
                }

            }
        });

    }

    private void onAddAchie() {
        // add your code here
        AddAchieDialog addAchieDialog = new AddAchieDialog(profile);
        addAchieDialog.setBounds(rectangle);
        addAchieDialog.setVisible(true);
        // dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private  void resetListContent() {
        achiesArray = Achie.getAchies(profile);
        achiesList.setListData(achiesArray);
    }

    public static void main(String[] args) {
        MainMenu dialog = new MainMenu(new USM(args[1]));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
