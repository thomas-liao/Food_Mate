package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_fullname) EditText _fullnameText;
    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.input_location) EditText _locationText;
    @BindView(R.id.input_bio) EditText _bioText;
    @BindView(R.id.input_email) EditText _emailText;

    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onSignupFailed();
                    return;
                }
                _signupButton.setEnabled(false);
                onSignupSuccess();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Signup finished", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String fullname = _fullnameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String location = _locationText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String bio = _bioText.getText().toString();


        if (fullname.isEmpty() || fullname.length() < 3) {
            _fullnameText.setError("Enter valid Full Name");
            valid = false;
        } else {
            _fullnameText.setError(null);
        }

        if (fullname.isEmpty() || fullname.length() < 3) {
            _usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (location.isEmpty()) {
            _locationText.setError("Enter Valid Location");
            valid = false;
        } else {
            _locationText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        if (bio.isEmpty()){
            _bioText.setError("Tell us about you!");
            valid = false;
        }else{
            _bioText.setError(null);
        }

        return valid;
    }
}
