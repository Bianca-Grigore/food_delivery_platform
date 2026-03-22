import java.util.Objects;

public abstract class MenuItem implements Comparable<MenuItem> {
    protected String name;
    protected double calories;
    protected double price;
    protected int id;
    private static int id_num=0;
    protected String description;
    protected boolean isAvailable;
    protected int estimatedTime;

    public MenuItem(String name, double calories, double price, String description, int estimatedTime) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.id = id_num;
        id_num+=1;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.isAvailable=true;
    }

    public void applyDiscount(double p){
        if (p > 0 && p < 100){
            double discountAmount = price * (p / 100);
            price -= discountAmount;
            System.out.println("Discount applied successfully. New price: " + price);
        }
        else
            System.out.println("Invalid discount percentage. Please enter a value between 0 and 100.");
        }

    @Override
    public String toString(){
        return "MenuItem " + id + ", name: " + name + ", calories: " + calories + ", price: " + price + ", description: " + description + ", estimated time: " + estimatedTime + " minutes, availability: " + (isAvailable ? "Available" : "Not Available");
    }

    @Override
    public int compareTo(MenuItem other){
        int pc = Double.compare(this.price, other.price);
        if(pc == 0){
            return this.name.compareTo(other.name);
        }
        return pc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
