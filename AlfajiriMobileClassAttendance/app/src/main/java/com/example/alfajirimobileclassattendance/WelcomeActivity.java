package com.example.alfajirimobileclassattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.alfajirimobileclassattendance.constructor.WelcomeContent;
import com.example.alfajirimobileclassattendance.helper.WelcomeHelper;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends AppCompatActivity {

    private ViewPager wlcmViewPage;
    private TabLayout wlcmTab;
    private Button bStart;
    private TextView tNext;
    int place;
    private WelcomeHelper welcomeHelper;
    Animation wlcmStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        wlcmViewPage = findViewById(R.id.viewPager);
        wlcmTab = findViewById(R.id.icTab);
        bStart = findViewById(R.id.startBtn);
        tNext = findViewById(R.id.txtNext);
        wlcmStartBtn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_anim);

        final List<WelcomeContent> welcomeContentList = new ArrayList<>();
        String details = getResources().getString(R.string.wlcm_details);
        String manage = getResources().getString(R.string.wlcm_manage);
        String allocate = getResources().getString(R.string.wlcm_allocate);
        String attendance = getResources().getString(R.string.wlcm_attendance);

        welcomeContentList.add(new WelcomeContent(manage, details, R.drawable.manage_icon));
        welcomeContentList.add(new WelcomeContent(allocate, details, R.drawable.course_allocation));
        welcomeContentList.add(new WelcomeContent(attendance, details, R.drawable.attendance_icon));

        welcomeHelper = new WelcomeHelper(this, welcomeContentList);
        wlcmViewPage.setAdapter(welcomeHelper);
        wlcmTab.setupWithViewPager(wlcmViewPage);

        tNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                place = wlcmViewPage.getCurrentItem();
                if (place < welcomeContentList.size()){
                    place++;
                    wlcmViewPage.setCurrentItem(place);
                }
                if (place == welcomeContentList.size() - 1){
                    welcomeScreen();
                }
            }
        });

        wlcmTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == welcomeContentList.size() - 1) {
                    welcomeScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveUser().wlcmSave(getApplicationContext(), true);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void welcomeScreen() {
        tNext.setVisibility(View.INVISIBLE);
        bStart.setVisibility(View.VISIBLE);
        wlcmTab.setVisibility(View.INVISIBLE);
        bStart.setAnimation(wlcmStartBtn);

    }
}