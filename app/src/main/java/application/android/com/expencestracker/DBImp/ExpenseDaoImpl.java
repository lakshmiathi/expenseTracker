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

public class ExpenseDaoImpl{
    private DBHelper sqLiteUtil;
    private SQLiteDatabase db;

    public ExpenseDaoImpl(Context context) {
        sqLiteUtil = new DBHelper(context, 3);
        db = sqLiteUtil.getWritableDatabase();
    }
    //insert a new expense item to database by giving an Expense object, you don't need to specify expense id
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

    public void delete(int expense_id) {
        db = sqLiteUtil.getWritableDatabase();
        db.delete(DBdesign.EXPENSE_TABLE_NAME, DBdesign.EXPENSE_TABLE_INFO_COLUM_ID+" = ?", new String[]{String.valueOf(expense_id)});
        db.close();
    }

    //get an cursor of all the expense items(a specify user) from database by giving an user id

    public Cursor getExpenseList(int userid) {
  //      public List<Expense> getExpenseList(int userid) {

            db = sqLiteUtil.getWritableDatabase();
        List<Expense> list = new ArrayList<Expense>();
        String querySql="select expenseid _id,* from " + DBdesign.EXPENSE_TABLE_NAME + " where " + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + "=" + userid + " order by " + DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE+ " desc";
        Cursor cursor = db.rawQuery(querySql, null);
       /* Expense expense = null;

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
        return list;*/
       return cursor;
    }

    //get an arraylist of all the expense items from database by giving a condition string
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

    //get an object of Expense by giving an expense item id
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

    public void closeDBConnection(){
        db.close();
    }
}
