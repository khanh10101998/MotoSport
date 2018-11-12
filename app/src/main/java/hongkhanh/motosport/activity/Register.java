package hongkhanh.motosport.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hongkhanh.motosport.R;
import hongkhanh.motosport.asynctask.RegisterUserTask;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = Register.class.getSimpleName();
    EditText edtFullName,edtPhone, edtEmail, edtPassword, edtAdress;
    private ProgressDialog pDialog;
    Button btnRegister;
    RegisterUserTask registerUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initControl();
        initData();
        initEvent();
        initDisplay();


    }

    private void initControl() {
        edtFullName = findViewById(R.id.edt_fullname);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_register);
        edtAdress = findViewById(R.id.edt_adresss);

    }
    private void initData() {
        registerUserTask = new RegisterUserTask(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }
    private void initEvent() {
        btnRegister.setOnClickListener(this);
    }
    private void initDisplay() {

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
            pDialog.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                RegisTer();
                break;

        }
    }

    private void RegisTer() {
        String name = edtFullName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String andress = edtAdress.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            registerUserTask.execute("register",name, phone, email, password, andress);
            Log.d("HongKhanh", "name: " +name + "|phone: "+ phone +"|email: "+email+"|pass: "+password+"|andress: "+andress);
            pDialog.setMessage("Registering ...");
            showDialog();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }
}


