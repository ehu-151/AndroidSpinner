package com.example.ehu.androidspinner;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //ハンドスピナー画像の宣言
    private ImageView handSpinnerImage;
    //バイブレーションの宣言
    private Vibrator vb;
    private int rotateTime = 1000;  //ハンドスピナーが一回転するまでかかる秒数
    private int repeatCount = 2;    //ハンドスピナーの回転回数

    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;
    private double spinnerVelocity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idと関連付け
        handSpinnerImage = (ImageView) findViewById(R.id.handSpinner);

        mGestureDetector = new GestureDetector(this, mOnGestureListener);
    }

    // タッチイベント
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    // タッチイベントのリスナー
    final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            try {

                // スピードを出力
                float velocity_y = Math.abs(velocityY);
                Log.d("speed", " 縦の移動スピード:" + velocity_y);
                return true;
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                return false;
            }
        }
    };

    //ハンドスピナーの速さ設定
    public void setSpinnerVelocity(double volocity) {
        spinnerVelocity = volocity;
    }

    //ハンドスピナーの速さ取得
    public double getSpinnerVolocity() {
        return spinnerVelocity;
    }

    //ハンドスピナーを回転させる

    public void startSpinner(View v) {

        RotateAnimation rotate = new RotateAnimation(0, 360, handSpinnerImage.getWidth() / 2, (handSpinnerImage.getHeight() / 2) + 75); // 画像の中心を軸に、0度から360度にかけて回転
        rotate.setDuration(rotateTime); // 3000msかけてアニメーションする
        rotate.setInterpolator(new LinearInterpolator());   //繰り返し終了時止まらない
        rotate.setRepeatCount(repeatCount - 1); //ずっと繰り返す
        handSpinnerImage.startAnimation(rotate); // アニメーション適用

        //バイブレーションで振動させる
        vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vb.vibrate(rotateTime * repeatCount);

        Log.d("start", "回転");
    }

    //ハンドスピナーを止める
    public void stopSpinner(View v) {
        handSpinnerImage.clearAnimation();  //回転を止める
        vb.cancel();    //バイブレーションを止める

        Log.d("stop", "停止");
    }
}
