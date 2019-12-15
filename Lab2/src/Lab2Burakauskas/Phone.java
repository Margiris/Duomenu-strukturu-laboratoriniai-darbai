package Lab2Burakauskas;

public class Phone implements Comparable<Phone>{
    public String getName() {
        return Name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String Name;
    private double price;

    public Phone()
    {

    }

    @Override
    public int compareTo(Phone o) {
        return Double.compare(this.price, o.getPrice());
    }

    public double getPrice()
    {
        return price;
    }

}
