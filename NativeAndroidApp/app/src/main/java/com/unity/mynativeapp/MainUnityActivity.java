package com.unity.mynativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.unity3d.player.OverrideUnityActivity;

public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControlsToFrame();
        Intent intent = getIntent();
        handleIntent(intent);

        mUnityPlayer.setX(140);
        mUnityPlayer.setY(300);
        mUnityPlayer.setLayoutParams(new FrameLayout.LayoutParams(800,1000));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if(intent == null || intent.getExtras() == null) return;

        if(intent.getExtras().containsKey("doQuit"))
            if(mUnityPlayer != null) {
                finish();
            }
    }

    @Override
    protected void showMainActivity(String setToColor) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("setColor", setToColor);
        startActivity(intent);
    }

    @Override public void onUnityPlayerUnloaded() {
        showMainActivity("");
    }

    public void addControlsToFrame() {
        FrameLayout layout = (FrameLayout) getWindow().getDecorView();
        layout.setBackgroundColor(0xff009f9f);

        for (int x = 0; x < 5; ++x)
        {
            for (int y = 0; y < 15; ++y)
            {
                TextView backgroundText = new TextView(this);
                backgroundText.setText("BACKGROUND");
                backgroundText.setX(x * 300);
                backgroundText.setY(y * 200);
                backgroundText.setRotation(-45);
                layout.addView(backgroundText, 300, 200);
            }
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Show Main");
            myButton.setX(80);
            myButton.setY(1500);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   showMainActivity("");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Send Msg");
            myButton.setX(390);
            myButton.setY(1500);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Cube", "ChangeColor", "yellow");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Unload");
            myButton.setX(700);
            myButton.setY(1500);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.unload();
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Finish");
            myButton.setX(700);
            myButton.setY(1800);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            layout.addView(myButton, 300, 200);
        }
    }


}
