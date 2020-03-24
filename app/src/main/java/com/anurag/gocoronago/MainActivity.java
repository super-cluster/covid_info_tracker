package com.anurag.gocoronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView appname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appname=(TextView)findViewById(R.id.textView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_1);
        appname.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(".HomeActivity"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
