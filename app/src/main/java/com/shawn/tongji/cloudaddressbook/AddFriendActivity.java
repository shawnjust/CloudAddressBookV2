package com.shawn.tongji.cloudaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shawn.tongji.cloudaddressbook.adapter.ContactsAdapter;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import retrofit.client.Response;


public class AddFriendActivity extends ActionBarActivity {

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;
    @ViewInject(id=R.id.contactsRecyclerView)
    RecyclerView contactsRecyclerView;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactsRecyclerView.setLayoutManager(linearLayoutManager);

        ContactsAdapter contactsAdapter = new ContactsAdapter();
        contactsRecyclerView.setAdapter(contactsAdapter);
        contactsAdapter.setOnContactsViewHolderClick(new ContactsAdapter.OnContactsViewHolderClick() {
            @Override
            public void onContactsViewHolderClick(ContactsAdapter.ContactsViewHolder viewHolder) {
                User user = viewHolder.getUser();
                Intent intent = new Intent(AddFriendActivity.this, UserDetailInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("USER", user);
                intent.putExtra("BUNDLE",bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        searchView = (SearchView) menu.findItem(R.id.search_friend).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(AddFriendActivity.this, "onQueryTextSubmit!", Toast.LENGTH_SHORT).show();
                UserServices userServices = UrlUtil.getRestAdapter().create(UserServices.class);
                userServices.search(query, new MyCallBack<List<User>>() {

                    @Override
                    public void success(List<User> userList, Response response) {
                        Toast.makeText(AddFriendActivity.this, "" + userList.size(), Toast.LENGTH_SHORT).show();
                        ContactsAdapter contactsAdapter = (ContactsAdapter) contactsRecyclerView.getAdapter();
                        contactsAdapter.setList(userList);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
