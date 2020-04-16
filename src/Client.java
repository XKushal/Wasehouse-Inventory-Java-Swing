
import java.util.*;
import java.io.*;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private String id;
    private List transactions = new LinkedList();
    private double balance;
    private Calendar orderDate;

    private static final String MEMBER_STRING = "C";

    public Client(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        id = MEMBER_STRING + (ClientIdServer.instance()).getId();
    }

    public Iterator getTransactions(String clientId) {
        List result = new LinkedList();
        for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = (Transaction) iterator.next();
            result.add(transaction);
        }
        return (result.iterator());
    }//End of getTransactions()

    public void addToTransactionList(double balance) {
        transactions.add(new Transaction(balance));
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double balance) {
        this.balance += balance;
    }

    public void subBalance(double balance) {
        this.balance -= balance;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public void setPhone(String newPhone) {
        phone = newPhone;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        String string = "Member name " + name + " address " + address + " id " + id + " phone " + phone;
        return string;
    }

    public String toStringBalance() {
        String string = "Member name: " + name + " id: " + id + " balance: " + balance;
        return string;
    }
}
