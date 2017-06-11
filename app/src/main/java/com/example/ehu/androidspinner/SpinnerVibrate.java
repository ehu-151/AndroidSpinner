package com.example.ehu.androidspinner;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


class SpinnerVibrate extends AppCompatActivity {
    //バイブレーションの宣言と初期化
    private Vibrator vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    //ハンドスピナー画像の宣言と初期化
    private ImageView handSpinnerImage = (ImageView) findViewById(R.id.handSpinner);

    SpinnerVibrate() {

    }

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
