package com.example.alice.photofeed.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.alice.photofeed.App;
import com.example.alice.photofeed.R;
import com.example.alice.photofeed.login.LogingPresenter;
import com.example.alice.photofeed.main.ui.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.alice.photofeed.R.id.txtPassword;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(txtPassword)
    EditText inputPassword;
    @BindView(R.id.btnSignin)
    Button btnSignin;
    @BindView(R.id.btnSignInUp)
    Button btnSignInUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layoutMainContainer)
    RelativeLayout layoutMainContainer;

    @Inject
     LogingPresenter presenter;
    @Inject
     SharedPreferences sharedPreferences;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        app = (App) getApplication();
        setupInjection();

        presenter.onCreate();
        presenter.validateLogin(null, null);
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setInputs (boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
        btnSignInUp.setEnabled(enabled);
    }

//    ====================================UI=======================================================

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

//    ===========================================AUth / sesion =====================================
    @OnClick(R.id.btnSignInUp)
    @Override
    public void handleSignUp() {
        Log.i("click", "handleSignUp");
        presenter.registerNewUser(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    @OnClick(R.id.btnSignin)
    @Override
    public void handleSignIn() {
        Log.i("click", "handleSignIn");
        presenter.validateLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    @Override
    public void navigationToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.loging_error_messagge_signin, error));
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(layoutMainContainer, R.string.login_notice_message_useradded, Snackbar.LENGTH_SHORT).show();;
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.loging_error_messagge_signup, error));
        inputPassword.setError(msgError);
    }



    @Override
    public void setUserEmail(String email) {
        if (email != null){
            sharedPreferences
                    .edit()
                    .putString(app.getEmailKey(),email)
                    .commit();
        }
    }

}
