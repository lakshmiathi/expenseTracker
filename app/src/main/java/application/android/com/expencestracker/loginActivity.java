package application.android.com.expencestracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import application.android.com.expencestracker.Model.UserSessionManager;

public class loginActivity extends AppCompatActivity {

    private UserSessionManager session;

    private EditText emailInput;
    private EditText pwInput;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        this._context = this.getApplicationContext();
        this.session = new UserSessionManager(this._context);

        emailInput =(EditText) findViewById(R.id.editText_Email);
        pwInput=(EditText) findViewById(R.id.editText_Password);

        // Find the Sign in Button.
        Button btnSignIn = (Button) findViewById(R.id.button_SignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String email_id = emailInput.getText().toString();
                String user_pwd = pwInput.getText().toString();
                session.createUserLoginSession(email_id, user_pwd);
                if (session.CheckLogIn()) {
                    // Switch to the Main Acitivity.
                    Intent i = new Intent(_context, MainActivity.class);
                    // Closing all the activities.
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // Add new flag.
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
/*
                    i.putExtra("USER_ID", email_id);
                    i.putExtra("USER_PWD", user_pwd);*/
                    // Now Start the Activity.
                    _context.startActivity(i);
                } else {
                    Toast.makeText(_context, "Incorrect User name or password", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Find the Sign up Button.
        TextView tvSignUp = (TextView) findViewById(R.id.textView_SignUp);
        // Start the Sign up Acitivity.
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newIntent = new Intent(_context, SignUpActivity.class);
                startActivity(newIntent);
            }
        });
    }
}

