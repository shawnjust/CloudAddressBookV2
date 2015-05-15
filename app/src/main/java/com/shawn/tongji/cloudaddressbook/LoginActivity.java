package com.shawn.tongji.cloudaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.net.MySharedPreferences;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends ActionBarActivity {

    @ViewInject(id = R.id.loginNameTextView)
    MaterialEditText loginNameEditText;

    @ViewInject(id = R.id.passwordTextView)
    MaterialEditText passwordEditText;

    @ViewInject(id = R.id.loginButton, click = "login")
    ButtonRectangle loginButton;

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.registrationClickableTextView, click = "registration")
    ButtonFlat registrationTextView;

    @ViewInject(id = R.id.forgotPasswordClickableTextView, click = "forgetPassword")
    ButtonFlat forgetPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginNameEditText.setText(MySharedPreferences.getInstance().getSharedPreferences().getString(MySharedPreferences.LOGIN_EMAIL, ""));
        passwordEditText.setText(MySharedPreferences.getInstance().getSharedPreferences().getString(MySharedPreferences.PASSWORD, ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        Pattern pattern = Pattern.compile("^(\\w)+(\\.|\\w)*@(\\w)+(\\.\\w+)+$");
        Matcher matcher = pattern.matcher(loginNameEditText.getText());
        if (!matcher.matches()) {
            loginNameEditText.setError(this.getString(R.string.error_invalid_email));
            return;
        }
        pattern = Pattern.compile("^[0-9\\w\\.]{4,16}$");
        matcher = pattern.matcher(passwordEditText.getText());
        if (!matcher.matches()) {
            passwordEditText.setError(this.getString(R.string.error_invalid_password));
            return;
        }

        UserServices services = UrlUtil.getRestAdapter().create(UserServices.class);
        services.login(loginNameEditText.getText().toString(), passwordEditText.getText().toString(), new MyCallBack<User>() {
            @Override
            public void success(User user, Response response) {
                MySharedPreferences.getInstance().setUserId(user.getUserId());
                Toast.makeText(LoginActivity.this, "YES! " + new Gson().toJson(user), Toast.LENGTH_LONG).show();
            }

            @Override
            public boolean httpFailure(int statusCode, RetrofitError error) {
                switch (statusCode) {
                    case UrlUtil.NOT_FOUND:
                        loginNameEditText.setError("找不到用户名");
                        return true;
                    case UrlUtil.UNAUTHORIZED:
                        passwordEditText.setError("密码错误");
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    public void registration(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}
