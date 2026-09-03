package com.t2ner.funpack;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.util.HashSet;
import java.util.Set;

public class PlayFun20Application extends Application implements Application.ActivityLifecycleCallbacks {
    private final Set<Activity> adAttached = new HashSet<>();
    private ConsentInformation consentInformation;
    private boolean adsInitialized = false;
    private boolean consentFlowStarted = false;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        consentInformation = UserMessagingPlatform.getConsentInformation(this);
    }

    private void startConsentAndAds(Activity activity) {
        if (consentFlowStarted) {
            if (consentInformation.canRequestAds()) initializeAndAttach(activity);
            return;
        }
        consentFlowStarted = true;

        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                        activity,
                        formError -> {
                            if (consentInformation.canRequestAds()) initializeAndAttach(activity);
                        }),
                requestConsentError -> {
                    if (consentInformation.canRequestAds()) initializeAndAttach(activity);
                });

        if (consentInformation.canRequestAds()) initializeAndAttach(activity);
    }

    private synchronized void initializeAndAttach(Activity activity) {
        if (!adsInitialized) {
            adsInitialized = true;
            MobileAds.initialize(this, initializationStatus -> activity.runOnUiThread(() -> attachBanner(activity)));
        } else {
            attachBanner(activity);
        }
    }

    private void attachBanner(Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || adAttached.contains(activity)) return;

        ViewGroup content = activity.findViewById(android.R.id.content);
        if (content == null) return;

        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(activity.getString(R.string.admob_banner_id));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        content.addView(adView, params);
        adAttached.add(activity);
        adView.loadAd(new AdRequest.Builder().build());
    }

    @Override public void onActivityResumed(Activity activity) { startConsentAndAds(activity); }
    @Override public void onActivityDestroyed(Activity activity) { adAttached.remove(activity); }
    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
    @Override public void onActivityStarted(Activity activity) {}
    @Override public void onActivityPaused(Activity activity) {}
    @Override public void onActivityStopped(Activity activity) {}
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
}
