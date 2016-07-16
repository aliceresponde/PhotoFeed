package com.example.alice.photofeed.main.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alice.photofeed.App;
import com.example.alice.photofeed.R;
import com.example.alice.photofeed.login.ui.LoginActivity;
import com.example.alice.photofeed.main.MainPresenter;
import com.example.alice.photofeed.photolist.ui.PhotoListFragment;
import com.example.alice.photofeed.PhotoMapFragment;
import com.example.alice.photofeed.main.adapters.MainSectionsPagerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    //    ======================MAPS ==================================
    GoogleApiClient apiClient;
    Location lastKnownLocation; //get location\

    private boolean resolvingError = false;
    private static final int REQUEST_RESOLVE_ERROR = 0;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    //    ============================PHOTO=======================================
    private final static int REQUEST_PICTURE = 1;
    private String photoPath;

//    =================INJECTABLES =================================================
    @Inject
    MainPresenter presenter;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    MainSectionsPagerAdapter adapter;


    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (App) getApplication();
        setupInjection();
        setupNavigation();  //tabs and  toolbar
        setupGoogleAPIClient(); // Location

        presenter.onCreate();
    }

    private void setupNavigation() {
        String email = sharedPreferences.getString(app.getEmailKey(), "");
        Log.i("email", email);
        toolbar.setTitle(email);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

    }

    private void setupInjection() {

        String[] titles = new String[]{getString(R.string.main_title_phpto_list),
                getString(R.string.main_title_map)
        };

        Fragment[] fragments = new Fragment[]{
                new PhotoListFragment(),
                new PhotoMapFragment()
        };

        app.getMainComponet(this, titles, fragments, getSupportFragmentManager())
            .inject(this);
    }


    /**
     * compare apiClient content -  with  listeners
     */
    private void setupGoogleAPIClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * connect to  google APIClient
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (apiClient != null) {
            apiClient.connect();
        }
    }

    /**
     * * Disconnect to  google APIClient
     */
    @Override
    protected void onStop() {
        super.onStop();

        if (apiClient != null) {
            apiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("onActivityResult", "requestCode " + requestCode + " RESULT_CODE "+ resultCode);
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            resolvingError = false;
            if (resultCode == RESULT_OK) { //was solved
                if (!apiClient.isConnecting() && !apiClient.isConnected()) {
                    apiClient.connect();
                }
            }
        } else if (requestCode == REQUEST_PICTURE) {
            if (resultCode == RESULT_OK) {

                Log.i("onActivityResult", "XXXXXXXXX");

                // 1   -1
                boolean fromCamera = (data == null || data.getData() == null);
                if (fromCamera) {
                    addToGalery();
                } else {
                    photoPath = getRealPathFromURI(data.getData());
                }

                Log.i("onActivityResult", "photoPath:  "+  photoPath);

                presenter.uploadPhoto(lastKnownLocation, photoPath);
            }
        }
    }


//    =========================== Menu =============================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.logout();
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //    ======================================GoogleApiClient.ConnectionCallbacks=======================
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            }

            return;
        }
        if (LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()) {
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            showSnackBar(lastKnownLocation.toString());
        } else {
            showSnackBar(R.string.main_error_location_not_available);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()) {
                        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                    } else {
                        showSnackBar(R.string.main_error_location_not_available);
                    }
                }

                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClient.connect();
    }


    /**
     * Validate  are  tring  to solve  the error
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (resolvingError) {
            return;
        } else if (connectionResult.hasResolution()) {
            resolvingError = true;
            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            resolvingError = true;
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), REQUEST_RESOLVE_ERROR).show();
        }
    }

    @Override
    public void onUploadInit() {
        showSnackBar(R.string.main_notice_upload_init);
    }

    @Override
    public void onUploadComplete() {
        showSnackBar(R.string.main_notice_upload_complete);
    }

    @Override
    public void onUploadError(String error) {
        showSnackBar(error);
    }

    //    ========================================================================================


    //    ===================================Photo===============================================
    @OnClick(R.id.fab)
    public void takePicture() {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();
        Intent pickIntent = new Intent(Intent.ACTION_PICK, //take picture
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("return-data", true);

        File photoFile = getFile();

        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)); //where  save the  file

            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                intentList = addIntentsToList(intentList, cameraIntent);
            }
        }

        if (pickIntent.resolveActivity(getPackageManager()) != null) {
            intentList = addIntentsToList(intentList, pickIntent);
        }

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    getString(R.string.main_message_picture_source));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        //verify  if device has camera ...
        if (chooserIntent != null) {
            startActivityForResult(chooserIntent, REQUEST_PICTURE);
        }

        //add intents
        //generar  archivos
    }

    /**
     * Generates File, .jpg inside pictures directory
     * @return
     */
    private File getFile() {
        File photoFile = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            Snackbar.make(viewPager, R.string.main_error_disparch_camera, Snackbar.LENGTH_SHORT).show();
        }

        photoPath = photoFile.getAbsolutePath();
        return photoFile;
    }

    /**
     * REcibe un listado de intents definido, y uno nuevo a arreglar
     * Para saber como trabaja el sistema, veo que apps pueden contestar a ese intent (resInfo)
     * Inicialmente sera la camara, y la galeria, pero si tengo mas apps para manejo de fotos,
     * esta se adiciona a la lista
     *
     * @param list   - pueden abrir la camara
     * @param intent - abrir la camara
     * @return la lista de intents para responder al intent inicial
     */
    private List<Intent> addIntentsToList(List<Intent> list, Intent intent) {
        // apps that  responses to the intent
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String pakageName = resolveInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(pakageName);
            list.add(targetIntent);
        }
        return list;
    }

    //    =========================================SNACKBAR=========================================
    private void showSnackBar(String msg) {
        Snackbar.make(viewPager, msg, Snackbar.LENGTH_LONG).show();
    }

    private void showSnackBar(int strResource) {
        Snackbar.make(viewPager, strResource, Snackbar.LENGTH_LONG).show();
    }


//    =============================================================================================

    /**
     * Recibe un contentURI , sin
     *
     * @param contentURI
     * @return
     */
    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            if (contentURI.toString().contains("mediaKey")) { // not local p hoto
                cursor.close();

                try { //from web
                    File file = File.createTempFile("tempImg", ".jpg", getCacheDir());
                    InputStream input = getContentResolver().openInputStream(contentURI);
                    OutputStream output = new FileOutputStream(file);

                    try {
                        byte[] buffer = new byte[4 * 1024];
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        result = file.getAbsolutePath();
                    } finally {
                        output.close();
                        input.close();
                    }

                } catch (Exception e) {
                }
            } else { // de la galeria
                cursor.moveToFirst();
                int dataColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(dataColumn);
                cursor.close();
            }

        }
        return result;
    }

    private void addToGalery() {
        Log.i("MainAct","addToGalery");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contetnUri = Uri.fromFile(file);
        mediaScanIntent.setData(contetnUri);
        sendBroadcast(mediaScanIntent);
    }

}
