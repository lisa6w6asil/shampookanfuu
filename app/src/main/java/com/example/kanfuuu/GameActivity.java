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
    private ImageView shampooHp;
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
                    shampoo.setImageResource(R.drawable.shampoo02);
                } else {
                    if (kickAnimatorSet.isRunning()) return;
                    kickAnimatorSet.start();
                    shampoo.setImageResource(R.drawable.shampoo01);
                }

                //10回hpを減らす最後のHPで Result 画面に遷移
                if (count >= 10 && count < 20) {
                    shampooHp.setImageResource(R.drawable.hp_1);
                } else if (count >= 20 && count < 30) {
                    shampooHp.setImageResource(R.drawable.hp_2);
                } else if (count >= 30 && count < 40) {
                    shampooHp.setImageResource(R.drawable.hp_3);
                } else if (count >= 40 && count < 50) {
                    shampooHp.setImageResource(R.drawable.hp_4);
                } else if (count >= 50 && count < 60) {
                    shampooHp.setImageResource(R.drawable.hp_5);
                } else if (count >= 60 && count < 70) {
                    shampooHp.setImageResource(R.drawable.hp_6);
                } else if (count >= 70 && count < 80) {
                    shampooHp.setImageResource(R.drawable.hp_7);
                } else if (count >= 80 && count < 90) {
                    shampooHp.setImageResource(R.drawable.hp_8);
                } else if (count >= 90 && count < 100) {
                    Intent intent = Result
                            .newIntent(GameActivity.this, count);
                    startActivity(intent);
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

    private ImageView people;
    private ImageView gameclear;
    private ImageView stageclear;
    private ImageView gameover;

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

                people = findViewById(R.id.people);
                gameclear = findViewById(R.id.gameclear);
                stageclear = findViewById(R.id.stageclear);
                gameover = findViewById(R.id.gameover);

                if (count < 100){
                    people.setVisibility(View.GONE);
                    gameclear.setVisibility(View.GONE);
                    stageclear.setVisibility(View.GONE);
                    gameover.setVisibility(View.VISIBLE);
                }

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