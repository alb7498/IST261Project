package IST261Project.Frontend;

import javax.swing.*;

public class ConfirmationPage extends JFrame{
    private String inventorynumber;
    private String address;
    public ConfirmationPage(){
        setContentPane(mainPanel);
        setTitle("Confirmation");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        buttonClose.addActionListener(e-> {
            dispose();
        });
    }
    public void setData(String inventorynumber, String address){
        labelInventoryNumberConfirmation.setText("Inventory Number: " + inventorynumber);

        labelAddressConfirmation.setText("Zip Code: " + address);
    }
    private JPanel mainPanel;
    private JLabel labelPSuccess;
    private JLabel labelInventoryNumberConfirmation;
    private JLabel labelAddressConfirmation;
    private JButton buttonClose;

    public JButton getButtonDone() {
        return buttonClose;
    }

    public JLabel getLabelAddressConfirmation() {
        return labelAddressConfirmation;
    }

    public JLabel getLabelInventoryNumberConfirmation() {
        return labelInventoryNumberConfirmation;
    }

    public JLabel getLabelPSuccess() {
        return labelPSuccess;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
