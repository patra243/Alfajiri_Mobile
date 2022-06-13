package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.WelcomeContent;

import java.util.List;

public class WelcomeHelper extends PagerAdapter {

    private Context context;
    private List<WelcomeContent> welcomeContentList;

    public  WelcomeHelper(Context context, List<WelcomeContent> welcomeContentList){
        this.context = context;
        this.welcomeContentList = welcomeContentList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int position){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.welcome_content, null);

        ImageView wlcmImage = view.findViewById(R.id.welcomeImage);
        TextView wlcmText1 = view.findViewById(R.id.welcomeTitleTxt);
        TextView wlcmText2 = view.findViewById(R.id.welcomeDetailsTxt);

        wlcmImage.setImageResource(welcomeContentList.get(position).getWelcome_image());
        wlcmText1.setText(welcomeContentList.get(position).getWelcome_title());
        wlcmText2.setText(welcomeContentList.get(position).getWelcome_details());
        viewGroup.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return welcomeContentList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup viewGroup, int position, @NonNull Object object){
        viewGroup.removeView((View) object);
    }
}
