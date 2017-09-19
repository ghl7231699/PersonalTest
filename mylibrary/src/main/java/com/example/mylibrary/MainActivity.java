package com.example.mylibrary;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn_permission);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionActivity.onRequestPermissionResult(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "权限通过", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String p :
                                deniedPermission) {
                            Toast.makeText(MainActivity.this, "权限被拒绝" + p, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
