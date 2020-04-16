
import java.util.*;
import java.io.*;

public class ClientList implements Serializable {

    private static final long serialVersionUID = 1L;
    private List clients = new LinkedList();
    private static ClientList clientList;

    private ClientList() {
    }

    public static ClientList instance() {
        if (clientList == null) {
            return (clientList = new ClientList());
        } else {
            return clientList;
        }
    }

    public boolean insertMember(Client member) {
        clients.add(member);
        return true;
    }

    public Iterator getMembers() {
        return clients.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(clientList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (clientList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (clientList == null) {
                    clientList = (ClientList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public Client find(String clientId) {
        for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
            Client client = (Client) iterator.next();
            if (clientId.equals(client.getId())) {
                return client;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return clients.toString();
    }
}
