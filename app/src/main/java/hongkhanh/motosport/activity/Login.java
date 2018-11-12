package hongkhanh.motosport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hongkhanh.motosport.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    TextView tvRegister;

    public Login() {
    }

    EditText edtPassword, edtUsername;

//    public static ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initControl();
        initEvent();
        initDisplay();
    }

    private void initData() {

    }

    private void initControl() {
        btnLogin = findViewById(R.id.btn_login);

        tvRegister = findViewById(R.id.tv_register);
        edtPassword = findViewById(R.id.edt_password);
        edtUsername = findViewById(R.id.edt_ussername);

    }

    private void initEvent() {
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    private void initDisplay() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login:{
                Login();
                break;
            }
            case R.id.tv_register:{
                tvRegister.setTextColor(0xFFFF3300);
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void Login() {
        String email = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Check for empty data in the form
        if (!email.isEmpty() && !password.isEmpty()) {
            // login user
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }




}

