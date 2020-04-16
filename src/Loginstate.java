//Author: Brandon Theisen - Stage 1. 
//Author: Eric Dorphy, Cha Yang, Brandon Theisen and Anil Timilsina - Stage 2
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Loginstate extends WareState implements ActionListener {

    private static final int MANAGER_LOGIN = 0;
    private static final int CLERK_LOGIN = 1;
    private static final int CLIENT_LOGIN = 2;
    private static final int EXIT = 3;
    private final Security security = new Security();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WareContext context;
    private static Loginstate instance;

    private final JButton clientButton, salesButton, managerButton;

    private JFrame loginFrame;

    private Loginstate() {
        super();

        salesButton = new JButton("Sales Clerk");
        salesButton.addActionListener(this);
        clientButton = new JButton("Client");
        clientButton.addActionListener(this);
        managerButton = new JButton("Manager");
        managerButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource().equals(this.clientButton)) {

            //WareContext.instance().changeState(WareContext.CLIENT_STATE);
            client();
        } else if (event.getSource().equals(this.salesButton)) {
            //WareContext.instance().changeState(WareContext.SALES_STATE);
            sales();
        } else if (event.getSource().equals(this.managerButton)) {
            //WareContext.instance().changeState(WareContext.MANAGER_STATE);
            manager();
        }
    }

    public static Loginstate instance() {
        if (instance == null) {
            instance = new Loginstate();
        }
        return instance;
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:"));
                if (value <= EXIT && value >= MANAGER_LOGIN) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    private void manager() {
        String userID = JOptionPane.showInputDialog(loginFrame, "Please input user ID: ");
        String password = JOptionPane.showInputDialog(loginFrame, "Enter Password: ");

        if (security.verifyPassword(userID, password, WareContext.MANAGER_STATE)) {
            (WareContext.instance()).setLogin(WareContext.IsManager);
            (WareContext.instance()).changeState(WareContext.MANAGER_STATE);
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Incorrect Password");
        }
    }

    private void sales() {
        String userID = JOptionPane.showInputDialog(
                loginFrame, "Please input user ID: ");

        String password = JOptionPane.showInputDialog(loginFrame, "Enter Password: ");

        if (security.verifyPassword(userID, password, WareContext.SALES_STATE)) {
            (WareContext.instance()).setLogin(WareContext.IsSales);
            (WareContext.instance()).changeState(WareContext.SALES_STATE);
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Incorrect Password");
        }
    }

    private void client() {
        String userID = JOptionPane.showInputDialog(loginFrame, "Please input user ID: ");
        if (Warehouse.instance().findClient(userID) == true) {
            String password = JOptionPane.showInputDialog(loginFrame, "Enter Password: ");
            if (security.verifyPassword(userID, password, WareContext.CLIENT_STATE)) {
                (WareContext.instance()).setLogin(WareContext.IsClient);
                (WareContext.instance()).setUser(userID);
                (WareContext.instance()).changeState(WareContext.CLIENT_STATE);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Incorrect Password");
            }
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid user id.");
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        loginFrame = WareContext.instance().getFrame();
        Container pane = loginFrame.getContentPane();
        pane.removeAll();
        pane.setLayout(new FlowLayout());
        pane.add(this.clientButton);
        pane.add(this.salesButton);
        pane.add(this.managerButton);
        loginFrame.setVisible(true);
        loginFrame.paint(loginFrame.getGraphics());
    }
}
