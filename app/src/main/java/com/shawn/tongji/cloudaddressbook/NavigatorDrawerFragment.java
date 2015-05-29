package com.shawn.tongji.cloudaddressbook;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigatorDrawerFragment extends Fragment {

    public static final int MESSAGE = 2;
    public static final int SELF_SETTING = 4;
    public static final int LOGOUT = 5;

    private OnItemClickListener onItemClickListener = null;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    View containerView;

    LinearLayout selfSettingLayout;
    LinearLayout messageLayout;
    LinearLayout logoutLayout;


    public NavigatorDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigator_drawer, container, false);
        selfSettingLayout = (LinearLayout) view.findViewById(R.id.selfSettingLayout);
        selfSettingLayout.setOnClickListener(clickListener);
        messageLayout = (LinearLayout) view.findViewById(R.id.messageLayout);
        messageLayout.setOnClickListener(clickListener);
        logoutLayout = (LinearLayout) view.findViewById(R.id.logoutLayout);
        logoutLayout.setOnClickListener(clickListener);
        return view;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.messageLayout:
                        onItemClickListener.onItemClick(MESSAGE);
                        break;
                    case R.id.selfSettingLayout:
                        onItemClickListener.onItemClick(SELF_SETTING);
                        break;
                    case R.id.logoutLayout:
                        onItemClickListener.onItemClick(LOGOUT);
                        break;
                }
            }
        }
    };


    public void setUp(int viewId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(viewId);
        this.drawerLayout = drawerLayout;
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int flag);
    }
}
