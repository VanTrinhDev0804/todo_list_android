package com.example.listtodo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.listtodo.R;
import com.example.listtodo.fragments.AccountFragment;
import com.example.listtodo.fragments.EventsFragment;
import com.example.listtodo.fragments.HomeFragment;
import com.example.listtodo.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    private int maKH= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        lấy mã khách từ login cho main  quản lý
        Intent incomingIntent = getIntent();
        int MaKHlogin = incomingIntent.getIntExtra("maKH",0);
        maKH =MaKHlogin;


//        bottom navigation cho 4 fragment Home , Event , Account , Setting
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container ,new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_search:
                        fragment = new EventsFragment();
                        break;

                    case R.id.nav_account:
                        fragment = new AccountFragment();
                        break;

                    case R.id.nav_setting:
                        fragment = new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container ,fragment).commit();

                return true;
            }
        });
    }

//    dùng để truyền mã cho các fragment
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
}