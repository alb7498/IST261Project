package IST261Project;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.InventoryManager;
import IST261Project.Backend.InventoryObject;
import IST261Project.Backend.InventoryStorageHandler;

import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        // Create the map
        Map<Integer, CarObject> inventory = InventoryStorageHandler.loadInventory();


        // Print full inventory
        System.out.println("FULL INVENTORY:");
        for (CarObject car : inventory.values()) {
            System.out.println(car);
        }

        // Retrieve one car by key
        System.out.println("\nLOOKUP INVENTORY NUMBER 1001:");
        CarObject foundCar = inventory.get(1010);
        System.out.println(foundCar);

        // Update price test
        System.out.println("\nUPDATING PRICE...");
        foundCar.setPrice(18500);

        System.out.println("UPDATED CAR:");
        System.out.println(foundCar);

        //InventoryStorageHandler.saveInventory(inventory);


       /*
       CarObject car = new CarObject(00001, "GMC", "Terrain", "SUV", "Red", 2017, 12343
        , 17.9, 2.4, 12000);

        System.out.println(car.toString());

        car.setPrice(10000);

        System.out.println(car.toString());

        car.setMileage(12500);

        System.out.println(car.toString());
        */
    }
}
