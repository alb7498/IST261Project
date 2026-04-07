package IST261Project.Backend;

import java.util.HashMap;
import java.util.Map;

public class FilterInventory {

    //Filter inventory by year
    public static Map<Integer, CarObject> filterByYearRange(Map<Integer, CarObject> inventory, int minYear, int maxYear) {
        Map<Integer, CarObject> filtered = new HashMap<>();

        for (Map.Entry<Integer, CarObject> entry : inventory.entrySet()) {
            int carYear = entry.getValue().getYear();

            if (carYear >= minYear && carYear <= maxYear) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }

    //More filters to come
}
