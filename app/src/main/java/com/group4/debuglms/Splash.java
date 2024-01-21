package com.group4.debuglms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.group4.debuglms.MainActivity;
import com.group4.debuglms.R;

import pl.droidsonroids.gif.GifImageView;

public class Splash extends AppCompatActivity {

    GifImageView applogo;

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.BaseFragmentActivityGingerbread, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        this.applogo = (GifImageView) findViewById(R.id.applogo);
        Animation animate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            /* class com.sti.ehms.Splash.AnonymousClass1 */

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                com.group4.debuglms.Splash.this.finish();
                com.group4.debuglms.Splash.this.startActivity(new Intent(com.group4.debuglms.Splash.this.getApplicationContext(), MainActivity.class));
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.applogo.setAnimation(animate);
    }

}