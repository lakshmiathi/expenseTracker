package application.android.com.expencestracker.DBImp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The DBHelper class is used to setup the database.
 *
 * @author
 * @version
 *
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, int version) {
        super(context, DBdesign.DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer createUserTable = new StringBuffer();
        createUserTable.append("CREATE TABLE IF NOT EXISTS ");
        createUserTable.append(DBdesign.USER_TABLE_NAME + "(");
        createUserTable.append(DBdesign.USER_TABLE_INFO_COLUM_ID + " integer primary key autoincrement ,");
        createUserTable.append(DBdesign.USER_TABLE_INFO_COLUM_USERNAME + " varchar(20),");
        createUserTable.append(DBdesign.USER_TABLE_INFO_COLUM_PASSWORD + " varchar(20),");
        createUserTable.append(DBdesign.USER_TABLE_INFO_COLUM_EMAIL + " varchar(20),");
        createUserTable.append(DBdesign.USER_TABLE_INFO_COLUM_STATUS + " integer,");
        createUserTable.append(DBdesign.USER_TABLE_INFO_LIMIT + " integer) " );
        db.execSQL(createUserTable.toString());

        StringBuffer createExpenseTable = new StringBuffer();
        createExpenseTable.append("CREATE TABLE IF NOT EXISTS ");
        createExpenseTable.append(DBdesign.EXPENSE_TABLE_NAME + "(");
        createExpenseTable.append(DBdesign.EXPENSE_TABLE_INFO_COLUM_ID + " integer primary key autoincrement ,");
        createExpenseTable.append(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT + " real,");
        createExpenseTable.append(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY + " varchar(20),");
        createExpenseTable.append(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE + " text,");
        createExpenseTable.append(DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + " integer,");
        createExpenseTable.append("FOREIGN KEY(" + DBdesign.EXPENSE_TABLE_INFO_COLUM_USER + ") REFERENCES " + DBdesign.USER_TABLE_NAME + "(" + DBdesign.USER_TABLE_INFO_COLUM_ID + "))");
        db.execSQL(createExpenseTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropUserTable = "drop table if exists "
                + DBdesign.USER_TABLE_NAME;
        String dropExpenseTable = "drop table if exists "
                + DBdesign.EXPENSE_TABLE_NAME;
        db.execSQL(dropUserTable);
        db.execSQL(dropExpenseTable);
        onCreate(db);
    }

}

