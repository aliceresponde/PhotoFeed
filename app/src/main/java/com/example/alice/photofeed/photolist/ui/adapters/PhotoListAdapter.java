package com.example.alice.photofeed.photolist.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alice.photofeed.R;
import com.example.alice.photofeed.domain.Util;
import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.libs.base.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alice on 7/16/16.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {


    private Util util; //geocoder
    private List<Photo> photoList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public PhotoListAdapter(Util util, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.util = util;
        this.photoList = photoList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photolist_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo currentPhoto = photoList.get(position);
        holder.setOnItemClickListener(currentPhoto, onItemClickListener);

//        loadPhotos
        imageLoader.load(holder.imgMain, currentPhoto.getUrl());  //mainPhoto
        imageLoader.load(holder.imgAvatar, util.getAvatarURL(currentPhoto.getEmail())); // photo from user that post the photo

        holder.txtUser.setText(currentPhoto.getEmail());
        double latitud = currentPhoto.getLatitud();
        double longitud = currentPhoto.getLongitud();

//        ============================Just show place place Address if it  has location =====================
        if (latitud != 0  && longitud != 0) {
            holder.txtPlaceAddress.setText(util.getFromLocation(latitud, longitud));
            holder.txtPlaceAddress.setVisibility(View.VISIBLE);
        }else{
            holder.txtPlaceAddress.setVisibility(View.GONE);
        }

//      ===================Just me can remove my photo =============================================
        if (currentPhoto.isPublishedByMe()){
            holder.imgDelete.setVisibility(View.VISIBLE);
        }else {
            holder.imgDelete.setVisibility(View.GONE);
        }
    }


    public void addPhoto(Photo photo){
        photoList.add(0, photo);  // add at top
        notifyDataSetChanged();
    }

    public void removePhoto(Photo photo){
        photoList.remove(photo);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.txtUser)
        TextView txtUser;
        @BindView(R.id.imgMain)
        ImageView imgMain;
        @BindView(R.id.txtPlaceAddress)
        TextView txtPlaceAddress;
        @BindView(R.id.imgShare)
        ImageButton imgShare;
        @BindView(R.id.imgDelete)
        ImageButton imgDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final Photo photo, final OnItemClickListener listener) {
            txtPlaceAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(photo);
                }
            });


            imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onShareClick(photo, imgMain);
                }
            });


            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(photo);
                }
            });
        }
    }
}
