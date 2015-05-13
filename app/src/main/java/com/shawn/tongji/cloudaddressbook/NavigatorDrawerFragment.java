package com.shawn.tongji.cloudaddressbook;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
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

    public static int SELF_SETTING = 4;

    public static String PREF_FILE_NAME = "navigatorFile";
    public static String KEY_USER_LEARNED_DRAWER;

    private OnItemClickListener onItemClickListener = null;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    View containerView;

    LinearLayout selfSettingLinearLayout;

    boolean mUserLearnedDrawer;
    boolean mFromSavedInstanceState;

    public NavigatorDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigator_drawer, container, false);
        selfSettingLinearLayout = (LinearLayout) view.findViewById(R.id.selfSettingLayout);
        selfSettingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!= null) {
                    onItemClickListener.onItemClick(SELF_SETTING);
                }
            }
        });
        return view;
    }


    public void setUp(int viewId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(viewId);
        this.drawerLayout = drawerLayout;
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, Boolean.toString(mUserLearnedDrawer));
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            this.drawerLayout.openDrawer(containerView);
        }

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
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
