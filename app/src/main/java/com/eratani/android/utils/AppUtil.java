package com.eratani.android.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.eratani.android.MyApplication;
import com.eratani.android.R;
import com.eratani.android.configapp.http.bean.User;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtil {

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("wtf", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    public static String extractYoutubeId(String url) {
        String videoId = "";
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }


    public static String getThubnailYoutube(String videoId) {
        String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg";
        return img_url;
    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static void openBrowser(Context context, String url) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW);
        appIntent.setData(Uri.parse(url));
        context.startActivity(appIntent);
    }

    /**
     * ?????? ???????????????????????? %20
     *
     * @param s
     * @return
     */
    public static String uriEncode(String s) {
        return Uri.encode(s);
    }
//
//    /**
//     * ?????????onCreate?????????mBtn.setBackgroundResource(R.drawable.splash)????????????????????????????????????onDestroy?????????((BitmapDrawable)mBtn.getBackground()).setCallback(null)?????????????????????????????????????????????????????????????????????????????????????????????????????????Bitmap?????????????????????????????????????????????java??????BitmapDrawable??????android?????????????????????Bitmap???????????????????????????ndk??????c??????????????????setCallback?????????????????????????????????????????????Bitmap???recycle()??????????????????
//     * <p>
//     * ?????????????????????onDestroy??????((BitmapDrawable)mBtn.getBackground()).getBitmap().recycle()????????????????????????????????????????????????????????????activity????????????????????????????????????activity?????????????????????????????????????????????activity????????????????????????????????????????????????????????????????????????activity?????????class????????????????????????10m????????????????????????????????????????????????android???????????????1.5,16??????????????????????????????????????????????????????????????????pdf??????????????????????????????
//     * <p>
//     * ????????????????????????????????????????????????activity??????????????????try to use a recycled bitmap"????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????recycle???????????? ??????????????????????????????????????????????????????activity?????????????????????????????????????????????????????????????????????????????????????????????setBackgroundResource?????????????????????????????????android????????????????????????id??????????????????????????????cache?????????????????????????????????????????????????????????cache???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????sdk???????????????
//     * <p>
//     * Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.splash);
//     * BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
//     * <p>
//     * mBtn.setBackgroundDrawable(bd);
//     * <p>
//     * ?????????mBtn.setBackgroundResource(R.drawable.splash)???
//     * <p>
//     * ????????????????????????
//     * <p>
//     * BitmapDrawable bd ??? (BitmapDrawable)mBtn.getBackground();
//     * <p>
//     * mBtn.setBackgroundResource(0);//????????????????????????null?????????onDraw????????????????????????used a recycled bitmap??????
//     * <p>
//     * bd.setCallback(null);
//     * bd.getBitmap().recycle();
//     * <p>
//     * ??????????????????????????????????????????????????????????????????????????????????????????
//     * <p>
//     * <p>
//     * ??????????????????????????????????????????inSampleSize ???????????? ????????????inTargetDensity/inDensity????????????
//     * ?????????????????????????????????  {@link #recycleBackground(View)} ??????????????????
//     *
//     * @param view
//     * @param resId
//     */
//    public static void setBackgroundResourceForView(View view, @DrawableRes int resId) {
//       LogUtil.logI("setBackgroundResourceForView");
//        Bitmap bm = generateBitmapForView(view, resId);
//        if (bm != null) {
//            BitmapDrawable bd = new BitmapDrawable(view.getResources(), bm);
//            LogUtil.logI("setBackgroundResourceForView bm");
//            view.setBackground(bd);
//        }
//    }
//
//    /**
//     * ??????????????????????????????????????????inSampleSize???????????? ????????????inTargetDensity/inDensity????????????
//     * ????????????????????????????????? {@link #recycleImageView(ImageView)}  ??????????????????
//     *
//     * @param view
//     * @param resId
//     */
//    public static void setImgResourceForImageView(ImageView view, @DrawableRes int resId) {
//        LogUtil.logI("setBackgroundResourceForImageView");
//        Bitmap bm = generateBitmapForView(view, resId);
//        if (bm != null) {
//            LogUtil.logI("setBackgroundResourceForImageView bm");
//            view.setImageBitmap(bm);
//        }
//    }

//    public static Bitmap generateBitmapForView(View view, @DrawableRes int resId) {
//        LogUtil.logI("generateBitmapForView");
//        if (view == null)
//            return null;
//
//        int height = view.getMeasuredHeight(), width = view.getMeasuredWidth();
//        LogUtil.logI(String.format("generateBitmapForView  width=%s height=%s", width, height));
//        LogUtil.logI(String.format("generateBitmapForView  width=%s height=%s", view.getWidth(), view.getHeight()));
//
//        if (width == 0) {
//            if (height == 0) {
//                view.measure(0, 0);
//                LogUtil.logI("generateBitmapForView   view.measure");
//                width = view.getMeasuredWidth();
//                height = view.getMeasuredHeight();
//            } else {
//                width = height;
//            }
//        } else {
//            if (height == 0) {
//                height = width;
//                LogUtil.logI("generateBitmapForView   height = width");
//            }
//        }
//
//        LogUtil.logI(String.format("generateBitmapForView  width=%s height=%s", width, height));
//
//        return Utils.decodeResource(view.getContext(), resId, width, height);
//    }

    public static boolean isYoutubeUrl(String youTubeURl) {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
            success = true;
        } else {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }

    public static void recycleBackground(View view) {
        if (view != null && view.getBackground() != null) {
            BitmapDrawable bd = (BitmapDrawable) view.getBackground();
            recycleBitmapDrawable(bd);
            view.setBackgroundResource(0);
        }
    }

    public static void recycleImageView(ImageView imageView) {
        if (imageView != null) {
            if (imageView.getDrawable() != null) {//??????????????????
                recycleBitmapDrawable(imageView.getDrawable());
                imageView.setImageDrawable(null);
            }
            //??????????????????
            recycleBackground(imageView);
        }
    }

    public static void recycleBitmapDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                recycleBitmap(((BitmapDrawable) drawable).getBitmap());
            }
            drawable.setCallback(null);
        }
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
    }

    public static String getAppH5CachePath(Context context) {
        return context.getApplicationContext().getFilesDir().getAbsolutePath() + "H5Cache/";
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static int getAppVersionCode(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            return (int) getPackageInfo(context).getLongVersionCode();
        }
        return getPackageInfo(context).versionCode;
    }

    public static String getAppVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * ?????????????????????app
     * <p>
     * (flags & ApplicationInfo.FLAG_SYSTEM) != 0 ??????????????????
     * (flags & ApplicationInfo.FLAG_SYSTEM) <= 0  ???????????????????????????
     * (flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0  ?????????????????????????????????????????????????????????????????????
     *
     * @param context
     * @return
     */
    public static Boolean isPreInstalledApp(Context context) {
        int flags = getPackageInfo(context).applicationInfo.flags;
        return (flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public static String getAppMetaData(Context context, String key) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo applicationInfo = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);

            return applicationInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static boolean isGooglePlayServiceAvailable(Context context) {
//        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
//        if (status == ConnectionResult.SUCCESS) {
//            com.qschou.pedulisehat.android.utils.LogUtil.logE("GooglePlayService", "GooglePlayServicesUtil service is available.");
//            return true;
//        }
//
//        com.qschou.pedulisehat.android.utils.LogUtil.logE("GooglePlayService", "GooglePlayServicesUtil service is NOT available.");
//        return false;
//    }
//
//
//    /**
//     * Check the device to make sure it has the Google Play Services APK. If
//     * it doesn't, display a dialog that allows users to download the APK from
//     * the Google Play Store or enable it in the device's system settings.
//     */
//    public static boolean checkIsGooglePlayServiceAvailable(AppCompatActivity context) {
////        int statusCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
////        if (statusCode != ConnectionResult.SUCCESS) {
////            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(context);
////        }
//
//        if (BuildConfig.DEBUG) return true;
//
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(context, resultCode, AppConstants.RequestCode.RSC_PLAY_SERVICES_RESOLUTION_REQUEST
//                        , dialog -> {
////                            dialog.dismiss();
//                            context.finish();
//                        }).show();
//            } else {
//                LogUtil.logI("Google play service is not supported on this device .");
//                context.finish();
//            }
//            return false;
//        }
//        return true;
//    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }

    public static void insertStatusBarHeight2TopPadding(View view) {
        setTopPadding(view, view.getPaddingTop() + getStatusBarHeight(view.getContext()));
    }

    public static void setBottomPadding(View view, int bottomPadding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), bottomPadding);
    }

    public static void setTopPadding(View view, int topPadding) {
        view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static <T> T getFieldInstance(Object object, String fieldName, Class<T> clz) {
        if (object == null) return null;

        try {
            Field declaredField = object.getClass().getDeclaredField(fieldName);
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }

            Object field = declaredField.get(object);
            if (clz.isInstance(field)) {
                return clz.cast(field);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


//    public static void fixFacebookLeak() {
//        try {
//
//            Field f = ActivityLifecycleTracker.class.getDeclaredField("viewIndexer");
//            if (!f.isAccessible()) {
//                f.setAccessible(true);
//            }
//
//            Object fieldVal = f.get(null);
//            if (fieldVal instanceof ViewIndexer) {
//                ((ViewIndexer) fieldVal).unschedule();
//                Timer timer = getFieldInstance(fieldVal, "indexingTimer", Timer.class);
//                if (timer != null) {
//                    timer.cancel();
//                }
//
//                Handler handler = getFieldInstance(fieldVal, "uiThreadHandler", Handler.class);
//                if (handler != null) {
//                    handler.removeCallbacksAndMessages(null);
//                }
//            }
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    public static void fixInputMethodManagerLeak(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = null;
        try {
            inputMethodManager = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (inputMethodManager == null) return;
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = inputMethodManager.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) continue;
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(inputMethodManager);
                if (obj == null || !(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(inputMethodManager, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private final static String UNREAD_MSG_COUNT = "UnreadNotificationCount";
    private final static String DONOR_VERSION = "donorVersion";

    //    public static void removeUser(String userId) {
//        if (userId == null) return;
//        final String key = String.format("user[%s]", userId);
//        SharedPreferencesTool.getInstance(MyApplication.getContext()).putString(key, null);
//    }
//
    public static void saveUser(User user) {
        if (user == null) return;
        final String key = String.format("user[%s]", user.getUserId());
        SharedPreferencesTool.getInstance(MyApplication.getContext()).putObject(key, user);
    }

    //
    public static User getUser(String userId) {
        if (userId == null) return null;
        final String key = String.format("user[%s]", userId);
        return SharedPreferencesTool.getInstance(MyApplication.getContext()).getObject(key, User.class, null);
    }

    //
    public static int getUnreadNotificationCount(String user_id) {
        if (TextUtils.isEmpty(user_id)) return 0;
        return SharedPreferencesTool.getInstance(MyApplication.getContext()).getInt(UNREAD_MSG_COUNT + user_id, 0);
    }

    //
    public static void updateUnreadNotificationCount(String user_id, int count) {
        if (TextUtils.isEmpty(user_id)) return;
        SharedPreferencesTool.getInstance(MyApplication.getContext()).putInt(UNREAD_MSG_COUNT + user_id, count);
    }

    //
    public static boolean getDonorVersionVal(String user_id) {
        if (TextUtils.isEmpty(user_id)) return true;
        return SharedPreferencesTool.getInstance(MyApplication.getContext()).getBoolean(DONOR_VERSION + user_id, true);
    }

    //
    public static void saveDonorVersionVal(String user_id, boolean donorVersion) {
        if (TextUtils.isEmpty(user_id)) return;
        SharedPreferencesTool.getInstance(MyApplication.getContext()).putBoolean(DONOR_VERSION + user_id, donorVersion);
    }

    //
//
    public static Drawable tintDrawable(Drawable drawable, @ColorInt int color) {
        if (drawable == null) return null;
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    //
    private static SparseArray<Typeface> fontCache = new SparseArray<>(2);

    public static Typeface getFont(Context context, @FontRes int id) {
        if (id == -1) { //id=-1 do preload and cache font resource
            getFont(context, R.font.helvetica);
            getFont(context, R.font.helvetica);
            getFont(context, R.font.helvetica);
            return null;
        }

        Typeface typeface = fontCache.get(id);
        if (typeface != null) {
            return typeface;
        }

        try {
            ResourcesCompat.getFont(context.getApplicationContext(), id, new ResourcesCompat.FontCallback() {
                @Override
                public void onFontRetrieved(@NonNull Typeface typeface) {
                    fontCache.append(id, typeface);
                }

                @Override
                public void onFontRetrievalFailed(int reason) {

                }
            }, null);
//        } catch (Resources.NotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id != R.font.helvetica ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT;
    }

    public static boolean isRouterUriMatch(Uri a, Uri b) {
        if (a == null || b == null) return false;

        if (!a.equals(b)) {
            if (a.getScheme() == null || !a.getScheme().equals(b.getScheme()))
                return false;

            if (a.getAuthority() == null || !a.getAuthority().equals(b.getAuthority()))
                return false;

            if (TextUtils.isEmpty(a.getPath())) {
                return TextUtils.isEmpty(b.getPath());
            }

            return a.getPath().equals(b.getPath());
        }

        return true;
    }

    public static Gson getGsonInstance() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }


    public static Gson ConverterJsonToObject() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }


}
