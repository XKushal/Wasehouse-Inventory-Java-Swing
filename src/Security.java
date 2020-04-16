
public class Security {

    public boolean verifyPassword(String userID, String password, int state) {
        boolean verified = verifyPasswordHidden(userID, password, state);
        return verified;
    }

    private boolean verifyPasswordHidden(String userID, String password, int state) {
        if (state == WareContext.MANAGER_STATE) {
            if (userID.equals("manager") && password.equals("manager")) {
                System.out.println("Return true.");
                return true;
            }
        }//End of if statement
        else if (state == WareContext.SALES_STATE) {
            if (userID.equals("salesclerk") && password.equals("salesclerk")) {
                return true;
            }
        }//End of else if statement
        else if (state == WareContext.CLIENT_STATE) {
            //System.out.println("Testing client password");
            if (userID.equals(password)) {
                return true;
            }
        }//End of else statement

        return false;
    }//End of verifyPassword

}
