package com.example.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPlayPause, btnStop, btnNext, btnBack;
    private ImageView songImageView;
    private TextView songNameTextView;
    private List<Song> songs = new ArrayList<>();
    private int currentSongIndex = 0;
    private AtomicReference<MediaPlayer> mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initializeUI();
        createSongList();
        setupMediaPlayer();
        setupListeners();
        updateSongDisplay();
    }

    private void initializeUI() {
        btnPlayPause = findViewById(R.id.playPauseButton);
        btnStop = findViewById(R.id.stopButton);
        btnNext = findViewById(R.id.nextButton);
        btnBack = findViewById(R.id.backButton);
        songImageView = findViewById(R.id.songImageView);
        songNameTextView = findViewById(R.id.songNameTextView);
    }

    private void createSongList() {
        songs.add(new Song("After Hours", R.raw.after_hours, R.drawable.after_hours_image));
        songs.add(new Song("Birds Of Feather", R.raw.birds_of_feather, R.drawable.birds_of_feather));
    }

    private void setupMediaPlayer() {
        mediaPlayer = new AtomicReference<>(MediaPlayer.create(MainActivity.this, songs.get(currentSongIndex).getAudioResId()));
    }

    private void setupListeners() {
        btnPlayPause.setOnClickListener(view -> togglePlayPause());
        btnStop.setOnClickListener(view -> stopPlayback());
        btnNext.setOnClickListener(view -> playNextSong());
        btnBack.setOnClickListener(view -> playPreviousSong());
    }

    private void togglePlayPause() {
        if (mediaPlayer.get().isPlaying()) {
            mediaPlayer.get().pause();
            btnPlayPause.setImageResource(R.drawable.play);
        } else {
            mediaPlayer.get().start();
            btnPlayPause.setImageResource(R.drawable.pause);
        }
    }

    private void stopPlayback() {
        if (mediaPlayer.get().isPlaying()) {
            mediaPlayer.get().stop();
            mediaPlayer.get().release();
            setupMediaPlayer();
            btnPlayPause.setImageResource(R.drawable.play); // Reset to play icon
        }
    }

    private void playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % songs.size();
        switchSong();
    }

    private void playPreviousSong() {
        currentSongIndex = (currentSongIndex - 1 + songs.size()) % songs.size();
        switchSong();
    }

    private void switchSong() {
        mediaPlayer.get().stop();
        mediaPlayer.get().release();
        mediaPlayer.set(MediaPlayer.create(MainActivity.this, songs.get(currentSongIndex).getAudioResId()));
        updateSongDisplay();
        mediaPlayer.get().start();
        btnPlayPause.setImageResource(R.drawable.pause);
    }

    private void updateSongDisplay() {
        songImageView.setImageResource(songs.get(currentSongIndex).getImageResId());

        songNameTextView.setText(songs.get(currentSongIndex).getName());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.get() != null) {
            mediaPlayer.get().release();
        }
    }
}
