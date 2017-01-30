package com.example.iratxe.rendecine;

/**
 * Created by aitor on 28/01/17.
 */
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;

import java.io.IOException;

public class AudioPlayer implements MediaController.MediaPlayerControl, MediaPlayer.OnPreparedListener {

    private View view;
    private MediaPlayer player;
    private MediaController controller;

    public AudioPlayer(View view){
        this.view=view;
        player=new MediaPlayer();
        player.setOnPreparedListener(this);
        controller=new MediaController(view.getContext()){

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    release();
                }
                return super.dispatchKeyEvent(event);
            }
        };

    }

    public void setAudioUri(Uri uri) throws IOException{

        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(view.getContext(),uri);
        player.prepare();
        player.start();
    }

    public void release(){
        if(player!=null){
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        controller.setMediaPlayer(this);
        controller.setAnchorView(view);
        controller.show(0);
    }

    @Override
    public void start() {
        player.start();
    }

    @Override
    public void pause() {

        player.pause();
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    public void quitar(){
        controller.hide();
    }
}

