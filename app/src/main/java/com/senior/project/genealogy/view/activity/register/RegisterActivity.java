package com.senior.project.genealogy.view.activity.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.login.LoginActivity;

import org.mindrot.jbcrypt.BCrypt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    @BindView(R.id.username)
    EditText edtUsername;

    @BindView(R.id.password)
    EditText edtPassword;

    @BindView(R.id.fullname)
    EditText edtFullname;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    @BindView(R.id.txtLogin)
    TextView txtLogin;

    private RegisterPresenterImpl registerPresenterImpl;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenterImpl = new RegisterPresenterImpl(this);
    }

    @OnTextChanged({R.id.username, R.id.password, R.id.fullname})
    protected void onTextChanged() {

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String fullname = edtFullname.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()){
            btnRegister.setEnabled(false);
        }
        else {
            btnRegister.setEnabled(true);
        }

    }

    @OnClick({R.id.btnRegister, R.id.txtLogin})
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.btnRegister:
                String password = BCrypt.hashpw(edtPassword.getText().toString(), BCrypt.gensalt());
                User user = new User(edtUsername.getText().toString(), password, edtFullname.getText().toString());
                registerPresenterImpl.register(user);
                break;

            case R.id.txtLogin:
                showActivity(LoginActivity.class);
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
        finish();
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

    @Override
    public void onBackPressed() {
        showActivity(LoginActivity.class);
    }

    @Override
    public void saveAccount(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_KEY.USERNAME,username);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.PASSWORD,password);
        editor.apply();
    }
}
