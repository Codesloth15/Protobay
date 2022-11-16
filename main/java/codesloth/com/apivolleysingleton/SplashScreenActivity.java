package codesloth.com.apivolleysingleton;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
handler= new Handler();
   handler.postDelayed(new Runnable() {
       @Override
       public void run() {
           startActivity(new Intent(SplashScreenActivity.this,MainMenu.class));
           finish();
       }
   },1500);

    }
}