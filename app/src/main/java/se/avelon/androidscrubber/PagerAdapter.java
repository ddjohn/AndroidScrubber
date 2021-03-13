package se.avelon.androidscrubber;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import se.avelon.androidscrubber.fragments.AbstractFragment;
import se.avelon.androidscrubber.fragments.AudioFragment;
import se.avelon.androidscrubber.fragments.BatteryFragment;
import se.avelon.androidscrubber.fragments.BluetoothFragment;
import se.avelon.androidscrubber.fragments.CameraFragment;
import se.avelon.androidscrubber.fragments.CellularFragment;
import se.avelon.androidscrubber.fragments.MediaFragment;
import se.avelon.androidscrubber.fragments.DebugFragment;
import se.avelon.androidscrubber.fragments.MapFragment;
import se.avelon.androidscrubber.fragments.NavigationFragment;
import se.avelon.androidscrubber.fragments.SensorFragment;
import se.avelon.androidscrubber.fragments.ScreenFragment;
import se.avelon.androidscrubber.fragments.TextFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private AbstractFragment[] fragments= {
            new DebugFragment(),
            new ScreenFragment(),
            new AudioFragment(),
            new BatteryFragment(),
            new BluetoothFragment(),
            new CameraFragment(),
            new CellularFragment(),
            new MapFragment(),
            new MediaFragment(),
            new NavigationFragment(),
            new SensorFragment(),
            new TextFragment(),
    };

    public PagerAdapter(Activity activity, FragmentManager fm) {
        super(fm);

        TabLayout tabLayout = (TabLayout)activity.findViewById(R.id.tab_layout);
        for(AbstractFragment fragment : fragments) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setIcon(fragment.getIcon());
            //tab.setText(fragment.getTitle());
            tabLayout.addTab(tab);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)activity.findViewById(R.id.pager);
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}