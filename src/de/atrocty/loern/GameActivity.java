package de.atrocty.loern;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener, Runnable
{
	public int mode = 0, punkte = 0, zeit = 600;
	private float massstab;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Button b = (Button) findViewById(R.id.answer1);
		mode = getIntent().getIntExtra("Mode", 0);
		massstab = getResources().getDisplayMetrics().density;
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
		handler.postDelayed(this,100);
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
		TextView tvPunkte = (TextView)findViewById(R.id.punkte);
		tvPunkte.setText(Integer.toString(punkte));
		
		TextView tvZeit = (TextView)findViewById(R.id.zeit);
		tvZeit.setText(Integer.toString((zeit/10)));
		
		FrameLayout flZeit = (FrameLayout)findViewById(R.id.bar_time);
		
		LayoutParams lpZeit = flZeit.getLayoutParams();
		lpZeit.width = Math.round( massstab * (zeit/10) * 300 / 60 );
	}
	
	public void countdown()
	{
		zeit -= 1;
		refresh();
		handler.postDelayed(this,100);
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
		}
		
	}

	@Override
	public void run() 
	{
		countdown();
	}
	
}
