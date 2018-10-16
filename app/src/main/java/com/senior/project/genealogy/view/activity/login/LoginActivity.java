package com.senior.project.genealogy.view.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.username)
    EditText edtUsername;

    @BindView(R.id.password)
    EditText edtPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.txtSignup)
    TextView txtSignup;

    private LoginPresenterImpl loginPresenterImpl;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenterImpl = new LoginPresenterImpl(this);
    }
    @OnTextChanged({R.id.username, R.id.password})
    protected void onTextChanged() {

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()){
            btnLogin.setEnabled(false);
        }
        else {
            btnLogin.setEnabled(true);
        }
    }
    @OnClick({R.id.btnLogin, R.id.txtSignup})
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.btnLogin:
                User user = new User(edtUsername.getText().toString(), edtPassword.getText().toString());
                loginPresenterImpl.login(user);
                break;

            case R.id.txtSignup:
                showActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public ProgressDialog initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog  progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token","Token " + token);
        editor.apply();
    }
}
