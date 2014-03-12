package de.atrocty.loern;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener 
{
	public int mode = 0, punkte = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Button b = (Button) findViewById(R.id.answer1);
		mode = getIntent().getIntExtra("Mode", 0);
		b.setText(String.valueOf(mode));
		b.setOnClickListener(this);
		refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void refresh()
	{
		TextView tvPunkte = (TextView)findViewById(R.id.punkte);
		tvPunkte.setText(Integer.toString(punkte));
	}

	public void bla()
	{
		
	}
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
	}
	
}
