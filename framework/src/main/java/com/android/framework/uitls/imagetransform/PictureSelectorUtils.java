package com.android.framework.uitls.imagetransform;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.android.framework.R;
import com.android.framework.base.BaseApplication;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.SdkVersionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.MEDIA_MOUNTED;
import static com.luck.picture.lib.tools.PictureFileUtils.getDataColumn;
import static com.luck.picture.lib.tools.PictureFileUtils.isDownloadsDocument;
import static com.luck.picture.lib.tools.PictureFileUtils.isExternalStorageDocument;
import static com.luck.picture.lib.tools.PictureFileUtils.isGooglePhotosUri;
import static com.luck.picture.lib.tools.PictureFileUtils.isMediaDocument;

/**
 * 图片选择器处理工具类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/9/27
 */
public class PictureSelectorUtils {
    public static ArrayList<String> delPath = new ArrayList<>();
    public static int[] qualitys = new int[]{90, 80, 70, 60, 50, 40, 30, 20};
    public static ArrayList<Bitmap> bitmaps = new ArrayList<>();

    /**
     * 获取图片回调结果
     *
     * @param data
     * @return
     */
    public static List<LocalMedia> selectResult(Intent data) {
        return PictureSelector.obtainMultipleResult(data);
    }

    /**
     * 单选图片回调最终处理
     *
     * @param localMedia
     * @return
     */
    public static String singleSelectResult(LocalMedia localMedia) {
        if (localMedia.isCompressed() && !TextUtils.isEmpty(localMedia.getCompressPath())) {
            return localMedia.getCompressPath();
        } else if (!TextUtils.isEmpty(localMedia.getCutPath())) {
            return localMedia.getCutPath();
        } else if (android.os.Build.VERSION.SDK_INT >= 29 && !TextUtils.isEmpty(localMedia.getAndroidQToPath())) {
            return qualityCompress(localMedia.getAndroidQToPath());
        }

        return qualityCompress(localMedia.getPath());
    }

    /**
     * 多选图片回调最终处理
     *
     * @param list
     * @return
     */
    public static List<String> multipleSelectResult(List<LocalMedia> list) {
        List<String> imgList = new ArrayList<>();
        for (LocalMedia media : list) {
            String path = singleSelectResult(media);
            imgList.add(path);
        }
        return imgList;
    }

    /**
     * 单选图片，默认不裁剪
     *
     * @param activity
     * @param resultCode
     */
    public static void pictureSingleSelect(Activity activity, int resultCode) {
        pictureSingleSelect(activity, resultCode, false);
    }

    /**
     * 单选图片，带比例裁剪
     *
     * @param activity
     * @param resultCode
     * @param cropW
     * @param cropH
     */
    public static void pictureSingleSelect(Activity activity, int resultCode, int cropW, int cropH) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
//                    .setLanguage(language)// 设置语言，默认中文
//                    .isPageStrategy(false)// 是否开启分页策略 & 每页多少条；默认开启
//                    .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                    .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
//                    .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
//                    .setRecyclerAnimationMode(animationMode)// 列表动画效果
                .isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                //.isAutomaticTitleRecyclerTop(false)// 连续点击标题栏RecyclerView是否自动回到顶部,默认true
                //.loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                //.setOutputCameraPath()// 自定义相机输出目录，只针对Android Q以下，例如 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +  File.separator + "Camera" + File.separator;
                //.setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 设置自定义相机按钮状态
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
//                .maxVideoSelectNum(1) // 视频最大选择数量
                //.minVideoSelectNum(1)// 视频最小选择数量
                //.closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 关闭在AndroidQ下获取图片或视频宽高相反自动转换
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
//                    .isOriginalImageControl(cb_original.isChecked())// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义视频播放回调控制，用户可以使用自己的视频播放界面
                //.bindCustomPreviewCallback(new MyCustomPreviewInterfaceListener())// 自定义图片预览回调接口
                //.bindCustomCameraInterfaceListener(new MyCustomCameraInterfaceListener())// 提供给用户的一些额外的自定义操作回调
                //.cameraFileName(System.currentTimeMillis() +".jpg")    // 重命名拍照文件名、如果是相册拍照则内部会自动拼上当前时间戳防止重复，注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
                //.renameCompressFile(System.currentTimeMillis() +".jpg")// 重命名压缩文件名、 如果是多张压缩则内部会自动拼上当前时间戳防止重复
                //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 如果是多张裁剪则内部会自动拼上当前时间戳防止重复
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                    .isSingleDirectReturn(cb_single_back.isChecked())// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
//                .isEnablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                //.isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
                //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType.PNG_Q
                .isEnableCrop(true)// 是否裁剪
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .withAspectRatio(cropW, cropH)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .circleDimmedLayer(false)// 是否圆形裁剪
//                .cropImageWideHigh(1, 1)// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                .isCompress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                //.compressSavePath(getPath())//压缩图片保存地址
                //.setCropDimmedColor(ContextCompat.getColor(getContext(), R.color.app_color_white))// 设置裁剪背景色值
                //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
//                    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                    .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                    .isOpenClickSound(cb_voice.isChecked())// 是否开启点击声音
//                .selectionData(selectionData)// 是否传入已选图片
                //.videoMinSecond(10)// 查询多少秒以内的视频
                //.videoMaxSecond(15)// 查询多少秒以内的视频
                //.recordVideoSecond(10)//录制视频秒数 默认60s
                //.isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
                .cutOutQuality(80)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .setLanguage(0)
                //.videoQuality()// 视频录制质量 0 or 1
                // 结果回调onActivityResult code
                .forResult(resultCode);
    }

    /**
     * 单选图片
     *
     * @param activity
     * @param resultCode
     * @param isCrop     是否可以进行裁剪
     */
    public static void pictureSingleSelect(Activity activity, int resultCode, boolean isCrop) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isGif(false)// 是否显示gif图片
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(isCrop)// 是否裁剪
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                .isCompress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .setLanguage(0)
                .forResult(resultCode);
    }

    /**
     * 可裁剪拍照
     *
     * @param activity
     * @param type
     * @param cropW    裁剪宽比列
     * @param cropH    裁剪高比列
     */
    public static void selectCamera(Activity activity, int type, int cropW, int cropH) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isEnableCrop(true)// 是否裁剪
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .withAspectRatio(cropW, cropH)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .circleDimmedLayer(false)// 是否圆形裁剪
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                .isCompress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(type);
    }

    /**
     * 无裁剪拍照
     *
     * @param activity
     * @param type
     */
    public static void selectCamera(Activity activity, int type) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(type);
    }

    /**
     * 多选图片，没有裁剪
     *
     * @param activity
     * @param resultCode
     */
    public static void pictureMultipleSelect(Activity activity, int resultCode) {
        pictureMultipleSelect(activity, resultCode, null, 6);
    }

    /**
     * 多选图片，没有裁剪
     *
     * @param activity
     * @param resultCode
     * @param list       已经选择的图片
     */
    public static void pictureMultipleSelect(Activity activity, int resultCode, List<LocalMedia> list, int max) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(max)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .selectionData(list)
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isGif(false)// 是否显示gif图片
                .isCompress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .setLanguage(0)
                .forResult(resultCode); // 结果回调onActivityResult code
    }

    /**
     * 质量压缩
     * 设置bitmap options属性，降低图片的质量，像素不会减少
     * 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
     * 设置options 属性0-100，来实现压缩
     */
    public static String qualityCompress(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }
        if (BaseApplication.getContext() == null) {
            return path;
        }
        path = getRealFilePath(BaseApplication.getContext(), path);
        //判断大小
        long size = new File(path).length();
        long m = 1024 * 1024;
        int quality = 100;
        if (size / 1024 > 200) {
            int num = (int) (size / m);
            if (num < qualitys.length - 1) {
                quality = qualitys[num];
            } else {
                quality = 20;
            }
        } else {
            quality = 100;
        }
        Bitmap preImg = BitmapFactory.decodeFile(path);
        if (preImg == null) {
            return null;
        }
        // 获取图片旋转角度，旋转图片
        int degree = getRotateDegree(path);
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap img = Bitmap.createBitmap(preImg, 0, 0, preImg.getWidth(), preImg.getHeight(), matrix, false);
        bitmaps.add(preImg);
        bitmaps.add(img);

        //新文件名
        String start = getMyDir();
        String end = path.substring(path.lastIndexOf("."));
        String pathCompress = start + "_" + System.currentTimeMillis() + end;
        delPath.add(pathCompress);
        //压缩
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, quality, bos);


        try {
            FileOutputStream fos = new FileOutputStream(new File(pathCompress));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
            bitmaps.add(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathCompress;
    }

    public static String getMyDir() {
        String newpath = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            newpath = BaseApplication.getContext().getExternalFilesDir("bxsfile").getAbsolutePath();
        } else {
            newpath = BaseApplication.getContext().getFilesDir().getAbsolutePath() + "bxsfile";

        }
        File fileNewDir = new File(newpath);
        if (!fileNewDir.exists()) {
            fileNewDir.mkdirs();
        }
        return newpath;
    }

    /**
     * 获取图片的旋转角度。
     * 只能通过原始文件获取，如果已经进行过bitmap操作无法获取。
     */
    private static int getRotateDegree(String path) {
        int result = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    result = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    result = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    result = 270;
                    break;
            }
        } catch (IOException ignore) {
            return 0;
        }
        return result;
    }

    /**
     * 获取文件的实际路径
     *
     * @param context
     * @param path
     * @return
     */
    public static String getRealFilePath(Context context, String path) {

        try {
            if (!path.startsWith("content") && !path.startsWith("file")) {
                path = URLEncoder.encode(path, "utf-8").replaceAll("\\+", "%20");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        final Uri uri = Uri.parse(path);
        return getRealFilePath(context, uri);
    }

    /**
     * 获取文件的实际路径
     */
    public static String getRealFilePath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }

                }
                cursor.close();
            }
            if (data == null) {
                data = getImageAbsolutePath(context, uri);
            }

        }
        return data;
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri)) {
                return imageUri.getLastPathSegment();
            }
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 展示图片
    ///////////////////////////////////////////////////////////////////////////
    public static void showImageList(Activity mActivity, List<String> list, int position) {
        List<LocalMedia> medias = new ArrayList<>();
        for (String string : list) {
            LocalMedia media = new LocalMedia();
            media.setPath(string);
            medias.add(media);
        }
        showImageMedia(mActivity, medias, position);
    }

    public static void showImageMedia(Activity mActivity, List<LocalMedia> medias, int position) {
        PictureSelector.create(mActivity)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .openExternalPreview(position, medias);
    }

    public static void showImageList(Fragment mFragment, List<String> list, int position) {
        List<LocalMedia> medias = new ArrayList<>();
        for (String string : list) {
            LocalMedia media = new LocalMedia();
            media.setPath(string);
            medias.add(media);
        }
        showImageMedia(mFragment, medias, position);
    }

    public static void showImageMedia(Fragment mFragment, List<LocalMedia> medias, int position) {
        PictureSelector.create(mFragment)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .openExternalPreview(position, medias);
    }
}
