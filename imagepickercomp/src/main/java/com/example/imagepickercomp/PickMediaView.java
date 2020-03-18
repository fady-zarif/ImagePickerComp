package com.example.imagepickercomp;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class PickMediaView extends LinearLayout implements View.OnClickListener {
    public final static int PICK_IMAGE_CODE = 88;
    PagerIndicator pagerIndicator;
    ViewPager viewPager;
    PickMediaPagerAdapter pickMediaPagerAdapter;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton btnfloatingRemove;
    FloatingActionButton btnfloatingAdd;
    ImageView imageView;
    Context context;
    IPickMedia.IHandlePickImage iHandlePickImage;
    int maxImages = 1;
    ArrayList<String> imagesArrayList;

    public PickMediaView(Context context) {
        super(context);
        initView(context);
    }

    public PickMediaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PickMediaViewStyle);

        // TODO: 2020-03-17  put attr for everything of the fab

        if (typedArray.hasValue(R.styleable.PickMediaViewStyle_editMenuColor)) {
            floatingActionMenu.setMenuButtonColorNormal(typedArray.getColor(R.styleable.PickMediaViewStyle_editMenuColor, -1));
            floatingActionMenu.setMenuButtonColorPressed(typedArray.getColor(R.styleable.PickMediaViewStyle_editMenuColor, -1));
            btnfloatingAdd.setColorNormal(typedArray.getColor(R.styleable.PickMediaViewStyle_editMenuColor, -1));
            btnfloatingAdd.setColorPressed(typedArray.getColor(R.styleable.PickMediaViewStyle_editMenuColor, -1));
            btnfloatingRemove.setColorPressed(typedArray.getColor(R.styleable.PickMediaViewStyle_editMenuColor, -1));
            btnfloatingRemove.setColorNormal(typedArray.getColor(R.styleable.PickMediaViewStyle_editMenuColor, -1));

        }
        if (typedArray.hasValue(R.styleable.PickMediaViewStyle_defaultbackgroundImage)) {
            imageView.setImageResource(typedArray.getResourceId(R.styleable.PickMediaViewStyle_defaultbackgroundImage, -1));
        }
    }

    public PickMediaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public PickMediaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.pick_media_view, this);
        pagerIndicator = findViewById(R.id.indicator_owner_property_images);
        viewPager = findViewById(R.id.imagesViewPager);
        floatingActionMenu = findViewById(R.id.menu_add_remove_photo);
        btnfloatingAdd = findViewById(R.id.menu_item_add);
        btnfloatingRemove = findViewById(R.id.menu_item_remove);
        btnfloatingRemove.setOnClickListener(this);
        btnfloatingAdd.setOnClickListener(this);
        imageView = findViewById(R.id.imgDefault);

        imagesArrayList = new ArrayList<>();
        showRemoveBtn();
        showHideAddButton();
        pickMediaPagerAdapter = new PickMediaPagerAdapter(imagesArrayList, context);
        viewPager.setAdapter(pickMediaPagerAdapter);
        pagerIndicator.setViewPager(viewPager);
    }


    /// you have to call this method from the activity or fragment to pick the pic
    public void handlePickMedia(int maxImages, IPickMedia.IHandlePickImage iHandlePickImage) {
        this.iHandlePickImage = iHandlePickImage;
        setMaxImages(maxImages);
    }

    private void showHideAddButton() {
        if (imagesArrayList.size() > 0 && imageView.getVisibility() == VISIBLE)
            imageView.setVisibility(GONE);
        if (imagesArrayList.size() == maxImages)
            btnfloatingAdd.setVisibility(GONE);
        else if (imagesArrayList.size() < maxImages)
            btnfloatingAdd.setVisibility(VISIBLE);
    }

    private void showRemoveBtn() {
        if (imagesArrayList.size() > 0 && btnfloatingRemove.getVisibility() == View.GONE)
            btnfloatingRemove.setVisibility(View.VISIBLE);
        else if (imagesArrayList.size() <= 0) {
            btnfloatingRemove.setVisibility(View.GONE);
            imageView.setVisibility(VISIBLE);
        }
    }


    public void setDataFromOnResult(Uri uri) {

        imagesArrayList.add(String.valueOf(uri));
        pagerIndicator.addOneDot(imagesArrayList.size() - 1, true);
        pickMediaPagerAdapter.updateImages(imagesArrayList);
        pickMediaPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(imagesArrayList.size() - 1);

        checkAddRemoveButtons();
        showHideAddButton();
    }

    private void checkAddRemoveButtons() {
        showRemoveBtn();
        showHideAddButton();
        floatingActionMenu.close(true);
    }

    public ArrayList<String> getImagesArrayList() {
        return imagesArrayList;
    }

    @Override
    public void onClick(View v) {
        if (v == btnfloatingAdd) {
            iHandlePickImage.onHandleAddPickImage();
        } else if (v == btnfloatingRemove) {
            int pos = viewPager.getCurrentItem();
            imagesArrayList.remove(pos);
            pagerIndicator.removeOnewDot(pos);
            pickMediaPagerAdapter.updateImages(imagesArrayList);
            pickMediaPagerAdapter.notifyDataSetChanged();
            if (imagesArrayList.size() != 0)
                viewPager.setCurrentItem(imagesArrayList.size() - 1);
            else
                imageView.setVisibility(VISIBLE);
            pickMediaPagerAdapter = new PickMediaPagerAdapter(imagesArrayList, context);
            viewPager.setAdapter(pickMediaPagerAdapter);
            checkAddRemoveButtons();
            showHideAddButton();


        }

    }
}
