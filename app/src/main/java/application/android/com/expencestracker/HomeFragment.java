package application.android.com.expencestracker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import application.android.com.expencestracker.Model.UserSessionManager;

import static application.android.com.expencestracker.MainActivity.username;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView textView;



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





        // Inflate the layout for this fragment
        return view;

        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void putUserName(Bundle bundle){
        String name= bundle.getString("username");
        textView.setText("madhu");
    }

}
