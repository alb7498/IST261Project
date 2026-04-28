package IST261Project.Frontend;

import javax.swing.*;
import java.awt.*;

public class ConfirmationPage extends JFrame{
    private String inventorynumber;
    private String address;
    public ConfirmationPage() {
        setContentPane(mainPanel);
        setTitle("Confirmation");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carPanel.setLayout(new BorderLayout());

        setSize(700, 600); // default window size
        setLocationRelativeTo(null);

        buttonClose.addActionListener(e -> {
            dispose();
        });
    }

    public void setData(String name, String inventorynumber, String address) {
        if (labelNameConfirmation != null) {
            labelNameConfirmation.setText("Customer Name: " + name);
        }

        if (labelInventoryNumberConfirmation != null) {
            labelInventoryNumberConfirmation.setText("Inventory Number: " + inventorynumber);
        }

        if (labelAddressConfirmation != null) {
            labelAddressConfirmation.setText("Zip Code: " + address);
        }
    }

    private JPanel mainPanel;
    private JLabel labelPSuccess;
    private JLabel labelInventoryNumberConfirmation;
    private JLabel labelAddressConfirmation;
    private JButton buttonClose;
    private JPanel carPanel;
    private JLabel labelNameConfirmation;

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

    public String getInventorynumber() {
        return inventorynumber;
    }

    public String getAddress() {
        return address;
    }

    public JButton getButtonClose() {
        return buttonClose;
    }

    public JPanel getCarPanel() {
        return carPanel;
    }
}
