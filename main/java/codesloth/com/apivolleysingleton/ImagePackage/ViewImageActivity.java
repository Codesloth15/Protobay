package codesloth.com.apivolleysingleton.ImagePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaDataSource;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Objects;

import codesloth.com.apivolleysingleton.R;

public class ViewImageActivity extends AppCompatActivity {
    Button download_button;
    TextView likes;
    ImageView image_view;
    private static final int REQEUST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        initializingVariables();
        intent();
        onClick();
    }
    public void intent(){
        Intent get_intent = getIntent();
        int Likes = get_intent.getExtras().getInt("Likes");
        String images = get_intent.getExtras().getString("Images");
        likes.setText(String.valueOf(Likes));
        glide(images);
    }
public void onClick(){
    download_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(ViewImageActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                ActivityCompat.requestPermissions(ViewImageActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQEUST_CODE);
            }
        }
    });
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQEUST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                Toast.makeText(ViewImageActivity.this, "required permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveImage() {
        Uri images;
        ContentResolver contentResolver = getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
    }else{
     images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"images/");
        Uri uri =contentResolver.insert(images,contentValues);
      try {
          BitmapDrawable bitmapDrawable = (BitmapDrawable) image_view.getDrawable();
          Bitmap bitmap = bitmapDrawable.getBitmap();
          OutputStream outputStream =contentResolver.openOutputStream(Objects.requireNonNull(uri));
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
          Objects.requireNonNull(outputStream);
          Toast.makeText(ViewImageActivity.this, "save successfully", Toast.LENGTH_SHORT).show();

      } catch (FileNotFoundException e) {
          Toast.makeText(ViewImageActivity.this, "failed to save", Toast.LENGTH_SHORT).show();
          e.printStackTrace();
      }
    }
    private void initializingVariables() {
        likes=findViewById(R.id.likes_in_ViewImage);
        image_view=findViewById(R.id.imageView_in_ViewImage);
        download_button=findViewById(R.id.downLoad_button_ViewImage);
    }
    public void glide(String url){
        Glide.with(ViewImageActivity.this).load(url).into(image_view);
    }
}