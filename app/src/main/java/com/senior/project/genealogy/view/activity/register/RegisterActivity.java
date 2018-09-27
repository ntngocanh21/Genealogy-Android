package com.senior.project.genealogy.view.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;

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
                User user = new User(edtUsername.getText().toString(), edtPassword.getText().toString(), edtFullname.getText().toString());
                registerPresenterImpl.register(user);
                break;

            case R.id.txtLogin:
                finish();
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
