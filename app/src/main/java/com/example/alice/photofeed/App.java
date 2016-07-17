package com.example.alice.photofeed;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.alice.photofeed.domain.di.AppModule;
import com.example.alice.photofeed.domain.di.DomainModule;
import com.example.alice.photofeed.libs.di.LibsModule;
import com.example.alice.photofeed.login.di.DaggerLoginComponent;
import com.example.alice.photofeed.login.di.LoginComponent;
import com.example.alice.photofeed.login.di.LoginModule;
import com.example.alice.photofeed.login.ui.LoginView;
import com.example.alice.photofeed.main.di.DaggerMainComponent;
import com.example.alice.photofeed.main.di.MainComponent;
import com.example.alice.photofeed.main.di.MainModule;
import com.example.alice.photofeed.main.ui.MainView;
import com.example.alice.photofeed.photolist.di.DaggerPhotoListComponent;
import com.example.alice.photofeed.photolist.di.PhotoListComponent;
import com.example.alice.photofeed.photolist.di.PhotoListModule;
import com.example.alice.photofeed.photolist.ui.PhotoListFragment;
import com.example.alice.photofeed.photolist.ui.PhotoListView;
import com.example.alice.photofeed.photolist.ui.adapters.OnItemClickListener;
import com.firebase.client.Firebase;

/**
 * Created by alice on 7/5/16.
 */

public class App extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://myphotofeededx.firebaseio.com/";

   // ===============Modules =============================================
    private AppModule appModule;
    private DomainModule domainModule;
    //Libs module

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
    }

    private void initModules() {
        appModule = new AppModule(this);
        domainModule = new DomainModule(FIREBASE_URL);
    }


    public  String getEmailKey() {
        return EMAIL_KEY;
    }

    public  String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    // regresar componente
    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .appModule(appModule)
                .domainModule(domainModule)
                .libsModule( new LibsModule(null))
                .loginModule( new LoginModule(view))
                .build();
    }

    /**
     * constructor ----  MainView view, String[] titles, Fragment[] fragments, FragmentManager fragmentManager
     * modules = {LoginModule.class, DomainModule.class, LibsModule.class, AppModule.class}
     * @return
     */
    public MainComponent getMainComponet(MainView view, String[] titles, Fragment[] fragments, FragmentManager fragmentManager){
        return DaggerMainComponent
                .builder()
                .appModule( appModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .mainModule( new MainModule (view, titles, fragments, fragmentManager))
                .build();

    }

    /**
     * PhotoListView view, OnItemClickListener onItemClickListener
     * modules = {PhotoListModule.class, DomainModule.class, LibsModule.class, AppModule.class}
     *
     * Note :  Required a fragment, to use in libs
     * @return
     */
    public PhotoListComponent getPhotoListComponente(PhotoListFragment fragment, PhotoListView view, OnItemClickListener onItemClickListener){
        return DaggerPhotoListComponent
                .builder()
                .appModule(appModule)
                .domainModule(domainModule)
                .libsModule( new LibsModule(fragment))
                .photoListModule( new PhotoListModule(view, onItemClickListener))
                .build();
    }

}
