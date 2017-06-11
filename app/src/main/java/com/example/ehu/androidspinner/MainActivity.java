package com.example.ehu.androidspinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private int rotateTime = 1000;  //ハンドスピナーが一回転するまでかかる秒数
    private int repeatCount = 2;    //ハンドスピナーの回転回数

    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;
    private SpinnerVibrate vb = new SpinnerVibrate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ジェスチャーのインスタンス化
        mGestureDetector = new GestureDetector(this, mOnGestureListener);

    }

    //アプリが中断された時
    @Override
    public void onPause() {
        super.onPause();
        //ハンドスピナーを止める
        vb.stopSpinner();
    }

    //アプリを閉じた時
    @Override
    public void onDestroy() {
        super.onDestroy();
        //ハンドスピナーを止める
        vb.stopSpinner();
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
                vb.startSpinner(velocity_y);
                return true;
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                return false;
            }
        }

        @Override
        public void onLongPress(MotionEvent event) {
            //ハンドスピナーを止める
            vb.stopSpinner();
        }
    };

}
