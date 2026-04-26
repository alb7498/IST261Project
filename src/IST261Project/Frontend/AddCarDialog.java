package IST261Project.Frontend;

import IST261Project.Backend.CarObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddCarDialog extends JDialog {
    private static final String OTHER_MAKE_OPTION = "Other";

    private JPanel mainPanel;
    private JComboBox<String> makeComboBox;
    private JLabel otherMakeLabel;
    private JTextField otherMakeField;
    private JTextField modelField;
    private JComboBox<String> bodyStyleComboBox;
    private JComboBox<String> colorComboBox;
    private JComboBox<Integer> yearComboBox;
    private JTextField mileageField;
    private JTextField gasMileageField;
    private JTextField engineSizeField;
    private JTextField priceField;

    private JTextField imagePathField;
    private JButton chooseImageButton;
    private File selectedImageFile;

    private JButton cancelButton;
    private JButton saveButton;
    private JLabel inventoryNumberValueLabel;

    private CarObject createdCar;
    private final int inventoryNumber;

    public AddCarDialog(Window owner, List<String> makes, int inventoryNumber) {
        super(owner, "Add New Car", Dialog.ModalityType.APPLICATION_MODAL);
        this.inventoryNumber = inventoryNumber;

        setContentPane(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        loadMakes(makes);
        loadBodyStyles();
        loadColors();
        loadYears();

        inventoryNumberValueLabel.setText(String.valueOf(inventoryNumber));
        imagePathField.setEditable(false);

        getRootPane().setDefaultButton(saveButton);

        wireEvents();

        pack();
        setLocationRelativeTo(owner);
    }

    public static CarObject showDialog(Component parent, List<String> makes, int inventoryNumber) {
        Window owner = SwingUtilities.getWindowAncestor(parent);
        AddCarDialog dialog = new AddCarDialog(owner, makes, inventoryNumber);
        dialog.setVisible(true);
        return dialog.createdCar;
    }

    private void loadMakes(List<String> makes) {
        List<String> safeMakes = new ArrayList<>(Arrays.asList(
                "Toyota", "Honda", "Ford", "Chevrolet", "Nissan",
                "Hyundai", "Kia", "Jeep", "Subaru", "GMC",
                "Ram", "Dodge", "BMW", "Mercedes-Benz", "Volkswagen",
                "Lexus", "Audi", "Mazda", "Tesla", "Cadillac"
        ));

        if (makes != null) {
            for (String make : makes) {
                if (make != null && !make.trim().isEmpty() && !safeMakes.contains(make.trim())) {
                    safeMakes.add(make.trim());
                }
            }
        }

        Collections.sort(safeMakes);
        safeMakes.add(OTHER_MAKE_OPTION);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String make : safeMakes) {
            model.addElement(make);
        }

        makeComboBox.setModel(model);
        otherMakeLabel.setVisible(false);
        otherMakeField.setVisible(false);
        otherMakeField.setText("");
    }

    private void loadBodyStyles() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (String bodyStyle : Arrays.asList(
                "Sedan", "SUV", "Truck", "Coupe",
                "Hatchback", "Van", "Convertible", "Wagon"
        )) {
            model.addElement(bodyStyle);
        }

        bodyStyleComboBox.setModel(model);
    }

    private void loadColors() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (String color : Arrays.asList(
                "Black", "White", "Gray", "Silver", "Blue",
                "Red", "Green", "Brown", "Purple", "Pink"
        )) {
            model.addElement(color);
        }

        colorComboBox.setModel(model);
    }

    private void loadYears() {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();

        int currentYear = Year.now().getValue();

        for (int year = currentYear + 1; year >= 1990; year--) {
            model.addElement(year);
        }

        yearComboBox.setModel(model);
    }

    private void wireEvents() {
        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveCar());
        makeComboBox.addActionListener(e -> toggleOtherMakeField());
        chooseImageButton.addActionListener(e -> chooseImageFile());
    }

    private void chooseImageFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Car Image");

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = chooser.getSelectedFile();
            imagePathField.setText(selectedImageFile.getName());
        }
    }

    private String saveImageToFolder() throws IOException {
        if (selectedImageFile == null) {
            return "";
        }

        File imageFolder = new File("src/IST261Project/resources/images");

        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        String fileName = inventoryNumber + "_" + selectedImageFile.getName();
        File destinationFile = new File(imageFolder, fileName);

        Files.copy(
                selectedImageFile.toPath(),
                destinationFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

        return "/IST261Project/resources/images/" + fileName;
    }

    private void saveCar() {
        try {
            String makeSelection = (String) makeComboBox.getSelectedItem();

            String make = OTHER_MAKE_OPTION.equals(makeSelection)
                    ? otherMakeField.getText().trim()
                    : makeSelection.trim();

            String model = modelField.getText().trim();
            String bodyStyle = ((String) bodyStyleComboBox.getSelectedItem()).trim();
            String color = ((String) colorComboBox.getSelectedItem()).trim();

            int year = (Integer) yearComboBox.getSelectedItem();
            int mileage = Integer.parseInt(mileageField.getText().trim());
            double gasMileage = Double.parseDouble(gasMileageField.getText().trim());
            String engineSize = engineSizeField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            if (make.isEmpty()) {
                throw new IllegalArgumentException("Make is required.");
            }

            if (model.isEmpty()) {
                throw new IllegalArgumentException("Model is required.");
            }

            String imagePath = saveImageToFolder();

            createdCar = new CarObject(
                    inventoryNumber,
                    make,
                    model,
                    bodyStyle,
                    color,
                    year,
                    mileage,
                    gasMileage,
                    engineSize,
                    price,
                    imagePath
            );

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Mileage, gas mileage, engine size, and price must be valid numbers.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not save image file.");
        }
    }

    private void toggleOtherMakeField() {
        boolean showOtherField = OTHER_MAKE_OPTION.equals(makeComboBox.getSelectedItem());

        otherMakeLabel.setVisible(showOtherField);
        otherMakeField.setVisible(showOtherField);

        if (!showOtherField) {
            otherMakeField.setText("");
        }

        mainPanel.revalidate();
        mainPanel.repaint();
        pack();
    }
}