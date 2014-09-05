package com.example.talkingtom.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class PlaylistOptions {

	ContentResolver mResolver;
	
	
	public PlaylistOptions(ContentResolver cResolver){
		mResolver = cResolver;
	}
	
	public void createPlaylist(String playlistName){
		
		final Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
		
		//Check to see if playlist exists already, if not a new playlist is created with the 
		//given playlistName passed to this method
		
			if(getPlaylistId(playlistName) == 0){
				
				Log.d("Test Insert", String.valueOf(getPlaylistId(playlistName)));
				ContentValues contVal = new ContentValues();
				contVal.put(MediaStore.Audio.Playlists.NAME, playlistName);
				
				mResolver.insert(uri, contVal);
			}
			
	}
	
	public void addSongToPlaylist(String playlistName, List<Mp3Helper> mp3List){
		
		long playlistId = getPlaylistId(playlistName);
		
		int base = 0;
		int counter = 0;
		
		Uri uriPlaylistMembers =  MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);
		String[] projection = new String[] { MediaStore.Audio.Playlists.Members.PLAY_ORDER };
		
		
		Cursor cursor = mResolver.query(uriPlaylistMembers, projection, null, null, null);
		
		//Set to the highest play order, because it may create a conflict with the already existing order
		if(cursor.getCount() != 0){
			
			if (cursor.moveToFirst()) {
				
			   do {
				   
				   base = cursor.getInt(0) + 1;
				   
			   } while (cursor.moveToNext());
				   
			}
		}
		
		cursor.close();
		
		// Add the songs to the list 
		ContentValues[] listOfSongsContentValue = new ContentValues[mp3List.size()];
		ContentValues songContentValue;
		
		for(Mp3Helper mp3 : mp3List){
			songContentValue = new ContentValues();
			
			songContentValue.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, base);
			songContentValue.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, Integer.parseInt(mp3.getId()));
			
			listOfSongsContentValue[counter] = songContentValue;
			
			counter++;
			base++;
		}
		
		mResolver.bulkInsert(uriPlaylistMembers, listOfSongsContentValue);
	}
	
	
	private int getPlaylistId(String playlistName){
		
		
		final String[] PROJECTION_PLAYLIST = new String[]{MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME};
		final String SELECTION_PLAYLIST = MediaStore.Audio.Playlists.NAME + " = ? ";
		final Uri uriPlaylist = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
		
		int playlistId = 0;
		
		Cursor cursor = mResolver.query(uriPlaylist, PROJECTION_PLAYLIST, SELECTION_PLAYLIST, new String[]{playlistName}, null);
		
		if (cursor.moveToFirst()) {
			
			   do {
				   playlistId = cursor.getInt(0);
			   } while (cursor.moveToNext());
			   
		}
		
		cursor.close();
		
		return playlistId;
	}
	
	public List<Mp3Helper> getPlaylist(String playlistName){
		
		final Uri uriPlaylist = MediaStore.Audio.Playlists.Members.getContentUri("external", getPlaylistId(playlistName));
		final String[] PROJECTION_PLAYLIST = new String[]{MediaStore.Audio.Playlists.Members.AUDIO_ID,
				MediaStore.Audio.Playlists.Members.ARTIST,
				MediaStore.Audio.Playlists.Members.TITLE,
				MediaStore.Audio.Playlists.Members.ALBUM,
				MediaStore.Audio.Playlists.Members.DURATION,
				MediaStore.Audio.Playlists.Members.DATA};
		
		List<Mp3Helper> mp3List = new ArrayList<Mp3Helper>();
		
		Cursor cursor = mResolver.query(uriPlaylist, PROJECTION_PLAYLIST, null, null, null);
		
		if(cursor.moveToFirst()){
			do{
				
				mp3List.add(setMp3(cursor));
				
			}while(cursor.moveToNext());
		}
		
		return mp3List;
	}
	
	private Mp3Helper setMp3(Cursor cursor){
		Mp3Helper mp3 = new Mp3Helper();
		
		mp3.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID)));
		mp3.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE)));
		mp3.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ARTIST)));
		mp3.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM)));
		mp3.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.DURATION)));
		mp3.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.DATA)));
		
		return mp3;
		
	}
	
}
