package IST261Project.Frontend;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.FilterInventory;
import IST261Project.Backend.InventoryManager;
import IST261Project.Backend.InventoryStorageHandler;


import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.Year;
import java.util.*;
import java.util.List;

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
        appView.getButtonFilter().addActionListener(e -> filterInventoryPage());
        appView.getButtonResetView().addActionListener(e -> resetView());
        appView.getInventoryPanel().setLayout(new BoxLayout(appView.getInventoryPanel(),BoxLayout.Y_AXIS));
    }

    private void showPurchasePage(){
        PurchasePage purchasePage = new PurchasePage();
        purchasePage.setVisible(true);
    }
    private void handleAddCar() {
        List<String> makes = makesFromInventory(inventoryManager.getInventory());
        int nextInventoryNumber = nextInventoryNumber(inventoryManager.getInventory());

        CarObject newCar = AddCarDialog.showDialog(appView.getMainPagePanel(), makes, nextInventoryNumber);
        if (newCar == null) {
            return;
        }

        inventoryManager.addCar(newCar);
        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
        displayInventory(inventoryManager.getInventory());
        JOptionPane.showMessageDialog(appView.getMainPagePanel(), "Added car #" + newCar.getInventoryNumber());
    }

    private void handleRemoveCar() {
        Integer removedInventoryNumber = RemoveCarForm.showDialog(appView.getMainPagePanel(), inventoryManager);
        if (removedInventoryNumber == null) return;

        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
        displayInventory(inventoryManager.getInventory());
        JOptionPane.showMessageDialog(appView.getMainPagePanel(), "Removed car #" + removedInventoryNumber);
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
            int currentYear = Year.now().getValue();

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
        inventoryPanel.removeAll();

        for (CarObject car : inventory.values()){
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(450, 180)); // width, height
            card.setMaximumSize(new Dimension(450, 180));   // prevents stretching
            card.setMinimumSize(new Dimension(450, 180));
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            String path = car.getImagePath();

            System.out.println("PATH: " + path);

            URL url = getClass().getResource(path);
            System.out.println("URL:" + url);

            JLabel imageLabel;

            if (url != null) {
                ImageIcon carImage = new ImageIcon(url);

                Image scaled = carImage.getImage()
                        .getScaledInstance(150, 120, Image.SCALE_SMOOTH);

                imageLabel = new JLabel(new ImageIcon(scaled));
            } else {
                System.out.println("Missing Image:" + car.getImagePath());
                imageLabel = new JLabel("No Image");
            }


            //car info
            JTextArea info = new JTextArea(
                    car.getYear() + " " + car.getMake() + " " + car.getModel() + "\n" +
                            "Price: $" + car.getPrice() + "\n" +
                            "Miles: " + car.getMileage() + "\n" +
                            "Gas Mileage: " + car.getGasMileage() + "\n" +
                            "Color: " + car.getColor() + "\n" +
                            "Engine Size: " + car.getEngineSize()
            );
            info.setEditable(false);

            //purchase button
            JButton purchaseBtn = new JButton("Purchase");
            purchaseBtn.addActionListener(e-> {
                PurchasePage purchasePage = new PurchasePage();
                //sets the car ID in the text field
                purchasePage.setCarID(car.getInventoryNumber());
                purchasePage.setVisible(true);
            });

            //add to card
            card.add(imageLabel, BorderLayout.EAST);
            card.add(info, BorderLayout.CENTER);
            card.add(purchaseBtn, BorderLayout.SOUTH);

            //add card to main panel
            inventoryPanel.add(card);
        }
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }
}