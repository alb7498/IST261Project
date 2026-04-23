package IST261Project;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.InventoryManager;
import IST261Project.Backend.InventoryStorageHandler;
import IST261Project.Frontend.AppController;
import IST261Project.Frontend.AppView;
import IST261Project.Frontend.PurchasePage;

import javax.swing.*;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.d3d", "false");
        System.setProperty("sun.java2d.opengl", "false");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Map<Integer, CarObject> storedInventory = InventoryStorageHandler.loadInventory();

           InventoryManager inventoryManager = new InventoryManager();
            for (CarObject car : storedInventory.values()) {
                inventoryManager.addCar(car);
            }

            AppView appView = new AppView();
            PurchasePage purchasePage = new PurchasePage();
            AppController controller = new AppController(appView, inventoryManager);
            controller.displayInventory(inventoryManager.getInventory());

            JFrame frame = new JFrame("Dealership Inventory");
            frame.setContentPane(appView.getMainPanel());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}