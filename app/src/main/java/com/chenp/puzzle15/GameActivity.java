package com.chenp.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView[] textViews;
    private TextView txvMoves;
    private TextView txvTime;
    private Button btnStart;
    private int countMoves = 0;
    private int timer = 0;
    private String count = "0000";
    private String timeString = "00:00";
    private boolean isPausedInCall = false;

    GameBoard gameBoard = new GameBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txvMoves = findViewById(R.id.txvMovesID);
        txvTime = findViewById(R.id.txvTimeID);
        btnStart = findViewById(R.id.btnStartID);

        textViews = new TextView[4 * 4];
        textViews[0] = findViewById(R.id.txv1ID);
        textViews[1] = findViewById(R.id.txv2ID);
        textViews[2] = findViewById(R.id.txv3ID);
        textViews[3] = findViewById(R.id.txv4ID);
        textViews[4] = findViewById(R.id.txv5ID);
        textViews[5] = findViewById(R.id.txv6ID);
        textViews[6] = findViewById(R.id.txv7ID);
        textViews[7] = findViewById(R.id.txv8ID);
        textViews[8] = findViewById(R.id.txv9ID);
        textViews[9] = findViewById(R.id.txv10ID);
        textViews[10] = findViewById(R.id.txv11ID);
        textViews[11] = findViewById(R.id.txv12ID);
        textViews[12] = findViewById(R.id.txv13ID);
        textViews[13] = findViewById(R.id.txv14ID);
        textViews[14] = findViewById(R.id.txv15ID);
        textViews[15] = findViewById(R.id.txv16ID);

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setOnClickListener(this);
        }
        txvMoves.setOnClickListener(this);
        txvMoves.setText("Moves: "+count);

        txvTime.setOnClickListener(this);
        txvTime.setText("Time: "+timeString);

        btnStart.setOnClickListener(this);

        displayNumbers();
        countUp();

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    isPausedInCall = true;
                }
                else if(state == TelephonyManager.CALL_STATE_IDLE)
                {
                    isPausedInCall = false;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };

        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //check if clicks that not moves a tile needs to count
            case R.id.txv1ID:
                if(gameBoard.needToSwap(0,0))
                { displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv2ID:

                if(gameBoard.needToSwap(0,1))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.txv3ID:

                if(gameBoard.needToSwap(0,2))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.txv4ID:
                if(gameBoard.needToSwap(0,3))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.txv5ID:
                if(gameBoard.needToSwap(1,0))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv6ID:
                if(gameBoard.needToSwap(1,1))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv7ID:
                if(gameBoard.needToSwap(1,2))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv8ID:
                if(gameBoard.needToSwap(1,3))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv9ID:
                if(gameBoard.needToSwap(2,0))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv10ID:
                if(gameBoard.needToSwap(2,1))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv11ID:
                if(gameBoard.needToSwap(2,2))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv12ID:
                if(gameBoard.needToSwap(2,3))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv13ID:
                if(gameBoard.needToSwap(3,0))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv14ID:
                if(gameBoard.needToSwap(3,1))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv15ID:
                if(gameBoard.needToSwap(3,2))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.txv16ID:
                if(gameBoard.needToSwap(3,3))
                {
                    displayNumbers();
                    movesCounter();
                    if(gameBoard.isGameOver()) {
                        lockBoard();
                        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.btnStartID:
                countMoves = 0;
                count = "0000";
                txvMoves.setText("Moves: "+count);
                timer = 0;
                timeString = "00:00";
                txvTime.setText("Time: "+timeString);
                gameBoard.scramble();
                displayNumbers();
                releaseBoard();
                break;
        }
    }


    private void displayNumbers()
    {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = gameBoard.getNum(i, j);
                if (num == 0) {
                    textViews[i * 4 + j].setText(" ");
                    textViews[i * 4 + j].setBackgroundDrawable(getResources().getDrawable(R.drawable.radius2));
                }
                else {
                    textViews[i * 4 + j].setText("" + num);
                    textViews[i * 4 + j].setBackgroundDrawable(getResources().getDrawable(R.drawable.radius));
                }
            }
        }
    }

    private void countUp()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(timer >= 0)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run(){
                            if(gameBoard.isGameOver()){
                                return; }
                            else {
                                long secs = (timer*1000) / 1000 % 60; // seconds, 0 - 59
                                long mins = (timer*1000) / 1000 / 60 % 60; // total seconds / 60, 0 - 59
                                timeString = String.format("%02d:%02d", mins, secs);
                                txvTime.setText("Time: " + timeString);
                            }
                        }
                    });
                    SystemClock.sleep(1000);    // sleep for 1000ms = 1sec
                    if(!isPausedInCall)
                        timer++;
                }
            }
        }).start();
    }

    private void lockBoard(){
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setClickable(false);
        }
    }

    private void releaseBoard(){
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setClickable(true);
        }
    }

    private void movesCounter() {
        if (9999 < countMoves) {
            return;
        }
        countMoves ++;
        count = String.format("%04d", countMoves);
        txvMoves.setText("Moves: "+count);
       }
    }
