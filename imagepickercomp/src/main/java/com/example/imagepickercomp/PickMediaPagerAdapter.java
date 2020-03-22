package com.example.imagepickercomp;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PickMediaPagerAdapter extends PagerAdapter {
    ArrayList<String> imagesArrayList;
    Context context;

    public PickMediaPagerAdapter(ArrayList<String> imagesArrayList, Context context) {
        this.context = context;
        this.imagesArrayList = imagesArrayList;
    }

    @Override
    public int getCount() {
        return imagesArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = getImageView();
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (imagesArrayList.get(position).substring(0, 3).equals("htt")) {
            Picasso.get().load(imagesArrayList.get(position)).placeholder(R.drawable.loading_image).into(imageView);
        } else
            imageView.setImageURI(Uri.parse(imagesArrayList.get(position)));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        this.notifyDataSetChanged();
    }

    private ImageView getImageView() {
        return new ImageView(context);
    }

    public void updateImages(ArrayList<String> imagesArrayList) {
        this.imagesArrayList = imagesArrayList;
        this.notifyDataSetChanged();
    }

}
