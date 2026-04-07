package IST261Project.Backend;

import java.util.Map;

public class InventoryManager {

    private Map<Integer, CarObject> inventory;

    public InventoryManager() {
        inventory = InventoryStorageHandler.loadInventory();
    }

    public void addCar(CarObject car) {
        inventory.put(car.getInventoryNumber(), car);
    }

    public CarObject getCar(int inventoryNumber) {
        return inventory.get(inventoryNumber);
    }

    public void removeCar(int inventoryNumber) {
        inventory.remove(inventoryNumber);
    }

    public Map<Integer, CarObject> getInventory() {
        return inventory;
    }

    public void saveInventory() {
        InventoryStorageHandler.saveInventory(inventory);
    }
}