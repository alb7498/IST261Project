package IST261Project.Backend;

public class InventoryObject{
    private int inventoryNumber;

    public InventoryObject(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;

    }

    //Getter Method
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    //Setter Method
    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
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
