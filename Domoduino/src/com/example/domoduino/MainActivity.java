package com.example.domoduino;

import java.util.ArrayList;
import java.util.Set;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	protected static final int REQUEST_ENABLE_BT = 0;
	private Button btnBluetooth; 
	private BluetoothAdapter bAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Get Bluettoth Adapter
				bAdapter = BluetoothAdapter.getDefaultAdapter();
				
				btnBluetooth = (Button)findViewById(R.id.btnBluetooth);
				btnBluetooth.setOnClickListener(new OnClickListener() 
		        {

					@Override
					public void onClick(View v) {
						
						//Notificación visual conectando...
						Toast toast1 = Toast.makeText(getApplicationContext(),"Conectando...", Toast.LENGTH_SHORT);
						
						// Check smartphone support Bluetooth
						if(bAdapter == null){
							//Device does not support Bluetooth
							Toast.makeText(getApplicationContext(), "Not support bluetooth", 5).show();
							finish();
						}
						
						// Check Bluetooth enabled
						if(!bAdapter.isEnabled()){
							Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivityForResult(enableBtIntent, 1);
							toast1.show();
							btnBluetooth.setText("Desconectar");
						}
						else
						{
							btnBluetooth.setText("Conectar");
							bAdapter.disable();
						}
						
						
					}
					
		        });
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true; //modificado por Ofelia
	}

}
