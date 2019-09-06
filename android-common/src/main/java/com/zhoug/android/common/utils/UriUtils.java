package com.zhoug.android.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Uri 处理工具
 */
public class UriUtils {
    /**
     * 根据uri获取图片path
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    public static String getPathFromUri(Context context, Uri uri){
        Log.i("getPathFromUri", "getPathFromUri: uri="+uri);
        if(uri==null) return null;
        String type=null;
        Cursor cursor=null;
        String[] columns=null;//要查询的列
        //处理这种类型的uri：content://com.android.providers.media.documents/document/image:67
        //处理这种类型的uri：content://com.android.providers.media.documents/document/image%67
        try{
            //DocumentsContract.isDocumentUri(context, uri)
            if(uri.toString().startsWith("content://com.")){
                System.out.println("DocumentsContract 类型的uri="+uri);
                //documentId为 image:67
                String documentId = DocumentsContract.getDocumentId(uri);
                System.out.println("documentId="+documentId);

                String[] split = documentId.split(":");
                if(split!=null && split.length==2){
                    //类型
                    type=split[0];
                    //id为 67
                    String id=documentId.split(":")[1];
                    System.out.println(" type="+type);
                    System.out.println(" id="+id);

                    if(type.equalsIgnoreCase("image")){
                        //根据id查询语句
                        String sel= MediaStore.Images.Media._ID+"="+id;
                        columns=new String[]{MediaStore.Images.Media.DATA};
                        cursor= context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    }else if(type.equalsIgnoreCase("video")){
                        //根据id查询语句
                        String sel= MediaStore.Video.Media._ID+"="+id;
                        columns=new String[]{MediaStore.Video.Media.DATA};
                        cursor= context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    }else if(type.equalsIgnoreCase("audio")){
                        //根据id查询语句
                        String sel= MediaStore.Audio.Media._ID+"="+id;
                        columns=new String[]{MediaStore.Audio.Media.DATA};
                        cursor= context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, sel, null, null);
                    }else{
                        return null;
                    }


                }


            }else{
                System.out.println("media 类型的uri="+uri);
                //处理这种类型的uri：
                // content://media/external/images/media/3951
                // content://media/external/video/media/197934
                // content://media/external/audio/media/202844
                String strUri = uri.toString();
                boolean b = strUri.startsWith("content:");
                if(b){
                    int i = strUri.indexOf("media");
                    String substring = strUri.substring(i);
                    String[] split = substring.split("/");
                    if(split!=null && split.length>=5){
                        type=split[2];
                        System.out.println(type);
                        if(type.equalsIgnoreCase("images")){
                            type="image";
                            columns=new String[]{MediaStore.Images.Media.DATA};
                            cursor = context.getContentResolver().query(uri, columns, null, null, null);
                        }else if(type.equalsIgnoreCase("video")){
                            columns=new String[]{MediaStore.Video.Media.DATA};
                            cursor = context.getContentResolver().query(uri, columns, null, null, null);
                        }else if(type.equalsIgnoreCase("audio")){
                            columns=new String[]{MediaStore.Audio.Media.DATA};
                            cursor = context.getContentResolver().query(uri, columns, null, null, null);
                        }else{
                            return null;
                        }



                    }else{
                        return  null;
                    }

                }else{
                    return null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (cursor == null) {
//			Toast.makeText(context, "该图片不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        cursor.moveToFirst();
        String path=cursor.getString(cursor.getColumnIndex(columns[0]));
        cursor.close();
        return path;

    }



}
