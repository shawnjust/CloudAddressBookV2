package com.shawn.tongji.cloudaddressbook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class RegistrationActivity extends ActionBarActivity {

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.nameEditText)
    MaterialEditText nameEditText;
    @ViewInject(id = R.id.emailEditText)
    MaterialEditText emailEditText;
    @ViewInject(id = R.id.passwordEditView)
    MaterialEditText passwordEditText;
    @ViewInject(id = R.id.passwordRepeatEditView)
    MaterialEditText passwordRepeatEditText;
    @ViewInject(id = R.id.registButton, click = "registration")
    ButtonRectangle registrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        FinalActivity.initInjectedView(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    public void registration(View view) {

        Pattern pattern = Pattern.compile("^(\\w)+(\\.|\\w)*@(\\w)+(\\.\\w+)+$");
        Matcher matcher = pattern.matcher(emailEditText.getText());
        if (!matcher.matches()) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            return;
        }
        if ("".equals(nameEditText.getText().toString())) {
            nameEditText.setError(getString(R.string.error_invalid_name));
            return;
        }
        pattern = Pattern.compile("^[0-9\\w\\.]{4,16}$");
        matcher = pattern.matcher(passwordEditText.getText());
        if (!matcher.matches()) {
            passwordEditText.setError(getString(R.string.error_invalid_password));
            return;
        }
        if (!passwordEditText.getText().toString().equals(passwordRepeatEditText.getText().toString())) {
            passwordRepeatEditText.setError(getString(R.string.error_different_password));
            return;
        }
        User user = new User();
        user.setUserName(nameEditText.getText().toString());
        user.setUserEmail(emailEditText.getText().toString());
        user.setUserPassword(passwordEditText.getText().toString());
        UserServices userServices = UrlUtil.getRestAdapter().create(UserServices.class);
        Toast.makeText(RegistrationActivity.this, new Gson().toJson(user), Toast.LENGTH_SHORT).show();
        userServices.registration(user, new MyCallBack<User>() {
            @Override
            public boolean httpFailure(int statusCode, RetrofitError error) {
                switch (statusCode) {
                    case UrlUtil.CONFLICT:
                        emailEditText.setError(getString(R.string.error_email_has_exist));
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void success(User user, Response response) {
                Toast.makeText(RegistrationActivity.this, "注册成功" + new Gson().toJson(user), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
