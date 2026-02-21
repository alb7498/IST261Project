package IST261Project.Backend;

public class CarObject {
    private String make;
    private String model;
    private String bodyStyle;
    private String color;
    private int year;
    private int mileage;
    private double gasMileage;
    private double engineSize;
    private double price;

    public CarObject(double price, double engineSize, double gasMileage, int mileage, int year, String color, String bodyStyle, String model, String make) {
        this.price = price;
        this.engineSize = engineSize;
        this.gasMileage = gasMileage;
        this.mileage = mileage;
        this.year = year;
        this.color = color;
        this.bodyStyle = bodyStyle;
        this.model = model;
        this.make = make;
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
    public double getEngineSize() {
        return engineSize;
    }
    public double getPrice() {
        return price;
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
                '}';
    }
}
