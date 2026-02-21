package IST261Project;

import IST261Project.Backend.InventoryObject;

public class Main {


    public static void main(String[] args) {
        InventoryObject car = new InventoryObject(12000, 4.8, 13.5, 12342, 2018,
                "Red", "SUV", "Terrain", "GMC", "000001");

        System.out.println(car.toString());

        car.setPrice(10000);

        System.out.println(car.toString());

        car.setMileage(12500);

        System.out.println(car.toString());


    }
}
