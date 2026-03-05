package IST261Project.Frontend;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.InventoryManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class RemoveCarForm extends JDialog {
    private JPanel mainPanel;
    private JButton cancelButton;
    private JButton removeButton;
    private JLabel inventoryNumberLabel;
    private JTextField inventoryNumberTF;
    private JLabel statusLabel;

    private final InventoryManager inventoryManager;
    private CarObject foundCar;
    private Integer foundInventoryNumber;
    private boolean removed;

    public RemoveCarForm(Window owner, InventoryManager inventoryManager) {
        super(owner, "Remove Car", Dialog.ModalityType.APPLICATION_MODAL);
        this.inventoryManager = inventoryManager;

        setContentPane(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        removeButton.setEnabled(false);
        statusLabel.setText("Enter a valid inventory number.");

        wireEvents();

        pack();
        setLocationRelativeTo(owner);
    }

    public static Integer showDialog(Component parent, InventoryManager inventoryManager) {
        Window owner = SwingUtilities.getWindowAncestor(parent);
        RemoveCarForm dialog = new RemoveCarForm(owner, inventoryManager);
        dialog.setVisible(true);
        return dialog.removed ? dialog.foundInventoryNumber : null;
    }

    private void wireEvents() {
        inventoryNumberTF.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {validateInput();}
            public void removeUpdate(DocumentEvent e) {validateInput();}
            public void changedUpdate(DocumentEvent e) {validateInput();}
        });

        cancelButton.addActionListener(e -> dispose());
        removeButton.addActionListener(e -> confirmRemove());
    }

    private void validateInput() {
        String text = inventoryNumberTF.getText().trim();

        try {
            int inventoryNumber = Integer.parseInt(text);
            CarObject car = inventoryManager.getCar(inventoryNumber);

            if (car == null) {
                foundCar = null;
                foundInventoryNumber = null;
                removeButton.setEnabled(false);
                statusLabel.setText("No car found for inventory #" + inventoryNumber);
                return;
            }

            foundCar = car;
            foundInventoryNumber = inventoryNumber;
            removeButton.setEnabled(true);
            statusLabel.setText("Found: " + car.getYear() + " " + car.getMake() + " " + car.getModel());
        } catch (NumberFormatException ex) {
            foundCar = null;
            foundInventoryNumber = null;
            removeButton.setEnabled(false);
            statusLabel.setText("Enter a valid inventory number.");
        }
    }

    private void confirmRemove() {
        if (foundCar == null || foundInventoryNumber == null) {
            return;
        }

        String msg = "Are you sure you want to remove this car?\n"
                + foundCar.getYear() + " " + foundCar.getMake() + " " + foundCar.getModel()
                + " (#" + foundInventoryNumber + ")";

        int choice = JOptionPane.showConfirmDialog(this, msg, "Confirm Remove", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (inventoryManager.getCar(foundInventoryNumber) == null) {
            JOptionPane.showMessageDialog(this, "That car was already removed.");
            removeButton.setEnabled(false);
            return;
        }

        inventoryManager.removeCar(foundInventoryNumber);
        removed = true;
        dispose();
    }
}
