package application.android.com.expencestracker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;
import application.android.com.expencestracker.Model.Expense;
import application.android.com.expencestracker.Model.ExpenseAdapter;
import application.android.com.expencestracker.Model.UserSessionManager;

import static application.android.com.expencestracker.MainActivity.username;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment {

    Context c;
    private UserSessionManager session;

    public ExpensesFragment() {
        // Required empty public constructor'

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*Bundle bundle = getArguments();
        String s = bundle.getString("ID");*/
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        c = this.getActivity();
        this.session = new UserSessionManager(c);
        HashMap<String, String> usr  = this.session.getUserDetails();
        int user_id = Integer.parseInt(usr.get(getResources().getString(R.string.KEY_USERID)));


        // Inflate the layout for this fragment




        ExpenseDaoImpl imp = new ExpenseDaoImpl(c);
        List<Expense> itemList = imp.getExpenseList(user_id);
        ListView listView = (ListView) view.findViewById(R.id.Explistview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item_view = (TextView) view.findViewById(R.id.item_Id);
                String itemId = item_view.getText().toString();
                System.out.println(itemId);
                //Intent objIndent = new Intent(getApplicationContext(), ExpenseDetailActivity.class);
                /*objIndent.putExtra("username", username);
                objIndent.putExtra("expense_Id", Integer.parseInt(itemId));*/
                //startActivity(objIndent);
            }
        });
        ExpenseAdapter adapter = new ExpenseAdapter(this.getActivity(), R.layout.listview_entry, itemList);
        listView.setAdapter(adapter);
        return view;

    }




}
