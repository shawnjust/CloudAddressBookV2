package com.shawn.tongji.cloudaddressbook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shawn.tongji.cloudaddressbook.bean.UserContactInfo;
import com.shawn.tongji.cloudaddressbook.net.MySharedPreferences;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;
import com.shawn.tongji.cloudaddressbook.net.VolleySingleton;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.HashMap;
import java.util.Map;


public class SetAddressListActivity extends ActionBarActivity {

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.nameEditText)
    MaterialEditText nameEditText;
    @ViewInject(id = R.id.homePhoneEditText)
    MaterialEditText homePhoneEditText;
    @ViewInject(id = R.id.emailEditText)
    MaterialEditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address_list);
        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_address_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionAdd) {

            final UserContactInfo userContactInfo = new UserContactInfo();
            int userId = MySharedPreferences.getInstance().getUserId();
            userContactInfo.setUserId(userId);
            userContactInfo.setName(nameEditText.getText().toString());
            userContactInfo.setHomePhone(homePhoneEditText.getText().toString());
            userContactInfo.setEmail(emailEditText.getText().toString());

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    UrlUtil.getSaveUserContactorUrl(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SetAddressListActivity.this, "保存成功: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SetAddressListActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    Gson gson = new Gson();
                    String contactor = gson.toJson(userContactInfo);
                    map.put("contactor", contactor);
                    return map;
                }

            };
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
