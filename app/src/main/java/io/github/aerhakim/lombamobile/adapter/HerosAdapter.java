package io.github.aerhakim.lombamobile.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.api.bayar.Config;
import io.github.aerhakim.lombamobile.model.Heros;


public class HerosAdapter extends RecyclerView.Adapter<HerosAdapter.MyViewHolder>{
    List<Heros> mHerosList;

    public HerosAdapter(List<Heros> HerosList) {
        mHerosList = HerosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_heros, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        holder.mTextViewName.setText(mHerosList.get(position).getName());
        holder.mTextViewDescription.setText(mHerosList.get(position).getDescription());
        holder.mTextViewDate.setText(mHerosList.get(position).getDate());
        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + mHerosList.get(position).getImage())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.mImageViewFoto);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    Intent mIntent = new Intent(view.getContext(), EditActivity.class);
//                    mIntent.putExtra("Id", mHerosList.get(position).getId());
//                    mIntent.putExtra("Name", mHerosList.get(position).getName());
//                    mIntent.putExtra("Description", mHerosList.get(position).getDescription());
//                    mIntent.putExtra("Date", mHerosList.get(position).getDate());
//                    mIntent.putExtra("Image", mHerosList.get(position).getImage());
//                    view.getContext().startActivity(mIntent);
//            }
//        });
    }

    @Override
    public int getItemCount () {
        return mHerosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName, mTextViewDescription, mTextViewDate;
        public ImageView mImageViewFoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewName = (TextView) itemView.findViewById(R.id.tv_item_name);
            mTextViewDescription = (TextView) itemView.findViewById(R.id.tv_item_description);
            mTextViewDate = (TextView) itemView.findViewById(R.id.tv_item_date);
            mImageViewFoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
