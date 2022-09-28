package com.example.tts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText speechText;
    TextToSpeech ts;
    Button speak_btn;
    SeekBar pitchSB,speedSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speak_btn=findViewById(R.id.button);
        pitchSB=findViewById(R.id.seekBarPitch);
        speedSB=findViewById(R.id.seekBarSpeed);
        speechText=findViewById(R.id.sentence);
        ts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.SUCCESS) {
                    int availableLanguage = ts.isLanguageAvailable(Locale.UK);
                    if (availableLanguage != ts.LANG_NOT_SUPPORTED) {
                        speak_btn.setEnabled(true);
                    } else {
                        speak_btn.setEnabled(false);
                        Log.d("Language Error", "Language not supported");
                    }
                }
                else{
                        Log.d("TTS Initialization", "TTS Initialization Failed");
                }
            }
        });
        speak_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
    }

    private void speak() {
        String speech=speechText.getText().toString();
        float pitch=pitchSB.getProgress()/50;
        float speed=speedSB.getProgress()/50;

        if(pitch<0.1) pitch=0.1f;
        if(speed<0.1) speed=0.1f;

        ts.setPitch(pitch);
        ts.setSpeechRate(speed);

        ts.speak(speech,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    @Override
    protected void onDestroy() {
        if(ts!=null){
            ts.stop();
            ts.shutdown();
        }
        super.onDestroy();
    }
}
