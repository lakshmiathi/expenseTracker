package application.android.com.expencestracker;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import application.android.com.expencestracker.DBImp.DBdesign;
import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;
import application.android.com.expencestracker.Model.DateUtil;
import application.android.com.expencestracker.Model.Expense;
import application.android.com.expencestracker.Model.UserSessionManager;
import application.android.com.expencestracker.DBImp.DBHelper;



/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment {

    // Retrieving the session details
    private Context _context;
    private UserSessionManager session;
    private static final String KEY_USERID = "USER_ID";
    private ExpenseDaoImpl expensedata;
    private Cursor expesnecursor;
    private View view;
    private static final String TAG = "ExpensesFragment";
    private Dialog Expdialog;
    private String expense_category;
    private String expense_date;
    private String expense_amount;
    private Double totalexpenses;
    private Double amount;
    private ListView list_expenses;
    private TextView total_expenses;
    private TextInputEditText text_Date;
    private TextInputEditText text_Amount;
    private Button button_AddExpense;
    private TextView txt_Close;
    private Button button_Save;
    private int item_id;
    private String user_id;
    private Spinner spinner;
    private TextView displayDate;
    private Calendar calendar;
    private String method_type;
    public  int num;


    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        expensedata = new ExpenseDaoImpl(getContext());

        /* Retrieve the user id from Shared preference */

        this._context = this.getContext();
        this.session = new UserSessionManager(this._context);
        HashMap<String, String> usr = this.session.getUserDetails();
        user_id = usr.get(KEY_USERID);

        /* Retrieve the expense list from the database  */

        expesnecursor = expensedata.getExpenseList(Integer.parseInt(user_id));
        view = inflater.inflate(R.layout.fragment_expenses, container, false);

        list_expenses = (ListView) view.findViewById(R.id.listview_expenses);
        total_expenses = (TextView) view.findViewById(R.id.textview_expensetotal);

        /* Retrieve the total expense for the particular user */

        totalexpenses = expensedata.sumAmountByUser(Integer.parseInt(user_id));
        total_expenses.setText(totalexpenses.toString());

        /* Display of the expenses in the list view using cursor adapter */

        CustomAdapterList adapterexpense = new CustomAdapterList(this.getContext(), expesnecursor);
        list_expenses.setAdapter(adapterexpense);

        list_expenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Alert box to enable user to manage the expense - Delete and Edit the expense
             * @param parent
             * @param view
             * @param position
             * @param id
             */

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog.Builder alert_menu = new AlertDialog.Builder(getContext());
                alert_menu.setTitle("Manage Expense");
                alert_menu.setMessage("User can EDIT or DELETE of the expenses");

                //  Edit the expense data
                alert_menu.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //  to call the add dialog box with the data populated

                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                        cursor.moveToPosition(position);
                        TextView txt_Close;
                        Button button_Save;
                        Expdialog.setContentView(R.layout.dialogexpense);

                        item_id = cursor.getInt(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_ID));
                        expense_category = cursor.getString(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY));
                        amount = cursor.getDouble(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT));
                        expense_date = cursor.getString(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE));
                        spinner = (Spinner) Expdialog.findViewById(R.id.spinner_Category);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Categories));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        int item_position = adapter.getPosition(expense_category);
                        spinner.setSelection(item_position);
                        text_Date = (TextInputEditText) Expdialog.findViewById(R.id.text_Date);
                        text_Date.setText(expense_date);
                        text_Amount = (TextInputEditText) Expdialog.findViewById(R.id.text_Amount);
                        text_Amount.setText(amount.toString());

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            /** Selection of category items from the dropdown
                             * @param parent
                             * @param view
                             * @param position
                             * @param id
                             */
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                expense_category = spinner.getSelectedItem().toString();
                            }

                            /** if no category selected nothing to do.
                             * @param parent
                             */
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        displayDate=(TextView)Expdialog.findViewById(R.id.text_Date);
                        displayDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDatePickerDialog();
                            }
                        });

                        button_Save=(Button) Expdialog.findViewById(R.id.button_AddExpense);
                        button_Save.setText("Save");
                        button_Save.setOnClickListener(new View.OnClickListener() {

                            /** expense amount, date and category edited on click of Edit button.
                             * @param v
                             */
                            @Override
                            public void onClick(View v) {

                                method_type="EDIT";

                                text_Amount = (TextInputEditText) Expdialog.findViewById(R.id.text_Amount);
                                expense_amount= text_Amount.getText().toString();
                                expense_date= displayDate.getText().toString();
                                ExpenseDaoImpl expenseDao = new ExpenseDaoImpl(getContext());

                                if (expense_amount.length() == 0 || Double.parseDouble(expense_amount) < 0) {
                                    text_Amount.setError("Amount can't be empty");
                                } else if (expense_date == null || expense_date == "") {
                                    text_Date.setError("Enter correct date");
                                } else {

                                    expenseDao.updateExpense(item_id, Double.parseDouble(expense_amount), spinner.getSelectedItem().toString(), DateUtil.createDate(expense_date));

                                    Listexpense();
                                    Expdialog.cancel();
                                    showToastSuccess("Expense updated",method_type);


                                }
                            }
                        });

                        txt_Close = (TextView) Expdialog.findViewById(R.id.txt_Close);
                        txt_Close.setOnClickListener(new View.OnClickListener() {

                            /** To close the add expense popup window
                             * @param v
                             */
                            @Override
                            public void onClick(View v) {
                                Expdialog.dismiss();
                            }
                        });

                        Expdialog.show();

                    }
                });



                /*  Alert to delete the selected expense*/
                alert_menu.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {

                    /** To delete the selected expense item from the list
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder deletealert = new AlertDialog.Builder(getContext());
                        deletealert.setTitle("Delete expense item ?");

                        deletealert.setMessage("This will delete the expense item from your list. ");

                        deletealert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.cancel();
                            }
                        });

                        deletealert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            /** Delete the selected expense item  from the list and the database on click of OK option
                             * @param dialog
                             * @param which
                             */
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                cursor.moveToPosition(position);
                                final int item_id = cursor.getInt(cursor.getColumnIndex(DBdesign.EXPENSE_TABLE_INFO_COLUM_ID));

                                expensedata = new ExpenseDaoImpl(getContext());
                                expensedata.delete(item_id);
                                Listexpense();

                            }
                        });
                        deletealert.show();
                    }
                });
                alert_menu.show();
            }
        });


        /* Created dialog window to add the expense*/
        Expdialog = new Dialog(this.getActivity());

        /*Onclick of Floating Action Button opens the popupwindow to add expenses manually.*/
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_Addexpens);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Expdialog.setContentView(R.layout.dialogexpense);
                txt_Close = (TextView) Expdialog.findViewById(R.id.txt_Close);
                spinner = (Spinner) Expdialog.findViewById(R.id.spinner_Category);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Categories));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                /*selected items of category dropdown is assigned to variable to be used as parameter in database add method.*/
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    /** Category selection is required to add new expense
                     * @param parent
                     * @param view
                     * @param position
                     * @param id
                     */
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (parent.getItemAtPosition(position).equals("Select Category")) {
                            text_Date = (TextInputEditText) Expdialog.findViewById(R.id.text_Date);
                            text_Date.setVisibility(View.INVISIBLE);
                            text_Amount = (TextInputEditText) Expdialog.findViewById(R.id.text_Amount);
                            text_Amount.setVisibility(View.INVISIBLE);
                           button_AddExpense = (Button) Expdialog.findViewById(R.id.button_AddExpense);
                            button_AddExpense.setVisibility(View.INVISIBLE);
                        } else {
                           text_Date = (TextInputEditText) Expdialog.findViewById(R.id.text_Date);
                            text_Date.setVisibility(View.VISIBLE);
                            text_Amount = (TextInputEditText) Expdialog.findViewById(R.id.text_Amount);
                            text_Amount.setVisibility(View.VISIBLE);
                           button_AddExpense = (Button) Expdialog.findViewById(R.id.button_AddExpense);
                            button_AddExpense.setVisibility(View.VISIBLE);
                        }
                        expense_category = spinner.getSelectedItem().toString();
                    }

                    /** Nothing to do if nothing selected
                     * @param parent
                     */
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*date picker is displayed to select date.*/
                displayDate = (TextView) Expdialog.findViewById(R.id.text_Date);
                displayDate.setOnClickListener(new View.OnClickListener() {

                    /** Date is selected on click of datepickerdialog
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }
                });

                /*Add button on click saves data into database.*/
                button_AddExpense = (Button) Expdialog.findViewById(R.id.button_AddExpense);
                button_AddExpense.setOnClickListener(new View.OnClickListener() {

                    /**
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {

                       int num= addExpense();

                        if(num==1){
                            ((MainActivity) getActivity()).notifyOnLimit(user_id);
                        }


                        }
                });

                /*on click of "X" closes the popupwindow*/
                txt_Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Expdialog.dismiss();
                        Listexpense();

                    }
                });

                Expdialog.show();
            }
        });


        return view;
    }

    /**
     * Date selected from datepicker popup.
     */
    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener;
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            /** Set date formatt mm/dd/yyyy
             * @param view
             * @param year
             * @param month
             * @param day
             */
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy:" + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                expense_date = date;
                displayDate.setText(date);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        datePickerDialog.show();

    }

    /**
     * Toast message customisation at center position.
     *
     * @param message
     * @param type
     */
    private void showToastSuccess(String message,String type){

        LayoutInflater inflater = getLayoutInflater();
        View toastview = inflater.inflate(R.layout.toastcustomization, (ViewGroup)getView().findViewById(R.id.toast_custom));
        TextView toasttext = toastview.findViewById(R.id.Text_toast);
        toasttext.setText(message);
        Toast toast = new Toast(getActivity());
        if(type.equals("Add")) {
            toast.setGravity(Gravity.CENTER_VERTICAL, 70, 70);
        }
        else
            {
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);

            }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastview);
        toast.show();

    }

    /**
     * To view expenses in list with total expenses shown.
     */
    private void Listexpense() {
        Cursor expesnecursor = expensedata.getExpenseList(Integer.parseInt(user_id));
        CustomAdapterList adapterexpense = new CustomAdapterList(this.getContext(), expesnecursor);
        adapterexpense.changeCursor(expesnecursor);
        list_expenses.setAdapter(adapterexpense);
        TextView total_expenses = (TextView) view.findViewById(R.id.textview_expensetotal);
        total_expenses.clearComposingText();
        totalexpenses = expensedata.sumAmountByUser(Integer.parseInt(user_id));
        total_expenses.setText(totalexpenses.toString());
    }


    /**
     * To add new expenses.
     */
    private int addExpense() {

       text_Amount = (TextInputEditText) Expdialog.findViewById(R.id.text_Amount);
        method_type = "Add";
       text_Date = (TextInputEditText) Expdialog.findViewById(R.id.text_Date);
        expense_amount = text_Amount.getText().toString();
        expense_date=text_Date.getText().toString();


        if (expense_amount.length() == 0 || Double.parseDouble(expense_amount) < 0) {
            text_Amount.setError("Amount can't be empty");
            num =0;
        } else if (expense_date.length() == 0 || expense_date == "") {
            text_Date.setError("Enter correct date");
            num=0;
        } else {
            Expense expenserecord = new Expense(Double.parseDouble(expense_amount), expense_category, DateUtil.createDate(expense_date), Integer.parseInt(user_id));
            expensedata.add(expenserecord);
            spinner.setSelection(0);
            showToastSuccess("Expense added",method_type);
            text_Amount.getText().clear();
            text_Date.setText("");
            expense_date = "";
            expense_amount="";
            num=1;
        }
        return num;
    }

}


