package com.shawn.tongji.cloudaddressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.Gson;
import com.shawn.tongji.cloudaddressbook.adapter.ContactsInfoAdapter;
import com.shawn.tongji.cloudaddressbook.bean.ContactsInfoItem;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.bean.UserRelation;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.net.MySharedPreferences;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;
import com.shawn.tongji.cloudaddressbook.util.DataUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class UserDetailInfoActivity extends ActionBarActivity {

    public static final int REQUEST_CODE_SETTING = 0;

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.contactsInfoRecyclerView)
    RecyclerView contactsInfoRecyclerView;

    @ViewInject(id = R.id.relationButton)
    ButtonRectangle relationButton;

    @ViewInject(id = R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    UserServices userServices = UrlUtil.getRestAdapter().create(UserServices.class);
    User user;
    ContactsInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_info);
        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        user = (User) bundle.getSerializable("USER");
        getSupportActionBar().setTitle(user.getUserName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactsInfoRecyclerView.setLayoutManager(linearLayoutManager);

        List<ContactsInfoItem> itemList = user.getContactsInfoItem();

        adapter = new ContactsInfoAdapter(this, itemList);
        contactsInfoRecyclerView.setAdapter(adapter);

        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view1);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
                userServices.getUserRelation(MySharedPreferences.getInstance().getUserId(), user.getUserId(), new MyCallBack<UserRelation>() {

                    @Override
                    public void success(UserRelation userRelation, Response response) {
                        Log.e(UserDetailInfoActivity.class.getName(), new Gson().toJson(userRelation));
                        setByRelation(userRelation);
                        ptrFrameLayout.refreshComplete();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ptrFrameLayout.refreshComplete();
                        super.failure(error);
                    }
                });
            }
        });

        ptrClassicFrameLayout.autoRefresh();


    }

    AddFriendClickListener addFriendClickListener = new AddFriendClickListener();

    public class AddFriendClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            userServices.changeRelation(MySharedPreferences.getInstance().getUserId(), user.getUserId(), DataUtil.RELATION_APPLY, new MyCallBack<UserRelation>() {

                @Override
                public void success(UserRelation userRelation, Response response) {
                    Toast.makeText(UserDetailInfoActivity.this, new Gson().toJson(userRelation), Toast.LENGTH_LONG).show();
                    user = userRelation.getUserTo();
                    setByRelation(userRelation);
                }
            });
        }
    }

    private void setByRelation(UserRelation userRelation) {
        switch (userRelation.getRelationType()) {
            case DataUtil.RELATION_APPLY:
            case DataUtil.RELATION_STRANGER:
                relationButton.setOnClickListener(addFriendClickListener);
                relationButton.setText("添加为好友");
                relationButton.setVisibility(View.VISIBLE);
                break;
            case DataUtil.RELATION_FOLLOW:
                relationButton.setVisibility(View.GONE);
                List<ContactsInfoItem> infoList;
                if (userRelation.getUserTo().getUserContactInfoList() == null || userRelation.getUserTo().getUserContactInfoList().size() == 0) {
                    infoList = userRelation.getUserTo().getContactsInfoItem();
                } else {
                    infoList = userRelation.getUserTo().getUserContactInfoList().get(0).getContactsInfoItem();
                }
                adapter.setList(infoList);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (user.getUserId() == MySharedPreferences.getInstance().getUserId()) {
            getMenuInflater().inflate(R.menu.menu_user_detail_info, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_self_info_settings) {
            Intent intent = new Intent(UserDetailInfoActivity.this, SetAddressListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("SELF", user);
            intent.putExtra("BUNDLE", bundle);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(UserDetailInfoActivity.class.getName(), requestCode + " " + resultCode + " ");
        switch (resultCode) {
            case Activity.RESULT_OK:
                ptrClassicFrameLayout.autoRefresh(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
