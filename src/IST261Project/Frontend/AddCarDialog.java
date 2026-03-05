package IST261Project.Frontend;

import IST261Project.Backend.CarObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AddCarDialog {

    private AddCarDialog() {
    }

    public static CarObject showDialog(Component parent, List<String> makes, int inventoryNumber) {
        List<String> safeMakes = new ArrayList<>(makes == null ? Collections.emptyList() : makes);
        if (safeMakes.isEmpty()) {
            safeMakes.add("Toyota");
            safeMakes.add("Honda");
            safeMakes.add("Ford");
            safeMakes.add("Chevrolet");
        }

        JComboBox<String> makeCombo = new JComboBox<>(safeMakes.toArray(new String[0]));
        JTextField modelField = new JTextField();
        JTextField bodyStyleField = new JTextField();
        JTextField colorField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField mileageField = new JTextField();
        JTextField gasMileageField = new JTextField();
        JTextField engineSizeField = new JTextField();
        JTextField priceField = new JTextField();

        JLabel selectedMakeValue = new JLabel((String) makeCombo.getSelectedItem());
        makeCombo.addActionListener(e -> selectedMakeValue.setText((String) makeCombo.getSelectedItem()));

        JPanel stepMake = new JPanel(new GridLayout(0, 1, 8, 8));
        stepMake.add(new JLabel("Select car make:"));
        stepMake.add(makeCombo);

        JPanel stepDetails = new JPanel(new GridLayout(0, 2, 8, 8));
        stepDetails.add(new JLabel("Make:"));
        stepDetails.add(selectedMakeValue);
        stepDetails.add(new JLabel("Model:"));
        stepDetails.add(modelField);
        stepDetails.add(new JLabel("Body Style:"));
        stepDetails.add(bodyStyleField);
        stepDetails.add(new JLabel("Color:"));
        stepDetails.add(colorField);
        stepDetails.add(new JLabel("Year:"));
        stepDetails.add(yearField);
        stepDetails.add(new JLabel("Mileage:"));
        stepDetails.add(mileageField);
        stepDetails.add(new JLabel("Gas Mileage:"));
        stepDetails.add(gasMileageField);
        stepDetails.add(new JLabel("Engine Size:"));
        stepDetails.add(engineSizeField);
        stepDetails.add(new JLabel("Price:"));
        stepDetails.add(priceField);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(stepMake, "MAKE");
        cardPanel.add(stepDetails, "DETAILS");

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Add New Car", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(cardPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        backButton.setEnabled(false);
        saveButton.setEnabled(false);

        final CarObject[] result = new CarObject[1];

        nextButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "DETAILS");
            backButton.setEnabled(true);
            nextButton.setEnabled(false);
            saveButton.setEnabled(true);
        });

        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "MAKE");
            backButton.setEnabled(false);
            nextButton.setEnabled(true);
            saveButton.setEnabled(false);
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        saveButton.addActionListener(e -> {
            try {
                String make = ((String) makeCombo.getSelectedItem()).trim();
                String model = modelField.getText().trim();
                String bodyStyle = bodyStyleField.getText().trim();
                String color = colorField.getText().trim();

                int year = Integer.parseInt(yearField.getText().trim());
                int mileage = Integer.parseInt(mileageField.getText().trim());
                double gasMileage = Double.parseDouble(gasMileageField.getText().trim());
                double engineSize = Double.parseDouble(engineSizeField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                if (model.isEmpty() || bodyStyle.isEmpty() || color.isEmpty()) {
                    throw new IllegalArgumentException("Model, body style, and color are required.");
                }

                result[0] = new CarObject(
                        inventoryNumber,
                        make,
                        model,
                        bodyStyle,
                        color,
                        year,
                        mileage,
                        gasMileage,
                        engineSize,
                        price
                );
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Year, mileage, gas mileage, engine size, and price must be valid numbers.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result[0];
    }
}


