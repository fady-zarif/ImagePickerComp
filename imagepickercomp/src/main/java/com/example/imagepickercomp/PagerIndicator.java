package com.example.imagepickercomp;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;


public class PagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    protected ArrayList<ImageView> dots;
    protected ViewPager viewPager;
    protected LinearLayout.LayoutParams paramsDots;
    protected int indicatorNonSelectedDot = R.drawable.indicator_non_selected_dot;
    protected int indicatorSelectedDot = R.drawable.indicator_selected_dot;
    int dotsCount;
    public PagerIndicator(Context context) {
        super(context);
        onInitial(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitial(context, attrs);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitial(context, attrs);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInitial(context, attrs);
    }

    protected void onInitial(Context context, AttributeSet attributeSet) {

    }

//    public void setViewPager(ViewPager viewPager, int indicatorSelectedDot, int indicatorNonSelectedDot) {
//        this.indicatorNonSelectedDot = indicatorNonSelectedDot;
//        this.indicatorSelectedDot = indicatorSelectedDot;
//        setViewPager(viewPager);
//    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(this);
        setUiPageViewController();
    }

    public void setDotsCount(int dotsCount) {
        this.dotsCount = dotsCount;
    }

    void setUiPageViewController() {
        int dotsCount = viewPager.getAdapter().getCount();

        if (paramsDots!=null)
            removeAllViews();

        paramsDots = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsDots.setMargins(4, 0, 4, 0);

        dots = new ArrayList<>();

        for (int i = 0; i < dotsCount; i++)
            addOneDot(i, false);

        if (dots.size() > 0)
            dots.get(0).setImageDrawable(getContext().getDrawable(indicatorSelectedDot));
    }

    public void addOneDot(int position, boolean isSelected) {
        dots.add(new ImageView(getContext()));

        if (isSelected)
            dots.get(position).setImageDrawable(getContext().getDrawable(indicatorSelectedDot));
        else
            dots.get(position).setImageDrawable(getContext().getDrawable(indicatorNonSelectedDot));

        addView(dots.get(position), paramsDots);
    }

    public void removeOnewDot(int position) {
        removeView(dots.get(position));
        dots.remove(position);

        if (position < dots.size())
            dots.get(position).setImageDrawable(getContext().getDrawable(indicatorSelectedDot));
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).setImageDrawable(getContext().getDrawable((indicatorNonSelectedDot)));
        }
        dots.get(position).setImageDrawable(getContext().getDrawable(indicatorSelectedDot));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
