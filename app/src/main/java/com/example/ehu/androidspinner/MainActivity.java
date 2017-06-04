package com.example.ehu.androidspinner;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    //ハンドスピナー画像の宣言
    private ImageView handSpinnerImage;
    //バイブレーションの宣言
    private Vibrator vb;
    private int rotateTime = 1000;  //ハンドスピナーが一回転するまでかかる秒数
    private int repeatCount = 2;    //ハンドスピナーの回転回数

    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idと関連付け
        handSpinnerImage = (ImageView) findViewById(R.id.handSpinner);

        //ジェスチャーのインスタンス化
        mGestureDetector = new GestureDetector(this, mOnGestureListener);

        //バイブレーションの初期化
        vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);


    }

    //アプリが中断された時
    @Override
    public void onPause() {
        super.onPause();
        //ハンドスピナーを止める
        stopSpinner();
    }

    //アプリを閉じた時
    @Override
    public void onDestroy() {
        super.onDestroy();
        //ハンドスピナーを止める
        stopSpinner();
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
                startSpinner(velocity_y);
                return true;
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                return false;
            }
        }

        @Override
        public void onLongPress(MotionEvent event) {
            //ハンドスピナーを止める
            stopSpinner();
        }
    };


    //ハンドスピナーを回転させる
    public void startSpinner(double velocity) {

        RotateAnimation rotate = new RotateAnimation(0, 360, handSpinnerImage.getWidth() / 2, (handSpinnerImage.getHeight() / 2) + 75); // 画像の中心を軸に、0度から360度にかけて回転

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //ハードウェアの大きさ
        double xM = (dm.widthPixels / dm.xdpi * 2.54) * 0.01;
        double yM = (dm.heightPixels / dm.ydpi * 2.54) * 0.01;
        Log.d("MainActivity", "幅 = " + xM + "m" + "   高さ = " + yM + "m");

        //フリックの速さ
        double m_s = velocity * yM / dm.heightPixels;
        Log.d("m/s", String.valueOf(m_s));

        double T = 2 * 3.14 * yM / m_s; //周期T
        rotate.setDuration((long) (T * 1000)); // 周期T分、かけてアニメーションする
        Log.d("周期", String.valueOf(T));
        rotate.setInterpolator(new LinearInterpolator());   //繰り返し終了時止まらない
        rotate.setRepeatCount(rotate.INFINITE); //ずっと繰り返す
        handSpinnerImage.startAnimation(rotate); // アニメーション適用

        // バイブレーションで振動させる
        vb.vibrate(10000);  //10秒間振動

        Log.d("start", "回転");
    }

    //ハンドスピナーを止める
    public void stopSpinner() {
        handSpinnerImage.clearAnimation();  //回転を止める
        vb.cancel();    //バイブレーションを止める

        Log.d("stop", "停止");
    }
}
