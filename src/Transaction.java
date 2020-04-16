
import java.util.*;
import java.io.*;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    private double balance;
    private String ID;
    private Calendar date;

    public Transaction(double balance) {
        this.balance = balance;
        date = new GregorianCalendar();
        date.setTimeInMillis(System.currentTimeMillis());
    }

    public boolean onDate(Calendar date) {
        return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
                && (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
                && (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
    }

    public double getBalance() {
        return balance;
    }

    public String getDate() {
        return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        return ("Date: " + getDate() + " Balance: " + balance);
    }
}
