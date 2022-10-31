import USSM.USSM.LOQ.Interpreter;
import USSM.USSM.LOQ.LOQ;
import USSM.USSM.LOQ.LOQNoProfileException;

import javax.swing.*;
import java.awt.event.*;

public class LOQTerm extends JDialog {
    private LOQ loq;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea LOQOutputFld;
    private JTextField LOQCmdFld;
    private JButton enterCmdBtn;

    public LOQTerm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        loq = new LOQ();
        LOQOutputFld.setText(loq.getProfName());
        enterCmdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        LOQOutputFld.setEditable(false);
    }

    private void onOK() {
        // add your code here
        LOQOutputFld.setText(LOQOutputFld.getText() + "\n" + loq.getProfName() + " > " + LOQCmdFld.getText() + "\n");
        try {
            loq.parseQuery(LOQCmdFld.getText());
            if (loq.changed()) {
                switch (loq.getLastFormat()) {
                    case 0:
                        LOQOutputFld.setText(LOQOutputFld.getText() + "\n" + loq.popInt());
                        break;
                    case 1:
                        LOQOutputFld.setText(LOQOutputFld.getText() + "\n" + loq.popStr());
                        break;
                    case 3:
                        LOQOutputFld.setText(LOQOutputFld.getText() + "\n" + (loq.getLockStatus() ? "locked" : "unlocked"));
                }
            }
        } catch (LOQNoProfileException ex) {
            LOQOutputFld.setText(ex.getMessage());
        }
        // LOQOutputFld.setText(loq.getProfName() + " > ");
        // LOQOutputFld.setText(loq.getProfName());
        // dispose();
    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
    }

    public static void main(String[] args) {
        LOQTerm dialog = new LOQTerm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
