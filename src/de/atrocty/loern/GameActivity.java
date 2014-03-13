package de.atrocty.loern;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.atrocty.loern.AutoResizeTextView;

public class GameActivity extends Activity implements OnClickListener, Runnable
{
	public long fileSize = 0;
	public int mode = 0, punkte = 0;
	public static int ticks = 1;
	public int zeit = 60 * ticks;
	public static int tick = (1000/ticks);
	private Handler handler = new Handler();
	private Random random = new Random();
	
	String[] Frage = {
		"Was ist der Sinn des Lebens?",
		"Wie lautet das Ohmsche Gesetz?",
		"Ach was, war doch nur Spaß",
		"lol",
		"Und hier nochmal eine extra extra EXTRA schön lange Frage zum testen. Mal schauen."
	};
	
	String[] Loesung = {
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	
	String[] Falsch1 = {
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	
	String[] Falsch2 = {
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	String[] Falsch3 = {
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
		};
	String[] Falsch4 = {
			"Was ist der Sinn des Lebens?",
			"Wie lautet das Ohmsche Gesetz?",
			"Ach was, war doch nur Spaß",
			"lol",
			"Und hier nochmal eine extra extra EXTRA schön lange Antwort zum testen. Mal schauen."
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
		
		AutoResizeTextView tvFrage = (AutoResizeTextView)findViewById(R.id.frage);
		tvFrage.setText(getQuestion(0));
		
		AutoResizeTextView tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer1);
		tvAntwort.setText(getQuestion(1));
		tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer2);
		tvAntwort.setText(getQuestion(2));
		tvAntwort.setMinTextSize(20);
		tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer3);
		tvAntwort.setText(getQuestion(3));
		tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer4);
		tvAntwort.setText(getQuestion(4));
		tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer5);
		tvAntwort.setText(getQuestion(5));
	}
	
	public void countdown()
	{
		zeit -= 1;
		refresh();
		handler.postDelayed(this,tick);
	}
	

	public String getQuestion(int i)
	{		
		int zufall = (int) (Frage.length * random.nextFloat());
		switch (i) 
		{
		case 0:
			return Frage[zufall];
		case 1:
			return Loesung[zufall];
		case 2:
			return Falsch1[zufall];
		case 3:
			return Falsch2[zufall];
		case 4:
			return Falsch3[zufall];
		case 5:
			return Falsch4[zufall];
		default:
			return null;
		}
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
			zeit += 10 * ticks;
		}
		
	}
	
	@Override
	public void run() 
	{
		countdown();
	}
	
}
