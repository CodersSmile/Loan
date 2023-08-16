package com.example.loan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import java.util.Date;

public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    /* access modifiers changed from: private */
    public static boolean isShowingAd = false;
    /* access modifiers changed from: private */
    public AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    /* access modifiers changed from: private */
    public long loadTime = 2;
    private final Activity myApplication;

    public interface splshADlistner {
        void onError(String str);

        void onShowAd();

        void onSuccess();
    }

    public AppOpenManager(Activity myApplication2) {
        this.myApplication = myApplication2;
        if (Build.VERSION.SDK_INT >= 29) {
            myApplication2.registerActivityLifecycleCallbacks(this);
        }
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public void loadAd(String AD_UNIT_ID, final splshADlistner listner) {
        if (!isAdAvailable()) {
            this.loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    AppOpenAd unused = AppOpenManager.this.appOpenAd = appOpenAd;
                    long unused2 = AppOpenManager.this.loadTime = new Date().getTime();
                    listner.onSuccess();
                }

                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    listner.onError(loadAdError.getMessage());
                }
            };
            AppOpenAd.load((Context) this.myApplication, AD_UNIT_ID, getAdRequest(), 1, this.loadCallback);
        }
    }

    public boolean chekAdAvailablity() {
        if (isShowingAd || !isAdAvailable()) {
            return false;
        }
        return true;
    }

    public void showAd(final splshADlistner listner) {
        this.appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            public void onAdDismissedFullScreenContent() {
                AppOpenAd unused = AppOpenManager.this.appOpenAd = null;
                boolean unused2 = AppOpenManager.isShowingAd = false;
                listner.onSuccess();
            }

            public void onAdFailedToShowFullScreenContent(AdError adError) {
                listner.onError(adError.getMessage());
            }

            public void onAdShowedFullScreenContent() {
                boolean unused = AppOpenManager.isShowingAd = true;
                listner.onShowAd();
            }
        });
        this.appOpenAd.show(this.currentActivity);
    }

    public void showAdIfAvailable(final splshADlistner listner) {
        if (isShowingAd || !isAdAvailable()) {
            listner.onError("");
            return;
        }
        this.appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            public void onAdDismissedFullScreenContent() {
                AppOpenAd unused = AppOpenManager.this.appOpenAd = null;
                boolean unused2 = AppOpenManager.isShowingAd = false;
                listner.onSuccess();
            }

            public void onAdFailedToShowFullScreenContent(AdError adError) {
                listner.onError(adError.getMessage());
            }

            public void onAdShowedFullScreenContent() {
                boolean unused = AppOpenManager.isShowingAd = true;
                listner.onShowAd();
            }
        });
        this.appOpenAd.show(this.currentActivity);
    }

    public void fetchAd(String AD_UNIT_ID, final splshADlistner listner) {
        if (!isAdAvailable()) {
            this.loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    AppOpenAd unused = AppOpenManager.this.appOpenAd = appOpenAd;
                    long unused2 = AppOpenManager.this.loadTime = new Date().getTime();
                    Log.e("APP_OPEN", "onAppOpenAdToLoad: ");
                    listner.onSuccess();
                }

                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e("APP_OPEN", "onAppOpenAdFailedToLoad: " + loadAdError.getMessage());
                    listner.onError(loadAdError.getMessage());
                }
            };
            AppOpenAd.load((Context) this.myApplication, AD_UNIT_ID, getAdRequest(), 1, this.loadCallback);
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        return new Date().getTime() - this.loadTime < 3600000 * numHours;
    }

    public boolean isAdAvailable() {
        return this.appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    public void onActivityPreCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityPostCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityPreStarted(Activity activity) {
    }

    public void onActivityStarted(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityPostStarted(Activity activity) {
    }

    public void onActivityPreResumed(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityPostResumed(Activity activity) {
    }

    public void onActivityPrePaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivityPostStopped(Activity activity) {
    }

    public void onActivityPreSaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityPostPaused(Activity activity) {
    }

    public void onActivityPreStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityPostSaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityPreDestroyed(Activity activity) {
    }

    public void onActivityDestroyed(Activity activity) {
        this.currentActivity = null;
    }

    public void onActivityPostDestroyed(Activity activity) {
    }
}
