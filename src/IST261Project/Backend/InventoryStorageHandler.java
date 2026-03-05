package IST261Project.Backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InventoryStorageHandler {

    private static final String FILE_PATH = "StoredInventory.JSON";

    public static void saveInventory(Map<Integer, CarObject> inventory) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            writer.write("{\n");

            int counter = 0;
            int size = inventory.size();

            for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {

                CarObject car = entry.getValue();

                writer.write("  \"" + entry.getKey() + "\": {\n");
                writer.write("    \"make\": \"" + car.getMake() + "\",\n");
                writer.write("    \"model\": \"" + car.getModel() + "\",\n");
                writer.write("    \"bodyStyle\": \"" + car.getBodyStyle() + "\",\n");
                writer.write("    \"color\": \"" + car.getColor() + "\",\n");
                writer.write("    \"year\": " + car.getYear() + ",\n");
                writer.write("    \"mileage\": " + car.getMileage() + ",\n");
                writer.write("    \"gasMileage\": " + car.getGasMileage() + ",\n");
                writer.write("    \"engineSize\": " + car.getEngineSize() + ",\n");
                writer.write("    \"price\": " + car.getPrice() + "\n");
                writer.write("  }");

                counter++;
                if(counter < size)
                    writer.write(",");

                writer.write("\n");
            }

            writer.write("}");

            System.out.println("Inventory saved to JSON.");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, CarObject> loadInventory() {

        Map<Integer, CarObject> inventory = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            int inventoryNumber = 0;

            String make = "", model = "", bodyStyle = "", color = "";
            int year = 0, mileage = 0;
            double gasMileage = 0, engineSize = 0, price = 0;

            while((line = reader.readLine()) != null){

                line = line.trim();

                if(line.startsWith("\"") && line.contains("\": {")){
                    inventoryNumber = Integer.parseInt(
                            line.substring(1, line.indexOf("\":")));
                }

                else if(line.contains("\"make\"")){
                    make = line.split(":")[1].replace("\"","").replace(",","").trim();
                }

                else if(line.contains("\"model\"")){
                    model = line.split(":")[1].replace("\"","").replace(",","").trim();
                }

                else if(line.contains("\"bodyStyle\"")){
                    bodyStyle = line.split(":")[1].replace("\"","").replace(",","").trim();
                }

                else if(line.contains("\"color\"")){
                    color = line.split(":")[1].replace("\"","").replace(",","").trim();
                }

                else if(line.contains("\"year\"")){
                    year = Integer.parseInt(line.split(":")[1].replace(",","").trim());
                }

                else if(line.contains("\"mileage\"")){
                    mileage = Integer.parseInt(line.split(":")[1].replace(",","").trim());
                }

                else if(line.contains("\"gasMileage\"")){
                    gasMileage = Double.parseDouble(line.split(":")[1].replace(",","").trim());
                }

                else if(line.contains("\"engineSize\"")){
                    engineSize = Double.parseDouble(line.split(":")[1].replace(",","").trim());
                }

                else if(line.contains("\"price\"")){
                    price = Double.parseDouble(line.split(":")[1].replace(",","").trim());

                    // When we reach price, we have all fields
                    CarObject car = new CarObject(
                            inventoryNumber, make, model, bodyStyle, color,
                            year, mileage, gasMileage, engineSize, price);

                    inventory.put(inventoryNumber, car);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return inventory;
    }
}
