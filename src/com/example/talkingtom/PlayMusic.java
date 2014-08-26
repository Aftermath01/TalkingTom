package com.example.talkingtom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.example.talkingtom.helpers.Mp3Helper;
import com.example.talkingtom.jsonparser.JSONParser;

public class PlayMusic extends Activity{

	private Button mPlayPauseSongButton;
	private Button mNextSongButton;
	private Button mPreviousSongButton;
	
	private List<Mp3Helper> mp3List;
	private MediaPlayer mMediaPlyer;
	private JSONParser readMp3List;
	private int mSongCounter;
	private Uri contentUri;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);
		initializeVariables();
		
		//TODO Make a listview with all of the playlists available 
		
		mp3List = readMp3List.parseFromJson("hate.json", this);
		
		contentUri = Uri.parse(mp3List.get(0).getFilePath());
		
		mMediaPlyer = MediaPlayer.create(this, contentUri );
		
		mPlayPauseSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playPauseButton();
			}
		});
	
		mNextSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nextSong();
			}
		} );
		
		mPreviousSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				previousSong();
			}
		});
		
		
	}
	
	private void initializeVariables(){
		mPlayPauseSongButton = (Button) findViewById(R.id.play_button);
		mNextSongButton = (Button) findViewById(R.id.forward_button);
		mPreviousSongButton = (Button) findViewById(R.id.backward_button);
		
		mp3List = new ArrayList<Mp3Helper>();
		readMp3List = new JSONParser();
		
		mSongCounter = 0;
		
	}
	
	private void changeAndStartSong(){
		mMediaPlyer.stop();
		mMediaPlyer.reset();
		
		try {
			
			mMediaPlyer.setDataSource(mp3List.get(mSongCounter).getFilePath());
			mMediaPlyer.prepare();
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mMediaPlyer.start();
	}
	
	private void changeSong(){
		
		try {
			
			mMediaPlyer.reset();
			mMediaPlyer.setDataSource(mp3List.get(mSongCounter).getFilePath());
			mMediaPlyer.prepare();
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void nextSong(){
		mSongCounter++;

		if(mSongCounter == mp3List.size()){
			mSongCounter = 0;
		}
		
		if(mMediaPlyer.isPlaying()){
			changeAndStartSong();
			
		}else{
			changeSong();
			
		}
	}
	
	private void previousSong(){
		mSongCounter--;
		
		if(mSongCounter < 0){
			mSongCounter = mp3List.size() - 1;
		}
		
		if(mMediaPlyer.isPlaying()){
			changeAndStartSong();
		}else{
			changeSong();
		}
	}
	
	private void playPauseButton(){
		
		Drawable resourcePlayButton =  getResources().getDrawable(R.drawable.button_play);
		Drawable resourcePauseButton = getResources().getDrawable(R.drawable.button_pause);
		
		if(mMediaPlyer.isPlaying()){
			Log.d("Boolean Test", "IN!!!!");
			mMediaPlyer.pause();
			mPlayPauseSongButton.setBackground(resourcePlayButton);
		}else{
			mMediaPlyer.start();
			mPlayPauseSongButton.setBackground(resourcePauseButton);
		}
	}
	
}
