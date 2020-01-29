package com.chenp.puzzle15;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn;
    private Switch swt;
    private boolean musicBtn;
    private MediaPlayer mediaPlayer;
    private boolean isPausedInCall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get all refrences from XML
        swt = findViewById(R.id.swtID);
        btn = findViewById(R.id.btnID);

        // event listeners
        btn.setOnClickListener(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    Log.d("debug", "true");
                    SharedPreferences.Editor editor = getSharedPreferences("music", MODE_PRIVATE).edit();
                    editor.putBoolean("musicState", true);editor.commit();

            }
                else{
                    mediaPlayer.pause();
                    Log.d("debug", "false");
                    SharedPreferences.Editor editor = getSharedPreferences("music", MODE_PRIVATE).edit();
                    editor.putBoolean("musicState", false);editor.commit();

            }
            }
        });

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying())
                    {
                        mediaPlayer.pause();
                        isPausedInCall = true;
                    }

                }
                else if(state == TelephonyManager.CALL_STATE_IDLE)
                {
                    if(mediaPlayer!=null) {
                        if (isPausedInCall) {
                            isPausedInCall = false;
                            if(!mediaPlayer.isPlaying())
                            {
                                mediaPlayer.setLooping(true);
                                mediaPlayer.start();
                            }
                        }
                    }
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
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("debug", "MainActivity onDestroy()");

        SharedPreferences sharedPrefs = getSharedPreferences("music", MODE_PRIVATE);
        musicBtn = sharedPrefs.getBoolean("musicState", false);


        if(musicBtn==true){
            Log.d("debug", "MainActivity onDestroy()true");
            SharedPreferences.Editor editor = getSharedPreferences("music", MODE_PRIVATE).edit();
            editor.putBoolean("musicState", true);editor.commit();
            mediaPlayer.release();
            mediaPlayer = null; }

        else{
            Log.d("debug", "MainActivity onDestroy()false");
            SharedPreferences.Editor editor = getSharedPreferences("music", MODE_PRIVATE).edit();
            editor.putBoolean("musicState", false);
            editor.commit();
        }

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("debug", "MainActivity onStart()");
        SharedPreferences sharedPrefs = getSharedPreferences("music", MODE_PRIVATE);
        musicBtn = sharedPrefs.getBoolean("musicState", false);

        if(musicBtn==true){
            Log.d("debug", "MainActivity onStart()true");
            swt.setChecked(true);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
        else{
            Log.d("debug", "MainActivity onStart()false");
            swt.setChecked(false);
            mediaPlayer.setLooping(false);
        }
    }


    @Override
    public void onClick(View v)
    {
        Log.d("debug", "onClick");
        switch (v.getId())
        {
            case R.id.btnID:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                //finish();  // close this Activity (MainActivity)
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuItem menuAbout = menu.add("About");
        MenuItem menuExit = menu.add("Exit");

        menuAbout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showAboutDialog();
                return true;
            }
        });

        menuExit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showExitDialog();
                return true;
            }
        });
        return true;
    }

    private void showAboutDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.ic_info_outline);
        alertDialog.setTitle("About Puzzle 15");
        alertDialog.setMessage("This app implements the Game of Fifteen\n\nBy Chen Parnasa and Racheli Verechzon (c)");
        alertDialog.show();
        //Toast.makeText(this, "ABOUT", Toast.LENGTH_SHORT).show();
    }

    private void showExitDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.ic_exit);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you really want to exit?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Toast.makeText(MainActivity.this, "Bye Bye!", Toast.LENGTH_SHORT).show();
                finish();  // destroy this activity
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
        //        Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }
}