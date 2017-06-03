package com.example.ehu.androidspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //ハンドスピナー画像の宣言
    ImageView handSpinnerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idと関連付け
        handSpinnerImage = (ImageView) findViewById(R.id.handSpinner);
    }

    public void startSpinner(View v) {

        RotateAnimation rotate = new RotateAnimation(0, 360, handSpinnerImage.getWidth() / 2, (handSpinnerImage.getHeight() / 2) + 75); // 画像の中心を軸に、0度から360度にかけて回転
        rotate.setDuration(100); // 3000msかけてアニメーションする
        rotate.setInterpolator(new LinearInterpolator());   //繰り返し終了時止まらない
        rotate.setRepeatCount(rotate.INFINITE); //ずっと繰り返す
        handSpinnerImage.startAnimation(rotate); // アニメーション適用
    }

    public void stopSpinner(View v) {
        handSpinnerImage.clearAnimation();
    }
}
