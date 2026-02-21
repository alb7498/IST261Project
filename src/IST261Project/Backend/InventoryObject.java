package IST261Project.Backend;

public class InventoryObject extends CarObject{
    private String inventoryNumber;

    public InventoryObject(double price, double engineSize, double gasMileage, int mileage, int year,
                           String color, String bodyStyle, String model, String make, String inventoryNumber) {

        super(price, engineSize, gasMileage, mileage, year, color, bodyStyle, model, make);
        this.inventoryNumber = inventoryNumber;
    }

    //Getter Method
    public String getInventoryNumber() {
        return inventoryNumber;
    }

    //To String

    @Override
    public String toString() {
        return "InventoryObject{" +
                "inventoryNumber='" + inventoryNumber + '\'' +
                super.toString() +
                '}';
    }
}
