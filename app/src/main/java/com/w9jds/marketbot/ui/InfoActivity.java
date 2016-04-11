package com.w9jds.marketbot.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.MarketBot;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity {

    @Bind(R.id.rix_icon) ImageView rixxIcon;
    @Bind(R.id.me_icon) ImageView jeremyIcon;
    @Bind(R.id.crest_version) TextView crestVersionView;
    @Bind(R.id.application_version) TextView appVersionView;
    @Bind(R.id.base_view) CoordinatorLayout baseView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Inject String crestVersion;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        MarketBot.createNewStorageSession().inject(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("MarketBot Info");
        }

        crestVersionView.setText(crestVersion);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            appVersionView.setText(version);
        }
        catch (Exception ex) {
            Snackbar.make(baseView, "Error getting application version", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Glide.with(this)
            .load("https://pbs.twimg.com/profile_images/708449861096460289/amHbjwSk.jpg")
            .into(rixxIcon);

        Glide.with(this)
            .load("https://pbs.twimg.com/profile_images/535955002460622849/qvLXDlKY.png")
            .into(jeremyIcon);
    }

    @OnClick(R.id.rixx_container)
    void onRixxClick() {
        openTwitterFeed("RixxJavix");
    }

    @OnClick(R.id.jeremy_container)
    void onJeremyClick() {
        openTwitterFeed("xboxmusicn3rd");
    }

    private void openTwitterFeed(String username) {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + username));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + username));
        }
        this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
