import USSM.USM.USM;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAchieDialog extends JDialog {
    private USM profile;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dateFld;
    private JTextField objectFld;
    private JTextField typeFld;
    private JTextField measureFld;
    private JTextField countFld;
    private JButton addImage;
    JFileChooser imgSrc;
    private Date date;

    public AddAchieDialog(USM profile) {
        date = new Date();
        this.profile = profile;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        dateFld.setText(SimpleDateFormat.getDateInstance().format(date));

        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgSrc = new JFileChooser();
                imgSrc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                imgSrc.showOpenDialog(AddAchieDialog.this);

            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
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
    }

    private void onOK() {
        // add your code here
        try {
            File imgSrcSelectedFile = imgSrc.getSelectedFile();
            FileChannel src = new FileInputStream(imgSrcSelectedFile).getChannel();
            File dstFile = new File("profiles" + File.separator + "res" + File.separator + profile.get_name() + File.separator + profile.gets("photo").size());
            FileChannel dst = new FileOutputStream(dstFile).getChannel();
            dst.transferFrom(src, 0, src.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        GUI.addAchie(profile, date.getTime(), objectFld.getText(), typeFld.getText(), String.valueOf(profile.gets("photo").size()), measureFld.getText(), Long.parseLong(countFld.getText()));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddAchieDialog dialog = new AddAchieDialog(new USM(args[1]));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
