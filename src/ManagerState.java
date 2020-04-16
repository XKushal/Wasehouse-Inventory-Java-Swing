/**
 *
 * @author Anil (all stages)
 * @author Eric - Refactor pre GUI
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ManagerState extends WareState {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private WareContext context;
    private static ManagerState instance;
    private JFrame managerFrame;
    private JPanel managerPanel;
    
    private final JButton logoutButton;
    private final JButton addClientButton;
    private final JButton addManufacturerButton;
    private final JButton deleteSupplierButton;
    private final JButton showManufacturersButton;
    private final JButton showClientsButton;
    private final JButton switchToSalesButton;
    
    private AddClientPanel addClientPanel;
    private AddManufacturerPanel addManufacturerPanel;
    private DeleteSupplierPanel deleteSupplierPanel;
    private ShowManufacturersPanel showManufacturersPanel;
    private ShowClientsPanel showClientsPanel;   

    private ManagerState() {
        super();
        warehouse = Warehouse.instance();        
        managerPanel = new JPanel();
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout();
            }
        });

        addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (addClientPanel == null){
                    addClientPanel = new AddClientPanel();
                }
                refreshGUI(addClientPanel);
            }
        });

        addManufacturerButton = new JButton("Add Manufacturer");
        addManufacturerButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (addManufacturerPanel == null){
                    addManufacturerPanel = new AddManufacturerPanel();
                }
                refreshGUI(addManufacturerPanel);
            }
        });

        deleteSupplierButton = new JButton("Remove Supplier");
        deleteSupplierButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (deleteSupplierPanel == null){
                    deleteSupplierPanel = new DeleteSupplierPanel();
                }
                //refreshGUI(deleteSupplierPanel);
                deleteSupplier();
            }
        });

        showManufacturersButton = new JButton("Show Manufacturers");
        showManufacturersButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (showManufacturersPanel == null){
                    showManufacturersPanel = new ShowManufacturersPanel();
                }
                refreshGUI(showManufacturersPanel);
                showManufacturers();
            }
        });

        showClientsButton = new JButton("Show Clients");
        showClientsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (showClientsPanel == null){
                    showClientsPanel = new ShowClientsPanel();
                }
                refreshGUI(showClientsPanel);
                
                showClients();                
            }
        });
        
        switchToSalesButton = new JButton("Switch to Sales");
        switchToSalesButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesMenu();
            }
        });
    }
    
    private void refreshGUI(JPanel showPanel) {
        managerPanel.removeAll();
        managerPanel.add(showPanel);
        managerFrame.validate();
    }

    public static ManagerState instance() {
        if (instance == null) {
            instance = new ManagerState();
        }
        return instance;
    }
    
    public boolean addClient(String name, String address, String phone) {
        Client result;

        if (name.equals("") || address.equals("") || phone.equals("")) {
            JOptionPane.showMessageDialog(managerFrame, "Client could not be added.");
            return false;
        }

        result = warehouse.addMember(name, address, phone);

        if (result != null) {
            JOptionPane.showMessageDialog(managerFrame, result);
        } else {
            JOptionPane.showMessageDialog(managerFrame, "Client could not be added.");
            return false;
        }
        return true;
    }//End addClients

    public boolean addManufacturer(String name, String address, String phone) {
        Manufacturer result;

        if (name.equals("") || address.equals("") || phone.equals("")) {
            JOptionPane.showMessageDialog(managerFrame, "Manufacturer could not be added.");
            return false;
        }

        result = warehouse.addManufacturer(name, address, phone);

        if (result != null) {
            JOptionPane.showMessageDialog(managerFrame, result);
        } else {
            JOptionPane.showMessageDialog(managerFrame, "Manufacturer could not be added.");
            return false;
        }
        return true;
    }//End addManufacturer
        
    private void deleteSupplier() {       
        String productID = JOptionPane.showInputDialog(managerFrame, "Please input Product ID: ");
        Product p = warehouse.findProduct(productID);
        
        if (p != null){
            String manID = JOptionPane.showInputDialog(managerFrame, "Please input Manufacturer ID: ");
            Manufacturer man = warehouse.findManufacturer(manID);
        
            if (man != null) {
                warehouse.deleteSupplierFromProduct(p, man);
                JOptionPane.showMessageDialog(managerFrame, "Successfully removed manufacturer from the supplier list for the product");
               
            } else {
                JOptionPane.showMessageDialog(managerFrame, "Cannot find manufacturer");          
            }
        } else {
            JOptionPane.showMessageDialog(managerFrame, "Cannot find product");          
        }
    }

    private void showManufacturers() {
        Iterator allManu = warehouse.getManufacturers();
        String manufacturerList = "";
        
        while (allManu.hasNext()) {
            Manufacturer manufacturer = (Manufacturer) (allManu.next());
            manufacturerList = manufacturerList + manufacturer.toString() + "\n";
            //System.out.println(manufacturer.toString());
        }
        showManufacturersPanel.manufacturersTextView.setText(manufacturerList);
        //showManufacturersPanel.jTextArea.setText("Manufacturer List: \n" + manufacturerList);
    }

    private void showClients() {
        Iterator allMembers = warehouse.getMembers();
        String clientList = "";
        
        while (allMembers.hasNext()) {
            Client member = (Client) (allMembers.next());
            clientList = clientList + member.toString() + "\n";           
        }
        
        showClientsPanel.clientsTextView.setText(clientList);
    }

    private void salesMenu() {
        (WareContext.instance()).changeState(WareContext.SALES_STATE); //go to sales state
    }


    public void logout() {
        (WareContext.instance()).changeState(WareContext.LOGIN_STATE); // exit
    }

    @Override
    public void run() {
        //process();
        managerFrame = WareContext.instance().getFrame();
        Container pane = managerFrame.getContentPane();
        pane.removeAll();
        pane.setLayout(new FlowLayout());
        pane.add(this.addClientButton);
        pane.add(this.addManufacturerButton);
        pane.add(this.deleteSupplierButton);
        pane.add(this.showManufacturersButton);
        pane.add(this.showClientsButton);
        pane.add(this.switchToSalesButton);
        pane.add(this.logoutButton);
        
        pane.add(managerPanel, BorderLayout.CENTER);
        
        managerFrame.setVisible(true);
        managerFrame.paint(managerFrame.getGraphics());
    }
}