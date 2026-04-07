package IST261Project;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.InventoryManager;
import IST261Project.Backend.InventoryStorageHandler;
import IST261Project.Frontend.AppController;
import IST261Project.Frontend.AppView;

import javax.swing.*;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<Integer, CarObject> storedInventory = InventoryStorageHandler.loadInventory();

            InventoryManager inventoryManager = new InventoryManager();
            for (CarObject car : storedInventory.values()) {
                inventoryManager.addCar(car);
            }

            AppView appView = new AppView();
            AppController controller = new AppController(appView, inventoryManager);
            controller.displayInventory();

            JFrame frame = new JFrame("Dealership Inventory");
            frame.setContentPane(appView.getMainFrame());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
