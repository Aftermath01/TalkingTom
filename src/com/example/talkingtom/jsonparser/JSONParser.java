package com.example.talkingtom.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.talkingtom.PlaylistFileCreator;
import com.example.talkingtom.helpers.Mp3Helper;
import com.example.talkingtom.utils.Utils;

public class JSONParser {

	private List<Mp3Helper> mMp3List;
	private JSONObject mTitleJsonObj;
	private JSONObject mArtistJsonObj;
	private JSONObject mAlbumJsonObj;
	private JSONObject mDurationJsonObj;
	private JSONObject mPathJsonObj;
	private JSONObject mPlaylistName;
	
	public JSONParser(List<Mp3Helper> mp3List, String playlistName){
		initializeVariables();
		setPlaylistName(playlistName);
		mMp3List = mp3List;
	}
	
	public JSONParser(){
	
	}
	
	private void initializeVariables(){
		mPlaylistName = new JSONObject();
		mMp3List = new ArrayList<Mp3Helper>();
	}
	
	public JSONArray parseToJson(){
		JSONArray outerJsonArr = new JSONArray();
		JSONArray innerJsonArr;
		
			outerJsonArr.put(mPlaylistName);
			
			// TODO REFACTOR
			for(Mp3Helper mp3 : mMp3List){
				innerJsonArr = new JSONArray();
				
				mTitleJsonObj = new JSONObject();
				mArtistJsonObj = new JSONObject();
				mAlbumJsonObj = new JSONObject();
				mDurationJsonObj = new JSONObject();
				mPathJsonObj = new JSONObject();
				
				try {
					mTitleJsonObj.put(Utils.MP3_TITLE, mp3.getTitle());
					mArtistJsonObj.put(Utils.MP3_ARTIS, mp3.getAuthor());
					mAlbumJsonObj.put(Utils.MP3_ALBUM, mp3.getAlbum());
					mDurationJsonObj.put(Utils.MP3_DURATION, mp3.getDuration());
					mPathJsonObj.put(Utils.MP3_PATH, mp3.getFilePath());
					
					innerJsonArr.put(mTitleJsonObj);
					innerJsonArr.put(mArtistJsonObj);
					innerJsonArr.put(mAlbumJsonObj);
					innerJsonArr.put(mDurationJsonObj);
					innerJsonArr.put(mPathJsonObj);
				
				} catch (JSONException e) {
					e.printStackTrace();
				}
				outerJsonArr.put(innerJsonArr);
			}
		
		return outerJsonArr;
	}
	
	public List<Mp3Helper> parseFromJson(String fileNamePlaylist, Context context){
		
		PlaylistFileCreator fileCreator = new PlaylistFileCreator();
		
		List<Mp3Helper> mp3List = new ArrayList<Mp3Helper>();
		Mp3Helper mp3 = new Mp3Helper();

		try {
			
			JSONArray outerJsonArr = new JSONArray(fileCreator.readFile(fileNamePlaylist, context));
			
			for(int jsonIndex = 1; jsonIndex < outerJsonArr.length(); jsonIndex++){
				
				mp3 = new Mp3Helper();
				
				mp3.setTitle(outerJsonArr.getJSONArray(jsonIndex).getJSONObject(0).get(Utils.MP3_TITLE).toString());
				mp3.setAuthor(outerJsonArr.getJSONArray(jsonIndex).getJSONObject(1).get(Utils.MP3_ARTIS).toString());
				mp3.setAlbum(outerJsonArr.getJSONArray(jsonIndex).getJSONObject(2).get(Utils.MP3_ALBUM).toString());
				mp3.setDuration(outerJsonArr.getJSONArray(jsonIndex).getJSONObject(3).get(Utils.MP3_DURATION).toString());
				mp3.setFilePath(outerJsonArr.getJSONArray(jsonIndex).getJSONObject(4).get(Utils.MP3_PATH).toString());
				
				mp3List.add(mp3);
			} 
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return mp3List;
	}
	
	private void setPlaylistName(String playlistName){
		
		try {
			
			mPlaylistName.put(Utils.MP3_PLAYLIST_NAME, playlistName);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}
