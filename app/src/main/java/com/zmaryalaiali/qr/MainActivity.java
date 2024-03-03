package com.zmaryalaiali.qr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frameLayout,new QRGeneratFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.qrCode_generate) {
                    Toast.makeText(MainActivity.this, "generate clicked", Toast.LENGTH_SHORT).show();
                    fragment = new QRGeneratFragment();
                } else if (R.id.qrCode_scann == item.getItemId()) {
                    Toast.makeText(MainActivity.this, "scanner clicked", Toast.LENGTH_SHORT).show();
                    fragment = new QRScannFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout,fragment).commit();

                return true;
            }
        });
    }
}