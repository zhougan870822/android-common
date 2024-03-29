package com.zhoug.androidcommon.app;


import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhoug.android.common.beans.Contacts;
import com.zhoug.android.common.broadcast.NetworkReceiver;
import com.zhoug.android.common.content.BitmapDraw;
import com.zhoug.android.common.utils.AppUtils;
import com.zhoug.android.common.utils.AudioUtils;
import com.zhoug.android.common.utils.BitmapUtils;
import com.zhoug.android.common.utils.ContactsUtils;
import com.zhoug.android.common.utils.FileUtils;
import com.zhoug.android.common.utils.IOUtils;
import com.zhoug.android.common.utils.IntentUtils;
import com.zhoug.android.common.utils.NetworkUtils;
import com.zhoug.android.common.utils.ResourceUtils;
import com.zhoug.android.common.utils.SmsUtils;
import com.zhoug.android.common.utils.ThreadUtils;
import com.zhoug.android.common.utils.TimeUtils;
import com.zhoug.android.common.utils.ToastUtils;
import com.zhoug.android.common.utils.UriUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity>>>";

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private ImageView imageView1;
    private ImageView imageView2;
    private NetworkReceiver netWorkBroadcastReceiver;
    private SmsUtils.SendSmsBroadcastReceiver sendSmsBroadcastReceiver;

    private String path;

    @Override
    protected int contentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        findViews();

    }

    private void findViews() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,


        }, 10012);

        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);


        test();

        addListener();
    }

    private void test() {
        Point screenSize = AppUtils.getScreenSize(this);
        Point realScreenSize = AppUtils.getRealScreenSize(this);
        Log.d(TAG, "screenSize:" + screenSize);
        Log.d(TAG, "realScreenSize:" + realScreenSize);
        Log.d(TAG, "findViews:差=" + (realScreenSize.y - screenSize.y));
        int statusHeight = AppUtils.getStatusHeight(this);
        Log.d(TAG, "findViews:statusHeight=" + statusHeight);
        int navigationBarHeight = AppUtils.getNavigationBarHeight(this);
        Log.d(TAG, "findViews:navigationBarHeight=" + navigationBarHeight);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Log.d(TAG, "findViews:底部导航栏是否显示" + AppUtils.isNavigationBarShow(this));
        }

        Log.d(TAG, "findViews:是否竖屏:" + AppUtils.isPortrait(this));
        Log.d(TAG, "findViews: 本机号码" + AppUtils.getLocalPhoneNumber(this));

        Date parse = TimeUtils.parse("2018-11-12 08:55:36", "yyyy-MM-dd HH:mm:ss");
        Log.d(TAG, "test: " + TimeUtils.format(parse, "yyyy-MM-dd HH:mm:ss"));
        Log.d(TAG, "test:" + TimeUtils.format(new Date(), null));
        Log.d(TAG, "test:" + TimeUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss"));
        Log.d(TAG, "test:" + TimeUtils.formatDateT("2018-18-12T08:55:36"));
        Log.d(TAG, "test:" + TimeUtils.formatDateT("2018-18-12TT08:55:36", "TT"));
        Log.d(TAG, "test:" + TimeUtils.formatDateYMD("2018-18-12TT08:55:36"));
        Log.d(TAG, "test:" + TimeUtils.formatDateHMS("2018-18-12T08:55:36"));
        Log.d(TAG, "test:" + TimeUtils.getCurrentTime());
        Log.d(TAG, "test:" + TimeUtils.getCurrentTime("yyyyMMddHHmm"));


        netWorkBroadcastReceiver = NetworkUtils.registerReceiver(this, new NetworkReceiver.OnNetworkChangeListener() {
            @Override
            public void onChangeListener(int state) {
                switch (state) {
                    case NetworkUtils.STATE_NONE:
                        toast("无网络");
                        break;
                    case NetworkUtils.STATE_MOBILE:
                        toast("移动网络连接");
                        break;
                    case NetworkUtils.STATE_WIFI:
                        toast("wifi连接");
                        break;
                }
            }
        });

        Log.d(TAG, "test:getDrawableId " + ResourceUtils.getDrawableId(this, "ic_launcher_background"));
        Log.d(TAG, "test:getDrawableId " + R.drawable.ic_launcher_background);

        Log.d(TAG, "test:getColorId " + ResourceUtils.getColorId(this, "colorAccent"));
        Log.d(TAG, "test:getColorId " + R.color.colorAccent);

        Log.d(TAG, "test:getDimenId " + ResourceUtils.getDimenId(this, "zxc"));
        Log.d(TAG, "test:getDimenId " + R.dimen.zxc);

        Log.d(TAG, "test:getId " + ResourceUtils.getId(this, "btn11"));
        Log.d(TAG, "test:getId " + R.id.btn11);

        Log.d(TAG, "test:getLayoutId " + ResourceUtils.getLayoutId(this, "activity_main"));
        Log.d(TAG, "test:getLayoutId " + R.layout.activity_main);

        Log.d(TAG, "test:getStringId " + ResourceUtils.getStringId(this, "string1"));
        Log.d(TAG, "test:getStringId " + R.string.string1);

        Log.d(TAG, "test:getXmlId " + ResourceUtils.getXmlId(this, "common_file_paths"));
        Log.d(TAG, "test:getXmlId " + R.xml.common_file_paths);

        Log.d(TAG, "test:getStyleId " + ResourceUtils.getStyleId(this, "AppTheme"));
        Log.d(TAG, "test:getStyleId " + R.style.AppTheme);

        long time1 = System.currentTimeMillis();
        List<Contacts> contacts = ContactsUtils.getContacts(this);
        long time2 = System.currentTimeMillis();
        Log.d(TAG, "test:" + (time2 - time1));
        Log.d(TAG, "test:联系人数目:" + contacts.size());
        for (Contacts contact : contacts) {
            Log.d(TAG, "test:" + contact);
            String telPhone = contact.getTelPhone();
            if (telPhone != null) {
                String[] split = telPhone.split(Contacts.SEPARATOR);
                for (String s : split) {
                    Log.d(TAG, "test:" + s);
                }
            }
            Log.d(TAG, "test:>>>>>>>>>>>>>>>>");
        }


        sendSmsBroadcastReceiver = SmsUtils.registerReceiver(this, new SmsUtils.OnSendSmsListener() {
            @Override
            public void onSendResult(boolean success) {
                if (success) {
                    toast("发送成功");
                } else {
                    toast("发送失败");
                }
            }

            @Override
            public void onAccept(boolean success) {
                if (success) {
                    toast("对方接受成功");
                } else {
                    toast("对方接受失败");
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1011 || requestCode == 1012 || requestCode == 1013 || requestCode == 1014 || requestCode == 1015 || requestCode == 1016) {
            if (data != null) {
                String pathFromUri = UriUtils.getPathFromUri(this, data.getData());


                Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
                toast(pathFromUri + "");
                if (pathFromUri != null && FileUtils.getType(pathFromUri) == FileUtils.TYPE_IMAGE) {
                    long time1 = System.currentTimeMillis();
                    Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1080, null);
                    long time2 = System.currentTimeMillis();
                    Log.d(TAG, "onActivityResult:" + (time2 - time1));
                    Bitmap circleBitmap = BitmapUtils.createCircleBitmap(bitmap);
                    long time3 = System.currentTimeMillis();
                    Log.d(TAG, "onActivityResult:" + (time3 - time2));
                    imageView1.setImageBitmap(circleBitmap);

                }
            }

        } else if (requestCode == 102 && data != null) {
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
            if (pathFromUri != null) {
                long time1 = System.currentTimeMillis();
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 0, 0, null);
                long time2 = System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:" + (time2 - time1));
                byte[] bytes = BitmapUtils.compressQualityTo(bitmap, 100);
                Toast.makeText(this, "size=" + (bytes.length / 1024) + "kb", Toast.LENGTH_SHORT).show();

                long time3 = System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:" + (time3 - time2));
                Log.d(TAG, "onActivityResult:bytes.length=" + bytes.length);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(TAG, "onActivityResult:[" + bitmap1.getWidth() + "," + bitmap1.getHeight() + "]");

                imageView1.setImageBitmap(bitmap1);
            }

        } else if (requestCode == 103 && data != null) {
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
            if (pathFromUri != null) {
                long time1 = System.currentTimeMillis();
                byte[] bytes = BitmapUtils.compressTo(pathFromUri, 100);
                Toast.makeText(this, "size=" + (bytes.length / 1024) + "kb", Toast.LENGTH_SHORT).show();
                IOUtils.keepFile("/storage/emulated/0/0image1/" + System.currentTimeMillis() + ".jpg", bytes);
                IOUtils.copyFile(pathFromUri, "/storage/emulated/0/0image1/" + "_" + System.currentTimeMillis() + ".jpg");

                long time2 = System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:" + (time2 - time1));
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(TAG, "onActivityResult:[" + bitmap1.getWidth() + "," + bitmap1.getHeight() + "]");
                imageView1.setImageBitmap(bitmap1);
            }

        } else if (requestCode == 104 && data != null) {
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
            if (pathFromUri != null) {
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1920, null);
                imageView1.setImageBitmap(bitmap);
                Bitmap mirrorHorizontal = BitmapUtils.getMirrorHorizontal(bitmap);
                imageView2.setImageBitmap(mirrorHorizontal);

            }

        } else if (requestCode == 105 && data != null) {
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
            if (pathFromUri != null) {
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1920, null);
                imageView1.setImageBitmap(bitmap);
                Bitmap mirrorHorizontal = BitmapUtils.getMirrorVertical(bitmap);
                imageView2.setImageBitmap(mirrorHorizontal);

            }

        } else if (requestCode == 107 && data != null) {
            String pathFromUri = UriUtils.getPathFromUri(this, data.getData());
            Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
            if (pathFromUri != null) {
                long time1 = System.currentTimeMillis();
                Bitmap bitmap = BitmapUtils.decodeFile(pathFromUri, 1080, 1080, null);
                long time2 = System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:" + (time2 - time1));
                Log.d(TAG, "onActivityResult:bitmap=" + bitmap);
                Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                new BitmapDraw().setAutoTextColor(false)
                        .setBitmap(copy)
                        .setGravity(BitmapDraw.BOTTOM)
                        .setLineSpacing(10)
                        .setTextSize(AppUtils.spTopx(this, 18))
                        .setTexts(Arrays.asList("zxc", "自行车自行车在自行车自行车在自行车自行车在自行车自行车在自行车自行车在自行车自行车在465156", ",OK的发了个OK的发了个OK的发了个OK的发了个OK的发了个OK的发了个"))
                        .setPadding(20, 20, 20, 50)
                        .setTextColor(Color.RED)
                        .draw();


                long time3 = System.currentTimeMillis();
                Log.d(TAG, "onActivityResult:" + (time3 - time2));

                imageView1.setImageBitmap(bitmap);
                imageView2.setImageBitmap(copy);


            }
        } else if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                String pathFromUri = null;
                if (data != null) {
                    pathFromUri = UriUtils.getPathFromUri(this, data.getData());
                }
                if (pathFromUri == null) {
                    pathFromUri = path;
                }
                Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);
                startActivity(IntentUtils.getReadFileIntent(MainActivity.this, pathFromUri, FileUtils.getMimeType(pathFromUri)));

            } else {
                Log.d(TAG, "onActivityResult:取消");
            }

        } else if (requestCode == 12) {
            if (resultCode == RESULT_OK) {
                String pathFromUri = null;
                if (data != null) {
                    pathFromUri = UriUtils.getPathFromUri(this, data.getData());
                }
                if (pathFromUri == null) {
                    pathFromUri = path;
                }
                Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);

                startActivity(IntentUtils.getReadFileIntent(MainActivity.this, pathFromUri, FileUtils.getMimeType(pathFromUri)));


            } else {
                Log.d(TAG, "onActivityResult:取消");
            }
        } else if (requestCode == 13) {
            if (resultCode == RESULT_OK) {
                String pathFromUri = null;
                if (data != null) {
                    pathFromUri = UriUtils.getPathFromUri(this, data.getData());
                }
                if (pathFromUri == null) {
                    pathFromUri = path;
                }
                Log.d(TAG, "onActivityResult:pathFromUri=" + pathFromUri);

                startActivity(IntentUtils.getReadFileIntent(MainActivity.this, pathFromUri, FileUtils.getMimeType(pathFromUri)));


            } else {
                Log.d(TAG, "onActivityResult:取消");
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.unregisterReceiver(this, netWorkBroadcastReceiver);
        SmsUtils.unregisterReceiver(this, sendSmsBroadcastReceiver);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void addListener() {
        findViewById(R.id.btn1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(IntentUtils.getPickImageIntent(), 1011);
            }
        });
        findViewById(R.id.btn1_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(IntentUtils.getContentIntent("image/*"), 1012);

            }
        });
        findViewById(R.id.btn1_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(IntentUtils.getPickVideoIntent(), 1013);
            }
        });
        findViewById(R.id.btn1_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(IntentUtils.getContentIntent("video/*"), 1014);
            }
        });
        findViewById(R.id.btn1_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(IntentUtils.getPickAudioIntent(), 1015);
            }
        });
        findViewById(R.id.btn1_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(IntentUtils.getContentIntent("audio/*"), 1016);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 102);
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 103);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 104);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 105);
            }
        });

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    AppUtils.installApk(MainActivity.this, FileUtils.getExternalFile("Tencent/QQfile_recv/qlymclz_v1.0.0.7_release.apk"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 107);

            }
        });
        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                    IntentUtils.callPhone(MainActivity.this, "10086");
                else
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1001);

            }
        });

        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsUtils.sendSmsUseSystemUi(MainActivity.this, "10086", "zxcsad");
            }
        });

        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsUtils.sendSms(MainActivity.this, "10086", "1");


            }
        });

        findViewById(R.id.btn11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = FileUtils.getExternalFile("0image/" + System.currentTimeMillis() + ".jpg").getAbsolutePath();

                Uri uriForFile = UriUtils.getUriForFile(MainActivity.this, path);
                Log.d(TAG, "onClick:uriForFile=" + uriForFile);
                Log.d(TAG, "onClick:path=" + UriUtils.getPathFromUri(MainActivity.this, uriForFile));

                startActivityForResult(IntentUtils.getCaptureImageIntent(MainActivity.this, path, null), 11);
            }
        });

        findViewById(R.id.btn12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = FileUtils.getExternalFile("0video/" + System.currentTimeMillis() + ".mp4").getAbsolutePath();
                startActivityForResult(IntentUtils.getCaptureVideoIntent(MainActivity.this, path, null), 12);
            }
        });
        findViewById(R.id.btn13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = FileUtils.getExternalFile("0audio/" + System.currentTimeMillis() + ".amr").getAbsolutePath();
                startActivityForResult(IntentUtils.getCaptureAudioIntent(MainActivity.this, path, null), 13);
            }
        });

        findViewById(R.id.btn14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriUtils.getPathFromUri(MainActivity.this, Uri.parse("file:///zxc/111/222/123.jpg"));
                path = FileUtils.getExternalFile("0audio/" + System.currentTimeMillis() + ".mp4").getAbsolutePath();

                UriUtils.getPathFromUri(MainActivity.this, Uri.fromFile(new File(path)));

                UriUtils.getPathFromUri(MainActivity.this, UriUtils.getUriForFile(MainActivity.this, path));


            }
        });
        findViewById(R.id.btn15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioUtils instance = AudioUtils.instance(MainActivity.this);
                instance.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);


            }
        });

        findViewById(R.id.btn16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioUtils instance = AudioUtils.instance(MainActivity.this);
                instance.setStreamVolume(AudioManager.STREAM_MUSIC, 5, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

            }
        });

        findViewById(R.id.btn17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtils.setDebug(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ThreadUtils.runMainThread(new Runnable() {
                            @Override
                            public void run() {
                                toast("thread:" + Thread.currentThread().getName());
                            }
                        });
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 200; i++) {
                            ThreadUtils.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "thread name:" + Thread.currentThread().getName() + ",count=" + count);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    count++;
                                }
                            });
                        }
                    }
                }).start();

            }
        });

        findViewById(R.id.btn18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application applicationByReflect = AppUtils.getApplicationByReflect();
                Log.d(TAG, "onClick:applicationByReflect=" + applicationByReflect);
                toast(applicationByReflect == null ? "null" : applicationByReflect.toString());
            }
        });

        findViewById(R.id.btn19).setOnClickListener(v -> {
            colorIndex++;
            if (colorIndex >= colors.length) {
                colorIndex = 0;
            }
            ToastUtils.setBgColor(colors[colorIndex]);
            ToastUtils.setMsgColor(Color.parseColor("#00ffff"));
            ToastUtils.setGravity(Gravity.BOTTOM);
            ToastUtils.showShort("测试ToastUtils");

        });

        findViewById(R.id.btn20).setOnClickListener(v->{
            Intent intent=new Intent(this,StatusChangeActivity.class);
            startActivity(intent);


        });
    }

    private int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN};
    private int colorIndex = -1;
    int count = 1;
}
