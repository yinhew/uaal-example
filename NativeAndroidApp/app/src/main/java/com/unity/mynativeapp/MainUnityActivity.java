package com.unity.mynativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

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
        mUnityPlayer.setY(80);
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

        // Background
        for (int x = 0; x < 10; ++x)
        {
            for (int y = 0; y < 20; ++y)
            {
                TextView backgroundText = new TextView(this);
                backgroundText.setText("BACKGROUND");
                backgroundText.setX(x * 300);
                backgroundText.setY(y * 200);
                backgroundText.setRotation(-45);
                layout.addView(backgroundText, 300, 200);
            }
        }

        // Spoken text label
        {
            TextView spokenTextLabel = new EditText(this);
            spokenTextLabel.setTextColor(0xffffffff);
            spokenTextLabel.setTextSize(9.0f);
            spokenTextLabel.setGravity(Gravity.CENTER_VERTICAL);
            spokenTextLabel.setText("Spoken Text:");
            spokenTextLabel.setX(80);
            spokenTextLabel.setY(1080);
            layout.addView(spokenTextLabel, 200, 100);
        }

        // Spoken text area
        {
            EditText spokenTextInput = new EditText(this);
            spokenTextInput.setTag("EditText_SpokenText");
            spokenTextInput.setBackgroundColor(0xffffffff);
            spokenTextInput.setText("Welcome to use Microsoft virtual avatar!");
            spokenTextInput.setGravity(Gravity.TOP);
            spokenTextInput.setX(80);
            spokenTextInput.setY(1150);
            layout.addView(spokenTextInput, 920, 240);
        }

        // Character label
        {
            TextView characterLabel = new EditText(this);
            characterLabel.setTextColor(0xffffffff);
            characterLabel.setTextSize(9.0f);
            characterLabel.setGravity(Gravity.CENTER_VERTICAL);
            characterLabel.setText("Character:");
            characterLabel.setX(80);
            characterLabel.setY(1370);
            layout.addView(characterLabel, 200, 100);
        }

        // Character drop down list
        {
            Spinner characterSpinner = new Spinner(this);
            characterSpinner.setTag("Spinner_Character");
            characterSpinner.setBackgroundColor(0xffffffff);
            characterSpinner.setX(80);
            characterSpinner.setY(1440);

            ArrayList<String> characterList = new ArrayList<String>();
            characterList.add("Aki");
            characterList.add("Shirley");
            characterList.add("Xiaoyou");

            ArrayAdapter<String> adatper = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, characterList );
            characterSpinner.setAdapter(adatper);
            characterSpinner.setSelection(0);
            characterSpinner.setOnItemSelectedListener( new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    FrameLayout layout = (FrameLayout) getWindow().getDecorView();
                    String characterName = ((Spinner) layout.findViewWithTag("Spinner_Character")).getSelectedItem().toString();
                    mUnityPlayer.UnitySendMessage("AnimationManager", "SetCharacter", characterName);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            layout.addView(characterSpinner, 445, 120);
        }

        // Motion label
        {
            TextView motionLabel = new EditText(this);
            motionLabel.setTextColor(0xffffffff);
            motionLabel.setTextSize(9.0f);
            motionLabel.setGravity(Gravity.CENTER_VERTICAL);
            motionLabel.setText("Motion:");
            motionLabel.setX(555);
            motionLabel.setY(1370);
            layout.addView(motionLabel, 200, 100);
        }

        // Motion drop down list
        {
            Spinner motionSpinner = new Spinner(this);
            motionSpinner.setTag("Spinner_Motion");
            motionSpinner.setBackgroundColor(0xffffffff);
            motionSpinner.setX(555);
            motionSpinner.setY(1440);

            ArrayList<String> characterList = new ArrayList<String>();
            characterList.add("Default");
            characterList.add("Dance");
            characterList.add("Happy");
            characterList.add("Angry");
            characterList.add("Cry");

            ArrayAdapter<String> adatper = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, characterList );
            motionSpinner.setAdapter(adatper);
            motionSpinner.setSelection(0);
            motionSpinner.setOnItemSelectedListener( new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    FrameLayout layout = (FrameLayout) getWindow().getDecorView();
                    String motionName = ((Spinner) layout.findViewWithTag("Spinner_Motion")).getSelectedItem().toString();
                    mUnityPlayer.UnitySendMessage("SpeechSynthesisManager", "SetMotion", motionName);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            layout.addView(motionSpinner, 445, 120);
        }

        // TTS voice label
        {
            TextView ttsVoiceLabel = new EditText(this);
            ttsVoiceLabel.setTextColor(0xffffffff);
            ttsVoiceLabel.setTextSize(9.0f);
            ttsVoiceLabel.setGravity(Gravity.CENTER_VERTICAL);
            ttsVoiceLabel.setText("TTS Voice:");
            ttsVoiceLabel.setX(80);
            ttsVoiceLabel.setY(1540);
            layout.addView(ttsVoiceLabel, 200, 100);
        }

        // TTS voice drop down list
        {
            Spinner ttsVoiceSpinner = new Spinner(this);
            ttsVoiceSpinner.setTag("Spinner_TtsVoice");
            ttsVoiceSpinner.setBackgroundColor(0xffffffff);
            ttsVoiceSpinner.setX(80);
            ttsVoiceSpinner.setY(1610);

            ArrayList<String> voiceList = new ArrayList<String>();
            voiceList.add("enUSJessaNeural");
            voiceList.add("enUSJennyNeural");
            voiceList.add("deDEKatjaNeural");
            voiceList.add("jaJPNanamiNeural");
            voiceList.add("zhCNXiaoxiaoNeural");
            voiceList.add("zhCNXiaoshuangNeural");
            voiceList.add("zhCNXiaochenNeural");
            voiceList.add("zhCNYunyangNeural");
            voiceList.add("zhCNYunxiNeural");

            ArrayAdapter<String> adatper = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, voiceList );
            ttsVoiceSpinner.setAdapter(adatper);
            ttsVoiceSpinner.setSelection(1);
            layout.addView(ttsVoiceSpinner, 920, 120);
        }

        // Speak button
        {
            Button myButton = new Button(this);
            myButton.setText("Speak");
            myButton.setX(80);
            myButton.setY(1800);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    FrameLayout layout = (FrameLayout) getWindow().getDecorView();
                    String voiceName = ((Spinner) layout.findViewWithTag("Spinner_TtsVoice")).getSelectedItem().toString();
                    mUnityPlayer.UnitySendMessage("AnimationManager", "SetVoice", voiceName);

                    String spokenText = ((EditText) layout.findViewWithTag("EditText_SpokenText")).getText().toString();
                    mUnityPlayer.UnitySendMessage("AnimationManager", "Speak", spokenText);
                }
            });
            layout.addView(myButton, 920, 200);
        }

        // Show main activity, without closing Unity activity
        {
            Button myButton = new Button(this);
            myButton.setText("Show Main");
            myButton.setX(80);
            myButton.setY(2000);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showMainActivity("");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        // Unload Unity activity
        {
            Button myButton = new Button(this);
            myButton.setText("Unload");
            myButton.setX(390);
            myButton.setY(2000);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.unload();
                }
            });
            layout.addView(myButton, 300, 200);
        }

        // Close Unity activity
        {
            Button myButton = new Button(this);
            myButton.setText("Finish");
            myButton.setX(700);
            myButton.setY(2000);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            layout.addView(myButton, 300, 200);
        }
    }

}
