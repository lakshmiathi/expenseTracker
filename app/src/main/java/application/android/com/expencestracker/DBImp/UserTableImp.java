package application.android.com.expencestracker.DBImp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import application.android.com.expencestracker.Model.Expense;
import application.android.com.expencestracker.Model.User;

/**
 * This class is used to maintain the "user_record" table in database, it provides
 * some functions to manipulate the user table, e.g. add a new user, update an
 * user's information, etc.
 *
 * @author
 * @version
 *
 */
public class UserTableImp {
    private DBHelper sqLiteUtil;
    private SQLiteDatabase db;

    public UserTableImp(Context context) {
        sqLiteUtil = new DBHelper(context, 3);
        db = sqLiteUtil.getWritableDatabase();
    }

    /**
     * The add method enables user to create a new user to database by giving an
     * email address, user name, password.
     *
     * @param user A new {@link User} to be added
     *
     */
    public void add(User user) {
        db = sqLiteUtil.getWritableDatabase();
        if (user != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_USERNAME, user.getUsername());
            contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD, user.getPassword());
            contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_EMAIL, user.getEmail());
            contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_STATUS, user.isStatus());
            db.insert(DBdesign.USER_TABLE_NAME, null, contentValues);
        }
        db.close();
    }

    public void delete(String email) {
        db = sqLiteUtil.getWritableDatabase();
        db.delete(DBdesign.USER_TABLE_NAME, DBdesign.USER_TABLE_INFO_COLUM_EMAIL+" = ?", new String[]{String.valueOf(email)});
        db.close();
    }

    /*public void update(String email, String password) {
        db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD,password);
        db.update(DBdesign.USER_TABLE_NAME,contentValues,DBdesign.USER_TABLE_INFO_COLUM_EMAIL+"="+email,null);
        db.close();
    }*/

    public void update(String user_name, String password, String email) {
        db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD,password);
        contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_USERNAME,user_name);
        db.update(DBdesign.USER_TABLE_NAME,contentValues,DBdesign.USER_TABLE_INFO_COLUM_EMAIL+"="+email,null);
        db.close();
    }

    /**
     * The getUser method will get an object of User by giving an email addrss.
     *
     *
     * @param pemail the email addrss which user wants to get.
     * @return return an user instance
     *
     */
    public User getUser(String pemail) {
        db = sqLiteUtil.getWritableDatabase();
        String querySQL= "select * from " + DBdesign.USER_TABLE_NAME + " where "+DBdesign.USER_TABLE_INFO_COLUM_EMAIL+"='"+pemail+"' and " + DBdesign.USER_TABLE_INFO_COLUM_STATUS + "=1";
        //System.out.println(querySQL);
        Cursor cursor = db.rawQuery(querySQL, null);
        User userObj = null;
        while (cursor.moveToNext()) {
            int userid = cursor.getInt(cursor.getColumnIndex(DBdesign.USER_TABLE_INFO_COLUM_ID));
            String username = cursor.getString(cursor.getColumnIndex(DBdesign.USER_TABLE_INFO_COLUM_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(DBdesign.USER_TABLE_INFO_COLUM_EMAIL));
            int statusInt = cursor.getInt(cursor.getColumnIndex(DBdesign.USER_TABLE_INFO_COLUM_STATUS));
            boolean status = (statusInt == 1) ? true:false;
            userObj = new User(userid, username, password, email, status);
        }
        cursor.close();
        db.close();
        return userObj;
    }



    public int getAmount() {
        db = sqLiteUtil.getWritableDatabase();
        String querySql = "select count(*) as count from " + DBdesign.USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(querySql, null);
        int count = 0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex("count"));
        }
        db.close();
        return count;
    }

    public int limit(){
        db = sqLiteUtil.getReadableDatabase();
        String querySql = "select "+DBdesign.USER_TABLE_INFO_LIMIT+ " as limits from " + DBdesign.USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(querySql, null);
        int limit = 0;
        while (cursor.moveToNext()) {
            limit = cursor.getInt(cursor.getColumnIndex("limits"));
        }
        db.close();
        return limit;
    }

    public int sumAmount(){
        db = sqLiteUtil.getReadableDatabase();
        String querySql = "SELECT sum(" + DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT + ") AS Total FROM " + DBdesign.EXPENSE_TABLE_NAME;
        Cursor cursor = db.rawQuery(querySql, null);
        int sumAmount = 0;
        while (cursor.moveToNext()) {
            sumAmount = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        db.close();
        return sumAmount;
    }






    public void update(int id, String limit) {
        db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBdesign.USER_TABLE_INFO_LIMIT,limit);

        db.update(DBdesign.USER_TABLE_NAME,contentValues,DBdesign.USER_TABLE_INFO_COLUM_ID+"="+id,null);
        db.close();

    }

    /**
     * The getLimit method will get the limit number of specified user.
     *
     *
     * @param user_id the user id which user wants to get limit number.
     * @return return the limit number of specified user.
     *
     */
    public String getLimit(int user_id) {
        db = sqLiteUtil.getWritableDatabase();
        String query="select userid _id ,"+ DBdesign.USER_TABLE_INFO_LIMIT+" from "+DBdesign.USER_TABLE_NAME + " where " +DBdesign.USER_TABLE_INFO_COLUM_ID +" = " +user_id;
        Cursor cursor = db.rawQuery(query, null);
        //double dou = 0;
        String str=null;

        //  cursor.moveToFirst();
        while (cursor.moveToNext()) {
            str = cursor.getString(cursor.getColumnIndexOrThrow("limit1"));
        }
        cursor.close();
        //   db.close();
        return str;

    }

    public void closeDBConnection(){
        db.close();
    }

}
