import java.util.*;
import java.io.*;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private List itemList = new LinkedList();

    public Order(String clientId) {
        this.clientId = clientId;
    }

    public Iterator getItemList() {
        return itemList.iterator();
    }

    public List getItemLists() {
        return itemList;
    }

    public void addItem(Item i) {
        itemList.add(i);
    }

    public String getClientId() {
        return clientId;
    }

    public Item find(String clientId) {
        for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            if (clientId.equals(item.getClientID())) {
                return item;
            }
        }
        return null;
    }

    public void printOrder() {
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i));
        }
    }
}
