package IST261Project.Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class InventoryStorageHandler {

    private static final String FILE_PATH = "StoredInventory.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Save inventory to JSON file
    public static void saveInventory(Map<Integer, CarObject> inventory) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(inventory, writer);
            System.out.println("Inventory saved to JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load inventory from JSON file
    public static Map<Integer, CarObject> loadInventory() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<Integer, CarObject>>() {}.getType();
            Map<Integer, CarObject> inventory = gson.fromJson(reader, type);

            if (inventory == null) {
                return new HashMap<>();
            }

            for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
                Integer key = entry.getKey();
                CarObject car = entry.getValue();

                if (car != null) {
                    car.setInventoryNumber(key);
                }
            }

            return inventory;

        } catch (IOException e) {
            System.out.println("No inventory file found. Starting with empty inventory.");
            return new HashMap<>();
        }
    }
}