package cat.flx.sprite;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Game game;
    Button pause;
    ImageView imagePause;
    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_main);
        final GameView gameView = (GameView) findViewById(R.id.view);
        score = (TextView) findViewById(R.id.score);
        game = new Game(this, score);
        gameView.setGame(game);
        pause = (Button) findViewById(R.id.pause);
        imagePause = (ImageView) findViewById(R.id.imagePause);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameView.isPause()){gameView.setPause(false);
                    imagePause.setVisibility(View.GONE);
                }
                else {gameView.setPause(true);
                    imagePause.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override public void onResume() {
        super.onResume();
        game.getAudio().startMusic();
    }

    @Override public void onPause() {
        game.getAudio().stopMusic();
        super.onPause();
    }

    @Override public boolean dispatchKeyEvent(KeyEvent event) {
        boolean down = (event.getAction() == KeyEvent.ACTION_DOWN);
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_A:
                game.keyLeft(down); break;
            case KeyEvent.KEYCODE_D:
                game.keyRight(down); break;
            case KeyEvent.KEYCODE_SPACE:
                game.keyJump(down); break;
        }
        return true;
    }
}
