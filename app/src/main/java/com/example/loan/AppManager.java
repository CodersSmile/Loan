package com.example.loan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.pesonal.adsdk.R;
import feyo.pesonal.adsdk.AppOpenManager;

public class AppManager {
    static Context activity;
    private static AppManager appManager;
    public static int app_id = 0;
    public static String app_name = "";
    public static String appopen = "";
    public static String banner = "";
    static InterstitialCallback iCallback;
    public static String inter = "";
    public static int inter_clicklimit = 1;
    public static SharedPreferences myappprefrece;
    public static String native1 = "";
    public static int need_internet = 1;
    public static String onesignal_app_id = "";
    public static String play_store_account = "";
    public static String privacy_link = "";
    public static String rewardedvideo = "";
    static RewardedInterCallback riCallback;
    static RewardedVideoCallback rvCallback;
    public static int show_ads = 0;
    public static int show_appopen = 1;
    public static int show_verson_dialog = 0;
    static SplashAdCallback splashAdCallback;
    public static int version = 1;
    /* access modifiers changed from: private */
    public boolean ad_load_failed = false;
    private boolean ad_ri_load_failed = false;
    /* access modifiers changed from: private */
    public boolean ad_rv_load_failed = false;
    private String admob_ao_pre;
    private String admob_i_pre;
    /* access modifiers changed from: private */
    public AppOpenManager appOpenManager;
    /* access modifiers changed from: private */
    public Dialog dialog;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    /* access modifiers changed from: private */
    public RewardedAd mRewardedAd;
    private RewardedInterstitialAd mRewardedInterstitialAd;
    /* access modifiers changed from: private */
    public NativeAd nativeAdL;
    /* access modifiers changed from: private */
    public NativeAd nativeAdM;
    /* access modifiers changed from: private */
    public NativeAd nativeAdS;

    public interface InterstitialCallback {
        void callbackCall();
    }

    public interface RewardedInterCallback {
        void callbackCall(int i);
    }

    public interface RewardedVideoCallback {
        void callbackCall(int i);
    }

    public interface SplashAdCallback {
        void callbackCall();
    }

    public AppManager(Context activity2) {
        activity = activity2;
        SharedPreferences sharedPreferences = activity2.getSharedPreferences(activity2.getPackageName(), 0);
        myappprefrece = sharedPreferences;
        app_id = sharedPreferences.getInt("app_id", 0);
        app_name = myappprefrece.getString("app_name", "");
        need_internet = myappprefrece.getInt("need_internet", 1);
        show_ads = myappprefrece.getInt("show_ads", 0);
        version = myappprefrece.getInt("version", 1);
        show_verson_dialog = myappprefrece.getInt("show_verson_dialog", 0);
        privacy_link = myappprefrece.getString("privacy_link", "");
        play_store_account = myappprefrece.getString("account", "");
        onesignal_app_id = myappprefrece.getString("onesignal_app_id", "");
        show_appopen = myappprefrece.getInt("show_appopen", 0);
        appopen = myappprefrece.getString("appopen", "");
        banner = myappprefrece.getString("banner", "");
        inter = myappprefrece.getString("inter", "");
        inter_clicklimit = myappprefrece.getInt("inter_clicklimit", 1);
        native1 = myappprefrece.getString("native", "");
        rewardedvideo = myappprefrece.getString("rewardedvideo", "");
    }

    public static AppManager getInstance(Context activity2) {
        activity = activity2;
        if (appManager == null) {
            appManager = new AppManager(activity2);
        }
        return appManager;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo[] info;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity == null || (info = connectivity.getAllNetworkInfo()) == null) {
            return false;
        }
        for (NetworkInfo state : info) {
            if (state.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSkipInterAd(Context context) {
        int limit = inter_clicklimit - 1;
        int click_count = context.getSharedPreferences("APP_MANAGER", 0).getInt("click_count", 0) + 1;
        if (click_count > limit) {
            click_count = 0;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences("APP_MANAGER", 0).edit();
        editor.putInt("click_count", click_count);
        editor.commit();
        Log.e("AppsPanel", "ClickCount : " + limit + " - " + click_count);
        if (limit != click_count) {
            return true;
        }
        return false;
    }

    public void initAppOpen(Activity activity2, final SplashAdCallback aoCallback) {
        if (show_ads == 0 || show_appopen == 0 || appopen.isEmpty()) {
            Log.d("AppsPanel", "Ad is Off");
            aoCallback.callbackCall();
            return;
        }
        this.admob_ao_pre = appopen;
        Log.d("AppsPanel", "AppOpen Ad-Request : " + appopen);
        AppOpenManager appOpenManager2 = new AppOpenManager(activity2);
        this.appOpenManager = appOpenManager2;
        appOpenManager2.loadAd(this.admob_ao_pre, new AppOpenManager.splshADlistner() {
            public void onSuccess() {
                Log.d("AppsPanel", "AppOpen Ad Loaded :");
                AppManager.this.appOpenManager.showAd(new AppOpenManager.splshADlistner() {
                    public void onSuccess() {
                        Log.d("AppsPanel", "AppOpen Ad Dissmissed");
                        aoCallback.callbackCall();
                    }

                    public void onShowAd() {
                        Log.d("AppsPanel", "AppOpen Ad Opened");
                    }

                    public void onError(String error) {
                        Log.d("AppsPanel", "AppOpen Ad Failed to Open +" + error);
                        aoCallback.callbackCall();
                    }
                });
            }

            public void onError(String error) {
                Log.d("AppsPanel", "AppOpen Ad Load failed :" + error);
                aoCallback.callbackCall();
            }

            public void onShowAd() {
            }
        });
    }

    public void loadAppOpenAd(Activity activity2) {
        if (show_ads == 0 || show_appopen == 0 || appopen.isEmpty()) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        Log.d("AppsPanel", "AppOpen Ad-Request : " + appopen);
        loadAdmobAppOpen(activity2, appopen);
    }

    private void loadAdmobAppOpen(Activity activity2, String admob_ao) {
        this.admob_ao_pre = admob_ao;
        AppOpenManager appOpenManager2 = new AppOpenManager(activity2);
        this.appOpenManager = appOpenManager2;
        appOpenManager2.loadAd(admob_ao, new AppOpenManager.splshADlistner() {
            public void onSuccess() {
                Log.d("AppsPanel", "AppOpen Ad Loaded :");
            }

            public void onError(String error) {
                Log.d("AppsPanel", "AppOpen Ad Load failed :" + error);
            }

            public void onShowAd() {
            }
        });
    }

    public void showAppOpenAd(Activity context, SplashAdCallback aoCallback) {
        splashAdCallback = aoCallback;
        if (show_ads == 0 || show_appopen == 0 || appopen.isEmpty() || !isNetworkAvailable(context)) {
            splashAddCallBack();
        } else if (!this.appOpenManager.chekAdAvailablity()) {
            Log.d("AppsPanel", "Appopen Ad not loaded, waiting...");
            this.dialog = new Dialog(context);
            this.dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, (ViewGroup) null));
            this.dialog.setCancelable(false);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.show();
            new CountDownTimer(2000, 10) {
                public void onTick(long millisUntilFinished) {
                    double d = (double) ((millisUntilFinished / 10) / 2);
                }

                public void onFinish() {
                    AppManager.this.dialog.dismiss();
                    if (!AppManager.this.appOpenManager.chekAdAvailablity()) {
                        Log.d("AppsPanel", "Appopen Ad still not loaded.");
                        AppManager.this.splashAddCallBack();
                        return;
                    }
                    AppManager.this.appOpenManager.showAd(new AppOpenManager.splshADlistner() {
                        public void onSuccess() {
                            Log.d("AppsPanel", "AppOpen Ad Dissmissed");
                            AppManager.this.splashAddCallBack();
                        }

                        public void onShowAd() {
                            Log.d("AppsPanel", "AppOpen Ad Opned");
                        }

                        public void onError(String error) {
                            Log.d("AppsPanel", "AppOpen Ad Failed to Open +" + error);
                            AppManager.this.splashAddCallBack();
                        }
                    });
                }
            }.start();
        } else {
            this.appOpenManager.showAd(new AppOpenManager.splshADlistner() {
                public void onSuccess() {
                    Log.d("AppsPanel", "AppOpen Ad Dissmissed");
                    AppManager.this.splashAddCallBack();
                }

                public void onShowAd() {
                    Log.d("AppsPanel", "AppOpen Ad Opned");
                }

                public void onError(String error) {
                    Log.d("AppsPanel", "AppOpen Ad Failed to Open +" + error);
                    AppManager.this.splashAddCallBack();
                }
            });
        }
    }

    public void loadNativeAd(final String AD_SIZE, final Activity activity2) {
        Log.d("AppsPanel", AD_SIZE + " Native Ad-Request : " + native1);
        AdLoader.Builder builder = new AdLoader.Builder(activity2, native1);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            public void onNativeAdLoaded(NativeAd nativeAd) {
                Log.d("AppsPanel", AD_SIZE + " Native Ad Response ID - " + nativeAd.getResponseInfo().getResponseId());
                if ((Build.VERSION.SDK_INT >= 17 && activity2.isDestroyed()) || activity2.isFinishing() || activity2.isChangingConfigurations()) {
                    nativeAd.destroy();
                } else if (AD_SIZE.equals("LARGE")) {
                    NativeAd unused = AppManager.this.nativeAdL = nativeAd;
                } else if (AD_SIZE.equals("MEDIUM")) {
                    NativeAd unused2 = AppManager.this.nativeAdM = nativeAd;
                } else if (AD_SIZE.equals("SMALL")) {
                    NativeAd unused3 = AppManager.this.nativeAdS = nativeAd;
                }
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d("AppsPanel", AD_SIZE + " Native Ad Failed -" + loadAdError.toString());
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    public void showNativeLarge(Activity activity2, LinearLayout adLayout) {
        if (show_ads == 0) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        if (this.nativeAdL != null) {
            Log.d("AppsPanel", "Native Ad Show Large - " + this.nativeAdL.getResponseInfo().getResponseId());
            NativeAdView nativeAdView = (NativeAdView) activity2.getLayoutInflater().inflate(R.layout.native_ad_large, (ViewGroup) null);
            nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.ad_media));
            nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
            nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
            nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
            nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
            nativeAdView.setPriceView(nativeAdView.findViewById(R.id.ad_price));
            nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
            nativeAdView.setStoreView(nativeAdView.findViewById(R.id.ad_store));
            nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.ad_advertiser));
            ((TextView) nativeAdView.getHeadlineView()).setText(this.nativeAdL.getHeadline());
            if (this.nativeAdL.getBody() == null) {
                nativeAdView.getBodyView().setVisibility(4);
            } else {
                nativeAdView.getBodyView().setVisibility(0);
                ((TextView) nativeAdView.getBodyView()).setText(this.nativeAdL.getBody());
            }
            if (this.nativeAdL.getCallToAction() == null) {
                nativeAdView.getCallToActionView().setVisibility(4);
            } else {
                nativeAdView.getCallToActionView().setVisibility(0);
                ((TextView) nativeAdView.getCallToActionView()).setText(this.nativeAdL.getCallToAction());
            }
            if (this.nativeAdL.getIcon() == null) {
                nativeAdView.getIconView().setVisibility(8);
            } else {
                ((ImageView) nativeAdView.getIconView()).setImageDrawable(this.nativeAdL.getIcon().getDrawable());
                nativeAdView.getIconView().setVisibility(0);
            }
            if (this.nativeAdL.getPrice() == null) {
                nativeAdView.getPriceView().setVisibility(4);
            } else {
                nativeAdView.getPriceView().setVisibility(0);
                ((TextView) nativeAdView.getPriceView()).setText(this.nativeAdL.getPrice());
            }
            if (this.nativeAdL.getStore() == null) {
                nativeAdView.getStoreView().setVisibility(4);
            } else {
                nativeAdView.getStoreView().setVisibility(0);
                ((TextView) nativeAdView.getStoreView()).setText(this.nativeAdL.getStore());
            }
            if (this.nativeAdL.getStarRating() == null) {
                nativeAdView.getStarRatingView().setVisibility(4);
            } else {
                ((RatingBar) nativeAdView.getStarRatingView()).setRating(this.nativeAdL.getStarRating().floatValue());
                nativeAdView.getStarRatingView().setVisibility(0);
            }
            if (this.nativeAdL.getAdvertiser() == null) {
                nativeAdView.getAdvertiserView().setVisibility(4);
            } else {
                ((TextView) nativeAdView.getAdvertiserView()).setText(this.nativeAdL.getAdvertiser());
                nativeAdView.getAdvertiserView().setVisibility(0);
            }
            nativeAdView.setNativeAd(this.nativeAdL);
            adLayout.removeAllViews();
            adLayout.addView(nativeAdView);
        } else {
            Log.d("AppsPanel", "Large Native Ad is Null");
        }
        loadNativeAd("LARGE", activity2);
    }

    public void showNativeMedium(final Activity activity2, final LinearLayout adLayout) {
        if (show_ads == 0) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        if (this.nativeAdM == null) {
            Log.d("AppsPanel", "Medium Native Ad-Request : " + native1);
            AdLoader.Builder builder = new AdLoader.Builder(activity2, native1);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                public void onNativeAdLoaded(NativeAd nativeAd) {
                    Log.d("AppsPanel", "Medium Native Ad Response ID - " + nativeAd.getResponseInfo().getResponseId());
                    if ((Build.VERSION.SDK_INT < 17 || !activity2.isDestroyed()) && !activity2.isFinishing() && !activity2.isChangingConfigurations()) {
                        Log.d("AppsPanel", "Native Ad Show Medium - " + nativeAd.getResponseInfo().getResponseId());
                        NativeAdView nativeAdView = (NativeAdView) activity2.getLayoutInflater().inflate(R.layout.native_ad_medium, (ViewGroup) null);
                        nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.ad_media));
                        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
                        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
                        nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
                        nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
                        nativeAdView.setPriceView(nativeAdView.findViewById(R.id.ad_price));
                        nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
                        nativeAdView.setStoreView(nativeAdView.findViewById(R.id.ad_store));
                        nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.ad_advertiser));
                        ((TextView) nativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
                        if (nativeAd.getBody() == null) {
                            nativeAdView.getBodyView().setVisibility(4);
                        } else {
                            nativeAdView.getBodyView().setVisibility(0);
                            ((TextView) nativeAdView.getBodyView()).setText(nativeAd.getBody());
                        }
                        if (nativeAd.getCallToAction() == null) {
                            nativeAdView.getCallToActionView().setVisibility(4);
                        } else {
                            nativeAdView.getCallToActionView().setVisibility(0);
                            ((TextView) nativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
                        }
                        if (nativeAd.getIcon() == null) {
                            nativeAdView.getIconView().setVisibility(8);
                        } else {
                            ((ImageView) nativeAdView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                            nativeAdView.getIconView().setVisibility(0);
                        }
                        if (nativeAd.getPrice() == null) {
                            nativeAdView.getPriceView().setVisibility(4);
                        } else {
                            nativeAdView.getPriceView().setVisibility(0);
                            ((TextView) nativeAdView.getPriceView()).setText(nativeAd.getPrice());
                        }
                        if (nativeAd.getStore() == null) {
                            nativeAdView.getStoreView().setVisibility(4);
                        } else {
                            nativeAdView.getStoreView().setVisibility(0);
                            ((TextView) nativeAdView.getStoreView()).setText(nativeAd.getStore());
                        }
                        if (nativeAd.getStarRating() == null) {
                            nativeAdView.getStarRatingView().setVisibility(4);
                        } else {
                            ((RatingBar) nativeAdView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
                            nativeAdView.getStarRatingView().setVisibility(0);
                        }
                        if (nativeAd.getAdvertiser() == null) {
                            nativeAdView.getAdvertiserView().setVisibility(4);
                        } else {
                            ((TextView) nativeAdView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                            nativeAdView.getAdvertiserView().setVisibility(0);
                        }
                        nativeAdView.setNativeAd(nativeAd);
                        adLayout.removeAllViews();
                        adLayout.addView(nativeAdView);
                        return;
                    }
                    nativeAd.destroy();
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Log.d("AppsPanel", "Medium Native Ad Failed -" + loadAdError.toString());
                }
            }).build().loadAd(new AdRequest.Builder().build());
        } else {
            Log.d("AppsPanel", "Native Ad Show Medium - " + this.nativeAdM.getResponseInfo().getResponseId());
            NativeAdView nativeAdView = (NativeAdView) activity2.getLayoutInflater().inflate(R.layout.native_ad_medium, (ViewGroup) null);
            nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.ad_media));
            nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
            nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
            nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
            nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
            nativeAdView.setPriceView(nativeAdView.findViewById(R.id.ad_price));
            nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
            nativeAdView.setStoreView(nativeAdView.findViewById(R.id.ad_store));
            nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.ad_advertiser));
            ((TextView) nativeAdView.getHeadlineView()).setText(this.nativeAdM.getHeadline());
            if (this.nativeAdM.getBody() == null) {
                nativeAdView.getBodyView().setVisibility(4);
            } else {
                nativeAdView.getBodyView().setVisibility(0);
                ((TextView) nativeAdView.getBodyView()).setText(this.nativeAdM.getBody());
            }
            if (this.nativeAdM.getCallToAction() == null) {
                nativeAdView.getCallToActionView().setVisibility(4);
            } else {
                nativeAdView.getCallToActionView().setVisibility(0);
                ((TextView) nativeAdView.getCallToActionView()).setText(this.nativeAdM.getCallToAction());
            }
            if (this.nativeAdM.getIcon() == null) {
                nativeAdView.getIconView().setVisibility(8);
            } else {
                ((ImageView) nativeAdView.getIconView()).setImageDrawable(this.nativeAdM.getIcon().getDrawable());
                nativeAdView.getIconView().setVisibility(0);
            }
            if (this.nativeAdM.getPrice() == null) {
                nativeAdView.getPriceView().setVisibility(4);
            } else {
                nativeAdView.getPriceView().setVisibility(0);
                ((TextView) nativeAdView.getPriceView()).setText(this.nativeAdM.getPrice());
            }
            if (this.nativeAdM.getStore() == null) {
                nativeAdView.getStoreView().setVisibility(4);
            } else {
                nativeAdView.getStoreView().setVisibility(0);
                ((TextView) nativeAdView.getStoreView()).setText(this.nativeAdM.getStore());
            }
            if (this.nativeAdM.getStarRating() == null) {
                nativeAdView.getStarRatingView().setVisibility(4);
            } else {
                ((RatingBar) nativeAdView.getStarRatingView()).setRating(this.nativeAdM.getStarRating().floatValue());
                nativeAdView.getStarRatingView().setVisibility(0);
            }
            if (this.nativeAdM.getAdvertiser() == null) {
                nativeAdView.getAdvertiserView().setVisibility(4);
            } else {
                ((TextView) nativeAdView.getAdvertiserView()).setText(this.nativeAdM.getAdvertiser());
                nativeAdView.getAdvertiserView().setVisibility(0);
            }
            nativeAdView.setNativeAd(this.nativeAdM);
            adLayout.removeAllViews();
            adLayout.addView(nativeAdView);
        }
        loadNativeAd("MEDIUM", activity2);
    }

    public void showNativeSmall(final Activity activity2, final LinearLayout adLayout) {
        if (show_ads == 0) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        if (this.nativeAdS == null) {
            Log.d("AppsPanel", "Small Native Ad-Request - " + native1);
            AdLoader.Builder builder = new AdLoader.Builder(activity2, native1);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                public void onNativeAdLoaded(NativeAd nativeAd) {
                    Log.d("AppsPanel", "Small Native Ad Response ID - " + nativeAd.getResponseInfo().getResponseId());
                    if ((Build.VERSION.SDK_INT < 17 || !activity2.isDestroyed()) && !activity2.isFinishing() && !activity2.isChangingConfigurations()) {
                        Log.d("AppsPanel", "Native Ad Show Small - " + nativeAd.getResponseInfo().getResponseId());
                        NativeAdView nativeAdView = (NativeAdView) activity2.getLayoutInflater().inflate(R.layout.native_ad_small, (ViewGroup) null);
                        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
                        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
                        nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
                        nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
                        nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
                        ((TextView) nativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
                        if (nativeAd.getBody() == null) {
                            nativeAdView.getBodyView().setVisibility(4);
                        } else {
                            nativeAdView.getBodyView().setVisibility(0);
                            ((TextView) nativeAdView.getBodyView()).setText(nativeAd.getBody());
                        }
                        if (nativeAd.getCallToAction() == null) {
                            nativeAdView.getCallToActionView().setVisibility(4);
                        } else {
                            nativeAdView.getCallToActionView().setVisibility(0);
                            ((TextView) nativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
                        }
                        if (nativeAd.getIcon() == null) {
                            nativeAdView.getIconView().setVisibility(8);
                        } else {
                            ((ImageView) nativeAdView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                            nativeAdView.getIconView().setVisibility(0);
                        }
                        if (nativeAd.getStarRating() == null) {
                            nativeAdView.getStarRatingView().setVisibility(4);
                        } else {
                            ((RatingBar) nativeAdView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
                            nativeAdView.getStarRatingView().setVisibility(0);
                        }
                        nativeAdView.setNativeAd(nativeAd);
                        adLayout.removeAllViews();
                        adLayout.addView(nativeAdView);
                        return;
                    }
                    nativeAd.destroy();
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Log.d("AppsPanel", "Small Native Ad Failed -" + loadAdError.toString());
                }
            }).build().loadAd(new AdRequest.Builder().build());
        } else {
            Log.d("AppsPanel", "Native Ad show  Small - " + this.nativeAdS.getResponseInfo().getResponseId());
            NativeAdView nativeAdView = (NativeAdView) activity2.getLayoutInflater().inflate(R.layout.native_ad_small, (ViewGroup) null);
            nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
            nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
            nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
            nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
            nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
            ((TextView) nativeAdView.getHeadlineView()).setText(this.nativeAdS.getHeadline());
            if (this.nativeAdS.getBody() == null) {
                nativeAdView.getBodyView().setVisibility(4);
            } else {
                nativeAdView.getBodyView().setVisibility(0);
                ((TextView) nativeAdView.getBodyView()).setText(this.nativeAdS.getBody());
            }
            if (this.nativeAdS.getCallToAction() == null) {
                nativeAdView.getCallToActionView().setVisibility(4);
            } else {
                nativeAdView.getCallToActionView().setVisibility(0);
                ((TextView) nativeAdView.getCallToActionView()).setText(this.nativeAdS.getCallToAction());
            }
            if (this.nativeAdS.getIcon() == null) {
                nativeAdView.getIconView().setVisibility(8);
            } else {
                ((ImageView) nativeAdView.getIconView()).setImageDrawable(this.nativeAdS.getIcon().getDrawable());
                nativeAdView.getIconView().setVisibility(0);
            }
            if (this.nativeAdS.getStarRating() == null) {
                nativeAdView.getStarRatingView().setVisibility(4);
            } else {
                ((RatingBar) nativeAdView.getStarRatingView()).setRating(this.nativeAdS.getStarRating().floatValue());
                nativeAdView.getStarRatingView().setVisibility(0);
            }
            nativeAdView.setNativeAd(this.nativeAdS);
            adLayout.removeAllViews();
            adLayout.addView(nativeAdView);
        }
        loadNativeAd("SMALL", activity2);
    }

    public void showBannerAd(final ViewGroup banner_container) {
        if (show_ads == 0) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        final AdView mAdView = new AdView(activity);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner);
        Log.d("AppsPanel", "Banner Ad Requested : " + mAdView.getAdUnitId() + " - " + mAdView.getAdSize().toString());
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("AppsPanel", "Banner Ad Load Failed :" + loadAdError);
                banner_container.removeAllViews();
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("AppsPanel", "Banner Ad Response : " + mAdView.getResponseInfo().getResponseId());
                banner_container.removeAllViews();
                banner_container.addView(mAdView);
            }
        });
    }

    public void loadInterstitialAd(Activity activity2) {
        if (show_ads == 0) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        Log.d("AppsPanel", "Interstitial Ad-Request : " + inter);
        loadAdmobInterstitial(activity2);
    }

    /* access modifiers changed from: private */
    public void loadAdmobInterstitial(final Activity activity2) {
        InterstitialAd.load(activity2, inter, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            public void onAdLoaded(InterstitialAd interstitialAd) {
                InterstitialAd unused = AppManager.this.mInterstitialAd = interstitialAd;
                Log.d("AppsPanel", "Interstitial Ad Response : " + interstitialAd.getResponseInfo().getResponseId());
                AppManager.this.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    public void onAdDismissedFullScreenContent() {
                        Log.d("AppsPanel", "Interstitial Ad Dismissed");
                        AppManager.this.loadAdmobInterstitial(activity2);
                        AppManager.this.interstitialCallBack();
                    }

                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("AppsPanel", "Interstitial Ad Show Failed : " + adError);
                    }

                    public void onAdShowedFullScreenContent() {
                        InterstitialAd unused = AppManager.this.mInterstitialAd = null;
                        Log.d("AppsPanel", "Interstitial Ad Opened");
                    }
                });
            }

            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d("AppsPanel", "Interstitial Ad Failed :" + loadAdError);
                InterstitialAd unused = AppManager.this.mInterstitialAd = null;
                boolean unused2 = AppManager.this.ad_load_failed = true;
            }
        });
    }

    public void showInterstitialAd(Activity context, InterstitialCallback iCallback2) {
        iCallback = iCallback2;
        if (show_ads == 0 || !isNetworkAvailable(context) || isSkipInterAd(context)) {
            interstitialCallBack();
            return;
        }
        if (this.ad_load_failed) {
            Log.d("AppsPanel", "Interstitial Ad-Request : " + inter);
            loadAdmobInterstitial(context);
        }
        InterstitialAd interstitialAd = this.mInterstitialAd;
        if (interstitialAd == null) {
            Log.d("AppsPanel", "Interstitial Ad not loaded, waiting...");
            this.dialog = new Dialog(context);
            this.dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, (ViewGroup) null));
            this.dialog.setCancelable(false);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.show();
            final Activity activity2 = context;
            new CountDownTimer(2000, 10) {
                public void onTick(long millisUntilFinished) {
                    double d = (double) ((millisUntilFinished / 10) / 2);
                }

                public void onFinish() {
                    AppManager.this.dialog.dismiss();
                    if (AppManager.this.mInterstitialAd == null) {
                        Log.d("AppsPanel", "Interstitial Ad still not loaded.");
                        Log.d("AppsPanel", "Interstitial Ad Requested : " + AppManager.inter);
                        AppManager.this.loadAdmobInterstitial(activity2);
                        AppManager.this.interstitialCallBack();
                        return;
                    }
                    AppManager.this.mInterstitialAd.show(activity2);
                }
            }.start();
            return;
        }
        interstitialAd.show(context);
    }

    public void loadRewardedVideoAd(Activity activity2) {
        if (show_ads == 0) {
            Log.d("AppsPanel", "Ad is Off");
            return;
        }
        Log.d("AppsPanel", "RewardedVideo Ad-Request : " + rewardedvideo);
        loadAdmobRewardedVideoAd(activity2);
    }

    /* access modifiers changed from: private */
    public void loadAdmobRewardedVideoAd(Activity activity2) {
        RewardedAd.load((Context) activity2, rewardedvideo, new AdRequest.Builder().build(), (RewardedAdLoadCallback) new RewardedAdLoadCallback() {
            public void onAdLoaded(RewardedAd rewardedAd) {
                RewardedAd unused = AppManager.this.mRewardedAd = rewardedAd;
                boolean unused2 = AppManager.this.ad_rv_load_failed = false;
                Log.d("AppsPanel", "RewardedVideo Ad Response : " + rewardedAd.getResponseInfo().getResponseId());
            }

            public void onAdFailedToLoad(LoadAdError loadAdError) {
                RewardedAd unused = AppManager.this.mRewardedAd = null;
                boolean unused2 = AppManager.this.ad_rv_load_failed = true;
                Log.d("AppsPanel", "RewardedVideo Ad Load Failed : " + loadAdError);
                AppManager.this.rewardedVideoCallBack(0);
            }
        });
    }

    public void showRewardedVideoAd(Activity context, RewardedVideoCallback ivCallback) {
        rvCallback = ivCallback;
        if (show_ads == 0 || !isNetworkAvailable(context)) {
            rewardedVideoCallBack(0);
            return;
        }
        if (this.ad_rv_load_failed) {
            Log.d("AppsPanel", "RewardedVideo Ad-Request : " + rewardedvideo);
            loadAdmobRewardedVideoAd(context);
        }
        RewardedAd rewardedAd = this.mRewardedAd;
        if (rewardedAd == null) {
            Log.d("AppsPanel", "RewardedVideo Ad not loaded, waiting...");
            this.dialog = new Dialog(context);
            this.dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, (ViewGroup) null));
            this.dialog.setCancelable(false);
            this.dialog.getWindow().setLayout(-1, -2);
            this.dialog.show();
            final Activity activity2 = context;
            new CountDownTimer(3000, 10) {
                public void onTick(long millisUntilFinished) {
                    double d = (double) ((millisUntilFinished / 10) / 3);
                }

                public void onFinish() {
                    AppManager.this.dialog.dismiss();
                    if (AppManager.this.mRewardedAd == null) {
                        Log.d("AppsPanel", "RewardedVideo Ad still not loaded.");
                        Log.d("AppsPanel", "RewardedVideo Ad-Request : " + AppManager.rewardedvideo);
                        AppManager.this.loadAdmobRewardedVideoAd(activity2);
                        AppManager.this.rewardedVideoCallBack(0);
                        return;
                    }
                    AppManager.this.mRewardedAd.show(activity2, new OnUserEarnedRewardListener() {
                        public void onUserEarnedReward(RewardItem rewardItem) {
                            Log.d("AppsPanel", "RewardedVideo Ad  EarnedReward : " + rewardItem.getAmount());
                            AppManager.this.rewardedVideoCallBack(rewardItem.getAmount());
                        }
                    });
                }
            }.start();
            return;
        }
        rewardedAd.show(context, new OnUserEarnedRewardListener() {
            public void onUserEarnedReward(RewardItem rewardItem) {
                Log.d("AppsPanel", "RewardedVideo Ad  EarnedReward : " + rewardItem.getAmount());
                AppManager.this.rewardedVideoCallBack(rewardItem.getAmount());
            }
        });
    }

    public void splashAddCallBack() {
        Log.d("AppsPanel", "AppOpen Ad Callback Called");
        SplashAdCallback splashAdCallback2 = splashAdCallback;
        if (splashAdCallback2 != null) {
            splashAdCallback2.callbackCall();
            splashAdCallback = null;
        }
    }

    public void interstitialCallBack() {
        Log.d("AppsPanel", "Interstitial Ad Callback Called");
        InterstitialCallback interstitialCallback = iCallback;
        if (interstitialCallback != null) {
            interstitialCallback.callbackCall();
            iCallback = null;
        }
    }

    public void rewardedVideoCallBack(int points) {
        Log.d("AppsPanel", "RewardedVideo Ad Callback Called");
        RewardedVideoCallback rewardedVideoCallback = rvCallback;
        if (rewardedVideoCallback != null) {
            rewardedVideoCallback.callbackCall(points);
            rvCallback = null;
        }
    }
}
