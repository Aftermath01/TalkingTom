package com.example.talkingtom.concur;

import android.util.Log;
import android.widget.SeekBar;

import com.example.talkingtom.helpers.MediaPlayerCustom;

public class SeekBarProgress implements Runnable {
	
	private SeekBar mSeekBar;
	private MediaPlayerCustom mMediaPlayer;
	private volatile boolean running;
	private volatile boolean isPaused;
	private int mSetProgress;
	
	public SeekBarProgress(SeekBar seekBar, MediaPlayerCustom mediaPlayer){
		mSeekBar = seekBar;
		mMediaPlayer = mediaPlayer;
		running = false;
		isPaused = false;
	}

    public void terminate() {
    	Log.d("Test Insert", "Thread Treminated!");
        running = false;
    }
    
    public void start(){
		running = true;
    }
    
    public boolean isRunning(){
    	return running;
    }
    
    public void pausePlaying(){
    	isPaused = true;
    }
	
    public void resumePlaying(){
    	isPaused = false;
    }
    
    public boolean isPaused(){
    	return isPaused;
    }
    
	@Override
	public void run() {
		
		while (running) {
	    	
	    	
	    	if(!isPaused){
	    		mSeekBar.setMax(mMediaPlayer.getDuration());
	    		mSetProgress = mMediaPlayer.getCurrentPosition();
	    		Log.d("Test Insert",  "" + mSetProgress);
		    	mSeekBar.setProgress(mSetProgress);
	    	}
	    	
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			    running = false;
	            return;
			}
	        
	        if(running == false){
	            return;
			}
        }
	}

}
