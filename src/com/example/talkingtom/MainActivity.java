package com.example.talkingtom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.example.talkingtom.adapters.SongListViewAdapter;
import com.example.talkingtom.helpers.Mp3Helper;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	
	private String[] mEverything = {"*"};
	private ListView songListView;
	private List<Mp3Helper> mListOfMp3;
	private Mp3Helper mMp3;
	private List<Mp3Helper> mListOfCheckedMp3;
	private Button mAddToPlaylistButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeVariables();
		listAllSongs();
		
		SongListViewAdapter arrayAdapter = new SongListViewAdapter(this, android.R.layout.simple_list_item_1, mListOfMp3);
		
		if(arrayAdapter != null && songListView != null){
			songListView.setAdapter(arrayAdapter);
		}
		
		mAddToPlaylistButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}
	
	private void listAllSongs(){
		Cursor cursor;
		Uri allSongsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 ";
		ContentResolver resolver = getContentResolver();
		cursor = resolver.query(allSongsUri, mEverything, selection, null, null);
		
		if (cursor != null){
			if(cursor.moveToFirst()){
				do{
					
					mMp3 = new Mp3Helper();
					mMp3.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
					mMp3.setAuthor(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
					mMp3.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
					mMp3.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
					
					mListOfMp3.add(mMp3);

				}while (cursor.moveToNext());
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int pos = songListView.getPositionForView(buttonView);
		
		if(isChecked == true){
			mListOfCheckedMp3.add(mListOfMp3.get(pos));
		}else {
			mListOfCheckedMp3.remove(mListOfMp3.get(pos));
		}
	}
	
	private void initializeVariables(){
		songListView = (ListView) findViewById(R.id.song_container_listView);
		
		mListOfMp3 = new ArrayList<Mp3Helper>();
		mListOfCheckedMp3 = new ArrayList<Mp3Helper>();
		mAddToPlaylistButton = (Button) findViewById(R.id.add_to_playlist_Button);
		
	}
}
