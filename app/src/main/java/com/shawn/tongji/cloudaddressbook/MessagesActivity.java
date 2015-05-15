package com.shawn.tongji.cloudaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shawn.tongji.cloudaddressbook.adapter.MessagesAdapter;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.net.MySharedPreferences;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;
import com.shawn.tongji.cloudaddressbook.util.DataUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MessagesActivity extends ActionBarActivity {

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.messagesRecyclerView)
    RecyclerView messageRecyclerView;

    @ViewInject(id = R.id.ptrFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;

    UserServices userServices;
    MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userServices = UrlUtil.getRestAdapter().create(UserServices.class);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(linearLayoutManager);

        messagesAdapter = new MessagesAdapter(new ArrayList<User>());
        messageRecyclerView.setAdapter(messagesAdapter);
        messagesAdapter.setOnItemViewHolderClickListener(new MessagesAdapter.OnItemViewHolderClick() {
            @Override
            public void onItemViewHolderClick(MessagesAdapter.MessagesViewHolder viewHolder) {
                User user = viewHolder.getUser();
                Intent intent = new Intent(MessagesActivity.this, UserDetailInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", user);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });

        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view1);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
                userServices.getUserList(MySharedPreferences.getInstance().getUserId(), DataUtil.RELATION_APPLY, new MyCallBack<List<User>>() {
                    @Override
                    public void success(List<User> userList, Response response) {
                        messagesAdapter.setList(userList);
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
        ptrFrameLayout.autoRefresh();

        messageRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ptrFrameLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
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
}
