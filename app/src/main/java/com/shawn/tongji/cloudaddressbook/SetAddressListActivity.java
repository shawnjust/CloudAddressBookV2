package com.shawn.tongji.cloudaddressbook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.bean.UserContactInfo;
import com.shawn.tongji.cloudaddressbook.client.UserServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.util.MySharedPreferences;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import retrofit.client.Response;


public class SetAddressListActivity extends ActionBarActivity {

    public static final int IMAGE_RESULT = 100;
    private static final int IMAGE_CROP = 200;

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    @ViewInject(id = R.id.nameEditText)
    MaterialEditText nameEditText;
    @ViewInject(id = R.id.mobilePhoneEditText)
    MaterialEditText mobilePhoneEditText;
    @ViewInject(id = R.id.homePhoneEditText)
    MaterialEditText homePhoneEditText;
    @ViewInject(id = R.id.emailEditText)
    MaterialEditText emailEditText;
    @ViewInject(id = R.id.homeAddressEditText)
    MaterialEditText homeAddressEditText;

    UserContactInfo userContactInfo;

    UserServices userServices = UrlUtil.getRestAdapter().create(UserServices.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address_list);
        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        User user = MySharedPreferences.getInstance().getUser();

        if (user.getUserContactInfoList() == null || user.getUserContactInfoList().size() == 0) {
            userContactInfo = new UserContactInfo();
            userContactInfo.setUserId(user.getUserId());
        } else {
            userContactInfo = user.getUserContactInfoList().get(0);
        }

        nameEditText.setText(userContactInfo.getName());
        homePhoneEditText.setText(userContactInfo.getHomePhone());
        mobilePhoneEditText.setText(userContactInfo.getMobilePhone());
        emailEditText.setText(userContactInfo.getEmail());
        homeAddressEditText.setText(userContactInfo.getHomeAddress());

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
        switch (id) {
            case R.id.action_add:
                userContactInfo.setName(nameEditText.getText().toString());
                userContactInfo.setHomePhone(homePhoneEditText.getText().toString());
                userContactInfo.setMobilePhone(mobilePhoneEditText.getText().toString());
                userContactInfo.setEmail(emailEditText.getText().toString());
                userContactInfo.setHomeAddress(homeAddressEditText.getText().toString());

                userServices.setContacts(MySharedPreferences.getInstance().getUser().getUserId(), userContactInfo, new MyCallBack<UserContactInfo>() {
                    @Override
                    public void success(UserContactInfo userContactInfo, Response response) {
//                    SetAddressListActivity.this.finishActivity(Activity.RESULT_OK);
                        setResult(Activity.RESULT_OK);
                        SetAddressListActivity.this.finish();
                    }
                });
                return true;
            case R.id.action_set_image:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_RESULT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_RESULT:
                if ((resultCode == Activity.RESULT_OK) && (data != null)) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Intent intent = new Intent(SetAddressListActivity.this, ImageCropActivity.class);
                    intent.putExtra("PICTURE_PATH", picturePath);
                    startActivityForResult(intent, IMAGE_CROP);
                }
                break;
            case IMAGE_CROP:
                if ((resultCode == Activity.RESULT_OK)) {

                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
