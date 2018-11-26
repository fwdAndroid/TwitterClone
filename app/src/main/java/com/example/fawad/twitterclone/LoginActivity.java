package com.example.fawad.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail,edtPass;
    TextView txtGoReg;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtPass = findViewById(R.id.edtPass);
        edtEmail = findViewById(R.id.edtEmail);
        txtGoReg = findViewById(R.id.txtGoReg);
        btnLogin = findViewById(R.id.btnLogin);

        edtEmail.setOnClickListener(this);
        edtPass.setOnClickListener(this);
        txtGoReg.setOnClickListener(this);
        btnLogin.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnLogin:

                final ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("Processing.......");
                pd.show();

                ParseUser.logInInBackground(edtEmail.getText().toString(), edtPass.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {


                        if (user != null)
                        {

                            Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Users.class));

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "UnSuccessfull For Login, Go And Register Your Account", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,SignUPActivity.class));
                        }
                        pd.dismiss();
                    }

                });

                break;
            case R.id.txtGoReg:
                startActivity(new Intent(getApplicationContext(),SignUPActivity.class));
                break;
        }

    }
}
