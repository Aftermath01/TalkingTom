package com.example.talkingtom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.talkingtom.helpers.Mp3Helper;
import com.example.talkingtom.jsonparser.JSONParser;

public class PlayMusic extends Activity{

	private ImageButton mPlayPauseSongButton;
	private Button mNextSongButton;
	private List<Mp3Helper> mp3List;
	private MediaPlayer mMediaPlyer;
	private JSONParser readMp3List;
	private int mSongCounter = 0;
	private Uri contentUri;
	private Context mContext;
	
	private boolean isMusicPlaying = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);
		initializeVariables();
		
		mp3List = readMp3List.parseFromJson("hate.json", this);
		
		contentUri = Uri.parse(mp3List.get(0).getFilePath());
		
		mMediaPlyer = MediaPlayer.create(this, contentUri );
		
		mPlayPauseSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mMediaPlyer.isPlaying()){
					mMediaPlyer.pause();
				}else{
					mMediaPlyer.start();
				}
				
			}
		});
		
		mNextSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.d("Size", String.valueOf(mp3List.size()));
				
				mSongCounter++;
				Log.d("Size", String.valueOf(mSongCounter));
				if(mSongCounter == mp3List.size()){
					mSongCounter = 0;
					Log.d("Size", String.valueOf(mSongCounter));
				}
				
				if(mMediaPlyer.isPlaying()){
					startNextSong();
					
				}else{
					nextSong();
					
				}
				
			}
		} );
	}

	
	private void initializeVariables(){
		mPlayPauseSongButton = (ImageButton) findViewById(R.id.play_button);
		mNextSongButton = (Button) findViewById(R.id.next_song_button);
		mp3List = new ArrayList<Mp3Helper>();
		readMp3List = new JSONParser();
		mContext = this;
	}
	
	private void startNextSong(){
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
	
	private void nextSong(){
		
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
}
