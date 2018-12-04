
package application.android.com.expencestracker;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;
import application.android.com.expencestracker.DBImp.UserTableImp;
import application.android.com.expencestracker.Model.UserSessionManager;

import static application.android.com.expencestracker.MainActivity.username;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView textView;

    UserTableImp userTableImp;
    EditText editTextLimit;
    private Context _context;
    private UserSessionManager session;
    private static final String KEY_USERID = "USER_ID";
    String user_id;
    ProgressBar progress1;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        textView=(TextView)view.findViewById(R.id.home1);


        this._context = this.getContext();
        this.session = new UserSessionManager(this._context);
        HashMap<String, String> usr = this.session.getUserDetails();
        user_id = usr.get(KEY_USERID);
        userTableImp=new UserTableImp(getContext());


        Bundle b=getArguments();
        String name =b.getString(username);
        textView.setText("Welcome "+ name);
        textView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_animation));

        progress1 = view.findViewById(R.id.pro);
       Progress_Bar();


        TextView total = (TextView)view.findViewById(R.id.textview_total);


        ExpenseDaoImpl expenseDao = new ExpenseDaoImpl(getContext());
        Double totalexpense=expenseDao.sumAmountByUser(Integer.parseInt(user_id));
        total.setText(totalexpense.toString());

        ListView categoryview = (ListView)view.findViewById(R.id.listview_category);

        Cursor expensesummary = expenseDao.AmountsCategoryname(Integer.parseInt(user_id));
        Categoryadapter expenseadapter = new Categoryadapter(this.getContext(),expensesummary);
        categoryview.setAdapter(expenseadapter);

        Button button = (Button) view.findViewById(R.id.buttton_limit);
        editTextLimit = view.findViewById(R.id.editText_limit);
        final Button button_ok = (Button) view.findViewById(R.id.button_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLimit.setVisibility(v.VISIBLE);
                button_ok.setVisibility(v.VISIBLE);
            }
        });
        button_ok .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboard();
                editTextLimit.setVisibility(v.INVISIBLE);
                button_ok.setVisibility(v.INVISIBLE);
                String s = editTextLimit.getText().toString();

                userTableImp.update(Integer.parseInt(user_id), s);
               Progress_Bar();
                Toast.makeText(getContext(), "value for limit:" + s, Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    /*
     * Closes the keyboard
     */
    private void closeKeyboard() {
        View view= getActivity().getCurrentFocus();
        if(view!=null){

            InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }

        }


    /**
     *
     */
       public  void Progress_Bar(){

            UserTableImp db = new UserTableImp(getActivity());
            int limit_expenses = db.limit();
            int sum_expenses = db.sumAmount();
            int per= (sum_expenses*100)/(limit_expenses+1);
            progress1.setMax(100);
            progress1.setProgress(per);
            progress1.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade));
            progress1.getProgress();
            progress1.getProgressDrawable();
            progress1.invalidate();
            
        }


}


class Categoryadapter extends CursorAdapter

{
    public Context context;
    public  Cursor cursor;

    public Categoryadapter(Context context, Cursor cursor)

    {
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.category,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView categoryname = (TextView) view.findViewById(R.id.textview_expense_category);
        TextView categorytotal = (TextView) view.findViewById(R.id.textview_totalamount);


        String expense_category = cursor.getString(2);
        String category_amount = cursor.getString(1);

        categoryname.setText(expense_category);
        categorytotal.setText(category_amount);

    }
    
    
        


}

