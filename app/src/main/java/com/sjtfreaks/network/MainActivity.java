package com.sjtfreaks.network;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sjtfreaks.network.adapter.BottomNavigationViewHelper;
import com.sjtfreaks.network.adapter.ViewPagerAdapter;
import com.sjtfreaks.network.fragment.NewsFragment;
import com.sjtfreaks.network.fragment.TalkFragment;
import com.sjtfreaks.network.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private MenuItem menuItem;
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mFragment = new ArrayList<>();
        mFragment.add(TalkFragment.newInstance("大家讨论"));
        mFragment.add(NewsFragment.newInstance("视衣商城"));
        mFragment.add(UserFragment.newInstance("个人信息"));
        viewPagerAdapter.setList(mFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.ic_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.ic_one:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.ic_user:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
}
