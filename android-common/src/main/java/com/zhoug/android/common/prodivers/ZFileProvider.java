package com.zhoug.android.common.prodivers;

import android.support.v4.content.FileProvider;

/**
 *
 * AndroidManifest.xml配置
 * <provider
 *    android:name="android.support.v4.content.FileProvider"
 *    android:authorities="packageName.fileProvider"
 *    android:grantUriPermissions="true"
 *    android:exported="false">
 *    <meta-data
 *       android:name="android.support.FILE_PROVIDER_PATHS"
 *       android:resource="@xml/common_file_paths"/>
 *</provider>
 *
 *
 *
 */
public class ZFileProvider extends FileProvider {
    public static  String AUTHORITY="packageName.fileProvider";


}
