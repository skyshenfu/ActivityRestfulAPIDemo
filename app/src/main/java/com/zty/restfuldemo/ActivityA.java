package com.zty.restfuldemo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Iterator;
import java.util.Map;

public class ActivityA extends AppCompatActivity  implements View.OnClickListener{
    Button toActivityBButton,cameraPermissionButton,manyPermissionButton;
    //正常startActivity跳转到B
    private ActivityResultLauncher<Intent> launcherToB;
    //申请单个权限
    private ActivityResultLauncher<String> launcherToCameraPermission;
    //申请多个权限
    private ActivityResultLauncher<String[]> launcherToManyPermission;
    public static final String PARAM_NAME="PARAM_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        toActivityBButton=findViewById(R.id.to_b_button);
        cameraPermissionButton=findViewById(R.id.to_camera_permission_button);
        manyPermissionButton=findViewById(R.id.to_write_and_read_permission_button);
        launcherToB =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK){
                    int data=result.getData().getIntExtra(PARAM_NAME,0);
                    Log.e("TAG", "onActivityResult: "+data );
                }
            }
        });

        launcherToCameraPermission=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    Log.e("TAG", "onActivityResult: 得到权限" );
                }else{
                    Log.e("TAG", "onActivityResult: 未到权限" );

                }
            }
        });
        launcherToManyPermission=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Iterator<String> iterator = result.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Boolean value = result.get(key);
                    Log.e("TAG", "key = " + key + ", value = " + value);
                }
            }
        });
        toActivityBButton.setOnClickListener(this);
        cameraPermissionButton.setOnClickListener(this);
        manyPermissionButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_b_button:
                Intent  intent =new Intent(this,ActivityB.class);
                launcherToB.launch(intent);
                break;
            case R.id.to_camera_permission_button:
                launcherToCameraPermission.launch(Manifest.permission.CAMERA);
                break;
            case R.id.to_write_and_read_permission_button:
                launcherToManyPermission.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
                break;
        }
    }
}