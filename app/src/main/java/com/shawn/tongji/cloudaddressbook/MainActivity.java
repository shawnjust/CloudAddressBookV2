package com.shawn.tongji.cloudaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.shawn.tongji.cloudaddressbook.adapter.ContactsAdapter;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.util.MySharedPreferences;
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

import static com.shawn.tongji.cloudaddressbook.NavigatorDrawerFragment.LOGOUT;
import static com.shawn.tongji.cloudaddressbook.NavigatorDrawerFragment.MESSAGE;
import static com.shawn.tongji.cloudaddressbook.NavigatorDrawerFragment.SELF_SETTING;


public class MainActivity extends ActionBarActivity {

    @ViewInject(id = R.id.SuccessButton)
    Button button;

    @ViewInject(id = R.id.SecondButton)
    Button secondButton;

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    NavigatorDrawerFragment fragment;

    @ViewInject(id = R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewInject(id = R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id = R.id.mainPageRecyclerView)
    RecyclerView mainRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ContactsAdapter adapter;

    UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if not login
        if (MySharedPreferences.getInstance().getUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
            return;
        }

        setContentView(R.layout.activity_main);

        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userServices = UrlUtil.getRestAdapter().create(UserServices.class);

        fragment = (NavigatorDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, toolbar);

        fragment.setOnItemClickListener(new NavigatorDrawerFragment.OnItemClickListener() {
            @Override
            public void onItemClick(int flag) {
                switch (flag) {
                    case SELF_SETTING:
                        startActivity(new Intent(MainActivity.this, SelfSettingActivity.class));
                        break;
                    case MESSAGE:
                        startActivity(new Intent(MainActivity.this, MessagesActivity.class));
                        break;
                    case LOGOUT:
                        MySharedPreferences.getInstance().clearUser();
                        if (MySharedPreferences.getInstance().getUser() == null) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                        break;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ContactsAdapter(new ArrayList<User>());
        mainRecyclerView.setAdapter(adapter);

        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view1);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                userServices.getUserList(MySharedPreferences.getInstance().getUser().getUserId(), DataUtil.RELATION_FOLLOW, new MyCallBack<List<User>>() {
                    @Override
                    public void success(List<User> userList, Response response) {
                        adapter.setList(userList);
                        ptrClassicFrameLayout.refreshComplete();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ptrClassicFrameLayout.refreshComplete();
                        super.failure(error);
                    }
                });
            }
        });

        mainRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ptrClassicFrameLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        adapter.setOnContactsViewHolderClick(new ContactsAdapter.OnContactsViewHolderClick() {
            @Override
            public void onContactsViewHolderClick(ContactsAdapter.ContactsViewHolder viewHolder) {
                User user = viewHolder.getUser();
                Intent intent = new Intent(MainActivity.this, UserDetailInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", user);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
        ptrClassicFrameLayout.autoRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.action_add_friend) {
            startActivity(new Intent(MainActivity.this, AddFriendActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
