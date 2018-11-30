package application.android.com.expencestracker.DBImp;

/**
 * The DBdesign class is used to store the database name, tables' name and columns'
 * name in database.
 *
 * @author
 * @version
 *
 */
public class DBdesign {
    public static final String DBNAME = "extrack.db";
    public static final String USER_TABLE_NAME = "user_record";
    public static final String USER_TABLE_INFO_COLUM_ID = "userid";
    public static final String USER_TABLE_INFO_COLUM_USERNAME = "username";
    public static final String USER_TABLE_INFO_COLUM_PASSWORD = "password";
    public static final String USER_TABLE_INFO_COLUM_EMAIL = "email";
    public static final String USER_TABLE_INFO_COLUM_STATUS = "status";
    public static final String USER_TABLE_INFO_LIMIT ="limit1";
    public static final String EXPENSE_TABLE_NAME = "user_expense_record";
    public static final String EXPENSE_TABLE_INFO_COLUM_ID = "expenseid";
    public static final String EXPENSE_TABLE_INFO_COLUM_AMOUNT = "amount";
    public static final String EXPENSE_TABLE_INFO_COLUM_CATEGORY = "category";
    public static final String EXPENSE_TABLE_INFO_COLUM_DATE = "date";
    public static final String EXPENSE_TABLE_INFO_COLUM_USER = "user";
}
