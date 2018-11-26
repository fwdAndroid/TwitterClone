package com.example.fawad.twitterclone;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUPActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtRegUser,edtRegEmail,edtRegPass;
    Button btnReg;
    TextView txtGoLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnReg = findViewById(R.id.btnReg);

        edtRegEmail = findViewById(R.id.edtRegEmail);
        edtRegPass = findViewById(R.id.edtRegPass);
        edtRegUser = findViewById(R.id.edtRegUser);

        txtGoLogIn = findViewById(R.id.txtGoLogIn);

        edtRegUser.setOnClickListener(this);
        edtRegPass.setOnClickListener(this);
        edtRegEmail.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        txtGoLogIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnReg:

                final ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("Processing.......");
                pd.show();


                ParseUser user = new ParseUser();
                 user.setUsername(edtRegUser.getText().toString());
                 user.setEmail(edtRegEmail.getText().toString());
                 user.setPassword(edtRegPass.getText().toString());

                 user.signUpInBackground(new SignUpCallback() {
                     @Override
                     public void done(ParseException e) {
                         if (e == null)
                         {
                             Toast.makeText(SignUPActivity.this, "Sucessful Sign Up!,Welcome\"", Toast.LENGTH_SHORT).show();
                         }
                         else
                         {
                             Toast.makeText(SignUPActivity.this, "Sucessful Sign Up!,Failed", Toast.LENGTH_SHORT).show();
                         }
                         pd.dismiss();
                     }
                 });

                break;
            case R.id.txtGoLogIn:
                break;
        }
    }
}
