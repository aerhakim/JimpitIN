package io.github.aerhakim.lombamobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import io.github.aerhakim.lombamobile.R;


public class OnBoardingAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public OnBoardingAdapter(Context context) {
        this.context = context;
    }
    int images[] = {
            R.drawable.icon3,
            R.drawable.icon3,
            R.drawable.icon3,
            R.drawable.icon3,
    };
    int headings[] = {
            R.string.hello_blank_fragment,
            R.string.hello_blank_fragment,
            R.string.hello_blank_fragment,
            R.string.hello_blank_fragment,
    };
    int descs[] = {
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
            R.string.app_name,
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)  context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView desc = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
