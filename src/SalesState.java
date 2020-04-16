
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.*;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Eric Dorphy -- Stage 1 and Stage 3
 * @author Brandon Theisen -- Stage 3
 * @author Eric Dorphy -- Refactor Stage 3
 */
public class SalesState extends WareState {

    private static Warehouse warehouse;
    private WareContext context;
    private static SalesState instance;

    private JFrame salesFrame;
    private JPanel salesMenuPanel;

    private JButton addProductButton;
    private JButton acceptPaymentButton;
    private JButton getOverdueBalanceButton;
    private JButton showWaitlistButton;
    private JButton acceptShipmentButton;
    private JButton showProductsButton;
    private JButton addSupplierButton;
    private JButton getProductSuppliersButton;
    private JButton switchToClientButton;
    private JButton logoutButton;

    private AddProductPanel addProductPanel;
    private ShowProductsPanel showProductsPanel;
    private AcceptShipmentPanel acceptShipmentPanel;
    private AddSupplierPanel addSupplierPanel;
    private ShowSuppliersPanel showSuppliersPanel;
    private ShowProductWaitlistPanel showProductWaitlistPanel;
    private ShowOverdueBalancePanel showOverdueBalancePanel;
    private AcceptPaymentPanel acceptPaymentPanel;

    /**
     * SalesState Default constructor
     */
    private SalesState() {
        super();
        warehouse = Warehouse.instance();

        addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (addProductPanel == null) {
                    addProductPanel = new AddProductPanel();
                }
                refreshGUI(addProductPanel);
            }
        });

        acceptPaymentButton = new JButton("Accept Payment");
        acceptPaymentButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (acceptPaymentPanel == null) {
                    acceptPaymentPanel = new AcceptPaymentPanel();
                }
                refreshGUI(acceptPaymentPanel);
            }
        });

        getOverdueBalanceButton = new JButton("Get Overdue Balance");
        getOverdueBalanceButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (showOverdueBalancePanel == null) {
                    showOverdueBalancePanel = new ShowOverdueBalancePanel();
                }
                refreshGUI(showOverdueBalancePanel);
            }
        });

        showWaitlistButton = new JButton("Show Waitlist");
        showWaitlistButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (showProductWaitlistPanel == null) {
                    showProductWaitlistPanel = new ShowProductWaitlistPanel();
                }
                refreshGUI(showProductWaitlistPanel);
            }
        });

        acceptShipmentButton = new JButton("Accept Shipment");
        acceptShipmentButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (acceptShipmentPanel == null) {
                    acceptShipmentPanel = new AcceptShipmentPanel();
                }
                refreshGUI(acceptShipmentPanel);
            }
        });

        showProductsButton = new JButton("Show Product List");
        showProductsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (showProductsPanel == null) {
                    showProductsPanel = new ShowProductsPanel();
                }
                refreshGUI(showProductsPanel);
                showProducts();
            }
        });

        addSupplierButton = new JButton("Add Supplier to Product");
        addSupplierButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (addSupplierPanel == null) {
                    addSupplierPanel = new AddSupplierPanel();
                }
                refreshGUI(addSupplierPanel);
            }
        });

        getProductSuppliersButton = new JButton("Get Product Suppliers");
        getProductSuppliersButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (showSuppliersPanel == null) {
                    showSuppliersPanel = new ShowSuppliersPanel();
                }
                refreshGUI(showSuppliersPanel);
            }
        });

        switchToClientButton = new JButton("Switch to Client");
        switchToClientButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                becomeClient();
            }
        });

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout();
            }
        });

        salesMenuPanel = new JPanel();
    }

    public static SalesState instance() {
        if (instance == null) {
            instance = new SalesState();
        }
        return instance;
    }

    public void addProducts() {
        Product result = null;

        do {

            String title = JOptionPane.showInputDialog(salesFrame, "Enter  product name: ");
            String productID = JOptionPane.showInputDialog(salesFrame, "Enter id: ");
            double price = new Double(JOptionPane.showInputDialog(salesFrame, "Enter Price: "));
            int quantity = new Integer(JOptionPane.showInputDialog(salesFrame, "Enter Quantity: "));

            if (title.equals("") != true) {
                result = warehouse.addProduct(title, productID, price, quantity);
            }

            if (result != null) {
                JOptionPane.showMessageDialog(salesFrame, result);
            } else {
                JOptionPane.showMessageDialog(salesFrame, "Product could not be added.");
            }
            int reply = JOptionPane.showConfirmDialog(salesFrame, "Add another product?");
            if (reply == JOptionPane.NO_OPTION || reply == JOptionPane.CANCEL_OPTION) {
                break;
            }
        } while (true);//End of do while

    }//End addProducts

    public boolean addProduct(String title, String productID, int quantity, double price) {
        Product result;

        if (title.equals("") || productID.equals("") || quantity == 0 || price == 0) {
            JOptionPane.showMessageDialog(salesFrame, "Product could not be added.");
            return false;
        }

        result = warehouse.addProduct(title, productID, price, quantity);

        if (result != null) {
            JOptionPane.showMessageDialog(salesFrame, result);
        } else {
            JOptionPane.showMessageDialog(salesFrame, "Product could not be added.");
            return false;
        }
        return true;
    }//End addProducts

    public boolean acceptPayment(String clientID, double payment) {
        double balance;

        if (warehouse.findClient(clientID)) {
            //client is found so we accept a payment from a client            

            warehouse.updateClientBalance(clientID, -payment);
            balance = warehouse.getClientBalance(clientID);

            JOptionPane.showMessageDialog(null, "The client's new balance is: " + balance);

        } else {
            JOptionPane.showMessageDialog(salesFrame, "Client not found.");
            return false;
        }

        return true;
    }//End of acceptPayment

    public boolean balanceCheck(String clientID) {
        double balance;
        if (warehouse.findClient(clientID)) {
            balance = warehouse.getClientBalance(clientID);
            JOptionPane.showMessageDialog(salesFrame, "The client's balance is: " + balance);
            return true;
        }
        JOptionPane.showMessageDialog(salesFrame, "Client not found.");
        return false;
    }

    public void getOverdueBalance() {
        String x = "";

        Iterator allMembers = warehouse.getMembers();
        while (allMembers.hasNext()) {
            Client member = (Client) (allMembers.next());
            if (member.getBalance() > 0) {
                x = x + member.toStringBalance() + "\n";
            }
        }

        showOverdueBalancePanel.textArea1.setText("The following have overdue balances: \n" + x);
    }

    public void showWaitlist(String pId) {
        String wait = "";

        if (warehouse.findProduct(pId) != null) {
            Iterator wholeWaitList = warehouse.getWaitList(pId);
            while (wholeWaitList.hasNext()) {
                Wait waitList = (Wait) (wholeWaitList.next());
                //IOHelper.Println(waitList.toString());
                wait = wait + waitList.toString() + "\n";
            }
            //JOptionPane.showMessageDialog(salesFrame, "Waitlist for " + pId + ":\n" + wait);
            showProductWaitlistPanel.jTextArea1.setText("Waitlist for: " + pId + "\n" + wait);
        } else {
            JOptionPane.showMessageDialog(salesFrame, "Product not found.");
        }
    }

    public boolean acceptShipment(String productId, int quantity) {
        Product p = warehouse.findProduct(productId);

        if (p != null) {

            for (Iterator waitList = warehouse.getWaitList(productId); waitList.hasNext();) {
                Wait wait = (Wait) waitList.next();
                JOptionPane.showMessageDialog(salesFrame, wait.toString());
                int clientQuantity = wait.getQuantity();

                int reply = JOptionPane.showConfirmDialog(salesFrame, "Do you want to fulfill waitlist for this client?");
                if (reply == JOptionPane.YES_OPTION) {
                    if (quantity >= clientQuantity) {
                        if (warehouse.fulfillWaitList(p, wait.getClient(), clientQuantity)) {
                            quantity -= clientQuantity;
                            waitList.remove();
                        }
                    } else {
                        if (warehouse.fulfillWaitList(p, wait.getClient(), quantity)) {
                            quantity -= quantity;
                            break;
                        }
                    }
                }
            }
            warehouse.updateQuantity(p, quantity);
        } else {
            JOptionPane.showMessageDialog(salesFrame, "Product ID not found.");
            return false;
        }

        return true;
    }//End of acceptOrders

    private void showProducts() {
        Iterator allProducts = warehouse.getProducts();
        String prodList = "";

        while (allProducts.hasNext()) {
            Product product = (Product) (allProducts.next());
            prodList = prodList + product.toString() + "\n";
        }

        showProductsPanel.jTextArea1.setText("Product list: \n" + prodList);
    }

    public boolean addSupplier(String pId, String mId, double price) {
        Product p = warehouse.findProduct(pId);

        if (p != null) {
            Manufacturer m;
            m = warehouse.findManufacturer(mId);

            if (m != null) {
                warehouse.addSupplierToProduct(p, m, price);
                JOptionPane.showMessageDialog(salesFrame, "Supplier added.");
            } else {
                JOptionPane.showMessageDialog(salesFrame, "Could not find manufacturer.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(salesFrame, "Product not found.");
            return false;
        }
        return true;
    }

    public void supplierList(String pId) {
        String supList = "";

        if (warehouse.findProduct(pId) != null) {
            Iterator supplierList = warehouse.getSupplierList(pId);

            while (supplierList.hasNext()) {
                Supplier sl = (Supplier) (supplierList.next());
                supList = supList + sl.toString() + "\n";
            }

            showSuppliersPanel.textArea1.setText("Supplier list for : " + pId + "\n" + supList);
        } else {
            JOptionPane.showMessageDialog(salesFrame, "Product not found.");
        }
    }

    private void becomeClient() {
        String userID = JOptionPane.showInputDialog("Please input the user id: ");

        if (Warehouse.instance().findClient(userID)) {
            (WareContext.instance()).setUser(userID);
            (WareContext.instance()).changeState(WareContext.CLIENT_STATE); //go to sales state
        } else {
            JOptionPane.showMessageDialog(salesFrame, "Invalid user id.");
        }
    }

    /**
     * If we are a manager, logout of sales and return to manager else we logout
     * and go to the main login menu
     */
    private void logout() {
        if ((WareContext.instance()).getLogin() == WareContext.IsManager) {
            (WareContext.instance()).changeState(WareContext.MANAGER_STATE);
        } else {
            (WareContext.instance()).changeState(WareContext.LOGIN_STATE);
        }
    }

    private void refreshGUI(JPanel showPanel) {
        salesMenuPanel.removeAll();
        salesMenuPanel.add(showPanel);
        salesFrame.validate();
    }

    @Override
    public void run() {
        salesFrame = WareContext.instance().getFrame();
        Container pane = salesFrame.getContentPane();
        pane.removeAll();
        pane.setLayout(new FlowLayout());
        pane.add(this.addProductButton);
        pane.add(this.acceptPaymentButton);
        pane.add(this.getOverdueBalanceButton);
        pane.add(this.showWaitlistButton);
        pane.add(this.acceptShipmentButton);
        pane.add(this.showProductsButton);
        pane.add(this.addSupplierButton);
        pane.add(this.getProductSuppliersButton);
        pane.add(this.switchToClientButton);
        pane.add(this.logoutButton);

        pane.add(salesMenuPanel, BorderLayout.CENTER);

        salesFrame.setVisible(true);
        salesFrame.paint(salesFrame.getGraphics());
    }
}
