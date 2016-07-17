package com.example.alice.photofeed.photolist.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.alice.photofeed.App;
import com.example.alice.photofeed.R;
import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.photolist.PhotoListPresenter;
import com.example.alice.photofeed.photolist.ui.adapters.OnItemClickListener;
import com.example.alice.photofeed.photolist.ui.adapters.PhotoListAdapter;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment implements  PhotoListView , OnItemClickListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layoutMainContainer)
    FrameLayout layoutMainContainer;
    private Unbinder unbinder;

//    ================Inject =============================
    @Inject
    PhotoListAdapter adapter;
    @Inject
    PhotoListPresenter presenter;
    private App app;

    /**
     * did not  require body   because is created dynacally
     */
    public PhotoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
    }

    private void setupInjection() {
        app = (App) getActivity().getApplication();
        app.getPhotoListComponente(this, this, this)
                .inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        presenter.subscribe(); // firebase
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe(); //firebase
        presenter.onDestroy();
        super.onDestroy();
    }

//    ==================================== PhotoListView =================================================
    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddeList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void addPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        adapter.removePhoto(photo);
    }

    @Override
    public void onPhotoError(String error) {
        Snackbar.make(layoutMainContainer, error , Snackbar.LENGTH_SHORT).show();
    }

//    ======================================OnItemClickListener ====================================

    @Override
    public void onPlaceClick(Photo photo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+ photo.getLatitud() + " ," + photo.getLongitud()));

        //is possible to solve the intent ...
        if (intent.resolveActivity(getActivity().getPackageManager()) != null ){
            startActivity(intent);
        }
    }

    /**
     * Share image
     * @param photo
     * @param img
     */
    @Override
    public void onShareClick(Photo photo, ImageView img) {
        Bitmap bitmap = ((GlideBitmapDrawable) img.getDrawable()).getBitmap();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null);
        Uri imageUri = Uri.parse(path);

        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.photolist_message_share)));

    }

    @Override
    public void onDeleteClick(Photo photo) {
        presenter.onRemove(photo);
    }
}
