package org.pjsip.pjsua2.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LauncherActivity extends Activity {
	Button startButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		startButton=(Button)findViewById(R.id.start);
		startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent gotoMainPage=new Intent(getApplicationContext(),MainActivity.class);
				startActivity(gotoMainPage);
				
			}
		});
	}

	
}
