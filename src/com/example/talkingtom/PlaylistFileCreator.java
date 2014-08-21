package com.example.talkingtom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.talkingtom.utils.Utils;

import android.content.Context;
import android.util.Log;

public class PlaylistFileCreator {
	
	private JSONArray mJsonArr;
	private Context mContext;
	private String mFileName;
	private String mFileExtension = ".json";
	
	public PlaylistFileCreator(JSONArray jsonArr, Context context){
		
		mJsonArr = jsonArr;
		mContext = context;
	}
	
	public PlaylistFileCreator(){
		
	}
	
	public void createPlaylistFile(){
		
		try {
			mFileName = mJsonArr.getJSONObject(0).get(Utils.MP3_PLAYLIST_NAME) + mFileExtension;
			Log.d("ReadingData", mFileName);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(mContext.getFilesDir(), mFileName);
		Log.d("ReadingData", mContext.getFilesDir().toString());
		try {
			
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream fileOutStream = new FileOutputStream(file);
			fileOutStream.write(mJsonArr.toString().getBytes());
			fileOutStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readFile(String fileName, Context context){
		
		Log.d("ReadingFile", fileName);
		
		if(mContext != null){
			context = mContext;
		}
		
		File file = new File(context.getFilesDir(), fileName);
		
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text.toString();
	}
}
