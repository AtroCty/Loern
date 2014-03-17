package de.atrocty.loern;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.atrocty.loern.AutoResizeTextView;

public class GameActivity extends Activity implements OnClickListener, Runnable
{
	public long fileSize = 0;
	public int mode = 0, punkte = 0;
	public static int ticks = 20;
	public int zeit = 60 * ticks;
	public int LoesungPosi = 0;
	public static int tick = (1000/ticks);
	public boolean nextRound = true;
	private Handler handler = new Handler();
	private Random random = new Random();
	
	String[] Frage = 
		{
		"Kategorie 1",
		"Kategorie 2",
		"Kategorie 3",
		"Kategorie 4",
		"Kategorie 5"
		};
	
	String[] Loesung = 
		{
			"Richtig",
			"Richtig",
			"Richtig",
			"Richtig",
			"Richtig",
			"Richtig",
			"Richtig"
		};
	
	String[] Falsch1 = 
		{
			"Falsch1",
			"Falsch1",
			"Falsch1",
			"Falsch1",
			"Falsch1",
			"Falsch1",
			"Falsch1"
		};
	
	String[] Falsch2 = 
		{
			"Falsch2",
			"Falsch2",
			"Falsch2",
			"Falsch2",
			"Falsch2",
			"Falsch2",
			"Falsch2"
		};
	String[] Falsch3 = 
		{
			"Falsch3",
			"Falsch3",
			"Falsch3",
			"Falsch3",
			"Falsch3",
			"Falsch3",
			"Falsch3"
		};
	String[] Falsch4 = 
		{
			"Falsch4",
			"Falsch4",
			"Falsch4",
			"Falsch4",
			"Falsch4",
			"Falsch4",
			"Falsch4"
		};
	
	String[] Schwierigkeit = 
		{
			"1",
			"2",
			"3",
			"4",
			"5"
		};
	
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
		handler.postDelayed(this,tick);
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
			tvZeit.setText(Integer.toString((zeit/ticks)));
					
			ProgressBar progressbar1 = (ProgressBar)findViewById(R.id.progressBar1);
			progressbar1.setVisibility(View.VISIBLE);
			progressbar1.setMax(60*ticks);
			progressbar1.setProgress(zeit);
		}	
		TextView tvPunkte = (TextView)findViewById(R.id.punkte);
		tvPunkte.setText(Integer.toString(punkte));
		
		if (nextRound == true)
		{
			nextRound = false;
			String[] task = getQuestion();
			String Frage = task[0];
			String[] antwort = { task[1], task[2], task[3], task[4], task[5] };
			shuffleArray(antwort);
			AutoResizeTextView tvFrage = (AutoResizeTextView)findViewById(R.id.frage);
			tvFrage.setMinTextSize(18);
			tvFrage.setTextSize(28);
			tvFrage.setText(Frage);
			AutoResizeTextView tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer1);
			tvAntwort.setMinTextSize(18);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[0]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer2);
			tvAntwort.setMinTextSize(18);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[1]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer3);
			tvAntwort.setMinTextSize(18);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[2]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer4);
			tvAntwort.setMinTextSize(18);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[3]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer5);
			tvAntwort.setMinTextSize(18);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[4]);
			
			FrameLayout LED = (FrameLayout)findViewById(R.id.difficulty);
			if (task[6] == "1")
				LED.getBackground().setColorFilter(Color.parseColor("#ff2222"), PorterDuff.Mode.DARKEN);
			if (task[6] == "2")
				LED.getBackground().setColorFilter(Color.parseColor("#ff9934"), PorterDuff.Mode.DARKEN);
			if (task[6] == "3")
				LED.getBackground().setColorFilter(Color.parseColor("#ffff00"), PorterDuff.Mode.DARKEN);
			if (task[6] == "4")
				LED.getBackground().setColorFilter(Color.parseColor("#99ff34"), PorterDuff.Mode.DARKEN);
			if (task[6] == "5")
				LED.getBackground().setColorFilter(Color.parseColor("#00cc00"), PorterDuff.Mode.DARKEN);		
		}
	}
	
	public void countdown()
	{
		zeit -= 1;
		refresh();
		handler.postDelayed(this,tick);
	}
	

	public String[] getQuestion()
	{		
		int zufall = (int) (Frage.length * random.nextFloat());
		return new String[] { Frage[zufall], Loesung[zufall], Falsch1[zufall], Falsch2[zufall], Falsch3[zufall], Falsch4[zufall], Schwierigkeit[zufall]};	
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId( )== R.id.answer1) 
		{
			if (LoesungPosi == 1)
				punkte += 5;
			nextRound = true;
		}
		if(v.getId() == R.id.answer2) 
		{
			if (LoesungPosi == 2)
				punkte += 5;
			nextRound = true;
		}
		if(v.getId() == R.id.answer3) 
		{
			if (LoesungPosi == 3)
				punkte += 5;
			nextRound = true;
		}
		if(v.getId() == R.id.answer4) 
		{
			if (LoesungPosi == 4)
				punkte += 5;
			nextRound = true;
		}
		if(v.getId() == R.id.answer5) 
		{
			
			if (LoesungPosi == 5)
				punkte += 5;
			nextRound = true;
		}
		
	}
	
	public void shuffleArray(String[] ar)
	{
	    Random rnd = new Random();
	    boolean isset = false;
	    for (int i = ar.length - 1; i > 0; i--)
	    {
		    int index = rnd.nextInt(i + 1);
		    if ((index == 0) && (isset == false))
		    {
		    	isset = true;
		    	LoesungPosi = i+1;
		    }
		    else if ((i == 1) && (isset == false))
		    {
		    	LoesungPosi = 1;
		    }
		    String a = ar[index];
		    ar[index] = ar[i];
		    ar[i] = a;
	    }
	}
	
	@Override
	public void run() 
	{
		countdown();
	}
	
}
