package application.android.com.expencestracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import application.android.com.expencestracker.Model.UserSessionManager;

public class MainActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    private static final String KEY_ID = "USER_ID";
    private static final String KEY_PWD = "USER_PWD";
    private static final String KEY_USERNAME = "USER_NAME";

    private EditText emailInput;
    private EditText pwInput;

    private Context _context;
    private UserSessionManager session;
    String user_id;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this._context = this.getApplicationContext();
        this.session = new UserSessionManager(this._context);

        if (!this.session.CheckLogIn()) {
            this.session.logoutUser();
        }

        HashMap<String, String> usr  = this.session.getUserDetails();
        //user_id = usr.get(KEY_ID);
        user_name = usr.get(KEY_USERNAME);

        TextView tv = (TextView) findViewById(R.id.tv_UserId);
        tv.setText("HomePage " + user_name);

        Button btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }

}
