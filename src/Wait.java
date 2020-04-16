
import java.io.*;

public class Wait implements Serializable {

    private final Client client;
    private int quantity;

    public Wait(Client member, int q) {
        this.client = member;
        this.quantity = q;
    }

    public Client getClient() {
        return client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int q) {
        this.quantity -= q;
    }

    @Override
    public String toString() {
        return "Client Id: " + client.getId() + " quantity: " + quantity;
    }
}
