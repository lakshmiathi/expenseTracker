package application.android.com.expencestracker.Model;

import java.util.Date;

/**
 * The Expense class represents a expense item with several properties, such as
 * expense id, amount, category, date, user.
 *
 * @author
 * @version 1.0
 *
 */
public class Expense {

    private int expenseid;
    private double amount;
    private String category;
    private Date date;
    private int user;

    public Expense() {

    }

    public Expense(int id, double amount, String category, Date date, int user) {
        this.expenseid = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.user = user;
    }


    public Expense(double amount, String category, Date date, int user) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.user = user;
    }

    public int getId() {
        return expenseid;
    }

    public void setId(int id) {
        this.expenseid = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser() {
        return this.user;
    }

 /*   public void setUser(int user) {
        this.user = user;
    }
*/

}
