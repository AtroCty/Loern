package de.atrocty.loern;

import java.util.Random;

import android.R.string;
import android.app.Activity;
import android.graphics.Color;
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
	public static int tick = (1000/ticks);
	public boolean nextRound = true;
	private Handler handler = new Handler();
	private Random random = new Random();
	
	String[] Frage = 
		{
		"Was ist der Sinn des Lebens?",
		"Wie lautet das Ohmsche Gesetz?",
		"Ach was, war doch nur Spaß",
		"lol",
		"Und hier nochmal eine extra extra EXTRA schön lange Frage zum testen. Mal schauen."
		};
	
	String[] Loesung = 
		{
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	
	String[] Falsch1 = 
		{
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	
	String[] Falsch2 = 
		{
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	String[] Falsch3 = 
		{
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	String[] Falsch4 = 
		{
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	
	String[] Schwierigkeit = 
		{
			"3",
			"2",
			"1",
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
			AutoResizeTextView tvFrage = (AutoResizeTextView)findViewById(R.id.frage);
			tvFrage.setMinTextSize(20);
			tvFrage.setTextSize(20);
			tvFrage.setText(task[0]);
			AutoResizeTextView tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer1);
			tvAntwort.setMinTextSize(17);
			tvAntwort.setTextSize(17);
			tvAntwort.setText(task[1]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer2);
			tvAntwort.setMinTextSize(17);
			tvAntwort.setTextSize(17);
			tvAntwort.setText(task[2]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer3);
			tvAntwort.setMinTextSize(17);
			tvAntwort.setTextSize(17);
			tvAntwort.setText(task[3]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer4);
			tvAntwort.setMinTextSize(17);
			tvAntwort.setTextSize(17);
			tvAntwort.setText(task[4]);
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer5);
			tvAntwort.setMinTextSize(17);
			tvAntwort.setTextSize(17);
			tvAntwort.setText(task[5]);
			
			FrameLayout LED = (FrameLayout)findViewById(R.id.difficulty);
			if (task[6] == "1")
				LED.setBackgroundColor(0xAAFF0000);
			if (task[6] == "2")
				LED.setBackgroundColor(0xAAFFA500);
			if (task[6] == "3")
				LED.setBackgroundColor(0xAAFFFF00);
			if (task[6] == "4")
				LED.setBackgroundColor(0xAA7FFF00);
			if (task[6] == "5")
				LED.setBackgroundColor(0xAA3CB371);		
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
			punkte += 1;
			nextRound = true;
		}
		if(v.getId() == R.id.answer2) 
		{
			punkte += 2;
			nextRound = true;
		}
		if(v.getId() == R.id.answer3) 
		{
			punkte += 3;
			nextRound = true;
		}
		if(v.getId() == R.id.answer4) 
		{
			punkte += 4;
			nextRound = true;
		}
		if(v.getId() == R.id.answer5) 
		{
			punkte += 5;
			zeit += 10 * ticks;
			nextRound = true;
		}
		
	}
	
	@Override
	public void run() 
	{
		countdown();
	}
	
}
