
import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    private ProductList productList;
    private ClientList clientList;
    private ManufacturerList manufacturerList;
    private OrderList orderList;
    private Order order;
    private static Warehouse warehouse;

    private Warehouse() {
        productList = ProductList.instance();
        clientList = ClientList.instance();
        manufacturerList = ManufacturerList.instance();
        orderList = OrderList.instance();

    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ClientIdServer.instance(); // instantiate all singletons
            ManufacturerIDServer.instance(); // instantiate all singletons
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    public Product addProduct(String title, String id, double price, int quantity) {

        Product product = new Product(title, id, price, quantity);
        if (productList.insertProduct(product)) {
            return (product);
        }
        return null;
    }

    public Client addMember(String name, String address, String phone) {
        Client member = new Client(name, address, phone);
        if (clientList.insertMember(member)) {
            return (member);
        }
        return null;
    }

    public Manufacturer addManufacturer(String name, String address, String phone) {
        Manufacturer manu = new Manufacturer(name, address, phone);
        if (manufacturerList.insertManufacturer(manu)) {
            return (manu);
        }
        return null;
    }

    public Iterator getProducts() {
        return productList.getProducts();
    }

    public Iterator getMembers() {
        return clientList.getMembers();
    }

    public Iterator getManufacturers() {
        return manufacturerList.getManufacturers();
    }

    public Iterator getWaitList(String pId) {
        // get product waitlist
        Product p = productList.find(pId);
        return p.getWaitList();
    }

    public Iterator getSupplierList(String pId) {
        Product p = productList.find(pId);
        return p.getSupplierList();
    }

    public Iterator getOrderList() {
        return order.getItemList();
    }

    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIdServer.retrieve(input);
            ManufacturerIDServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIdServer.instance());
            output.writeObject(ManufacturerIDServer.instance());

            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public boolean addToOrderList() {
        return orderList.addOrder(order);
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean findClient(String clientID) {
        return clientList.find(clientID) != null;
    }

    public Client getClient(String clientID) {
        return clientList.find(clientID);
    }

    public Order findOrder(String clientID) {
        return orderList.find(clientID);
    }

    public void printOrder(String clientId) {
        Order order = warehouse.findOrder(clientId);;
        order.printOrder();

    }

    public Product findProduct(String pId) {
        return productList.find(pId);
    }

    public Manufacturer findManufacturer(String mId) {
        return manufacturerList.find(mId);
    }

    public void updateClientBalance(String clientId, double total) {
        Client client = clientList.find(clientId);
        client.addBalance(total);
    }

    public double getClientBalance(String clientId) {
        Client client = clientList.find(clientId);
        return client.getBalance();
    }

    // creates an order for a client
    public void createOrder(String clientId) {
        order = new Order(clientId);
    }

    public void addToOrder(String cId, String pId, int quantity) {
        Product product = productList.find(pId);
        if (product != null) {
            Item newItem = new Item(pId, quantity, (product.getPrice() * quantity));
            order.getItemLists().add(newItem);
        }
    }

    public void processOrder(String cId) {
        Client client = clientList.find(cId);
        double totalPrice = 0;
        int quantity;
        Iterator allItems = warehouse.getOrderList();
        System.out.println("Invoice");
        System.out.println("---------");

        while (allItems.hasNext()) {
            Item i = (Item) (allItems.next());
            // System.out.println(i.print());
            quantity = i.getQuantity();
            totalPrice += i.getTotal();

            Product product = productList.find(i.getProductId());
            if (product != null) {
                if (quantity > product.getQuantity()) {
                    int waitListQuantity = quantity - product.getQuantity();
                    //Item newItem = new Item(pId, quantity, (product.getPrice() * quantity));
                    Item newWaitListItem = new Item(i.getProductId(), waitListQuantity, product.getPrice() * waitListQuantity);
                    newWaitListItem.associateClientID(cId);
                    //  order.getItemLists().add(newItem);
                    Wait test = new Wait(client, waitListQuantity);
                    System.out.println(product.getQuantity() + " " + product.getProduct() + " fulfilled.");
                    System.out.println(waitListQuantity + " " + product.getProduct() + " added to wait list.");
                    product.addToWaitList(test);
                    //waitList.addItem(newWaitListItem);
                    product.setQuantity(0); // Set it to 0 since its out of stock

                } else if (quantity <= product.getQuantity()) {
                    //Item newItem = new Item(pId, quantity, (product.getPrice() * quantity));
                    //order.addItem(newItem);
                    int newQuantity = product.getQuantity() - quantity;
                    product.setQuantity(newQuantity);

                    System.out.println(quantity + " " + product.getProduct() + " fulfilled. ");
                    // System.out.println("Quantity remaining: " + newQuantity);
                }
            } else {
                System.out.println("Product not found.");
            }
        }
        System.out.println("Total order = " + totalPrice);

    }

    public void updateQuantity(Product p, int q) {
        p.updateQuantity(q);
    }

    public void addSupplierToProduct(Product p, Manufacturer m, double price) {
        Supplier s = new Supplier(m, price);
        p.addToSupplierList(s);
    }

    public void deleteSupplierFromProduct(Product p, Manufacturer m) {
        // find supplier first
        Supplier s = p.find(m.getId());

        p.deleteFromSupplierList(s);
    }

    public boolean fulfillWaitList(Product p, Client c, int q) {
        if (p.fulfillOrder(c, q)) {
            updateClientBalance(c.getId(), p.getPrice() * q);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return warehouse + "\n" + clientList;
    }

    public Iterator getTransactions(String memberId) {
        Client client = clientList.find(memberId);
        if (client == null) {
            return (null);
        }
        return client.getTransactions(memberId);
    }

    public void addToTransactions(String clientId, double balance) {
        Client client = clientList.find(clientId);
        client.addToTransactionList(balance);
    }
}
