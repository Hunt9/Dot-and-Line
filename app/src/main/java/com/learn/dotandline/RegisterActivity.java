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
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.learn.dotandline.Model.Register;

public class RegisterActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etname, etPhone, etPassword, etConfirmPassword, etEmail;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvLogin = findViewById(R.id.Login);
        etname =findViewById(R.id.Name);
        etPhone =findViewById(R.id.Phone);
        etPassword =findViewById(R.id.Password);
        etConfirmPassword =findViewById(R.id.ConfirmPassword);
        etEmail =findViewById(R.id.Email);
        btnRegister =findViewById(R.id.Register);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Password Does Not Matched!", Toast.LENGTH_SHORT).show();
                }else
                {
                    register(etname.getText().toString(),etPhone.getText().toString(),etPassword.getText().toString(),etEmail.getText().toString());
                }

            }
        });

    }

    private void register(String name, String phoneNumber, String password,String email) {

        AndroidNetworking.post("http://qa.homechef.pk/api/v1/register")
                .addBodyParameter("name", name)
                .addBodyParameter("mobile", phoneNumber)
                .addBodyParameter("password", password)
                .addBodyParameter("email", email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Register.class, new ParsedRequestListener<Register>(){
                    @Override
                    public void onResponse(Register response) {
                        if (response!=null)
                        {
                            if (response.getMessage().equals("Please enter all required fields."))
                            {
                                Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
