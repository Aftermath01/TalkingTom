package com.example.talkingtom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mCreatePlaylistButton;
	private Context mContext;
	Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		initializeVariables();
		
		mCreatePlaylistButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(mContext, CreatePlaylist.class);
				startActivity(mIntent);
			}
		});
	}

	private void initializeVariables(){
		mCreatePlaylistButton = (Button) findViewById(R.id.create_playlist_button);
		mContext = this;
	}
}
