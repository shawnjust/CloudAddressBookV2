package com.shawn.tongji.cloudaddressbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.client.ImageServices;
import com.shawn.tongji.cloudaddressbook.net.MyCallBack;
import com.shawn.tongji.cloudaddressbook.util.MySharedPreferences;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.FileOutputStream;
import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class ImageCropActivity extends ActionBarActivity {

    @ViewInject(id = R.id.CropImageView)
    CropImageView cropImageView;

    @ViewInject(id = R.id.appBar)
    Toolbar toolbar;

    ImageServices imageServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        FinalActivity.initInjectedView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String path = intent.getStringExtra("PICTURE_PATH");

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        cropImageView.setImageBitmap(bitmap);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setAspectRatio(2, 2);

        imageServices = UrlUtil.getRestAdapter().create(ImageServices.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_crop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {
            Bitmap croppedBitmap = cropImageView.getCroppedImage();
            try {
                FileOutputStream out = this.openFileOutput("imageCache.png", Context.MODE_PRIVATE);
//                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(imageCache));
//                FileOutputStream out = new FileOutputStream(imageCache);
                croppedBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                TypedFile uploadImage = new TypedFile("multipart/form-data", this.getFileStreamPath("imageCache.png"));
                imageServices.uploadImage(MySharedPreferences.getInstance().getUser().getUserId(), uploadImage, new MyCallBack<User>() {
                    @Override
                    public void success(User user, Response response) {
                        ImageCropActivity.this.finishActivity(Activity.RESULT_OK);
                        ImageCropActivity.this.finish();
                    }

                    @Override
                    public boolean httpFailure(int statusCode, RetrofitError error) {
                        Toast.makeText(ImageCropActivity.this, "头像上传错误，请重试", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
