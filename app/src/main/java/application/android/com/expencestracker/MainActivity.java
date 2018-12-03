package application.android.com.expencestracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;

import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;
import application.android.com.expencestracker.DBImp.UserTableImp;
import application.android.com.expencestracker.Model.UserSessionManager;

;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID=1;

    public static final String CHANNEL_ID ="personal Notification";

    private static final String KEY_USERNAME = "USER_NAME";
    private BottomNavigationView mainnav;
    private HomeFragment homeFragment;
    private ExpensesFragment expensesFragment;
    private StatisticsFragment statisticsFragment;
    private AboutUsFragment aboutUsFragment;
    private BarFragment barFragment;

    private FrameLayout mainFrame;
    public static String username;
    private EditText emailInput;
    private EditText pwInput;
    private Context _context;
    private UserSessionManager session;
    String user_id;
    String user_name;
    double value;
    String limit;
    HomeFragment setHomeFragment;
    UserTableImp userTableImp;
    /*
    Getting the limit from database and issue notification, if exceeding the limits
    @param user_id the id of the user
     */
    public void notifyOnLimit(String user_id) {
        if(user_id != null) {
            userTableImp = new UserTableImp(this);
            String limit = userTableImp.getLimit(Integer.parseInt(user_id));
            if(limit != null) {
                value = Integer.parseInt(limit);

                ExpenseDaoImpl expenseDao = new ExpenseDaoImpl(this);
                Double totalexpense = expenseDao.sumAmountByUser(Integer.parseInt(user_id));


                if (value < totalexpense) {
                    createNotificationChannel();
                    creatNotification();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this._context = this.getApplicationContext();
        this.session = new UserSessionManager(this._context);

        if (!this.session.CheckLogIn()) {
            this.session.logoutUser();
        }

        HashMap<String, String> usr  = this.session.getUserDetails();
        user_id = usr.get(getResources().getString(R.string.KEY_USERID));
        user_name = usr.get(getResources().getString(R.string.KEY_USERNAME));

        TextView tv = (TextView) findViewById(R.id.tv_UserId);
        homeFragment= new HomeFragment();
        statisticsFragment = new StatisticsFragment();
        expensesFragment= new ExpensesFragment();
        aboutUsFragment = new AboutUsFragment();
        barFragment= new BarFragment();

        setFragment(homeFragment, "HOME_FRAGMENT");
        Bundle bundle=new Bundle();
        bundle.putString(username,user_name);
        setHomeFragment=new HomeFragment();
        homeFragment.setArguments(bundle);

        mainnav=(BottomNavigationView)findViewById(R.id.main_nav);
        mainFrame=(FrameLayout)findViewById(R.id.main_frame);
        mainnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home:
                        // mainnav.setItemBackgroundResource(R.color.colorPrimaryDark);

                        setFragment(homeFragment, "HOME_FRAGMENT");
                        return true;
                    case R.id.Expenses:
                        setFragment(expensesFragment, "EXPENSE_FRAGMENT");
                        //mainnav.setItemBackgroundResource(R.color.nav_colors);
                        return true;
                    case R.id.Statistics:
                        setFragment(statisticsFragment, "STAT_FRAGMENT");
                        return true;
                    case R.id.BarChart:
                        setFragment(barFragment, "BAR_FRAGMENT");
                        return true;
                    case R.id.AboutUs:
                        setFragment(aboutUsFragment, "ABOUTUS_FRAGMENT");
                        return true;
                    default:
                        return false;
                }

            }
        });



    }

    /**
     * Creates notification channel
     */
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "personal Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    /**
     *  Invokes the notification by using notification manager
     */
    public void creatNotification(){
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(getApplicationContext(),2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.expenses)

                .setContentTitle("You Exceeded your Limit of Expenses")
                .setContentText("Please check your ExTrack to see your expenses")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }


    /**
     * @param fragment as input
     * Navigation between fragments
     */

    private void setFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment, fragmentTag);
        fragmentTransaction.commit();
        Bundle bundle = new Bundle();
        bundle.putString("ID",String.valueOf(user_id));
        bundle.putString(username,user_name);
        fragment.setArguments(bundle);
    }


    public String setLimit(String s)  {
        limit = s;
        return limit;
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
            Intent intent= new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.action_LogOut){
            session.logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

