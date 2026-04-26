package IST261Project.Backend;

public class CarObject extends InventoryObject{
    private String make;
    private String model;
    private String  bodyStyle;
    private String color;
    private int year;
    private int mileage;
    private double gasMileage;
    private String engineSize;
    private double price;
    private String imagePath;

    public CarObject(int inventoryNumber, String make, String model, String bodyStyle, String color,
                     int year, int mileage, double gasMileage, String engineSize, double price,
                     String imagePath) {
        super(inventoryNumber);
        this.make = make;
        this.model = model;
        this.bodyStyle = bodyStyle;
        this.color = color;
        this.year = year;
        this.mileage = mileage;
        this.gasMileage = gasMileage;
        this.engineSize = engineSize;
        this.price = price;
        this.imagePath = imagePath;
    }

    //Getter Methods
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public String getBodyStyle() {
        return bodyStyle;
    }
    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }
    public int getMileage() {
        return mileage;
    }
    public double getGasMileage() {
        return gasMileage;
    }
    public String getEngineSize() {
        return engineSize;
    }
    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    //Setter Methods (Only set what makes sense)
    public void setPrice(double price) {
        if(price >=0)
            this.price = price;
    }
    public void setMileage(int mileage) {
        if(mileage >= 0)
            this.mileage = mileage;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "CarObject{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", bodyStyle='" + bodyStyle + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", gasMileage=" + gasMileage +
                ", engineSize=" + engineSize +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
