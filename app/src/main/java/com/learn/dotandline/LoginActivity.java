package com.learn.dotandline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.learn.dotandline.Helper.AppHelper;
import com.learn.dotandline.Model.Login;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegister;
    private EditText etPhone,etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegister = findViewById(R.id.Register);
        etPhone = findViewById(R.id.Phone);
        etPassword = findViewById(R.id.Password);
        btnLogin = findViewById(R.id.Login);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(etPhone.getText().toString(),etPassword.getText().toString());
            }
        });


    }

    private void signIn(String email, String password) {

        AndroidNetworking.get(AppHelper.Login)
                .addQueryParameter("Email", email)
                .addQueryParameter("Password", password)
                .addQueryParameter("Signature", AppHelper.getSignature(email,password))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Login.class, new ParsedRequestListener<Login>(){
//                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(Login response) {
                        if (response!=null)
                        {
                            if (response.getToken()!=null)
                            {
                                Toast.makeText(LoginActivity.this,"Login Successfull" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,TransactionActivity.class));
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,"Invalid Credentials" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
