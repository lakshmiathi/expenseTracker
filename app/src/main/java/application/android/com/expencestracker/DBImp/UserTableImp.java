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
        sqLiteUtil = new DBHelper(context, 2);
        db = sqLiteUtil.getWritableDatabase();
    }


    public void add(User user) {
        //sqLiteUtil.onUpgrade(db,2,2);
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


    public void update(String email, String password) {
        db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD,password);
        db.update(DBdesign.USER_TABLE_NAME,contentValues,DBdesign.USER_TABLE_INFO_COLUM_EMAIL+"="+email,null);
        db.close();
    }

    public void update(String user_name, String password, String email) {
        db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD,password);
        contentValues.put(DBdesign.USER_TABLE_INFO_COLUM_USERNAME,user_name);
        db.update(DBdesign.USER_TABLE_NAME,contentValues,DBdesign.USER_TABLE_INFO_COLUM_EMAIL+"="+email,null);
        db.close();
    }


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


    public void closeDBConnection(){
        db.close();
    }

}
