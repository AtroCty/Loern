package de.atrocty.loern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.button2);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.button3);
		b.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		if(v.getId( )== R.id.button1) 
		{
			
		}
		if(v.getId() == R.id.button2) 
		{
			
		}
		if(v.getId() == R.id.button3) 
		{
			
		}
		startActivity(new Intent(this, GameActivity.class));
	}
	
}
