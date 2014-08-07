package com.example.talkingtom.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.talkingtom.MainActivity;
import com.example.talkingtom.R;
import com.example.talkingtom.helpers.Mp3Helper;

public class SongListViewAdapter extends ArrayAdapter{
	
	private int mResource;
	private Context mContext;
	private int mPosition;
	private List<Mp3Helper> mSongList;
	
	public SongListViewAdapter(Context context, int resource, List<Mp3Helper> objects) {
		super(context, resource, objects);
		mResource = resource;
		mContext = context;
		mSongList = new ArrayList<Mp3Helper>();
		mSongList = objects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public int getPosition(Object item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.listview_adapter_layout, null);
		
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.song_select_CheckBox);
		TextView songNameTextView = (TextView) view.findViewById(R.id.song_name_TextView);
		
		songNameTextView.setText(mSongList.get(position).getTitle());
		
		Log.d("CheckForNull", mSongList.get(position).getTitle());
		
		checkBox.setOnCheckedChangeListener( (MainActivity) mContext);
				
		return view;
	}

}
