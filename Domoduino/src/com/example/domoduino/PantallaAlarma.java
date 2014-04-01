package com.example.domoduino;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TimePicker;

public class PantallaAlarma extends Activity
{
	private TimePicker timePicker1;
	private int hour;
	private int minute;
	
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_alarma);
	        
	        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
	 }
}
