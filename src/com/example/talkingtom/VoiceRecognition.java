/**
 * 
 */
package com.example.talkingtom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Aftermath
 *
 */
public class VoiceRecognition  extends Activity{
	 private static final int REQUEST_CODE = 1234;
	 private ListView resultList;
	 Button speakButton;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.voice_recognition);

	  speakButton = (Button) findViewById(R.id.speakButton);

	  resultList = (ListView) findViewById(R.id.list);

	  // Disable button if no recognition service is present
	  PackageManager pm = getPackageManager();
	  
	  List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
	    RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
	  if (activities.size() == 0) {
	   speakButton.setEnabled(false);
	   Toast.makeText(getApplicationContext(), "Recognizer Not Found",
	     1000).show();
	  }
	  speakButton.setOnClickListener(new OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    startVoiceRecognitionActivity();
	   }
	  });
	 }

	 private void startVoiceRecognitionActivity() {
	  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
	    "AndroidBite Voice Recognition...");
	  startActivityForResult(intent, REQUEST_CODE);
	 }

	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
	   ArrayList<String> matches = data
	     .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	   resultList.setAdapter(new ArrayAdapter<String>(this,
	     android.R.layout.simple_list_item_1, matches));
	  }
	  super.onActivityResult(requestCode, resultCode, data);
	 }
}
