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
        imageView.setImageURI(Uri.parse(imagesArrayList.get(position)));

////        String imagePath = imagesArrayList.get(position);
//        if (true) {
//            imageView.setImageURI(Uri.parse(imagesArrayList.get(position)));
////            int imageId = Integer.parseInt(imagePath);
////            if (imageId != 0) {
////                imageView.setImageResource(imageId);
////            }
//        } else {
//            Picasso.get().load(imagesArrayList.get(position)).placeholder(R.drawable.bg_pick_media).into(imageView);
//        }
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
