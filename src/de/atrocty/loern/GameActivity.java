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
import android.content.SharedPreferences;
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
		
	// Spieleinstellungen
	int setZeit = 60;
	
	// Programmintern
	public long fileSize = 0;
	public int mode = 0, zufall = 0;
	public static int ticks = 20;
	public int zeit = setZeit * ticks;
	public int LoesungPosi = 0;
	public static int tick = (1000/ticks);
	public boolean nextRound = true;
	private Handler handler = new Handler();
	private Random random = new Random();
	boolean answergiven = false;
	public int currentID = 0;
	public int currentDiff = 0;
	
	//---------------------------------------------------------------------------
	// 	Dummy-Fragen (Später durch Datenbank ersetzt)
	//---------------------------------------------------------------------------
	String[][] Question = new String[][]
	{
		{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"}, 	//ID
		//Fragen
		{
			"Komplexe Automatisierungsgeräte tauschen Nachrichten in der Aktor-Sensor-Ebene häufig nach dem Master-Slave-Verfahren mit zyklischem Poling aus. Welches besondere Merkmal ist damit verbunden?",
			"In welcher Auswahlantwort ist die Variablendeklaration für die Variable Zähler zweckmäßig und auch richtig dargestellt?",
			"Bei der Programmierung des Prozessrechners wird ein Compiler eingesetzt. Wozu dient dieser?",
			"Welches besondere Merkmal besitzt ein Flashspeicher?",
			"Ein ER-Modell enthält bestimmte Informationen. Welche Antwort trifft zu?",
			"Welcher Port muss im Router geöffnet sein, um einen Zugriff auf den http-Dienst des Webserver-PCs zu ermöglichen?",
			"Bei der Übertragung über den RS485-Bus wird jedem Datentelegramm ein Paritätsbit angefügt. Wozu dienst dieses?",
			"In welcher Antwort sind ausschließlich Protokolle aufgeführt, die der Anwendungsschicht des OSI-Referenzmodells zugeordnet sind?",
			"Welches Sicherheitsziel wird mit einem RAID-System verfolgt?",
			"Was versteht man im Zusammenhang mit der Programmentwicklung unter einem Breakpoint?",
			"Welchen Vorteil hat die Anbindung über RS485 gegenüber RS232?",
			"Welchen Vorteil hat die Archivierung von Daten innerhalb einer Datenbank gegenüber der herkömmlichen Datenspeicherung?",
			"Die Archivierungsdaten werden in einem Backup-Verfahren gesichert. Welchen Vorteil bietet eine Vollsicherung gegenüber einer Sicherungskopie?",
			"Aus welchem Grund wird an der Schnittstelle zwischen einem LAN und einem WAN häufig ein Router eingesetzt?",
			"Welche Subnetzmaske gehört zu der öffentlichen IP-Adresse 217.91.85.78/30?",
			"Wessen IP-Adresse muss als Gateway in den IP-Konfigurationen des Web-Servers und des Archivierungs-PCs eingetragen werden?",
			"Auf dem UMTS-Router wird der NAT-Dienst eingesetzt. Welcher Vorteil ist damit verbunden?",
			"Die Architektur des UMTS-Netzwerks basiert auf sogenannten Funkzellen. Welche Aussage über Funkzellen ist zutreffend?",
			"Was ist zutreffend bei einem Feldbussystem?",
			"Welchen Vorteil bietet eine Stromschnittstelle gegenüber einer Spannungsschnittstelle?",
			"Was ist bei einem Interbus zutreffend?",
			"Welche Aussage ist zum I²C zutreffend?",
			"Welche Bestandteile muss ein Software-Protokoll mindestens haben, um geregelten Datenaustausch mit variable Nutzungslänge zu ermöglichen?",
			"Welchen Vorteil bietet der NAT-Dienst (Network Address Translation)?"
		},
		// Loesung
		{
			"Eine garantierte gleichbleibende Abfragezykluszeit.",
			"int Zaehler;",
			"Der Compiler übersetzt den Quellcode und die Headerdateien in den Objektcode",
			"Er ist ein elektrisch umprogrammierbarer Festwertspeicher",
			"Es zeigt Entitätsmengen, Beziehungen und Kardinalitäten",
			"80",
			"Es dient zur Erkennung von Übertragungsfehlern",
			"HTTP, FTP, SMTP, POP3",
			"Schutz der Verfügbarkeit der Daten durch Redundanz",
			"Er kann mit einem Debugger an beliebigen Stellen in einem zu testenden Programm zur Fehleranalyse gesetzt werden",
			"Die Datenübertragung ist weniger störanfällig",
			"Der Entwickler muss nur angeben, was er speichern möchte, nicht wie er es speichert",
			"Bei der Vollsicherung werden die gesicherten Dateien auf dem Ursprungslaufwerk als gesichert markiert",
			"Der Router übersetzt die externe IP-Adresse in Adressen aus dem internen Adressbereich",
			"255.255.255.252",
			"Die interne IP-Adresse des UMTS-Routers",
			"Im LAN können private IP-Adressen verwendet werden",
			"Innerhalb einer Funkzelle teilen sich alle Teilnehmer die Datentransferrate von maximal 2 Mbit/s",
			"Verbindung von Baugruppen der Steuerungs- und Regelungstechnik, Bedien- und Anzeigegeräte, Mess- und Überwachungsgeräte",
			"Die Stromschleife besitzt eine höhere Störsicherheit, es sind größere Übertragungsstrecken (>500 m) möglich",
			"Es ist ein serielles Bussystem, das als Fernbus eine Gesamtausdehnung mit Repeatern bis zu ca. 13 km aufweisen kann",
			"Der I²C ist ein serielles Bussystem, das normalerweise innerhalb eines Geräts zur Anwendung kommt",
			"Zieladresse, Länge des Datenrahmens (Frames), Daten",
			"Im LAN können private IP-Adressen verwendet werden"
		},
		//Falsch1
		{
			"Jedem Sensor ist ein eigener Master zugeordnet.",
			"Zaehler int;",
			"Der Compiler übersetzt den Quellcode und die Headerdateien in ein ablauffähiges Programm",
			"Er kann nur einmalig programmiert werden",
			"Es zeigt Primärschlüssel und Fremdschlüssel",
			"21",
			"Es kennzeichnet bevorzugt zu behandelnde Datentelegramme",
			"DNS, TCP, X25, FTP",
			"Schutz der Vertraulichkeit der Daten durch Verschlüsselung",
			"Er bezeichnet eine fehlerhafte Stelle im Programm, an der dieses abstürzt",
			"Sie ist weniger aufwendig (Kosten. Bauteile...)",
			"Die Suche nach Werten ist bei einer Dateilösung unmöglich",
			"Bei der Vollsicherung werden nur veränderte oder neu erstellte Dateien gesichert",
			"Der Router ist in erster Linie für die Zwischenspeicherung von Internetseiten vorhanden",
			"255.255.0.0",
			"Die externe IP-Adresse der Luftschnittstelle vom UMTS-Router",
			"Im LAN können öffentliche IP-Adressen verwendet werden",
			"Der Radius einer Funkzelle darf 100m nicht übersteigen",
			"Komplexität behindert Automatisierung von technischen Einrichtungen",
			"Die Stromschleife besitzt eine höhere Störsicherheit, es sind Übertragungsstrecken bis 100 m möglich",
			"Es ist ein serielles Bussystem, das normalerweise innerhalb eines Gerätes zur Anwendung kommt",
			"Der I²C ist ein serielles Bussystem, das als Feldbussystem auch entfernte Sensoren an das System anbinden kann",
			"Quelladresse, Zieladresse, Daten, Prüfsumme",
			"Im LAN können öffentliche IP-Adressen verwendet werden"
		},
		//Falsch2
		{
			"Die Sensoren übernehmen die Masterfunktion und können somit sehr schnell Daten an das Automatisierungsgerät übertragen.",
			"Zaehler float;",
			"Der Compiler übersetzt nur den Quellcode in den Objektcode",
			"Er ist nach jedem Einschalten vollständig gelöscht ",
			"Es zeigt Primärschlüssel, Beziehungen und Kardinalitäten",
			"23",
			"Es kennzeichnet nachrangige Datentelegramme",
			"HTTP, UDP, IP, SMTP",
			"Schutz der Integrität der Daten durch digitale Signatur",
			"Er markiert eine Abbruchbedingung in einer Wiederholschleife",
			"Im Voll-Duplex-Betrieb benötigt sie weniger Leitungen",
			"Die speicherbaren Datenmengen sind größer",
			"Bei der Vollsicherung werden nur die am jeweiligen Tag geänderten Dateien gesichert",
			"Der Router dient als Parallel/Seriell-Umsetzer",
			"255.255.255.0",
			"Die Netzwerk-Adresse des LANs",
			"Dadurch wird der Zugriff vom Fernwartungs-PC auf den Web-Server ermöglicht",
			"Wenn ein Teilnehmer die Funkzelle verlässt, wird die UMTS-Verbindung unterbrochen",
			"Herstellerunabhängiger Elementaustausch ist durch eigene Übertragungsprotokolle unmöglich",
			"Die Stromschleife besitzt eine niedrigere Störsicherheit, es sind Übertragungsstrecken bis 100 m möglich",
			"Es ist ein serielles Bussystem, von dem über sogenannte Buskoppler Lokalbusse mit jeweils 50 m Länge abzweigen können",
			"Der I²C ist ein serielles Bussystem, das für eine schnelle Kommunikation zwischen Industrierechner und Sensoren entwickelt wurde",
			"Quelladresse, Zieladresse, Länge des Datenrahmens (Frames), Daten",
			"Dadurch wird der Zugriff vom Fernwartungs-PC auf den Web-Server ermöglicht"
		},
		//Falsch3
		{
			"Es können nur Aktoren über den Bus mit Daten versorgt werden.",
			"float Zaehler;",
			"Der Compiler übersetzt nur den Objektcode in den Quellcode",
			"Er ist ein nicht flüchtiger Nur-Lese-Speicher",
			"Es zeigt Fremdschlüssel und Primärschlüsselbeziehungen",
			"53",
			"Es kennzeichnet das Ende eines Telegrammes",
			"FTP, HTTP, X32, Ethernet",
			"Schutz vor Manipulation der Daten durch Zugangskontrolle",
			"Er unterbricht ein Hauptprogramm wenn z.B. ein Not-Aus betätigt wird",
			"RS485 arbeitet mit einem massesymmetrischen Signal",
			"Der Entwickler muss nur angeben, wie er etwas speichern möchte, nicht was er speichert",
			"Bei der Vollsicherung werden grundsätzlich alle Systemdateien mit gesichert",
			"Der Router stellt eine Wählverbindung zum Provider her",
			"255.255.255.240",
			"Die Broadcast-Adresse des LANs",
			"Nur der Fernwartungs-PC kann auf den Web-Server zugreifen",
			"Je größer die Ausdehnung einer Funkzelle ist, desto größer ist die theoretisch nutzbare Datentransferrate",
			"Verbindung der industriellen Kommunikation: Einzelleitebene, Blockleitebene, Fabrikebene",
			"Die Stromschleife besitzt eine niedrigere Störsicherheit, es sind größere Übertragungsstrecken (>500 m) möglich",
			"Der Interbus-Master reagiert auf Interruptanforderung durch die Slaves mit einem Sende-Aufforderungsprotokoll",
			"Der I²C ist ein paralleles Bussystem, das normalerweise innerhalb eines Geräts zur Anwendung kommt",
			"Zieladresse, Länge des Datenrahmens (Frames), Daten, Prüfsumme",
			"Nur der Fernwartungs-PC kann auf den Web-Server zugreifen"
		},
		//Falsch4
		{
			"Zwischen Master und Slave existiert eine parallele Datenverbindung.",
			"double Zaehler;",
			"Der Compiler übersetzt nur die Headerdateien",
			"Er gestattet ausschließlich den sequenziellen Datenzugriff",
			"Es zeigt Entitäten, Beziehungen und Attribute",
			"110",
			"Es dient der Unterscheidung von Frage- und Antworttelegrammen",
			"DNS, FTP, SMTP, IP",
			"Schutz vor Datenverlust durch Backup-Medium",
			"Er kann bei der Programmierung nicht verwendet werden",
			"Im Gegensatz zu RS232 können hiermit auch Binärdaten übertragen werden",
			"Nur mit der Datenbankspeicherung sind Backupsicherungen möglich",
			"Die Vollsicherung ist mit der Sicherungskopie",
			"Ein Router wird an dieser Stelle niemals eingesetzt",
			"255.255.255.255",
			"Es muss keine Adresse konfiguriert werden, wenn der Zugang über einen UMTS-Router erfolgt",
			"Unerwünschte Absender können blockiert werden (Blacklist)",
			"Mit Funkzelle wird der Raum beim Sender bezeichnet, in dem sich die Sendeantenne befindet",
			"Für schnelle Reaktion der Busteilnehmer haben alle uneingeschränkten Zugriff auf den Feldbus",
			"Die Stromschleife hat eine niedrigere Störsicherheit, deshalb sind kurze Übertragungsstrecken möglich, aber kleinerer Leiterquerschnitt",
			"Der Interbus ist ein paralleles Bussystem, das als Feldbussystem auch entfernte Sensoren an das System anbinden kann",
			"Der I²C ist ein paralleles Bussystem, das als Feldbussystem auch entfernte Sensoren an das System anbinden kann",
			"Zieladresse, Daten, Prüfsumme",
			"Unerwünschte Absender können blockiert werden (Blacklist)"
		},
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
	//  Setzen der Schwierigkeit in den Android Storage, damit diese
	//  beim nächsten Aufruf der App erhalten bleiben
	//---------------------------------------------------------------------------
	
	private void setDiff(int diff) 
	{
		SharedPreferences pref = getSharedPreferences("GAME", 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(String.valueOf(currentID), diff);
		editor.commit();
	}
	//---------------------------------------------------------------------------
	// 	Einlesen der aktuellen Schwierigkeit
	//---------------------------------------------------------------------------
	
	private int getDiff() 
	{
		SharedPreferences pref = getSharedPreferences("GAME", 0);
		// Wenn vorhanden gebe Wert weiter, ansonsten  standartmäßig 3
		return pref.getInt(String.valueOf(currentID), 3);
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
		
		//---------------------------------------------------------------------------
		// 	Wenn Antwort gegeben, dann entferne Listener auf den Buttons, und
		// 	mache den ganzen Bildschirm als Listener. Ansonsten umgekehrt.
		//---------------------------------------------------------------------------
		
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
		
		// Aktualisierung der Zeitleiste, wenn nicht Prüfungsmodus aktiv
		if (mode != 2)
		{					
			ProgressBar progressbar1 = (ProgressBar)findViewById(R.id.progressBar1);
			progressbar1.setVisibility(View.VISIBLE);
			progressbar1.setMax(setZeit*ticks);
			progressbar1.setProgress(zeit);
		}	
		
		// Anweisungen, wenn nächste Frage gestellt werden soll
		if (nextRound == true)
		{
			nextRound = false;
			String[] task = getQuestion();
			String Frage = task[0];
			String[] antwort = { task[1], task[2], task[3], task[4], task[5] };
			currentID = Integer.parseInt(task[6]);
			shuffleArray(antwort);
			currentDiff = getDiff();
			
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
			if (currentDiff == 1)
				LED.getBackground().setColorFilter(Color.parseColor("#ff2222"), PorterDuff.Mode.DARKEN);
			if (currentDiff == 2)
				LED.getBackground().setColorFilter(Color.parseColor("#ff9934"), PorterDuff.Mode.DARKEN);
			if (currentDiff == 3)
				LED.getBackground().setColorFilter(Color.parseColor("#ffff00"), PorterDuff.Mode.DARKEN);
			if (currentDiff == 4)
				LED.getBackground().setColorFilter(Color.parseColor("#99ff34"), PorterDuff.Mode.DARKEN);
			if (currentDiff == 5)
				LED.getBackground().setColorFilter(Color.parseColor("#00cc00"), PorterDuff.Mode.DARKEN);		
		}
	}
	
	public void countdown()
	{
		if ((answergiven==false) && (zeit >= 0) && (mode == 3))
			zeit -= 1;
		refresh();
		handler.postDelayed(this,tick);
		if ((zeit == 0) && (mode != 1))
		{
			gameOver();
		}
	}
	
	
	private void gameOver() 
	{
		final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent);
		dialog.setContentView(R.layout.gameover);
		Button finish = (Button) dialog.findViewById(R.id.finish);
		finish.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				dialog.dismiss();
				finish();
			}
		});		
		if (dialog.isShowing()== false)
			dialog.show();
	}

	public String[] getQuestion()
	{		
		int[] cnt = {0,0,0,0,0}; 
		for (currentID = 0; currentID < Question[0].length; currentID++) 
		{
			cnt[(getDiff()-1)]++;
		}
		int decide = 6;
		while (decide >= 6)
		{
			// Anzahl der Fragen mit Multiplikator. Existiert der Wert nicht, dann re-roll.
			decide = (int) ((cnt[0]*10+cnt[1]*8+cnt[2]*6+cnt[3]*4+cnt[4]*2) * random.nextFloat() + 6);
			if (((decide>=6) && (decide<=(cnt[0]*10+5)) && (cnt[0] != 0)) || ((cnt[1] == 0) && (cnt[2] == 0) && (cnt[3] == 0) && (cnt[4] == 0) ))
				decide = 1;
			else if ((decide>=(cnt[0]*10+6)) && (decide<=cnt[0]*10+cnt[1]*8+5) && (cnt[1] != 0))
				decide = 2;
			else if ((decide>=(cnt[0]*10+cnt[1]*8+6)) && (decide<=cnt[0]*10+cnt[1]*8+cnt[2]*6+5) && (cnt[2] != 0))
				decide = 3;
			else if ((decide>=(cnt[0]*10+cnt[1]*8+cnt[2]*6+6)) && (decide<=cnt[0]*10+cnt[1]*8+cnt[2]*6+cnt[3]*4+5) && (cnt[3] != 0))
				decide = 4;
			else if ((decide>=(cnt[0]*10+cnt[1]*8+cnt[2]*6+cnt[3]*4+6)) && (decide<=cnt[0]*10+cnt[1]*8+cnt[2]*6+cnt[3]*4+cnt[4]*2+5) && (cnt[4] != 0))
				decide = 5;
		}
		int match = 0;
		do
		{
			zufall = (int) (Question[0].length * random.nextFloat());
			currentID = zufall+1;
			match = getDiff();
		}
		while (decide != match);
		return new String[] { Question[1][zufall],Question[2][zufall],Question[3][zufall],Question[4][zufall],Question[5][zufall],Question[6][zufall],Question[0][zufall]};	
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
				if (currentDiff>1)
				{
					currentDiff--;
					setDiff(currentDiff);
				}
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
				if (currentDiff>1)
				{
					currentDiff--;
					setDiff(currentDiff);
				}
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
				if (currentDiff>1)
				{
					currentDiff--;
					setDiff(currentDiff);
				}
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
				if (currentDiff>1)
				{
					currentDiff--;
					setDiff(currentDiff);
				}
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
				if (currentDiff>1)
				{
					currentDiff--;
					setDiff(currentDiff);
				}
			}
			answergiven = true;
			ans = (Button) findViewById(R.id.answer5);
		}
		
		if(v.getId() == R.id.finish) 
		{
			finish();
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
	
	
	//---------------------------------------------------------------------------
	// 	Anweisungen bei einer richtigen Antwort
	//---------------------------------------------------------------------------
	
	public void rightAnswer()
	{
		if (currentDiff < 5)
		{
			currentDiff++;
			setDiff(currentDiff);
		}
		
	}
	
	//---------------------------------------------------------------------------
	// 	Mischen eines Arrays
	//---------------------------------------------------------------------------
	
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
