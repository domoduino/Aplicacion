package com.example.domoduino;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btnBluetooth; 
	private BluetoothAdapter bAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
				// Obtenemos el adaptador Bluetooth. Si es NULL, significara que el
				// dispositivo no posee Bluetooth, por lo que deshabilitamos el boton
				// encargado de activar/desactivar esta caracteristica.
				btnBluetooth = (Button)findViewById(R.id.btnBluetooth);
				btnBluetooth.setOnClickListener(new OnClickListener() 
		        {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						bAdapter = BluetoothAdapter.getDefaultAdapter();
						if(bAdapter.isEnabled())
						{
						    btnBluetooth.setText("desactivado");
						    bAdapter.disable();
						}
						else
						{
						    btnBluetooth.setText("activado");
							bAdapter.enable();
						}
					}
					
		        });
				
				//Prueba botón pulsar
				/*button1 = (Button) findViewById(R.id.button1);
				button1.setOnClickListener(new OnClickListener() 
		        {
					
		        });*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true; //modificado por Ofelia
	}

}
