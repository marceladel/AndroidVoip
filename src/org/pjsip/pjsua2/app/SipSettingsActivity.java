package org.pjsip.pjsua2.app;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.StringVector;
import org.pjsip.pjsua2.pjsip_status_code;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SipSettingsActivity extends Activity implements MyAppObserver,
		OnClickListener {
	EditText id;
	EditText username;
	EditText password;
	EditText registrar;
	EditText proxy;
	Button save;
	Button cancel;
	TextView laststatus;

	String idValue;
	String regValue;
	String passwordValue;
	String usernameValue;
	String proxyValue;
	private String lastRegStatus = "";
	public static MyApp app = null;
	public static MyCall currentCall = null;
	public static MyAccount account = null;
	public static AccountConfig accCfg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sip_settings);
		initializeViews();

		if (app == null) {
			app = new MyApp();
			// Wait for GDB to init, for native debugging only
			if (false && (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
			}

			app.init(SipSettingsActivity.this, getFilesDir().getAbsolutePath());
		}

		if (app.accList.size() == 0) {
			accCfg = new AccountConfig();
			accCfg.setIdUri("sip:localhost");
			accCfg.getNatConfig().setIceEnabled(true);
			accCfg.getVideoConfig().setAutoTransmitOutgoing(true);
			accCfg.getVideoConfig().setAutoShowIncoming(true);
			account = app.addAcc(accCfg);
		} else {
			account = app.accList.get(0);
			accCfg = account.cfg;
		}

		if (lastRegStatus.length() != 0) {
			laststatus.setText("Last status: " + lastRegStatus);
		}

		id.setText(accCfg.getIdUri());
		registrar.setText(accCfg.getRegConfig().getRegistrarUri());

		StringVector proxies = accCfg.getSipConfig().getProxies();
		if (proxies.size() > 0)
			proxy.setText(proxies.get(0));
		else
			proxy.setText("");
		AuthCredInfoVector creds = accCfg.getSipConfig().getAuthCreds();
		if (creds.size() > 0) {
			username.setText(creds.get(0).getUsername());
			password.setText(creds.get(0).getData());
		} else {
			username.setText("");
			password.setText("");
		}

	} // end of oncreat

	public void initializeViews() {
		id = (EditText) findViewById(R.id.id);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		registrar = (EditText) findViewById(R.id.registrar);
		laststatus = (TextView) findViewById(R.id.laststatus);
		proxy = (EditText) findViewById(R.id.proxy);
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.save) {
			idValue = id.getText().toString();
			usernameValue = username.getText().toString();
			passwordValue = password.getText().toString();
			regValue = registrar.getText().toString();
			proxyValue = proxy.getText().toString();

			Log.d("values", "id is:" + idValue + " " + "registrar is: "
					+ regValue + " " + "username is :" + usernameValue + " "
					+ "password is : " + passwordValue);

			accCfg.setIdUri(idValue);
			accCfg.getRegConfig().setRegistrarUri(regValue);
			AuthCredInfoVector creds = accCfg.getSipConfig().getAuthCreds();
			creds.clear();
			if (username.length() != 0) {
				creds.add(new AuthCredInfo("Digest", "*", usernameValue, 0,
						passwordValue));
			}
			StringVector proxies = accCfg.getSipConfig().getProxies();
			proxies.clear();
			if (proxyValue.length() != 0) {
				proxies.add(proxyValue);
			}

			/* Enable ICE */
			accCfg.getNatConfig().setIceEnabled(true);

			/* Finally */
			lastRegStatus = "";
			try {
				account.modify(accCfg);
			} catch (Exception e) {
			}

		}

	}

	@Override
	public void notifyRegState(pjsip_status_code code, String reason,
			int expiration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyIncomingCall(MyCall call) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyCallState(MyCall call) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyCallMediaState(MyCall call) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyBuddyState(MyBuddy buddy) {
		// TODO Auto-generated method stub
		
	}
}
