package de.atrocty.loern;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener, Runnable
{
	public long fileSize = 0;
	public int mode = 0, punkte = 0, zeit = 1200;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Button b = (Button) findViewById(R.id.answer1);
		mode = getIntent().getIntExtra("Mode", 0);
		//b.setText(String.valueOf(mode));
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.answer2);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.answer3);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.answer4);
		b.setOnClickListener(this);
		b = (Button) findViewById(R.id.answer5);
		b.setOnClickListener(this);
		
		startRound();
	}

	public void startRound()
	{
		handler.postDelayed(this,50);
	}
	
	public interface Runnable 
	{
		public void run();
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
		if (mode == 3)
		{
			TextView tvZeit = (TextView)findViewById(R.id.zeit);
			tvZeit.setText(Integer.toString((zeit/20)));
					
			ProgressBar progressbar1 = (ProgressBar)findViewById(R.id.progressBar1);
			progressbar1.setVisibility(View.VISIBLE);
		}	
		TextView tvPunkte = (TextView)findViewById(R.id.punkte);
		tvPunkte.setText(Integer.toString(punkte));
	}
	
	public void countdown()
	{
		zeit -= 1;
		refresh();
		handler.postDelayed(this,50);
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId( )== R.id.answer1) 
		{
			punkte += 1;			
		}
		if(v.getId() == R.id.answer2) 
		{
			punkte += 2;
		}
		if(v.getId() == R.id.answer3) 
		{
			punkte += 3;
		}
		if(v.getId() == R.id.answer4) 
		{
			punkte += 4;
		}
		if(v.getId() == R.id.answer5) 
		{
			punkte += 5;
			zeit += 200;
		}
		
	}
	
	@Override
	public void run() 
	{
		countdown();
	}
	
}
