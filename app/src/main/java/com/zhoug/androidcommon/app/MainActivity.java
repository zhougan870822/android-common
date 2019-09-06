package com.zhoug.androidcommon.app;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhoug.android.common.utils.AppUtils;
import com.zhoug.android.common.utils.BitmapUtils;
import com.zhoug.android.common.utils.FileUtils;
import com.zhoug.android.common.utils.IOUtils;
import com.zhoug.android.common.utils.TimeUtils;
import com.zhoug.android.common.utils.UriUtils;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity>>>";

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews(){
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);


        test();

        addListener();
    }
    private void test(){
        Point screenSize = AppUtils.getScreenSize(this);
        Point realScreenSize = AppUtils.getRealScreenSize(this);
        Log.d(TAG, "screenSize:"+screenSize);
        Log.d(TAG, "realScreenSize:"+realScreenSize);
        Log.d(TAG, "findViews:差="+(realScreenSize.y-screenSize.y));
        int statusHeight = AppUtils.getStatusHeight(this);
        Log.d(TAG, "findViews:statusHeight="+statusHeight);
        int navigationBarHeight = AppUtils.getNavigationBarHeight(this);
        Log.d(TAG, "findViews:navigationBarHeight="+navigationBarHeight);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Log.d(TAG, "findViews:底部导航栏是否显示"+AppUtils.isNavigationBarShow(this));
        }

        Log.d(TAG, "findViews:是否竖屏:"+AppUtils.isPortrait(this));
        Log.d(TAG, "findViews: 本机号码"+AppUtils.getLocalPhoneNumber(this));

        Log.d(TAG, "test: "+ TimeUtils.getCurrentTime());
        DateFormat dateInstance = SimpleDateFormat.getDateInstance();
        String format = dateInstance.format(new Date());
        Log.d(TAG, "test: format="+format);
    }
    private void addListener(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,101 );
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,102 );
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,103);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,104);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,105);
            }
        });

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    AppUtils.installApk(MainActivity.this,FileUtils.getExternalFile("Tencent/QQfile_recv/qlymclz_v1.0.0.7_release.apk") );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && data!=null){
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri="+pathFromUri);
            if(pathFromUri!=null){
                long time1=System.currentTimeMillis();
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1080, null);
                long time2=System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:"+(time2-time1));
                Bitmap circleBitmap = BitmapUtils.createCircleBitmap(bitmap);
                long time3=System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:"+(time3-time2));
                imageView1.setImageBitmap(circleBitmap);

            }
        }else  if(requestCode==102 && data!=null){
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri="+pathFromUri);
            if(pathFromUri!=null) {
                long time1=System.currentTimeMillis();
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 0, 0, null);
                long time2=System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:"+(time2-time1));
                byte[] bytes = BitmapUtils.compressQualityTo(bitmap, 100);
                Toast.makeText(this, "size="+(bytes.length/1024)+"kb", Toast.LENGTH_SHORT).show();

                long time3=System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:"+(time3-time2));
                Log.d(TAG, "onActivityResult:bytes.length="+bytes.length);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(TAG, "onActivityResult:["+bitmap1.getWidth()+","+bitmap1.getHeight()+"]");

                imageView1.setImageBitmap(bitmap1);
            }

        }else  if(requestCode==103 && data!=null){
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri="+pathFromUri);
            if(pathFromUri!=null) {
                long time1=System.currentTimeMillis();
                byte[] bytes = BitmapUtils.compressTo(pathFromUri, 100);
                Toast.makeText(this, "size="+(bytes.length/1024)+"kb", Toast.LENGTH_SHORT).show();
                IOUtils.keepFile("/storage/emulated/0/0image1/"+System.currentTimeMillis()+".jpg",bytes );
                IOUtils.copyFile(pathFromUri,"/storage/emulated/0/0image1/"+"_"+System.currentTimeMillis()+".jpg");

                long time2=System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:"+(time2-time1));
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(TAG, "onActivityResult:["+bitmap1.getWidth()+","+bitmap1.getHeight()+"]");
                imageView1.setImageBitmap(bitmap1);
            }

        }else  if(requestCode==104 && data!=null){
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri="+pathFromUri);
            if(pathFromUri!=null) {
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1920, null);
                imageView1.setImageBitmap(bitmap);
                Bitmap mirrorHorizontal = BitmapUtils.getMirrorHorizontal(bitmap);
                imageView2.setImageBitmap(mirrorHorizontal);

            }

        }else  if(requestCode==105 && data!=null){
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri="+pathFromUri);
            if(pathFromUri!=null) {
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1920, null);
                imageView1.setImageBitmap(bitmap);
                Bitmap mirrorHorizontal = BitmapUtils.getMirrorVertical(bitmap);
                imageView2.setImageBitmap(mirrorHorizontal);

            }

        }

    }


}
