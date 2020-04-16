
import java.util.*;
import java.io.*;

public class ManufacturerList implements Serializable {

    private static final long serialVersionUID = 1L;
    private List manufacturers = new LinkedList();
    private static ManufacturerList manufacturerList;

    private ManufacturerList() {
        
    }

    public static ManufacturerList instance() {
        if (manufacturerList == null) {
            return (manufacturerList = new ManufacturerList());
        } else {
            return manufacturerList;
        }
    }

    public Manufacturer find(String manufactID) {
        for (Iterator iterator = manufacturers.iterator(); iterator.hasNext();) {
            Manufacturer manufacturer = (Manufacturer) iterator.next();
            if (manufactID.equals(manufacturer.getId())) {
                return manufacturer;
            }
        }
        return null;
    }

    public boolean insertManufacturer(Manufacturer member) {
        manufacturers.add(member);
        return true;
    }

    public Iterator getManufacturers() {
        return manufacturers.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(manufacturerList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (manufacturerList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (manufacturerList == null) {
                    manufacturerList = (ManufacturerList) input.readObject();
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

    public String toString() {
        return manufacturers.toString();
    }
}
