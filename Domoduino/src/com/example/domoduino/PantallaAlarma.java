package com.example.domoduino;


import java.util.Calendar;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class PantallaAlarma extends Activity
{
	private TimePicker timePicker1;
	private int hour;
	private int minute;
	
	private ImageButton bt1;
	private ImageButton bt2;
	private ImageButton bt3;
	private ImageButton bt4;
	private ImageButton btListo;
	private ImageButton btCancelar;
	private String nombreAlarma;
	private int idAlarma;
	private static LogicaAlarma logica;
	private int accion = 0;
	private boolean b = false;
	
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_alarma);
	        
	        bt1=(ImageButton) findViewById(R.id.imagen1);
			bt1.setOnClickListener(btn1);
			bt2=(ImageButton) findViewById(R.id.imagen2);
			bt2.setOnClickListener(btn2);
			bt3=(ImageButton) findViewById(R.id.imagen3);
			bt3.setOnClickListener(btn3);
			bt4=(ImageButton) findViewById(R.id.imagen4);
			bt4.setOnClickListener(btn4);
 
			btListo = (ImageButton) findViewById(R.id.imagen21);
			btListo.setOnClickListener(botonlisto);
			btCancelar = (ImageButton) findViewById(R.id.imagen22);
			btCancelar.setOnClickListener(botoncancelar);
			
	    	logica = new LogicaAlarma(getApplicationContext());
			
	    	setCurrentTimeOnView();
	 }

	  
	 
	 private ImageButton.OnClickListener botonlisto = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				String hora1 = pad(timePicker1.getCurrentHour());
				String minuto1 = pad(timePicker1.getCurrentMinute());
				
				Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
				if(nombreAlarma != null)
				{
					logica.modificarAlarma(idAlarma, new Alarma(idAlarma,nombreAlarma,hora1,minuto1,accion));
					i.putExtra("nombreAlarma", nombreAlarma);
				}
				else
				{
					SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
					int id = preferencias.getInt("id", -1);
					int idNuevo = -1;
					if(id!=-1)
					{
						idNuevo = id + 1;					}
					else
					{
						idNuevo = 1;
					}
					
					SharedPreferences.Editor editor = preferencias.edit();
					editor.putInt("id", idNuevo);
					editor.commit();
					logica.guardarAlarma(new Alarma(idNuevo,"Alarma " + idNuevo,hora1,minuto1,accion));
				}
	       	    startActivity(i);		
			}
		};
	 
	private ImageButton.OnClickListener botoncancelar = new ImageButton.OnClickListener()
		{
				public void onClick(View v)
				{
					Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
		       	    startActivity(i);			
				}
		};	
		
	 public void setCurrentTimeOnView() {
		 
		 	timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		 
	        timePicker1.setIs24HourView(true);
	        
	        
		    Bundle bundle = getIntent().getExtras();
		    if(bundle!=null)
		    {
		        idAlarma = bundle.getInt("idAlarma");
		    	String horaEntera = bundle.getString("horaEntera");
		    	nombreAlarma = bundle.getString("nombreAlarma");
		    	
		    	Toast toast = Toast.makeText(getApplicationContext(), horaEntera, Toast.LENGTH_LONG);
		    	toast.show();
		    	String[] retval = horaEntera.split(":", 2);
		    	
		    	int hora1 = Integer.parseInt(retval[0]);
				int hora2 = Integer.parseInt(retval[1]);
				
				timePicker1.setCurrentHour(hora1);
				timePicker1.setCurrentMinute(hora2);
		    }
		    else
		    {
				final Calendar c = Calendar.getInstance();
				hour = c.get(Calendar.HOUR_OF_DAY);
				minute = c.get(Calendar.MINUTE);
				// set current time into timepicker
				timePicker1.setCurrentHour(hour);
				timePicker1.setCurrentMinute(minute);
		    }
	 
		}
	 
		 private static String pad(int c)
		 {
				if (c >= 10)
				   return String.valueOf(c);
				else
				   return "0" + String.valueOf(c);
		 }
	 
	 	private ImageButton.OnClickListener btn1 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				//accion = 1;
				if(b==false)
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.start);
					bt1.setImageBitmap(bmp);
					accion = 1;
					b=true;
				}
				else if(b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedastart);
					bt1.setImageBitmap(bmp);
					b=false;
				}
			}
		};
		
		
		private ImageButton.OnClickListener btn2 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				if(b==false)
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.stop);
					bt2.setImageBitmap(bmp);
					accion = 2;
					b=true;
				}
				else if(b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedastop);
					bt2.setImageBitmap(bmp);
					b=false;
				}
			}
		};
		
		private ImageButton.OnClickListener btn3 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				if(b==false)
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bombilla);
					bt3.setImageBitmap(bmp);
					accion = 3;
					b=true;
				}
				else if(b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaencendida);
					bt3.setImageBitmap(bmp);
					b=false;
				}
			}
		};
		
		private ImageButton.OnClickListener btn4 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				if(b==false)
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bombillaeapagada);
					bt4.setImageBitmap(bmp);
					accion = 4;
					b=true;
				}
				else if(b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
				{
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaapagada);
					bt4.setImageBitmap(bmp);
					b=false;
				}
			}
		};
}
