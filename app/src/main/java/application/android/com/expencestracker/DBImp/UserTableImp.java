package application.android.com.expencestracker.DBImp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import application.android.com.expencestracker.Model.User;

public class UserTableImp {
    private DBHelper sqLiteUtil;
    private SQLiteDatabase db;

    public UserTableImp(Context context) {
        sqLiteUtil = new DBHelper(context, 1);
        db = sqLiteUtil.getWritableDatabase();
    }


    public void add(User user) {
        //sqLiteUtil.onUpgrade(db,8,8);
        if (user != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(User.USER_TABLE_INFO_COLUM_USERNAME, user.getUsername());
            contentValues.put(User.USER_TABLE_INFO_COLUM_PASSWORD, user.getPassword());
            contentValues.put(User.USER_TABLE_INFO_COLUM_EMAIL, user.getEmail());
            contentValues.put(User.USER_TABLE_INFO_COLUM_STATUS, user.isStatus());
            db.insert(User.USER_TABLE_NAME, null, contentValues);
        }
    }


    public void delete(String email) {
        db.delete(User.USER_TABLE_NAME, User.USER_TABLE_INFO_COLUM_EMAIL+" = ?", new String[]{String.valueOf(email)});
        db.close();
    }


    public void update(String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.USER_TABLE_INFO_COLUM_PASSWORD,password);
        db.update(User.USER_TABLE_NAME,contentValues,User.USER_TABLE_INFO_COLUM_EMAIL+"="+email,null);
        db.close();
    }

    public void update(String user_name, String password, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.USER_TABLE_INFO_COLUM_PASSWORD,password);
        contentValues.put(User.USER_TABLE_INFO_COLUM_USERNAME,user_name);
        db.update(User.USER_TABLE_NAME,contentValues,User.USER_TABLE_INFO_COLUM_EMAIL+"="+email,null);
        db.close();
    }


    public User getUser(String pemail) {
        String querySQL= "select * from " + User.USER_TABLE_NAME + " where "+User.USER_TABLE_INFO_COLUM_EMAIL+"='"+pemail+"' and " + User.USER_TABLE_INFO_COLUM_STATUS + "=1";
        //System.out.println(querySQL);
        Cursor cursor = db.rawQuery(querySQL, null);
        User userObj = null;

        while (cursor.moveToNext()) {
            int userid = cursor.getInt(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_ID));
            String username = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_EMAIL));
            int statusInt = cursor.getInt(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_STATUS));
            boolean status = (statusInt == 1) ? true:false;
            userObj = new User(userid, username, password, email, status);
        }
        cursor.close();
        return userObj;
    }


    public void closeDBConnection(){
        db.close();
    }

}
