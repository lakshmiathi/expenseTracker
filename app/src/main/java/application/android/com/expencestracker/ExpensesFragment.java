package application.android.com.expencestracker;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;
import application.android.com.expencestracker.Model.DateUtil;
import application.android.com.expencestracker.Model.Expense;
import application.android.com.expencestracker.Model.UserSessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment {
    private static final String TAG = "ExpensesFragment";
    Dialog Expdialog;
    public String expense_category;
    public String expense_date;
    ExpenseDaoImpl expensedata;
    private Context _context;
    private UserSessionManager session;
    private static final String KEY_USERID = "USER_ID";
    String user_id;
    Spinner spinner;
    Date expense_date_record;
    TextView displayDate;
    Calendar calendar;


    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        this._context = this.getContext();
        this.session = new UserSessionManager(this._context);
        HashMap<String, String> usr  = this.session.getUserDetails();
        Log.d("Check ","check "+usr.get(KEY_USERID));
        user_id = usr.get(KEY_USERID);
        expensedata = new ExpenseDaoImpl(getContext());


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        Expdialog = new Dialog(this.getActivity());
        //Onclick of Floating Action Button opens the popupwindow to add expenses manually.

        FloatingActionButton floatingActionButton=(FloatingActionButton)view.findViewById(R.id.fab_Addexpens);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt_Close;
                EditText expense_amount;
                Button button_Add;
                //final TextView displayDate;
                Expdialog.setContentView(R.layout.dialogexpense);
                txt_Close = (TextView) Expdialog.findViewById(R.id.txt_Close);



                spinner = (Spinner) Expdialog.findViewById(R.id.spinner_Category);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Categories));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                //selected items of category dropdown is assigned to variable to be used as parameter in db add method.
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if(parent.getItemAtPosition(position).equals("Select Category"))
                        {
                            TextView text_Date = (TextView)Expdialog.findViewById(R.id.text_Date);
                            text_Date.setVisibility(View.INVISIBLE);
                            EditText expense_amount = (EditText) Expdialog.findViewById(R.id.editText_Amount);
                            expense_amount.setVisibility(View.INVISIBLE);
                            Button button_Add=(Button) Expdialog.findViewById(R.id.button_Add);
                            button_Add.setVisibility(View.INVISIBLE);

                        }else
                        {
                            TextView text_Date = (TextView)Expdialog.findViewById(R.id.text_Date);
                            text_Date.setVisibility(View.VISIBLE);
                            EditText expense_amount = (EditText) Expdialog.findViewById(R.id.editText_Amount);
                            expense_amount.setVisibility(View.VISIBLE);
                            Button button_Add=(Button) Expdialog.findViewById(R.id.button_Add);
                            button_Add.setVisibility(View.VISIBLE);
                        }

                        expense_category = spinner.getSelectedItem().toString();
                        Log.d("expense_category","expense_category"+expense_category);
                        //  Toast.makeText(getActivity(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                //date picker is displayed to select date.
              displayDate=(TextView)Expdialog.findViewById(R.id.text_Date);
                displayDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }


                });

                //Add button on click saves data into database.
                button_Add=(Button) Expdialog.findViewById(R.id.button_Add);
                button_Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText expense_amount = (EditText)Expdialog.findViewById(R.id.editText_Amount);
                        TextView expense_dt = (TextView) Expdialog.findViewById(R.id.text_Date);
                        String exp=expense_amount.getText().toString();

                        Log.d("Record check","records are "+ exp + expense_category + expense_date_record + user_id);
                        //Expense expenserecord = new Expense();
                        if(exp.length()==0 || Double.parseDouble(exp)< 0){
                            Toast.makeText(getActivity(),"Enter correct amount!", Toast.LENGTH_SHORT).show();
                        }else if(expense_date==null|| expense_date == ""){
                            Toast.makeText(getActivity(),"Enter correct date!", Toast.LENGTH_SHORT).show();
                        }else{
                            Expense expenserecord = new Expense(Double.parseDouble(exp), expense_category, DateUtil.createDate(expense_date), Integer.parseInt(user_id));
                            expensedata.add(expenserecord);
                            spinner.setSelection(0);
                            Toast.makeText(getActivity(),"Expense added", Toast.LENGTH_SHORT).show();
                            expense_amount.getText().clear();
                            expense_dt.setText("");


                        }



                    }

                });

                //on click of "X" closes the popupwindow*/
                txt_Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Expdialog.dismiss();
                    }
                });

                Expdialog.show();
            }

        });




        return view;
    }


    public void initaldate(){
        showDatePickerDialog();
    }



    private void showDatePickerDialog() {

        //Calendar calendar= Calendar.getInstance();
        calendar= Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener;
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy:" + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                expense_date = date;
                displayDate.setText(date);
            }
        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener, year, month,day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        datePickerDialog.show();

    }


}


