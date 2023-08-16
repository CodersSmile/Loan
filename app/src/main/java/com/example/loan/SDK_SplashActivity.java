package com.example.loan;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;

public class SDK_SplashActivity extends AppCompatActivity {
    public static int need_internet;
    public static int show_ads;
    public static int show_appopen;
    public static int show_verson_dialog;
    public static int version;
    String AppResponse;
    private AppOpenManager appOpenManager;
    String appopen;
    /* access modifiers changed from: private */
    public AppDataListner callback;
    /* access modifiers changed from: private */
    public Dialog dialog;
    boolean is_retry;
    boolean is_splash_ad_loaded = false;
    /* access modifiers changed from: private */
    public SharedPreferences.Editor preference_editor;
    /* access modifiers changed from: private */
    public Handler refreshHandler;
    /* access modifiers changed from: private */
    public TextView retry_buttton;
    private Runnable runnable;

    public interface AppDataListner {
        void onReload();

        void onSuccess();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.retry_layout, (ViewGroup) null);
        this.dialog.setContentView(view);
        TextView textView = (TextView) view.findViewById(R.id.retry_buttton);
        this.retry_buttton = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!SDK_SplashActivity.this.is_retry) {
                    SDK_SplashActivity.this.startActivityForResult(new Intent("android.settings.SETTINGS"), 0);
                } else if (SDK_SplashActivity.need_internet == 1) {
                    SDK_SplashActivity.this.callback.onReload();
                } else {
                    SDK_SplashActivity.this.callback.onSuccess();
                }
            }
        });
    }

    public void InitSDK(Activity activity, AppDataListner callback2) {
        int app_open_mode;
        final Activity activity2 = activity;
        final AppDataListner appDataListner = callback2;
        MobileAds.initialize(activity2, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList(new String[]{"504C2EE746E52FB422E6532AD39442F1"})).build());
        this.callback = appDataListner;
        AppManager.myappprefrece = activity2.getSharedPreferences(activity.getPackageName(), 0);
        this.preference_editor = AppManager.myappprefrece.edit();
        checkInternetRequired(activity);
        checkAppUpdateRequired(activity);
        this.AppResponse = "app/response/" + activity.getPackageName();
        StringBuilder sb = new StringBuilder();
        sb.append(this.AppResponse);
        sb.append(Boolean.parseBoolean("true") ? "/debug/" : "/release/");
        this.AppResponse = sb.toString();
        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(calender.getTime());
        if (AppManager.myappprefrece.getString("firsttime", "true").equals("true")) {
            this.preference_editor.putString("date", currentDate).apply();
            this.preference_editor.putString("firsttime", "false").apply();
            app_open_mode = 1;
        } else if (!currentDate.equals(AppManager.myappprefrece.getString("date", ""))) {
            this.preference_editor.putString("date", currentDate).apply();
            app_open_mode = 2;
        } else {
            app_open_mode = 0;
        }
        this.AppResponse += app_open_mode;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(1, AESSUtils.decrypt(AESSUtils.baseCode()) + this.AppResponse, (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                String str;
                JSONObject jSONObject = response;
                String str2 = "native";
                Log.d("AppsPanel", response.toString());
                try {
                    boolean status = jSONObject.getBoolean(NotificationCompat.CATEGORY_STATUS);
                    str = "AppsPanel";
                    if (status) {
                        try {
                            JSONObject app_data = jSONObject.getJSONObject("app_data");
                            boolean z = status;
                            JSONObject ads_data = jSONObject.getJSONObject("ads_data");
                            SDK_SplashActivity.this.preference_editor.putInt("app_id", app_data.getInt("id"));
                            SDK_SplashActivity.this.preference_editor.putString("app_name", app_data.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                            SDK_SplashActivity.this.preference_editor.putInt("need_internet", app_data.getInt("need_internet"));
                            SDK_SplashActivity.this.preference_editor.putInt("show_ads", app_data.getInt("show_ads"));
                            SDK_SplashActivity.this.preference_editor.putInt("version", app_data.getInt("version"));
                            SDK_SplashActivity.this.preference_editor.putInt("show_verson_dialog", app_data.getInt("show_verson_dialog"));
                            SDK_SplashActivity.this.preference_editor.putString("privacy_link", app_data.getString("privacy_link"));
                            SDK_SplashActivity.this.preference_editor.putString("account", app_data.getString("account"));
                            SDK_SplashActivity.this.preference_editor.putInt("show_appopen", ads_data.getInt("show_appopen"));
                            SDK_SplashActivity.this.preference_editor.putInt("inter_clicklimit", ads_data.getInt("inter_clicklimit"));
                            SDK_SplashActivity.this.preference_editor.putString("appopen", ads_data.getString("appopen"));
                            SDK_SplashActivity.this.preference_editor.putString("banner", ads_data.getString("banner"));
                            String str3 = "inter";
                            SDK_SplashActivity.this.preference_editor.putString(str3, ads_data.getString(str3));
                            String str4 = str2;
                            SDK_SplashActivity.this.preference_editor.putString(str4, ads_data.getString(str4));
                            String str5 = "rewardedvideo";
                            SDK_SplashActivity.this.preference_editor.putString(str5, ads_data.getString(str5));
                            SDK_SplashActivity.this.preference_editor.apply();
                        } catch (Exception e) {
                            e = e;
                            Log.d(str, e.toString());
                            SDK_SplashActivity.this.checkAppUpdateRequired(activity2);
                            appDataListner.onSuccess();
                        }
                    } else {
                        Toast.makeText(activity2, "App is not assigned", 0).show();
                    }
                } catch (Exception e2) {
                    e = e2;
                    str = "AppsPanel";
                    Log.d(str, e.toString());
                    SDK_SplashActivity.this.checkAppUpdateRequired(activity2);
                    appDataListner.onSuccess();
                }
                SDK_SplashActivity.this.checkAppUpdateRequired(activity2);
                appDataListner.onSuccess();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("AppsPanel", error.toString());
            }
        });
        jsonObjectRequest.setShouldCache(false);
        requestQueue.add(jsonObjectRequest);
    }

    private void checkInternetRequired(final Activity activity) {
        need_internet = AppManager.myappprefrece.getInt("need_internet", 1);
        this.refreshHandler = new Handler();
        AnonymousClass5 r0 = new Runnable() {
            public void run() {
                if (SDK_SplashActivity.this.isNetworkAvailable()) {
                    SDK_SplashActivity.this.is_retry = true;
                    SDK_SplashActivity.this.retry_buttton.setText(activity.getString(R.string.retry));
                } else if (SDK_SplashActivity.need_internet == 1) {
                    SDK_SplashActivity.this.dialog.show();
                    SDK_SplashActivity.this.is_retry = false;
                    SDK_SplashActivity.this.retry_buttton.setText(activity.getString(R.string.connect_internet));
                }
                SDK_SplashActivity.this.refreshHandler.postDelayed(this, 1000);
            }
        };
        this.runnable = r0;
        this.refreshHandler.postDelayed(r0, 1000);
    }

    /* access modifiers changed from: private */
    public void checkAppUpdateRequired(Activity activity) {
        version = AppManager.myappprefrece.getInt("version", 1);
        int i = AppManager.myappprefrece.getInt("show_verson_dialog", 0);
        show_verson_dialog = i;
        if (i == 1 && version != getCurrentVersionCode()) {
            this.is_splash_ad_loaded = true;
            showUpdateDialog(activity);
        }
    }

    /* access modifiers changed from: private */
    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public int getCurrentVersionCode() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void showUpdateDialog(final Activity activity) {
        Dialog dialog2 = new Dialog(activity);
        dialog2.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.installnewappdialog, (ViewGroup) null);
        dialog2.setContentView(view);
        TextView update = (TextView) view.findViewById(R.id.update);
        TextView txt_decription = (TextView) view.findViewById(R.id.txt_decription);
        update.setText("Update Now");
        ((TextView) view.findViewById(R.id.txt_title)).setText("Update our new app now and enjoy");
        txt_decription.setText("");
        txt_decription.setVisibility(8);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    SDK_SplashActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
                } catch (ActivityNotFoundException e) {
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            dialog2.create();
        }
        dialog2.show();
        Window window = dialog2.getWindow();
        window.setLayout(-1, -2);
        window.setBackgroundDrawable(new ColorDrawable(0));
    }
}
