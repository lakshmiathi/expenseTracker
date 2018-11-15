package application.android.com.expencestracker.DBImp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import application.android.com.expencestracker.Model.User;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, int version) {
        super(context, User.DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer createUserTable = new StringBuffer();
        createUserTable.append("CREATE TABLE IF NOT EXISTS ");
        createUserTable.append(User.USER_TABLE_NAME + "(");
        createUserTable.append(User.USER_TABLE_INFO_COLUM_ID + " integer primary key autoincrement ,");
        createUserTable.append(User.USER_TABLE_INFO_COLUM_USERNAME + " varchar(12),");
        createUserTable.append(User.USER_TABLE_INFO_COLUM_PASSWORD + " varchar(20),");
        createUserTable.append(User.USER_TABLE_INFO_COLUM_EMAIL + " varchar(20),");
        createUserTable.append(User.USER_TABLE_INFO_COLUM_STATUS + " integer)");

        //System.out.println(createUserTable.toString());
        db.execSQL(createUserTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropUserTable = "drop table if exists "
                + User.USER_TABLE_NAME;
        db.execSQL(dropUserTable);
        onCreate(db);
    }

}

