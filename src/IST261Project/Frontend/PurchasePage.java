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
        buttonConfirmPurchase.addActionListener(e -> {
            address = textAddress.getText(); //stores
            inventorynumber = textInvNum.getText();
            ConfirmationPage confirm = new ConfirmationPage();
            confirm.setData(textInvNum.getText(), textAddress.getText());
            confirm.setVisible(true);
        });
    }

    public void setCarID(int id) {
       textInvNum.setText(id + "");
    }

    private JPanel PurchasePanel;
    private JLabel labelInvNum;
    private JTextField textInvNum;
    private JLabel labelCardNum;
    private JTextField textCarNum;
    private JLabel labelAddress;
    private JTextField textAddress;
    private JButton buttonConfirmPurchase;

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

    public JTextField getTextInvNum() {
        return textInvNum;
    }

    public JLabel getLabelInvNum() {
        return labelInvNum;
    }

    public JPanel getPurchasePanel() {
        return PurchasePanel;
    }
}
