package com.shawn.tongji.cloudaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = (NavigatorDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, toolbar);

        fragment.setOnItemClickListener(new NavigatorDrawerFragment.OnItemClickListener() {
            @Override
            public void onItemClick(int flag) {
                if (flag == SELF_SETTING) {
                    startActivity(new Intent(MainActivity.this, SelfSettingActivity.class));
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

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        } else if (id == R.id.navigate) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        } else if (id == R.id.action_add_friend) {
            startActivity(new Intent(MainActivity.this, AddFriendActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
