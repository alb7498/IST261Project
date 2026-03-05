package IST261Project.Frontend;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.InventoryManager;
import IST261Project.Backend.InventoryStorageHandler;

import javax.swing.*;
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
        JOptionPane.showMessageDialog(appView.getMainFrame(), "Added car #" + newCar.getInventoryNumber());
    }

    private void handleRemoveCar() {
        Integer removedInventoryNumber = RemoveCarForm.showDialog(appView.getMainFrame(), inventoryManager);
        if (removedInventoryNumber == null) return;

        InventoryStorageHandler.saveInventory(inventoryManager.getInventory());
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
}
