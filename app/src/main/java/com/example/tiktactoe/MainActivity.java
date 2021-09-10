package com.example.tiktactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView0,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int draw=0;
    int  active_player=0;
    String statusUpdate;
    TextView status;
    MediaPlayer mediaPlayer1,mediaPlayer,mediaPlayer2;
    Dialog dialog;
    TextView winner;
    ImageView Close;
    Boolean flag = false;
    private static final String CHANNEL_ID = "com.example.notification.notify";

    Button reset,home;
    TextView playerOne,playerTwo;
    int[][] winPositions = {{0,1,2},{3,4,5},{6,7,8},
                           {0,3,6},{1,4,7},{2,5,8},

                           {0,4,8},{2,4,6}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        createNotificationChannel();
        Intent intent = new Intent(this, player_name.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Hello there...")
                .setContentText("Play Tic Tac Toe with you friends offline right now")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        int notificationId = 512333;
        notificationManager.notify(notificationId, builder.build());
        status = findViewById(R.id.status);
        reset = findViewById(R.id.reset);
        home = findViewById(R.id.homee);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.start();
                Intent intent1 = new Intent(MainActivity.this,player_name.class);
                startActivity(intent1);
            }
        });
        mediaPlayer1 = MediaPlayer.create(this,R.raw.os);
        mediaPlayer = MediaPlayer.create(this,R.raw.jump);
        mediaPlayer2 = MediaPlayer.create(this,R.raw.winner);

        playerOne = findViewById(R.id.oneplayer);
        playerTwo = findViewById(R.id.twoplayer);
        Intent intent1 = getIntent();
        String nameOne = intent1.getStringExtra("one");
        String nameTwo = intent1.getStringExtra("two");
        playerOne.setText(nameOne);
        playerTwo.setText(nameTwo);

        imageView0 = findViewById(R.id.imageView2);
        imageView1 = findViewById(R.id.imageView3);
        imageView2 = findViewById(R.id.imageView4);
        imageView3 = findViewById(R.id.imageView5);
        imageView4 = findViewById(R.id.imageView6);
        imageView5 = findViewById(R.id.imageView7);
        imageView6 = findViewById(R.id.imageView8);
        imageView7 = findViewById(R.id.imageView9);
        imageView8 = findViewById(R.id.imageView10);

        imageView0.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        statusUpdate=playerOne.getText().toString();
        status.setText(statusUpdate+"'s Turn");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame(v);
            }

        });
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.winner_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         Close = dialog.findViewById(R.id.tab);
         winner = dialog.findViewById(R.id.win);
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }

    @Override
    public void onClick(View view) {
        ImageView img = (ImageView) view;
        int tappedImg = Integer.parseInt(img.getTag().toString());
        if(gameState[tappedImg]==2){
            draw++;
            mediaPlayer.start();
            gameState[tappedImg]=active_player;
            img.setTranslationY(-1000f);
            if(active_player==0){
                img.setImageResource(R.drawable.o);
                active_player=1;
                playerTwo.setTextColor(getResources().getColor(R.color.my));
                playerOne.setTextColor(getResources().getColor(R.color.black));
                statusUpdate = playerTwo.getText().toString();
                status.setText(statusUpdate+"'s Turn");

            }
            else {
                mediaPlayer.start();
                img.setImageResource(R.drawable.x);
                active_player=0;
                playerOne.setTextColor(getResources().getColor(R.color.my));
                playerTwo.setTextColor(getResources().getColor(R.color.black));
                statusUpdate = playerOne.getText().toString();
                status.setText(statusUpdate+"'s Turn");

            }
             img.animate().translationYBy(1000f).setDuration(300);
            //check if any player has won...

            for(int[] winPosition: winPositions) {



//
                if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {

                    if (gameState[winPosition[0]] == 0) {
                        statusUpdate = playerOne.getText().toString();
                        status.setText(statusUpdate + " you won the match");
                        mediaPlayer2.start();
                        winner.setText(statusUpdate);
                        flag = true;
                        dialog.show();
                        Toast.makeText(MainActivity.this, "Please reset the game to play again ", Toast.LENGTH_SHORT).show();


                    } else if (gameState[winPosition[1]] == 1) {
                        statusUpdate = playerTwo.getText().toString();
                        winner.setText(statusUpdate);
                        mediaPlayer2.start();
                        dialog.show();
                        flag = true;
                        status.setText(statusUpdate + " you won the match");
                        Toast.makeText(MainActivity.this, "Please reset the game to play again ", Toast.LENGTH_SHORT).show();
                    }


                }
                else if (draw==9){
                    Toast.makeText(MainActivity.this,"Reset the game to play again............",Toast.LENGTH_SHORT).show();
                    status.setText("---DRAW---");
                }


            }
            
        }
    }

    public void resetGame(View v) {
        mediaPlayer1.start();
        imageView0.setImageResource(0);
        imageView1.setImageResource(0);
        imageView2.setImageResource(0);
        imageView3.setImageResource(0);
        imageView4.setImageResource(0);
        imageView5.setImageResource(0);
        imageView6.setImageResource(0);
        imageView7.setImageResource(0);
        imageView8.setImageResource(0);
        statusUpdate = playerOne.getText().toString();
        status.setText(statusUpdate + " Turn");
        playerOne.setTextColor(getResources().getColor(R.color.black));
        playerTwo.setTextColor(getResources().getColor(R.color.black));
        for(int i=0; i< gameState.length;i++){
            gameState[i]=2;

        }
        draw=0;

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notify";
            String description = "notify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}