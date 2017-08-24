package appwork.almayce.easyboil;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by almayce on 09.12.16.
 */

public class BoiledActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mp;
    TextView ok;

    @Override
    protected void onResume() {
        super.onResume();

        mp = new MediaPlayer();
        mp = MediaPlayer.create(this, R.raw.boiled);

        new Thread() {
            public void run() {
                mp.start();
            }
        }.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boiled);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();

        runOnUiThread(new Runnable(){
            public void run(){
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            }
        });
        ok = (TextView) findViewById(R.id.tvStop);
        ok.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    public void onClick(View view) {
onBackPressed();
    }
}
