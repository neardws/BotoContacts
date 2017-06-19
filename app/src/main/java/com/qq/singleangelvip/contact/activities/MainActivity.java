package com.qq.singleangelvip.contact.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.fragments.FragmentContact;
import com.qq.singleangelvip.contact.fragments.FragmentHistory;
import com.qq.singleangelvip.contact.fragments.FragmentSms;
import com.qq.singleangelvip.contact.popupwindows.PopupCall;
import com.qq.singleangelvip.contact.popupwindows.SlideFromBottomPopup;
import com.qq.singleangelvip.contact.qrcard.capture.CaptureActivity;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        if (bundle != null){
            int theme = bundle.getInt("theme");
            if (theme == R.style.AppThemePink_NoActionBar || theme == R.style.AppBlueTheme_NoActionBar || theme == R.style.AppGreenTheme_NoActionBar
                    || theme == R.style.AppOrangeTheme_NoActionBar || theme == R.style.AppPurpleTheme_NoActionBar || theme == R.style.AppRedTheme_NoActionBar){
                setTheme(theme);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);//设置导航栏图标
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Boto博通",Toast.LENGTH_SHORT).show();
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        try {
            assert tabLayout != null;
            tabLayout.getTabAt(0).setIcon(getDrawable(R.drawable.history));
            tabLayout.getTabAt(1).setIcon(getDrawable(R.drawable.contacts));
            tabLayout.getTabAt(2).setIcon(getDrawable(R.drawable.message));
        }catch (NullPointerException e){
        }


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        PopupCall popupCall = new PopupCall(MainActivity.this);
                        popupCall.showPopupWindow();
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        if (bundle != null){
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("theme",bundle.getInt("theme"));
                            intent1.putExtras(bundle);
                        }
                        intent1.setClass(MainActivity.this,AddContactActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case 2:
                        Uri uri = Uri.parse("smsto:");
                        Intent intent2 = new Intent(Intent.ACTION_SENDTO,uri);
                        intent2.putExtra("sms_body", "");
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }

            }
        });

        //设置toolbar的菜单监视器
        assert toolbar != null;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_settings:
                        Intent intent = new Intent();
                        if (bundle != null){
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("theme",bundle.getInt("theme"));
                            intent.putExtras(bundle);
                        }
                        intent.setClass(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_feedback:
                        Intent intent2 = new Intent();
                        if (bundle != null){
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("theme",bundle.getInt("theme"));
                            intent2.putExtras(bundle);
                        }
                        intent2.setClass(MainActivity.this,FeedbackActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.action_qr_card:
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, CaptureActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_about:
                        Intent intent3 = new Intent();
                        if (bundle != null){
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("theme",bundle.getInt("theme"));
                            intent3.putExtras(bundle);
                        }
                        intent3.setClass(MainActivity.this,AboutActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * 通过反射，设置menu显示icon
     *
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return FragmentHistory.newInstance(0);
                case 1:
                    return FragmentContact.newInstance(1);
                case 2:
                    return FragmentSms.newInstance(2);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        String title;
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    title = getString(R.string.history);
                    break;
                case 1:
                    title = getString(R.string.contact);
                    break;
                case 2:
                    title = getString(R.string.Sms);
                    break;
                default:
                    break;
            }
            return null;
        }


    }
}
