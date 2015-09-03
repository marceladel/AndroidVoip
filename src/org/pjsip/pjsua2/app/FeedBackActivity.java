package org.pjsip.pjsua2.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class FeedBackActivity extends Activity implements OnClickListener {
	EditText feedback;
	Button done;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_feed_back);
		initializeViews();
		
		
	}
	
	public void initializeViews()
	{
		feedback=(EditText)findViewById(R.id.feedback);
		done=(Button)findViewById(R.id.done);
		done.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent gotoMain=new Intent(getApplicationContext(), MainActivity.class);
		startActivity(gotoMain);
		
	}

	
}
