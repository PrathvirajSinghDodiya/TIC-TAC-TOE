package com.example.tiktactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class player_name extends AppCompatActivity {
    EditText editText,editText1;
    Button button;
    MediaPlayer mediaPlayer;
    TextView textViewS,textViewM,textViewO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);
        getSupportActionBar().hide();
        editText = findViewById(R.id.editTextTextPersonName);
        editText1 = findViewById(R.id.editTextTextPersonName2);
        button = findViewById(R.id.button);
        mediaPlayer = MediaPlayer.create(player_name.this,R.raw.music);
        mediaPlayer.start();
        textViewM = findViewById(R.id.textView);
        textViewO = findViewById(R.id.textViewo);
        textViewS = findViewById(R.id.textViewS);

        textViewM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewM.setTextColor(getResources().getColor(R.color.my));
                Toast.makeText(player_name.this,"click on Start",Toast.LENGTH_SHORT).show();
            }
        });
        textViewO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(player_name.this,"Coming soon....only Multiplayer mode available",Toast.LENGTH_SHORT).show();
            }
        });
        textViewS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(player_name.this,"Coming soon....only Multiplayer mode available",Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(player_name.this,MainActivity.class);
                String name1 = editText.getText().toString();
                intent.putExtra("one",name1);
                String name2 = editText1.getText().toString();
                intent.putExtra("two",name2);
                startActivity(intent);
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });




    }

    @Override
    protected void onPause() {
        super.onPause();
       try{
           mediaPlayer.pause();
     }catch (Exception e){
           e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}