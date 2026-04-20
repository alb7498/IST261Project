package IST261Project.Frontend;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.FilterInventory;
import IST261Project.Backend.InventoryManager;
import IST261Project.Backend.InventoryStorageHandler;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AppController {

    private final AppView appView;
    private final InventoryManager inventoryManager;

    public AppController(AppView appView, InventoryManager inventoryManager) {
        this.appView = appView;
        this.inventoryManager = inventoryManager;
        wireEvents();
    }

    private void wireEvents() {
        appView.getButtonAdd().addActionListener(e -> handleAddCar());
        appView.getButtonRemove().addActionListener(e -> handleRemoveCar());
        appView.getButtonPurchase().addActionListener(e-> showPurchasePage());
        appView.getButtonFilter().addActionListener(e ->filterInventoryPage());
        appView.getButtonResetView().addActionListener(e -> resetView());
    }

    private void showPurchasePage(){
        PurchasePage purchasePage = new PurchasePage();
        purchasePage.setVisible(true);
    }
    private void handleAddCar() {
        List<String> makes = makesFromInventory(inventoryManager.getInventory());
        int nextInventoryNumber = nextInventoryNumber(inventoryManager.getInventory());

        CarObject newCar = AddCarDialog.showDialog(appView.getMainPanel(), makes, nextInventoryNumber);
        if (newCar == null) {
            return;
        }

        inventoryManager.addCar(newCar);
        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
        displayInventory(inventoryManager.getInventory());
        JOptionPane.showMessageDialog(appView.getMainPanel(), "Added car #" + newCar.getInventoryNumber());
    }

    private void handleRemoveCar() {
        Integer removedInventoryNumber = RemoveCarForm.showDialog(appView.getMainPanel(), inventoryManager);
        if (removedInventoryNumber == null) return;

        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
        displayInventory(inventoryManager.getInventory());
        JOptionPane.showMessageDialog(appView.getMainPanel(), "Removed car #" + removedInventoryNumber);
    }

    private int nextInventoryNumber(Map<Integer, CarObject> inventory) {
        int max = 1000;
        for (Integer inventoryNumber : inventory.keySet()) {
            if (inventoryNumber > max) {
                max = inventoryNumber;
            }
        }
        return max + 1;
    }

    //Get all makes from the inventory
    private List<String> makesFromInventory(Map<Integer, CarObject> inventory) {
        List<String> makes = new ArrayList<>();
        for (CarObject car : inventory.values()) {
            String make = car.getMake();
            if (make != null && !make.trim().isEmpty() && !makes.contains(make)) {
                makes.add(make);
            }
        }
        Collections.sort(makes);
        return makes;
    }

    //Get all colors from the inventory
    private List<String> colorsFromInventory(Map<Integer, CarObject> inventory) {
        List<String> colors = new ArrayList<>();
        for (CarObject car : inventory.values()) {
            String make = car.getColor();
            if (make != null && !make.trim().isEmpty() && !colors.contains(make)) {
                colors.add(make);
            }
        }
        Collections.sort(colors);
        return colors;
    }

    //Filter Inventory
    private void filterInventoryPage(){
        FilterPage filterPage = new FilterPage();
        filterPage.setVisible(true);

        List<String> makes = makesFromInventory(inventoryManager.getInventory());
        List<String> colors = colorsFromInventory(inventoryManager.getInventory());

        //Populate Make CB
        filterPage.getMakeCB().removeAllItems();
        filterPage.getMakeCB().addItem("Select Make");
        for (String make : makes) {
            filterPage.getMakeCB().addItem(make);
        }

        //Populate Color CB
        filterPage.getColorCB().removeAllItems();
        filterPage.getColorCB().addItem("Select Color");
        for (String color : colors) {
            filterPage.getColorCB().addItem(color);
        }

        filterPage.getApplybutton().addActionListener(e -> {
            FilterInventory filterInventory = new FilterInventory();
            Map<Integer, CarObject> inventory  = inventoryManager.getInventory();

            // Year Validation
            int currentYear = java.time.Year.now().getValue();

            String minYearText = filterPage.getMinYearTF().getText().trim();
            String maxYearText = filterPage.getMaxYearTF().getText().trim();

            Integer minYear = null;
            Integer maxYear = null;

            try {
                if (!minYearText.isEmpty() && !minYearText.equals("Minimum:")) {
                    minYear = Integer.parseInt(minYearText);
                    if (minYear < 1950 || minYear > currentYear) {
                        JOptionPane.showMessageDialog(null, "Min year must be between 1950 and " + currentYear);
                        return;
                    }
                }

                if (!maxYearText.isEmpty() && !maxYearText.equals("Maximum:")) {
                    maxYear = Integer.parseInt(maxYearText);
                    if (maxYear < 1950 || maxYear > currentYear) {
                        JOptionPane.showMessageDialog(null, "Max year must be between 1950 and " + currentYear);
                        return;
                    }
                }

                if (minYear != null && maxYear != null && minYear > maxYear) {
                    JOptionPane.showMessageDialog(null, "Min year cannot be greater than max year");
                    return;
                }

            } catch (NumberFormatException temp) {
                JOptionPane.showMessageDialog(null, "Year must be a valid number");
                return;
            }

            // Price Validation
            String minPriceText = filterPage.getMinPriceTF().getText().trim();
            String maxPriceText = filterPage.getMaxPriceTF().getText().trim();

            Double minPrice = null;
            Double maxPrice = null;

            try {
                if (!minPriceText.isEmpty() && !minPriceText.equals("Minimum:")) {
                    minPrice = Double.parseDouble(minPriceText);
                    if (minPrice < 0) {
                        JOptionPane.showMessageDialog(null, "Min price cannot be below $0");
                        return;
                    }
                }

                if (!maxPriceText.isEmpty() && !maxPriceText.equals("Maximum:")) {
                    maxPrice = Double.parseDouble(maxPriceText);
                    if (maxPrice < 0) {
                        JOptionPane.showMessageDialog(null, "Max price cannot be below $0");
                        return;
                    }
                }

                if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                    JOptionPane.showMessageDialog(null, "Min price cannot be greater than max price");
                    return;
                }

            } catch (NumberFormatException temp) {
                JOptionPane.showMessageDialog(null, "Price must be a valid number");
                return;
            }

            String selectedMake = (String) filterPage.getMakeCB().getSelectedItem();
            String selectedColor = (String) filterPage.getColorCB().getSelectedItem();

            if (selectedMake != null && !selectedMake.equals("Select Make")) {
                inventory = filterInventory.filterByMake(inventory, selectedMake);
            }

            if (selectedColor != null && !selectedColor.equals("Select Color")) {
                inventory = filterInventory.filterByColor(inventory, selectedColor);
            }

            if (minYear != null) {
                inventory = filterInventory.filterByMinYear(inventory, minYear);
            }

            if (maxYear != null) {
                inventory = filterInventory.filterByMaxYear(inventory, maxYear);
            }

            if (minPrice != null) {
                inventory = filterInventory.filterByMinPrice(inventory, minPrice);
            }

            if (maxPrice != null) {
                inventory = filterInventory.filterByMaxPrice(inventory, maxPrice);
            }

            if (inventory.isEmpty()){
                JOptionPane.showMessageDialog(null, "There are no cars matching this filter.");
            }else {
                displayInventory(inventory);
                filterPage.dispose();
            }
        });
    }

    //Reset Inventory View
    private void resetView(){
        displayInventory(inventoryManager.getInventory());
    }

    // front end main page GUI inventory scroll pane
    public void displayInventory(Map<Integer, CarObject> inventory){
        JPanel inventoryPanel = appView.getInventoryPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.removeAll();
        // header row
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.setBorder(BorderFactory.createLineBorder(Color.black));

        header.add(new JLabel("ID"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Make"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Model"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Year"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Miles"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Body Style"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Gas Mileage"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Engine"));
        header.add(Box.createRigidArea(new Dimension(10,0)));
        header.add(new JLabel("Price"));

        inventoryPanel.add(header);
        inventoryPanel.add(Box.createRigidArea(new Dimension(0,5))); //spacing


        // add card for each car
        for (CarObject car : inventory.values()){
            JPanel carCard = new JPanel();
            carCard.setLayout(new BoxLayout(carCard, BoxLayout.X_AXIS));
            carCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            carCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            carCard.add(new JLabel((String.valueOf(car.getInventoryNumber()))));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(car.getMake()));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(car.getModel()));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(String.valueOf(car.getYear())));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(String.valueOf(car.getMileage())));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(car.getBodyStyle()));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(String.valueOf(car.getGasMileage())));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel(String.valueOf(car.getEngineSize())));
            carCard.add(Box.createRigidArea(new Dimension(10,0)));
            carCard.add(new JLabel("$" + car.getPrice()));

            inventoryPanel.add(carCard);
            inventoryPanel.add(Box.createRigidArea(new Dimension(0,5))); //spacing
        }
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }
}
