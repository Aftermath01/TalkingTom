package com.example.talkingtom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private List<String> mListOfCheckedMp3;
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
				listAllSongs();
				jsonParser();
				readFile();
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
					mListOfMp3.add(setMp3(cursor));
				}while (cursor.moveToNext());
				
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int pos = songListView.getPositionForView(buttonView);
		
		if(isChecked == true){
			mListOfCheckedMp3.add(mListOfMp3.get(pos).getFilePath());
		}else {
			mListOfCheckedMp3.remove(mListOfMp3.get(pos));
		}
	}
	
	private void initializeVariables(){
		songListView = (ListView) findViewById(R.id.song_container_listView);
		
		mListOfMp3 = new ArrayList<Mp3Helper>();
		mListOfCheckedMp3 = new ArrayList<String>();
		mAddToPlaylistButton = (Button) findViewById(R.id.add_to_playlist_Button);
		
	}
	
	private Mp3Helper setMp3(Cursor cursor){
		
		Mp3Helper mp3 = new Mp3Helper();
		mp3.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
		mp3.setAuthor(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
		mp3.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
		mp3.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
		mp3.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
		return mp3;
	}
	
	private void jsonParser(){
		
		String fileName = "myfile";
		
		JSONArray jsonArray = new JSONArray(mListOfCheckedMp3);
		
		File file = new File(this.getFilesDir(), fileName);
		try {
			
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream fileOutStream = new FileOutputStream(file);
			fileOutStream.write(jsonArray.toString().getBytes());
			fileOutStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		Log.d("CheckForNull",String.valueOf(file.exists()));
//		FileOutputStream outStream = new FileOutputStream(file);
		
	}
	
	private void readFile(){
		String fileName = "myfile";
		File file = new File(this.getFilesDir(), fileName);
		StringBuilder text = new StringBuilder();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = br.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
			
			Log.d("ReadingData", text.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
