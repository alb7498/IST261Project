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
        appView.getButtonFilter().addActionListener(e -> filterInventory());
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

    //Filter Inventory
    private void filterInventory(){
        // displayInventory(FilterInventory.filterByYearRange(inventoryManager.getInventory(), 2016, 2020));
        FilterInventory filter = new FilterInventory();
        Map<Integer, CarObject> result =
                filter.filterByMinYear(inventoryManager.getInventory(), 2016);
        result = filter.filterByMaxYear(result, 2020);
        displayInventory(result);
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
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
            card.add(info, BorderLayout.CENTER);
            card.add(purchaseBtn, BorderLayout.SOUTH);

            //add card to main panel
            inventoryPanel.add(card);
        }
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }
}
