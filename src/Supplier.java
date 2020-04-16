import java.io.*;

public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    private Manufacturer m;
    private double price;

    public Supplier(Manufacturer m, double p) {
        this.m = m;
        this.price = p;

    }

    public String getId() {
        return m.getId();
    }

    public String supplierName() {
        return m.getName();
    }

    public String supplierId() {
        return m.getId();
    }

    public double supplierPrice() {
        return price;
    }

    @Override
    public String toString() {
        return ("Id: " + supplierId() + " Supplier: " + supplierName() + " Price: " + supplierPrice());
    }
}
