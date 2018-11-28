package application.android.com.expencestracker.DBImp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import application.android.com.expencestracker.Model.DateUtil;
import application.android.com.expencestracker.Model.Expense;


/**
 * This class is used to maintain the "user_expense_record" table in database,
 * it provides some functions to manipulate the expense items table , e.g. add
 * a new expense, update an expense, remove an expense, get expense list, etc.
 *
 * @author
 * @version
 *
 */

public class ExpenseDaoImpl{
    private DBHelper sqLiteUtil;
    private SQLiteDatabase db;

    /**
     * Constructor for objects of class ExpenseDaoImpl, the constructor will create
     * a new database if there is no database.
     *
     */
    public ExpenseDaoImpl(Context context) {
        sqLiteUtil = new DBHelper(context, 3);
        db = sqLiteUtil.getWritableDatabase();
    }

    /**
     * The add method enables user to insert a new expense item to database by giving
     * an Expense object.
     *
     * @param expense A new {@link Expense} to be added, if the task is expense, nothing
     *               will be added
     *
     */
    public void add(Expense expense) {
        if (expense != null) {
            db = sqLiteUtil.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT, expense.getAmount());
            contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY, expense.getCategory());
            contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE, DateUtil.dateToString(expense.getDate()));
            contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_USER, expense.getUser());
            db.insert(DBdesign.EXPENSE_TABLE_NAME, null, contentValues);
            db.close();
        }
    }

    /**
     * The delete method enables user to remove an expense record from the database with
     * a given expense id.
     *
     * @param expense_id the expense id which user want to remove.
     *
     */
    public void delete(int expense_id) {
        db = sqLiteUtil.getWritableDatabase();
        db.delete(DBdesign.EXPENSE_TABLE_NAME, DBdesign.EXPENSE_TABLE_INFO_COLUM_ID+" = ?", new String[]{String.valueOf(expense_id)});
        db.close();
    }


    /**
     * The getExpenseList method will get a cursor of all the expense items(a specify user)
     * from database by giving an user id.
     *
     * @param userid the user id which user wants to search for.
     * @return return a cursor, if the user id isn't found, return null
     */
        public Cursor getExpenseList(int userid) {
        db = sqLiteUtil.getWritableDatabase();
        List<Expense> list = new ArrayList<Expense>();
        String querySql="select expenseid _id,* from " + DBdesign.EXPENSE_TABLE_NAME + " where " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + "=" + userid + " order by " + DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE+ " desc";
        Cursor cursor = db.rawQuery(querySql, null);
       return cursor;
    }

    /**
     * The getExpenseList method will return an arraylist of all the expense items from
     * database by giving a condition string.
     *
     * @param condition the condition statement which user wants to search for.
     * @return return an arraylist instance
     *
     */
    public List<Expense> getExpenseList(String condition) {
        db = sqLiteUtil.getWritableDatabase();
        List<Expense> list = new ArrayList<Expense>();
        String querySql="";
        if (condition==null||condition.trim().equals("")){
            querySql="select * from " + DBdesign.EXPENSE_TABLE_NAME;
        }else {
            querySql="select * from " + DBdesign.EXPENSE_TABLE_NAME +condition;
        }
        Cursor cursor = db.rawQuery(querySql, null);
        Expense expense = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_ID));
            Double amount = cursor.getDouble(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT));
            String category = cursor.getString(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY));
            String datetime = cursor.getString(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE));
            int user = cursor.getInt(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_USER));
            expense = new Expense(id, amount, category, DateUtil.createDate(datetime), user);
            list.add(expense);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * The getSingleExpense method will get an object of Expense by giving an expense item id.
     *
     *
     * @param expense_id the expense id which user wants to get.
     * @return return an expense instance
     *
     */
    public Expense getSingleExpense(int expense_id) {
        db = sqLiteUtil.getWritableDatabase();
        String selectQuery="Select * From " + DBdesign.EXPENSE_TABLE_NAME
                + " WHERE " + DBdesign.EXPENSE_TABLE_INFO_COLUM_ID + "=?";
        int iCount=0;
        Expense expense =new Expense();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(expense_id)});
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_ID));
                Double amount = cursor.getDouble(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT));
                String category = cursor.getString(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY));
                String datetime = cursor.getString(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE));
                int user = cursor.getInt(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_USER));
                expense = new Expense(id, amount, category, DateUtil.createDate(datetime), user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expense;
    }

    /**
     * The updateExpense method enables user to update an expense item to database.
     *
     * @param expense_id the item's expense id user want to update
     * @param amount new amount user want to update
     * @param category new category user want to update
     * @param date new date user want to update
     *
     */
    public void updateExpense(int expense_id, double amount, String category, Date date) {
        db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT,amount);
        contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY,category);
        contentValues.put(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE,DateUtil.dateToString(date));
        db.update(DBdesign.EXPENSE_TABLE_NAME,contentValues,DBdesign.EXPENSE_TABLE_INFO_COLUM_ID + "=" + expense_id ,null);
        //db.update(DBdesign.EXPENSE_TABLE_NAME,contentValues,DBdesign.EXPENSE_TABLE_INFO_COLUM_ID + "=" + expense_id + " and " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER +"="+ user,null);
        db.close();
    }

    /**
     * The sumAmountByUser method will get the total amount of expense of a specified user.
     *
     *
     * @param userid the user id which user wants to get.
     * @return return the total amount of expense
     *
     */
    public double sumAmountByUser(int userid) {
        db = sqLiteUtil.getWritableDatabase();
        String querySql="select sum(" + DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT + ") as sum from " + DBdesign.EXPENSE_TABLE_NAME + " where " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + "=" + userid;
        Cursor cursor = db.rawQuery(querySql, null);
        double dou = 0;
        while (cursor.moveToNext()) {
            dou = cursor.getDouble(cursor.getColumnIndex("sum"));
        }
        //   db.close();
        return dou;
    }

    /**
     * The Categories method will get the distinct categories of expenses for a specified user.
     *
     *
     * @param userid the user id which user wants to get.
     * @return return an arraylist of categories
     *
     */
    public ArrayList<String> Categories (int userid ){
        db = sqLiteUtil.getWritableDatabase();
        String querySql="select " + DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY + " from " + DBdesign.EXPENSE_TABLE_NAME + " where " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + "=" + userid + " group by " +DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY;
        Cursor cursor = db.rawQuery(querySql, null);
        ArrayList<String> xNewData = new ArrayList<String>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            xNewData.add(cursor.getString(0));
        }
        cursor.close();
        return xNewData;
    }

    /**
     * The Amounts method will get the total amounts of each distinct category.
     *
     *
     * @param userid the user id which user wants to get.
     * @return return an arraylist of total amount for each category
     *
     */
    public ArrayList<Double> Amounts (int userid ) {
        db = sqLiteUtil.getWritableDatabase();
        String querySql="SELECT sum(" + DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT + ") AS Total FROM " + DBdesign.EXPENSE_TABLE_NAME + " WHERE " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + "=" + userid + " GROUP BY " +DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY;
        Cursor cursor = db.rawQuery(querySql, null);
        ArrayList<Double> yNewData = new ArrayList<Double>();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            yNewData.add(cursor.getDouble(cursor.getColumnIndexOrThrow("Total")));

      
        }
        cursor.close();
        return yNewData;
    }

    public Cursor AmountsCategoryname (int userid ) {
        db = sqLiteUtil.getWritableDatabase();
        String querySql=" SELECT expenseid _id,sum(" + DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT + ") AS Total,"+ DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY  + " FROM " + DBdesign.EXPENSE_TABLE_NAME + " WHERE " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + "=" + userid + " GROUP BY " +DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY;
        Cursor cursor = db.rawQuery(querySql, null);

       

        return cursor;
    }

    public void closeDBConnection(){
        db.close();
    }
}


