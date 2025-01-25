package com.example.kanfuuu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    private static final int DEFAULT_TIMER_VALUE = 30;
    private static final long TOTAL_TIME = DEFAULT_TIMER_VALUE * 1000;
    private static final long INTERVAL_TIME = 1000;

    public static Intent newIntent(Context context){
        return new Intent(context, GameActivity.class);
    }
    private View shampooHp;
    private TextView timerText;
    private ImageView shampoo;
    private View actionPunch;
    private AnimatorSet punchAnimatorSet;
    private View actionKick;
    private AnimatorSet kickAnimatorSet;
    private CountDownTimer timer;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shampoo = findViewById(R.id.shampoo);
        actionPunch = findViewById(R.id.punch);
        actionKick = findViewById(R.id.kick);
        timerText = findViewById(R.id.time_text);
        shampooHp = findViewById(R.id.hp);

        punchActionSetting();
        kickActionSerring();


        shampoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++count;
                if (count % 2 == 0) {
                    if (punchAnimatorSet.isRunning()) return;
                    punchAnimatorSet.start();
                    shampoo.setImageResource(R.drawable.punch);
                } else {
                    if (kickAnimatorSet.isRunning()) return;
                    kickAnimatorSet.start();
                    shampoo.setImageResource(R.drawable.kick);
                }
            }
        });
        initTimer();
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    private void initTimer(){
        timerText.setText(String.valueOf(DEFAULT_TIMER_VALUE));
        timer = new CountDownTimer(TOTAL_TIME, INTERVAL_TIME) {
            @Override
            public void onTick(long millisUnitlFinished) {
                long second = millisUnitlFinished / 1000;
                timerText.setText(String.valueOf(second));
            }

            @Override
            public void onFinish() {
                shampoo.setOnClickListener(null);
                Intent intent = Result
                        .newIntent(GameActivity.this, count);
                startActivity(intent);

            }
        };
    }

    private void punchActionSetting(){
        ObjectAnimator moveRight = ObjectAnimator.ofFloat(actionPunch, "translationX", -1000f, 0f);
        moveRight.setDuration(300);
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(actionPunch, "translationY", 1000f, 0f);
        moveUp.setDuration(300);
        punchAnimatorSet = new AnimatorSet();
        punchAnimatorSet.playTogether(moveRight, moveUp);
        punchAnimatorSet.addListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                actionPunch.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                actionPunch.setVisibility(View.VISIBLE);
            }
        });
    }

    private void kickActionSerring(){
        ObjectAnimator moveleft = ObjectAnimator.ofFloat(actionKick, "translationX",1000f, 0f);
        moveleft.setDuration(300);
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(actionKick, "translationY", 1000f, 0f);
        moveUp.setDuration(300);
        kickAnimatorSet = new AnimatorSet();
        kickAnimatorSet.playTogether(moveleft, moveUp);
        kickAnimatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                actionKick.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                actionKick.setVisibility(View.VISIBLE);
            }
        });
    }
}