package com.example.alice.photofeed.photolist.ui.adapters;

import android.widget.ImageView;

import com.example.alice.photofeed.entities.Photo;

/**
 * Created by alice on 7/16/16.
 */
public interface OnItemClickListener {
    void  onPlaceClick(Photo photo);
    void  onShareClick(Photo photo, ImageView img);
    void  onDeleteClick(Photo photo);
}
