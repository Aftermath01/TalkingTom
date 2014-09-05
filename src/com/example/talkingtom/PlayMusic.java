package com.example.talkingtom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.talkingtom.concur.SeekBarProgress;
import com.example.talkingtom.helpers.MediaPlayerCustom;
import com.example.talkingtom.helpers.Mp3Helper;
import com.example.talkingtom.helpers.PlaylistOptions;

public class PlayMusic extends Activity{

	private Button mPlayPauseSongButton;
	private Button mNextSongButton;
	private Button mPreviousSongButton;
	private SeekBar mSeekBar;
	
	private PlaylistOptions playlistOptions;
	private MediaPlayerCustom mMediaPlayer;
	
	private List<Mp3Helper> mp3List;
	private int mSeekBarProgress;
	private Thread mSeekBarThread;
	private SeekBarProgress seekBarRunnable;
	private boolean mStopProgress = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);
		initializeVariables();
		
		//TODO Make a listview with all of the playlists available 
		
		mPlayPauseSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMediaPlayer.playPause(mPlayPauseSongButton, getResources());
			}
		});
	
		mNextSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMediaPlayer.nextSong();
				mSeekBar.setProgress(0);
			}
		} );
		
		mPreviousSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMediaPlayer.previousSong();
				mSeekBar.setProgress(0);
			}
		});
		
		
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mMediaPlayer.seekTo(mSeekBarProgress);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mSeekBarProgress = progress;
			}
		});
	}
	
	private void initializeVariables(){
		mPlayPauseSongButton = (Button) findViewById(R.id.play_button);
		mNextSongButton = (Button) findViewById(R.id.forward_button);
		mPreviousSongButton = (Button) findViewById(R.id.backward_button);
		mSeekBar = (SeekBar) findViewById(R.id.player_seekbar);
		
		
		playlistOptions = new PlaylistOptions(getContentResolver());
		
		mSeekBarProgress = 0;
		mp3List = new ArrayList<Mp3Helper>();
		mp3List = playlistOptions.getPlaylist("relax");
		
		mMediaPlayer = new MediaPlayerCustom(mp3List, this);
		
		seekBarRunnable = new SeekBarProgress(mSeekBar, mMediaPlayer);
		
		mMediaPlayer.setSeekBarRunnable(seekBarRunnable);
	}
	
}
