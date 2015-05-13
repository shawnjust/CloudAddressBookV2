package com.shawn.tongji.cloudaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shawn.tongji.cloudaddressbook.adapter.ContactsInfoAdapter;
import com.shawn.tongji.cloudaddressbook.bean.ContactsInfoItem;
import com.shawn.tongji.cloudaddressbook.bean.User;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UserDetailInfoActivity extends ActionBarActivity {

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.contactsInfoRecyclerView)
    RecyclerView contactsInfoRecyclerView;

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
        User user = bundle.getParcelable("USER");
        getSupportActionBar().setTitle(user.getUserName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactsInfoRecyclerView.setLayoutManager(linearLayoutManager);

        List<ContactsInfoItem> itemList = user.getContactsInfoItem();
        Collections.sort(itemList, new Comparator<ContactsInfoItem>() {
            @Override
            public int compare(ContactsInfoItem lhs, ContactsInfoItem rhs) {
                if (lhs.getGravity() > rhs.getGravity()) {
                    return 1;
                } else if (lhs.getGravity() == rhs.getGravity()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        ContactsInfoAdapter adapter = new ContactsInfoAdapter(this, itemList);
        contactsInfoRecyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail_info, menu);
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
