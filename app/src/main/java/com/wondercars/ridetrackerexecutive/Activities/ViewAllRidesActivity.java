package com.wondercars.ridetrackerexecutive.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import com.wondercars.ridetrackerexecutive.Adapters.ViewPagerAdapter;
import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.Fragments.RidesFragment;
import com.wondercars.ridetrackerexecutive.Fragments.TestDrivesFragment;
import com.wondercars.ridetrackerexecutive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllRidesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_rides);
        ButterKnife.bind(this);
        initActionBar();
        setupTabLayout();
        setupViewPager(mViewPager);
    }


    private void setupViewPager(ViewPager mViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(RidesFragment.newInstance(), "All Rides");
        adapter.addFragment(TestDrivesFragment.newInstance(), "Test Drives");
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {
             /*   if (state == 0) {
                    try {
                        OtherSelectMedicineFragment.selectMedicineAdapterOther.clearData();
                    } catch (Exception e) {

                    }

                }*/

            }
        });
    }

    private void setupTabLayout() {
        tabs.setupWithViewPager(mViewPager);
    }

    private void initActionBar() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>View Rides </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}



