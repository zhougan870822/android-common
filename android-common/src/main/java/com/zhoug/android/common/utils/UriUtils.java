package com.zhoug.android.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Uri 处理工具
 */
public class UriUtils {
    private static final String TAG = "UriUtils";

    /**
     * 根据uri获取图片path
     *
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    @Deprecated
    public static String getPathFromUri1(Context context, Uri uri) {
        Log.i("getPathFromUri", "getPathFromUri: uri=" + uri);
        if (uri == null) return null;
        String type = null;
        Cursor cursor = null;
        String[] columns = null;//要查询的列
        //处理这种类型的uri：content://com.android.providers.media.documents/document/image:67
        //处理这种类型的uri：content://com.android.providers.media.documents/document/image%67
        try {
            //DocumentsContract.isDocumentUri(context, uri)
            if (uri.toString().startsWith("content://com.")) {
                System.out.println("DocumentsContract 类型的uri=" + uri);
                //documentId为 image:67
                String documentId = DocumentsContract.getDocumentId(uri);
                System.out.println("documentId=" + documentId);

                String[] split = documentId.split(":");
                if (split != null && split.length == 2) {
                    //类型
                    type = split[0];
                    //id为 67
                    String id = documentId.split(":")[1];
                    System.out.println(" type=" + type);
                    System.out.println(" id=" + id);

                    if (type.equalsIgnoreCase("image")) {
                        //根据id查询语句
                        String sel = MediaStore.Images.Media._ID + "=" + id;
                        columns = new String[]{MediaStore.Images.Media.DATA};
                        cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    } else if (type.equalsIgnoreCase("video")) {
                        //根据id查询语句
                        String sel = MediaStore.Video.Media._ID + "=" + id;
                        columns = new String[]{MediaStore.Video.Media.DATA};
                        cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    } else if (type.equalsIgnoreCase("audio")) {
                        //根据id查询语句
                        String sel = MediaStore.Audio.Media._ID + "=" + id;
                        columns = new String[]{MediaStore.Audio.Media.DATA};
                        cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    } else {
                        return null;
                    }


                }


            } else {
                System.out.println("media 类型的uri=" + uri);
                //处理这种类型的uri：
                // content://media/external/images/media/3951
                // content://media/external/video/media/197934
                // content://media/external/audio/media/202844
                String strUri = uri.toString();
                boolean b = strUri.startsWith("content:");
                if (b) {
                    int i = strUri.indexOf("media");
                    String substring = strUri.substring(i);
                    String[] split = substring.split("/");
                    if (split != null && split.length >= 5) {
                        type = split[2];
                        System.out.println(type);
                        if (type.equalsIgnoreCase("images")) {
                            type = "image";
                            columns = new String[]{MediaStore.Images.Media.DATA};
                            cursor = context.getContentResolver().query(uri, columns, null, null, null);
                        } else if (type.equalsIgnoreCase("video")) {
                            columns = new String[]{MediaStore.Video.Media.DATA};
                            cursor = context.getContentResolver().query(uri, columns, null, null, null);
                        } else if (type.equalsIgnoreCase("audio")) {
                            columns = new String[]{MediaStore.Audio.Media.DATA};
                            cursor = context.getContentResolver().query(uri, columns, null, null, null);
                        } else {
                            return null;
                        }


                    } else {
                        return null;
                    }

                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor == null) {
//			Toast.makeText(context, "该图片不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(columns[0]));
        cursor.close();
        return path;

    }

    /**
     * 根据uri获取文件path
     * uri类型:{
     *  content://com.zhoug.androidcommon.app.fileProvider/sdcard/0audio/1568103220253.mp4
     *  content://media/extenral/images/media/17766
     *  content://com.android.providers.media.documents/document/image:67
     *  content://com.android.providers.media.documents/document/image%67
     *  file:///storage/emulated/0/0audio/1568103220253.mp4
     * }
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromUri(Context context, Uri uri) {
        if (uri == null) {
            Log.i(TAG, "getPathFromUri: uri is null");
            return null;
        }
        Log.d(TAG, "getPathFromUri:uri=" + uri);
        String path = null;
        String scheme = uri.getScheme();
        if (scheme == null) {
            path= uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path= uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            if(isDocumentUri(uri)){
                path=getPathFromDocumentUri(context, uri);
            }else if(isMediaUri(uri)){
                path=getPathFromMediaUri(context, uri);
            }
        }

        Log.i(TAG, "getPathFromUri:path="+path);
        Log.d(TAG, "getPathFromUri:>>>>>>>>>>>>>>");
        return path;
    }


    /**
     * 解析uri获取文件真实路径
     * 4.4以上版本
     * uri类型:content://com.android.providers.media.documents/document/
     * eg: content://com.android.providers.media.documents/document/image:67
     * eg:content://com.android.providers.media.documents/document/image%67
     * @param context
     * @param uri
     * @return
     */
    private static String getPathFromDocumentUri(Context context,Uri uri){
        if(uri==null){
            return null;
        }
        String path=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //image:378558
            try {
                String documentId = DocumentsContract.getDocumentId(uri);
//                Log.d(TAG, "getPathFromDocumentUri:documentId="+documentId);
                if(documentId!=null){
                    String[] split = documentId.split(":");
                    //image
                    String type=split[0];
                    //378558
                    String id=split[1];
                    String [] columns=null;
                    Cursor cursor=null;
                    if (type.equalsIgnoreCase("image")) {
                        //根据id查询语句
                        String sel = MediaStore.Images.Media._ID + "=" + id;
                        columns = new String[]{MediaStore.Images.Media.DATA};
                        cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    } else if (type.equalsIgnoreCase("video")) {
                        //根据id查询语句
                        String sel = MediaStore.Video.Media._ID + "=" + id;
                        columns = new String[]{MediaStore.Video.Media.DATA};
                        cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    } else if (type.equalsIgnoreCase("audio")) {
                        //根据id查询语句
                        String sel = MediaStore.Audio.Media._ID + "=" + id;
                        columns = new String[]{MediaStore.Audio.Media.DATA};
                        cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    }

                    if(cursor!=null){
                        if(cursor.moveToFirst()){
                             path = cursor.getString(cursor.getColumnIndex(columns[0]));
                        }
                        cursor.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        Log.d(TAG, "getPathFromDocumentUri:path="+path);
        return path;
    }


    /**
     * 解析uri获取文件真实路径
     * uri类型:content://media/
     * eg:content://media/extenral/images/media/17766
     * @param context
     * @param uri
     * @return
     */
    private static String getPathFromMediaUri(Context context,Uri uri){
//        Log.d(TAG, "getPathFromMediaUri:media uri");
        if(uri==null){
            return null;
        }
        String path=null;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                if(columnIndex>=0){
                    path = cursor.getString(columnIndex);
                }
            }
            cursor.close();
        }
//        Log.d(TAG, "getPathFromMediaUri:path="+path);
        return path;
    }


    private static boolean isDocumentUri(Uri uri){
        if(uri!=null){
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
        return false;
    }

    private static boolean isMediaUri(Uri uri){
        if(uri!=null){
            return "media".equals(uri.getAuthority());
        }
        return false;
    }



    /**
     * 根据文件路径获取uri
     *
     * @param context
     * @param path
     * @param authority
     * @return
     */
    public static Uri getUriForFile(Context context, String path, String authority) {
        if (authority == null) {
            authority = context.getPackageName() + ".fileProvider";
        }
        //7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, authority, new File(path));
        } else {
            return Uri.fromFile(new File(path));
        }
    }

    /**
     * 根据文件路径获取uri
     *
     * @param context
     * @param path
     * @return
     */
    public static Uri getUriForFile(Context context, String path) {
        return getUriForFile(context, path, null);
    }

}
