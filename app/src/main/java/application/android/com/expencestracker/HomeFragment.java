
package application.android.com.expencestracker;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;
import application.android.com.expencestracker.Model.UserSessionManager;

import static application.android.com.expencestracker.MainActivity.username;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView textView;
    private Context _context;
    private UserSessionManager session;
    private static final String KEY_USERID = "USER_ID";



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        textView=(TextView)view.findViewById(R.id.home1);
        //String name= this.getString("username");

        Bundle b=getArguments();
        String name =b.getString(username);
        textView.setText("Welcome "+ name);
        textView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_animation));
        //Animation anime = AnimationUtils.loadAnimation(getContext(),R.anim.bounce_interpolator);
        //textView.startAnimation(anime);

        this._context = this.getContext();
        this.session = new UserSessionManager(this._context);
        HashMap<String, String> usr = this.session.getUserDetails();
        String user_id = usr.get(KEY_USERID);
        TextView total = (TextView)view.findViewById(R.id.textview_total);


        ExpenseDaoImpl expenseDao = new ExpenseDaoImpl(getContext());
        Double totalexpense=expenseDao.sumAmountByUser(Integer.parseInt(user_id));
        total.setText(totalexpense.toString());

        ListView categoryview = (ListView)view.findViewById(R.id.listview_category);

        Cursor expensesummary = expenseDao.AmountsCategoryname(Integer.parseInt(user_id));
        Categoryadapter expenseadapter = new Categoryadapter(this.getContext(),expensesummary);
        categoryview.setAdapter(expenseadapter);

        // Inflate the layout for this fragment
        return view;

        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void putUserName(Bundle bundle){
        String name= bundle.getString("username");
        textView.setText("madhu");
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

