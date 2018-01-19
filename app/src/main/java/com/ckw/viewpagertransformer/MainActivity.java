package com.ckw.viewpagertransformer;

import android.app.ActionBar;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ckw.easyviewpagertransformer.AccordionTransformer;
import com.ckw.easyviewpagertransformer.BackgroundToForegroundTransformer;
import com.ckw.easyviewpagertransformer.CubeInTransformer;
import com.ckw.easyviewpagertransformer.CubeOutTransformer;
import com.ckw.easyviewpagertransformer.DefaultTransformer;
import com.ckw.easyviewpagertransformer.DepthPageTransformer;
import com.ckw.easyviewpagertransformer.DrawerTransformer;
import com.ckw.easyviewpagertransformer.FilmPagerTransformer;
import com.ckw.easyviewpagertransformer.FlipHorizontalTransformer;
import com.ckw.easyviewpagertransformer.FlipVerticalTransformer;
import com.ckw.easyviewpagertransformer.ForegroundToBackgroundTransformer;
import com.ckw.easyviewpagertransformer.RotateDownTransformer;
import com.ckw.easyviewpagertransformer.RotateUpTransformer;
import com.ckw.easyviewpagertransformer.ScaleInOutTransformer;
import com.ckw.easyviewpagertransformer.StackTransformer;
import com.ckw.easyviewpagertransformer.TabletTransformer;
import com.ckw.easyviewpagertransformer.ZoomInTransformer;
import com.ckw.easyviewpagertransformer.ZoomOutPageTransformer;
import com.ckw.easyviewpagertransformer.ZoomOutSlideTransformer;
import com.ckw.easyviewpagertransformer.ZoomOutTransformer;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {
    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS";
    private static final ArrayList<TransformerItem> TRANSFORM_CLASSES;

    static {
        TRANSFORM_CLASSES = new ArrayList<>();
        TRANSFORM_CLASSES.add(new TransformerItem(DefaultTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(AccordionTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(BackgroundToForegroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(DepthPageTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipHorizontalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FilmPagerTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipVerticalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ForegroundToBackgroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateDownTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateUpTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ScaleInOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(StackTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(TabletTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutSlideTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutPageTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(DrawerTransformer.class));
    }

    private int mSelectedItem;
    private ViewPager mPager;
    private PageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int selectedPage = 0;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(KEY_SELECTED_CLASS);
            selectedPage = savedInstanceState.getInt(KEY_SELECTED_PAGE);
        }

        final ArrayAdapter<TransformerItem> actionBarAdapter = new ArrayAdapter<TransformerItem>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, TRANSFORM_CLASSES);

        final ActionBar actionBar = getActionBar();
        actionBar.setListNavigationCallbacks(actionBarAdapter, this);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);

        setContentView(R.layout.activity_main);

        mAdapter = new PageAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin(40);
        mPager.setOffscreenPageLimit(2);
        mPager.setCurrentItem(selectedPage);

        actionBar.setSelectedNavigationItem(mSelectedItem);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        mSelectedItem = itemPosition;
        try {
            mPager.setPageTransformer(true, TRANSFORM_CLASSES.get(itemPosition).clazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(KEY_SELECTED_CLASS, mSelectedItem);
        outState.putInt(KEY_SELECTED_PAGE, mPager.getCurrentItem());
    }


    public static class PlaceholderFragment extends android.support.v4.app.Fragment {

        private static final String EXTRA_POSITION = "EXTRA_POSITION";
        private static final int[] COLORS = new int[] { 0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444 };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int position = getArguments().getInt(EXTRA_POSITION);
            final TextView textViewPosition = (TextView) inflater.inflate(R.layout.fragment_main, container, false);
            textViewPosition.setText(Integer.toString(position));
            textViewPosition.setBackgroundColor(COLORS[position - 1]);

            return textViewPosition;
        }

    }

    private static final class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(PlaceholderFragment.EXTRA_POSITION, position + 1);

            final PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

    private static final class TransformerItem {

        final String title;
        final Class<? extends ViewPager.PageTransformer> clazz;

        public TransformerItem(Class<? extends ViewPager.PageTransformer> clazz) {
            this.clazz = clazz;
            title = clazz.getSimpleName();
        }

        @Override
        public String toString() {
            return title;
        }

    }
}
