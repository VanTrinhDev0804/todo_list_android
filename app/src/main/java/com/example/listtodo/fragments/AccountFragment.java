package com.example.listtodo.fragments;

import android.app.AlertDialog;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listtodo.R;
import com.example.listtodo.dataBase.Database;
import com.example.listtodo.views.CreateTask;
import com.example.listtodo.views.LoginActivity;
import com.example.listtodo.views.MainActivity;

import java.sql.Blob;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Database db;
    TextView txtEmail , txtName;
    ImageView imageViewAccount;
    ConstraintLayout changeInfor, changePass, Logout;
    private MainActivity mainActivity;
    private String email,name,pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mainActivity= (MainActivity) getActivity();
        txtEmail = view.findViewById(R.id.profile_email);
        txtName = view.findViewById(R.id.profile_name);
//        imageViewAccount = view.findViewById(R.id.imageAcc);
        changeInfor = view.findViewById(R.id.btnchange_info);
        changePass = view.findViewById(R.id.btnchange_pass);
         Logout = view.findViewById(R.id.btnLog_out);

// lấy thông tin khách đổ vào email và tên
        getDataUser();
        txtEmail.setText(email);
        txtName.setText(name);

//        thoát trở ra login
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                startActivity(logout);
            }
        });
//          đổi email tên
        changeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInfor();

            }
        });

//        đổi mật khẩu
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassWord();
            }
        });

        return  view;
    }
// hàm lấy thông tin khách
    private void getDataUser() {
        try{
            db = new Database(AccountFragment.this.getContext());
            int maKH = mainActivity.getMaKH();
            Cursor c = db.query_with_result("select * from KhachHang where maKH = '"+maKH+"'");
            while (c.moveToNext()){
                name = c.getString(1);
                email = c.getString(2);
                pass = c.getString(3);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//    hàm đổi mật khẩu
    private void ChangePassWord(){
        final EditText CurrPass, NewPass, ConfirmPass;
        TextView txtCurr, txtNew, txtConFirm;
        LinearLayout btnExit, btnSaveChange;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AccountFragment.this.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView =inflater.inflate(R.layout.change_password, null);

        CurrPass = dialogView.findViewById(R.id.CurrPassword);
        NewPass =dialogView.findViewById(R.id.newPass);
        ConfirmPass = dialogView.findViewById(R.id.confirmPas);

        txtCurr = dialogView.findViewById(R.id.txtCurrPass);
        txtNew= dialogView.findViewById(R.id.txtNewPass);
        txtConFirm = dialogView.findViewById(R.id.txtConFirmpass);
        btnExit = dialogView.findViewById(R.id.btnExitUpdatePass);
        btnSaveChange = dialogView.findViewById(R.id.btnSaveUpdatePass);



        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("");
        AlertDialog b= dialogBuilder.create();
        b.show();

//thoát
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

        // lưu lại sau khi đổi
        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDataUser();
                String CurrentPass = CurrPass.getText().toString();
                String Newpass = NewPass.getText().toString();
                String Confirm = ConfirmPass.getText().toString();


                if (pass.equalsIgnoreCase(CurrentPass) && !pass.equalsIgnoreCase(Newpass)
                        && !Newpass.equalsIgnoreCase("")&& !Confirm.equalsIgnoreCase("")
                        && Confirm.equalsIgnoreCase(Newpass)) {
                                int maKH = mainActivity.getMaKH();
                                UpdatePass(maKH, Newpass);
                                b.dismiss();
                    Toast.makeText(getContext(), "update password success", Toast.LENGTH_SHORT).show();
                } else if (Newpass.equalsIgnoreCase("") || Confirm.equalsIgnoreCase("")) {
                        txtNew.setText("*"); // tách các trường hợp ở trên để thông báo lỗi
                        txtConFirm.setText("*");
                } else if(!Newpass.equalsIgnoreCase(Confirm)){
                        txtConFirm.setText("not the same new password");

                }else if(pass.equalsIgnoreCase(Newpass)) {
                    txtNew.setText("same old password");
                }

                else {
                        txtCurr.setText("'password is incorrect'");
                }
            }
        });

//        clear notification
        EventEditTextChange(CurrPass,txtCurr);
        EventEditTextChange(NewPass,txtNew);
        EventEditTextChange(ConfirmPass,txtConFirm);

    }

// hàm update pass
    private void UpdatePass(int ma, String newpass) {
        db = new Database(AccountFragment.this.getContext());
        db.upDatePassWord(ma,newpass);
        db.close();

    }


// hàm updat infor
    private void ChangeInfor() {
        final EditText txtUpdateName , txtUpdateEmail;
        LinearLayout btnExit, btnSaveChange;


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AccountFragment.this.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView =inflater.inflate(R.layout.change_info, null);

        txtUpdateName = dialogView.findViewById(R.id.updateUsers);
        txtUpdateEmail = dialogView.findViewById(R.id.updateEmail);
        btnExit = dialogView.findViewById(R.id.btnExitUpdateInfor);
        btnSaveChange = dialogView.findViewById(R.id.btnSaveChangeInfo);

        //lấy thông tin đổ vào update
        getDataUser();
        txtUpdateName.setText(name);
        txtUpdateEmail.setText(email);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("");
        AlertDialog b= dialogBuilder.create();
        b.show();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDataUser();
                Toast.makeText(AccountFragment.this.getContext(), "Update success !!", Toast.LENGTH_SHORT).show();
                b.dismiss();
            }

            private void UpdateDataUser() {
                String nameUpdate = txtUpdateName.getText().toString();
                String emailUpdate = txtUpdateEmail.getText().toString();
                txtEmail.setText(emailUpdate);
                txtName.setText(nameUpdate);
                try{
                    db = new Database(AccountFragment.this.getContext());
                    int maKH = mainActivity.getMaKH();
                    db.upDateInfoUser(maKH, nameUpdate, emailUpdate);
                    db.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
// hàm clear các thông báo khi nhập sai và bắt dầu nhập lại
    private void EventEditTextChange(EditText editText, TextView textView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText("");
            }
        });
    }


}