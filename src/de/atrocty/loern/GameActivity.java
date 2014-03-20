package de.atrocty.loern;

import java.util.Random;

import android.app.Activity;
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
	boolean answergiven = false;
	
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
		mode = getIntent().getIntExtra("Mode", 0);	
		startRound();	
	}

	public void startRound()
	{
		handler.postDelayed(this,tick);
	}
	
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
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
		if (answergiven==false)
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
		boolean rightanswer = false;
		Button ans = null;
		if(v.getId( )== R.id.answer1) 
		{
			if (LoesungPosi == 1)
			{
				rightAnswer();
				rightanswer = true;
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
	    
		//b.getBackground().setColorFilter(Color.parseColor("#cc0000"), PorterDuff.Mode.DARKEN);
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
