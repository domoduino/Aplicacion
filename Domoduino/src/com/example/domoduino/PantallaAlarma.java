package com.example.domoduino;


import java.util.Calendar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	private String nombreAlarma;
	private int idAlarma;
	private static LogicaAlarma logica;
	
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
 
	    	logica = new LogicaAlarma(getApplicationContext());
			
	    	setCurrentTimeOnView();
	 }
	 
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
				String hora1 = pad(timePicker1.getCurrentHour());
				String minuto1 = pad(timePicker1.getCurrentMinute());
				//logica.guardarAlarma(new Alarma(1,"prueba",hora1,minuto1,1));
				
				Toast.makeText(getApplicationContext(), hora1,10).show();
				
				Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
				if(nombreAlarma != null)
				{
					logica.modificarAlarma(idAlarma, new Alarma(idAlarma,"Prueba",hora1,minuto1,1));
					i.putExtra("nombreAlarma", nombreAlarma);
				}
				else
				{
					logica.guardarAlarma(new Alarma(1,"Nueva",hora1,minuto1,1));
				}
	       	    startActivity(i);	
			}
		};
		
		
		private ImageButton.OnClickListener btn2 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				String hora1 = pad(timePicker1.getCurrentHour());
				String minuto1 = pad(timePicker1.getCurrentMinute());
				Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
				i.putExtra("hora", hora1);
				i.putExtra("minutos", minuto1);
	       	    startActivity(i);	
			}
		};
		
		private ImageButton.OnClickListener btn3 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				String hora1 = pad(timePicker1.getCurrentHour());
				String minuto1 = pad(timePicker1.getCurrentMinute());
				Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
				i.putExtra("hora", hora1);
				i.putExtra("minutos", minuto1);
	       	    startActivity(i);	
			}
		};
		
		private ImageButton.OnClickListener btn4 = new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				String hora1 = pad(timePicker1.getCurrentHour());
				String minuto1 = pad(timePicker1.getCurrentMinute());
				Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
				i.putExtra("hora", hora1);
				i.putExtra("minutos", minuto1);
	       	    startActivity(i);	
			}
		};
}
