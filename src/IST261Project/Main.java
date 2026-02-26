package IST261Project;

import IST261Project.Backend.CarObject;
import IST261Project.Backend.InventoryObject;

public class Main {


    public static void main(String[] args) {
        CarObject car = new CarObject(00001, "GMC", "Terrain", "SUV", "Red", 2017, 12343
        , 17.9, 2.4, 12000);

        System.out.println(car.toString());

        car.setPrice(10000);

        System.out.println(car.toString());

        car.setMileage(12500);

        System.out.println(car.toString());


    }
}
