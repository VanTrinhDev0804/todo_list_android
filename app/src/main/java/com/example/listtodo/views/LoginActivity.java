package com.example.listtodo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listtodo.R;
import com.example.listtodo.dataBase.Database;
import com.example.listtodo.fragments.AccountFragment;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    Database db;

    EditText lgEmail, lgPass;
    LinearLayout login, register;
    private int mDay, mMonth, mYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lgEmail = findViewById(R.id.lgEmail);
        lgPass = findViewById(R.id.lgPW);

        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = lgEmail.getText().toString();
                String mPass = lgPass.getText().toString();
                db = new Database(LoginActivity.this);
                Cursor c = db.query_with_result("select * from KhachHang");
                while (c.moveToNext()){
                    int idKH = c.getInt(0);
                    String emailData = c.getString(2);
                    String passData = c.getString(3);
                   if(mEmail.equalsIgnoreCase(emailData)&& mPass.equalsIgnoreCase(passData)){
                       Intent itMain = new Intent(LoginActivity.this, MainActivity.class);
                       itMain.putExtra("maKH",idKH);
                       startActivity(itMain);
                       lgEmail.setText("");
                       lgPass.setText("");
                       break;
                   }
                   else {
                       Toast.makeText(LoginActivity.this, "Please check your input Email or Password", Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

    }

    private void registerAccount() {

        final EditText edName, edBirthDay, edEmail,edPassWord, edConfirm;

        RadioButton radMale ,radFemale;
        LinearLayout btnAceptRegister;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView =inflater.inflate(R.layout.register, null);

        edName= dialogView.findViewById(R.id.etName);
        edBirthDay= dialogView.findViewById(R.id.etBD);
        edEmail= dialogView.findViewById(R.id.etEmail);
        edPassWord= dialogView.findViewById(R.id.edPW);
        edConfirm= dialogView.findViewById(R.id.edCPW);
        radMale = dialogView.findViewById(R.id.radMale);
        radFemale = dialogView.findViewById(R.id.radFemale);
        btnAceptRegister = dialogView.findViewById(R.id.btnAccepRegis);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("");
        AlertDialog b= dialogBuilder.create();
        b.show();
//      tạo select cho ngày sinh
        edBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(LoginActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edBirthDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
// dang kí
            btnAceptRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( checkInput(edName, "your name")
                            && checkInput(edBirthDay,"your birth day")
                            && checkInput(edEmail,"your email")
                            && checkInput(edPassWord," password")
                            && checkInput(edConfirm,"confirm password")) {
                        try {
                            db = new Database(getApplicationContext());
                            String name = edName.getText().toString();
                            String birthDay = edBirthDay.getText().toString();
                            String email = edEmail.getText().toString();
                            String pass = edPassWord.getText().toString();
                            String passCPM = edConfirm.getText().toString();
                            String gender= null;
                            if (radMale.isChecked()){
                                gender = "Male";
                            }
                            else if (radFemale.isChecked()){
                                gender ="Female";
                            }

                            if (pass.equalsIgnoreCase(passCPM)) {
                                db.addAccount(name, email, pass, birthDay,gender);
                                db.close();
                                b.dismiss();
                                Toast.makeText(getApplicationContext(), "Register success!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please check confirm you password ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        checkInput(edName, "your name");
                        checkInput(edBirthDay,"your birth day");
                        checkInput(edEmail,"your email") ;
                        checkInput(edPassWord," password");
                        checkInput(edConfirm,"confirm password");
                    }
                }
            });
        }

    private boolean checkInput(EditText editText, String message) {
        if(editText.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter a valid "+message, Toast.LENGTH_SHORT).show();
            return false;
        }
        else return  true;

    }

}