package IST261Project.Backend;

import java.util.HashMap;
import java.util.Map;

public class FilterInventory {

    //Filter by Make
    public Map<Integer, CarObject> filterByMake(Map<Integer, CarObject> inventory, String selectedMake) {
        Map<Integer, CarObject> result = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            CarObject car = entry.getValue();

            if (car.getMake() != null &&
                    car.getMake().equalsIgnoreCase(selectedMake)) {
                result.put(entry.getKey(), car);
            }
        }
        return result;
    }

    //Filter by color
    public Map<Integer, CarObject> filterByColor(Map<Integer, CarObject> inventory, String selectedColor) {
        Map<Integer, CarObject> result = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            CarObject car = entry.getValue();

            if (car.getColor() != null &&
                    car.getColor().equalsIgnoreCase(selectedColor)) {
                result.put(entry.getKey(), car);
            }
        }

        return result;
    }

    //Filter by Min Year
    public Map<Integer, CarObject> filterByMinYear(Map<Integer, CarObject> inventory, int minYear) {
        Map<Integer, CarObject> result = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            if (entry.getValue().getYear() >= minYear) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    //Filter by Max year
    public Map<Integer, CarObject> filterByMaxYear(Map<Integer, CarObject> inventory, int maxYear) {
        Map<Integer, CarObject> result = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            if (entry.getValue().getYear() <= maxYear) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    //Filter by Min price
    public Map<Integer, CarObject> filterByMinPrice(Map<Integer, CarObject> inventory, double minPrice) {
        Map<Integer, CarObject> result = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            if (entry.getValue().getPrice() >= minPrice) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    //Filter by Max price
    public Map<Integer, CarObject> filterByMaxPrice(Map<Integer, CarObject> inventory, double maxPrice) {
        Map<Integer, CarObject> result = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            if (entry.getValue().getPrice() <= maxPrice) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
}
