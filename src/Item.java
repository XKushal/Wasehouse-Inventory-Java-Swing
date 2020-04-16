
import java.io.*;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private int quantity;
    private String productId;
    private double total;

    public Item(String pId, int quantity, double total) {
        this.productId = pId;
        this.quantity = quantity;
        this.total = total;
    }

    public void associateClientID(String clientId) {
        this.clientId = clientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getClientID() {
        return clientId;
    }

    public double getTotal() {
        return total;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Client ID: " + clientId + " Product ID: " + productId + " Quantity: " + quantity + " Total: " + total;
    }

    public String print() {
        return "Product ID: " + productId + " Quantity: " + quantity + " Total: " + total;
    }
}
