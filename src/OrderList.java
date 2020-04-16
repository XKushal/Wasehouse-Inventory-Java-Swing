
import java.util.*;
import java.io.*;

public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;
    private LinkedList orders = new LinkedList();
    private static OrderList orderList;

    private OrderList() {
    }

    public static OrderList instance() {
        if (orderList == null) {
            return (orderList = new OrderList());
        } else {
            return orderList;
        }
    }

    public LinkedList getList() {
        return orders;
    }

    public boolean addOrder(Order o) {
        return orders.add(o);
    }

    public Order find(String clientId) {
        for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
            Order order = (Order) iterator.next();
            if (clientId.equals(order.getClientId())) {
                return order;
            }
        }
        return null;
    }

    public Iterator getOrderList() {
        return orders.iterator();
    }
}
