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
        appView.getButtonFilter().addActionListener(e ->filterInventory());
        appView.getButtonResetView().addActionListener(e -> resetView());
    }

    private void showPurchasePage(){
        PurchasePage purchasePage = new PurchasePage();
        purchasePage.setVisible(true);
    }
    private void handleAddCar() {
        List<String> makes = makesFromInventory(inventoryManager.getInventory());
        int nextInventoryNumber = nextInventoryNumber(inventoryManager.getInventory());

        CarObject newCar = AddCarDialog.showDialog(appView.getMainFrame(), makes, nextInventoryNumber);
        if (newCar == null) {
            return;
        }

        inventoryManager.addCar(newCar);
        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
        displayInventory(inventoryManager.getInventory());
        JOptionPane.showMessageDialog(appView.getMainFrame(), "Added car #" + newCar.getInventoryNumber());
    }

    private void handleRemoveCar() {
        Integer removedInventoryNumber = RemoveCarForm.showDialog(appView.getMainFrame(), inventoryManager);
        if (removedInventoryNumber == null) return;

        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
        displayInventory(inventoryManager.getInventory());
        JOptionPane.showMessageDialog(appView.getMainFrame(), "Removed car #" + removedInventoryNumber);
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
        displayInventory(FilterInventory.filterByYearRange(inventoryManager.getInventory(), 2016, 2020));
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
