package com.zhoug.android.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

public class IntentUtils {
    private static final String TAG = "IntentUtils";

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param authority
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getReadFileProvideIntent(Context context, String path, String authority,String minitype) {
        if (minitype == null) {
            minitype = "*/*";
        }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
            Uri contentUri = FileProvider.getUriForFile(context, authority, new File(path));
            intent.setDataAndType(contentUri, minitype);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), minitype);
        }
        return intent;
    }

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getReadFileProvideIntent(Context context, String path,String minitype) {
        return getReadFileProvideIntent(context, path,context.getPackageName()+".fileProvider",minitype);


    }

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param authority
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getWriteFileProvideIntent(Context context, String path, String authority,String minitype) {
        if (minitype == null) {
            minitype = "*/*";
        }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, authority, new File(path));
            intent.setDataAndType(contentUri, minitype);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), minitype);
        }
        return intent;
    }

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getWriteFileProvideIntent(Context context, String path,String minitype) {
       return getWriteFileProvideIntent(context,path,context.getPackageName()+".fileProvider",minitype);
    }


}
