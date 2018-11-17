package application.android.com.expencestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import application.android.com.expencestracker.DBImp.UserTableImp;
import application.android.com.expencestracker.Model.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText userInput;
    private EditText emailInput;
    private EditText pwInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signup(View view) {
        userInput = (EditText) findViewById(R.id.editText_SignUpName);
        emailInput = (EditText) findViewById(R.id.editText_SignUpEmail);
        pwInput = (EditText) findViewById(R.id.editText_SignUpPswd);

        String username = userInput.getText().toString().trim();
        String emailname = emailInput.getText().toString().trim();
        String password = pwInput.getText().toString().trim();
        String reg = "\\w+[\\w]*@[\\w]+\\.[\\w]+$";
        if (username.equals("") || username == null) {
            Toast myToast = Toast.makeText(this, "Please input the user name!",
                    Toast.LENGTH_SHORT);
            myToast.show();
            ((EditText) findViewById(R.id.editText_SignUpName)).requestFocus();
        } else if (emailname.equals("") || emailname == null) {
            Toast myToast = Toast.makeText(this, "Please input the email address!",
                    Toast.LENGTH_SHORT);
            myToast.show();
            ((EditText) findViewById(R.id.editText_SignUpEmail)).requestFocus();
        } else if (!emailname.matches(reg)) {
            Toast myToast = Toast.makeText(this, "Email address is illegal!",
                    Toast.LENGTH_SHORT);
            myToast.show();
            ((EditText) findViewById(R.id.editText_SignUpPswd)).requestFocus();
        }else if (password.equals("") || password == null) {
            Toast myToast = Toast.makeText(this, "Please input the password!",
                    Toast.LENGTH_SHORT);
            myToast.show();
            ((EditText) findViewById(R.id.editText_SignUpPswd)).requestFocus();
        } else {
            UserTableImp userDao = new UserTableImp(getBaseContext());
            User user = userDao.getUser(emailname);
            if (user != null) {
                Toast myToast = Toast.makeText(this, "Email address is already used!",
                        Toast.LENGTH_SHORT);
                myToast.show();
                ((EditText) findViewById(R.id.editText_SignUpEmail)).requestFocus();
                ((UserTableImp) userDao).closeDBConnection();
            } else {
                User newUser = new User(username, password, emailname,true);
                userDao.add(newUser);
                Toast myToast = Toast.makeText(this, "Registration Success!",
                        Toast.LENGTH_SHORT);
                myToast.show();
                finish();
            }
        }

    }


    public void cancel(View view) {
        finish();
    }
}

