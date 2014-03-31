package de.atrocty.loern;

//----------------------------------------------------------------------
// Titel     : Gameactivity (Quiz-Activity)
//----------------------------------------------------------------------
// Funktion  : Starten des Quiz-Bildschirmes und des Spiels.
//----------------------------------------------------------------------
// Sprache   : Java
// Datum     : 21. Maerz 2014
// Autor     : Timm Schuette, Jannik Weihrauch, Maik Habben
//----------------------------------------------------------------------

import java.util.Random;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import de.atrocty.loern.AutoResizeTextView;

public class GameActivity extends Activity implements OnClickListener, Runnable
{
	//---------------------------------------------------------------------------
	// 	Globale Variablen
	//---------------------------------------------------------------------------
	
	public long fileSize = 0;
	public int mode = 0, punkte = 0, zufall = 0;
	public static int ticks = 20;
	public int zeit = 60 * ticks;
	public int LoesungPosi = 0;
	public static int tick = (1000/ticks);
	public boolean nextRound = true;
	private Handler handler = new Handler();
	private Random random = new Random();
	boolean answergiven = false;
	
	//---------------------------------------------------------------------------
	// 	Dummy-Fragen (Später durch Datenbank ersetzt)
	//---------------------------------------------------------------------------
	String[] Frage = 
		{
			"Komplexe Automatisierungsgeräte tauschen Nachrichten in der Aktor-Sensor-Ebene häufig nach dem Master-Slave-Verfahren mit zyklischem Poling aus. Welches besondere Merkmal ist damit verbunden?",
			"Frage 2",
		};
	
	String[] Loesung = 
		{
			"Eine garantierte gleichbleibende Abfragezykluszeit.",
			"Richtig"
		};
	
	String[] Falsch1 = 
		{
			"Jedem Sensor ist ein eigener Master zugeordnet.",
			"Falsch1"
		};
	
	String[] Falsch2 = 
		{
			"Die Sensoren übernehmen die Masterfunktion und können somit sehr schnell Daten an das Automatisierungsgerät übertragen.",
			"Falsch2"
		};
	String[] Falsch3 = 
		{
			"Es können nur Aktoren über den Bus mit Daten versorgt werden.",
			"Falsch3"
		};
	String[] Falsch4 = 
		{
			"Zwischen Master und Slave existiert eine parallele Datenverbindung.",
			"Falsch4"
		};
	
	int[] Schwierigkeit = 
		{
			3,
			3
		};
	
	String[][] Question = 
	{
		{"1","2","3","4"}, 	//ID
		//Fragen
		{
			"Komplexe Automatisierungsgeräte tauschen Nachrichten in der Aktor-Sensor-Ebene häufig nach dem Master-Slave-Verfahren mit zyklischem Poling aus. Welches besondere Merkmal ist damit verbunden?",
			"Frage2",
			"Frage3",
			"Frage4"
		},
		// Loesung
		{
			"Eine garantierte gleichbleibende Abfragezykluszeit.",
			"Richtig",
			"Richtig",
			"Richtig"
		},
		//Falsch1
		{
			"Jedem Sensor ist ein eigener Master zugeordnet.",
			"Falsch 1",
			"Falsch 1",
			"Falsch 1"
		},
		//Falsch2
		{
			"Die Sensoren übernehmen die Masterfunktion und können somit sehr schnell Daten an das Automatisierungsgerät übertragen.",
			"Falsch 2",
			"Falsch 2",
			"Falsch 2"
		},
		//Falsch3
		{
			"Es können nur Aktoren über den Bus mit Daten versorgt werden.",
			"Falsch 3",
			"Falsch 3",
			"Falsch 3",
		},
		//Falsch4
		{
			"Zwischen Master und Slave existiert eine parallele Datenverbindung.",
			"Falsch 4",
			"Falsch 4",
			"Falsch 4",
		},
		//Schwierigkeit
		{
			"3",
			"3",
			"3",
			"3"
		}
	};
	//---------------------------------------------------------------------------
	// 	Initialiserungen beim ersten Aufruf
	//---------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		// Uebergabewert aus anderer Activity
		mode = getIntent().getIntExtra("Mode", 0);	
		startRound();	
	}
	
	//---------------------------------------------------------------------------
	// 	Starten des Handlers
	//---------------------------------------------------------------------------
	
	public void startRound()
	{
		handler.postDelayed(this,tick);
	}
	
	//---------------------------------------------------------------------------
	// 	Rücksetzen der Button-Layout-Animation
	//---------------------------------------------------------------------------
	
	public void resetButtons()
	{
		Button b = null;
		for (int i = 1; i < 6; i++) 
		{
			switch (i) 
			{
			case 1:
				b = (Button) findViewById(R.id.answer1);
				break;
			case 2:
				b = (Button) findViewById(R.id.answer2);
				break;
			case 3:
				b = (Button) findViewById(R.id.answer3);
				break;
			case 4:
				b = (Button) findViewById(R.id.answer4);
				break;
			case 5:
				b = (Button) findViewById(R.id.answer5);
				break;
			default:
				break;
			}
			b.setBackgroundResource(R.drawable.wrong);
			AnimationDrawable frameAnimation = (AnimationDrawable) b.getBackground();
			frameAnimation.stop();
			frameAnimation.selectDrawable(0);
		}
	}
	
	public interface Runnable 
	{
		public void run();
	}
	
	//---------------------------------------------------------------------------
	// 	Optionsmenue (Benoetigen wir in dieser Activity nicht)
	//---------------------------------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//---------------------------------------------------------------------------
	// 	Layout-Aktualisierungen pro Tick
	//---------------------------------------------------------------------------
	
	public void refresh()
	{
		RelativeLayout fl = (RelativeLayout)findViewById(R.id.Mainmenu);
		if (answergiven == true)
		{	
			fl.setFocusable(true);
			fl.setOnClickListener(this);
			fl.setClickable(true);
			
			Button b = (Button) findViewById(R.id.answer1);
			b.setOnClickListener(null);
			b.setClickable(false);
			b = (Button) findViewById(R.id.answer2);
			b.setOnClickListener(null);
			b.setClickable(false);
			b = (Button) findViewById(R.id.answer3);
			b.setOnClickListener(null);
			b.setClickable(false);
			b = (Button) findViewById(R.id.answer4);
			b.setOnClickListener(null);
			b.setClickable(false);
			b = (Button) findViewById(R.id.answer5);
			b.setOnClickListener(null);
			b.setClickable(false);
		}
		else
		{
			fl.setFocusable(false);
			fl.setOnClickListener(null);
			fl.setClickable(false);
			
			Button b = (Button) findViewById(R.id.answer1);
			b.setOnClickListener(this);
			b.setClickable(true);
			b = (Button) findViewById(R.id.answer2);
			b.setOnClickListener(this);
			b.setClickable(true);
			b = (Button) findViewById(R.id.answer3);
			b.setOnClickListener(this);
			b.setClickable(true);
			b = (Button) findViewById(R.id.answer4);
			b.setOnClickListener(this);
			b.setClickable(true);
			b = (Button) findViewById(R.id.answer5);
			b.setOnClickListener(this);
			b.setClickable(true);
			
		}
		
		if (mode == 3)
		{					
			ProgressBar progressbar1 = (ProgressBar)findViewById(R.id.progressBar1);
			progressbar1.setVisibility(View.VISIBLE);
			progressbar1.setMax(60*ticks);
			progressbar1.setProgress(zeit);
		}	
		
		if (nextRound == true)
		{
			nextRound = false;
			String[] task = getQuestion();
			String Frage = task[0];
			String[] antwort = { task[1], task[2], task[3], task[4], task[5] };
			shuffleArray(antwort);
			
			AutoResizeTextView tvFrage = (AutoResizeTextView)findViewById(R.id.frage);
			tvFrage.setMinTextSize(12);
			tvFrage.setTextSize(28);
			tvFrage.setText(Frage);
			
			AutoResizeTextView tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer1);
			tvAntwort.setMinTextSize(11);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[0]);
			
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer2);
			tvAntwort.setMinTextSize(11);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[1]);
			
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer3);
			tvAntwort.setMinTextSize(11);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[2]);
			
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer4);
			tvAntwort.setMinTextSize(11);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[3]);
			
			tvAntwort = (AutoResizeTextView)findViewById(R.id.tanswer5);
			tvAntwort.setMinTextSize(11);
			tvAntwort.setTextSize(18);
			tvAntwort.setText(antwort[4]);
			
			FrameLayout LED = (FrameLayout)findViewById(R.id.difficulty);
			if (Schwierigkeit[zufall] == 1)
				LED.getBackground().setColorFilter(Color.parseColor("#ff2222"), PorterDuff.Mode.DARKEN);
			if (Schwierigkeit[zufall] == 2)
				LED.getBackground().setColorFilter(Color.parseColor("#ff9934"), PorterDuff.Mode.DARKEN);
			if (Schwierigkeit[zufall] == 3)
				LED.getBackground().setColorFilter(Color.parseColor("#ffff00"), PorterDuff.Mode.DARKEN);
			if (Schwierigkeit[zufall] == 4)
				LED.getBackground().setColorFilter(Color.parseColor("#99ff34"), PorterDuff.Mode.DARKEN);
			if (Schwierigkeit[zufall] == 5)
				LED.getBackground().setColorFilter(Color.parseColor("#00cc00"), PorterDuff.Mode.DARKEN);		
		}
	}
	
	public void countdown()
	{
		if (answergiven==false)
			zeit -= 1;
		refresh();
		handler.postDelayed(this,tick);
		if (zeit == 0)
		{
			gameOver();
		}
	}
	
	
	private void gameOver() 
	{
		Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.gameover);
		dialog.show();
	}

	public String[] getQuestion()
	{		
		zufall = (int) (Frage.length * random.nextFloat());
		return new String[] { Frage[zufall], Loesung[zufall], Falsch1[zufall], Falsch2[zufall], Falsch3[zufall], Falsch4[zufall]};	
	}
	
	@Override
	public void onClick(View v) 
	{		
		boolean rightanswer = false;
		Button ans = null;
		if(v.getId( )== R.id.answer1) 
		{
			if (LoesungPosi == 1)
			{
				rightAnswer();
				rightanswer = true;
			}
			else
			{
				if (Schwierigkeit[zufall]>1)
					Schwierigkeit[zufall]--;
			}
			answergiven = true;
			ans = (Button) findViewById(R.id.answer1);
		}
		if(v.getId() == R.id.answer2) 
		{
			if (LoesungPosi == 2)
			{
				rightAnswer();
				rightanswer = true;
			}
			else
			{
				if (Schwierigkeit[zufall]>1)
					Schwierigkeit[zufall]--;
			}
			answergiven = true;
			ans = (Button) findViewById(R.id.answer2);
		}
		if(v.getId() == R.id.answer3) 
		{
			if (LoesungPosi == 3)
			{
				rightAnswer();
				rightanswer = true;
			}
			else
			{
				if (Schwierigkeit[zufall]>1)
					Schwierigkeit[zufall]--;
			}
			answergiven = true;
			ans = (Button) findViewById(R.id.answer3);
		}
		if(v.getId() == R.id.answer4) 
		{
			if (LoesungPosi == 4)
			{
				rightAnswer();
				rightanswer = true;
			}
			else
			{
				if (Schwierigkeit[zufall]>1)
					Schwierigkeit[zufall]--;
			}
			answergiven = true;
			ans = (Button) findViewById(R.id.answer4);
		}
		if(v.getId() == R.id.answer5) 
		{
			if (LoesungPosi == 5)
			{
				rightAnswer();
				rightanswer = true;
			}
			else
			{
				if (Schwierigkeit[zufall]>1)
					Schwierigkeit[zufall]--;
			}
			answergiven = true;
			ans = (Button) findViewById(R.id.answer5);
		}
		
		AnimationDrawable frameAnimation = null;
		if ((rightanswer == true) && (v.getId() != R.id.Mainmenu))
			ans.setBackgroundResource(R.drawable.roundbuttonright);
		else if ((rightanswer == false) && (v.getId() != R.id.Mainmenu))
		{
			ans.setBackgroundResource(R.drawable.roundbuttonwrong);
			switch (LoesungPosi) 
			{
			case 1:
				ans = (Button) findViewById(R.id.answer1);			
				break;
			case 2:
				ans = (Button) findViewById(R.id.answer2);			
				break;
			case 3:
				ans = (Button) findViewById(R.id.answer3);			
				break;
			case 4:
				ans = (Button) findViewById(R.id.answer4);			
				break;
			case 5:
				ans = (Button) findViewById(R.id.answer5);			
				break;
			default:
				break;
			}
			ans.setBackgroundResource(R.drawable.right);
			frameAnimation = (AnimationDrawable) ans.getBackground();
		}
		if (frameAnimation != null)
			frameAnimation.start();
	    
		if((v.getId() == R.id.Mainmenu) && (answergiven == true))
		{
			answergiven = false;
			resetButtons();
			nextRound = true;
		}
		
	}
	
	public void rightAnswer()
	{
		punkte += 5;
		if (Schwierigkeit[zufall] < 5)
			Schwierigkeit[zufall]++;
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
