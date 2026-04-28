package IST261Project.Frontend;

import javax.swing.*;

public class PurchasePage extends JFrame {
    private String address;
    private String inventorynumber;
    public PurchasePage() {
        setTitle("Purchase Car");
        setContentPane(PurchasePanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    public void setCarID(int id) {
       textInvNum.setText(id + "");
    }

    private JPanel PurchasePanel;
    private JLabel labelInvNum;
    private JLabel textInvNum;
    private JLabel labelCardNum;
    private JTextField textCarNum;
    private JLabel labelAddress;
    private JTextField textAddress;
    private JButton buttonConfirmPurchase;
    private JTextField textName;
    private JLabel labelName;
    private JTextField textCCV;
    private JLabel labelCCV;

    public JButton getButtonConfirmPurchase() {
        return buttonConfirmPurchase;
    }

    public JTextField getTextAddress() {
        return textAddress;
    }

    public JLabel getLabelAddress() {
        return labelAddress;
    }

    public JTextField getTextCarNum() {
        return textCarNum;
    }

    public JLabel getLabelCardNum() {
        return labelCardNum;
    }

    public JLabel getTextInvNum() {
        return textInvNum;
    }

    public JLabel getLabelInvNum() {
        return labelInvNum;
    }

    public JPanel getPurchasePanel() {
        return PurchasePanel;
    }

    public JTextField getTextName() { return textName; }

    public JLabel getLabelName() { return labelName; }

    public JTextField getTextCCV() { return textCCV; }

    public JLabel getLabelCCV() { return labelCCV; }
}
