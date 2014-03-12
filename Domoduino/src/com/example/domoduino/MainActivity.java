package com.example.domoduino;

import java.util.ArrayList;
import java.util.Set;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listDevicesFound;
	private ImageButton image;
	private ArrayAdapter<String> btArrayAdapter;
    ListView list;
    Dialog listDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        	listDevicesFound = (ListView)findViewById(R.id.devicesfound);
		    image = (ImageButton)findViewById(R.id.btnConectar);
	        
	        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	        btArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
	        listDevicesFound.setAdapter(btArrayAdapter);
	        listDevicesFound.setOnItemClickListener(btn);

	        image.setOnClickListener(btnScanDeviceOnClickListener);

	        registerReceiver(ActionFoundReceiver, 
	        		new IntentFilter(BluetoothDevice.ACTION_FOUND));
	}
	
	 private OnItemClickListener btn
	    = new ListView.OnItemClickListener(){
		 public void onItemClick(AdapterView<?> a, View v, int position,long id) {
			 
			 ListAdapter la = (ListAdapter) a.getAdapter();
			 
			 Toast.makeText(
			         v.getContext()
			         ,la.getItem(position).toString()
			         ,Toast.LENGTH_LONG)
			         .show();
 	    } 
	 };
	
	 private ImageButton.OnClickListener btnScanDeviceOnClickListener
	    = new ImageButton.OnClickListener()
	 {

			public void onClick(View v)
			{
				connect();
	 }};
			
			public void connect ()
			{
				if (bluetoothAdapter == null)
		    	{
					Toast.makeText(getApplicationContext(),"No soporta Bluetooth", Toast.LENGTH_SHORT).show();
		        }
		    	else
		    	{
		        	if (bluetoothAdapter.isEnabled())
		        	{
		        		if(bluetoothAdapter.isDiscovering())
		        		{
		        			Toast.makeText(getApplicationContext(),"Bluetooth en proceso ", Toast.LENGTH_SHORT).show();
		        		}
		        		else
		        		{
		        			bluetoothAdapter.startDiscovery();
		        			Toast.makeText(getApplicationContext(),"Bluetooth ya activado", Toast.LENGTH_SHORT).show();
		        		}
		        	}
		        	else
		        	{
		        		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  //Muestra una actividad del sistema que permite al usuario activar Bluetooth.
		        	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT); //Iniciar una actividad para la que desea un resultado cuando se terminó. Cuando sale de esta actividad, su método onActivityResult () será llamada con el requestCode dado
		        	}
		        }
				//btArrayAdapter.clear(); // Borra todos los elementos de la lista
			}
			
			protected void onActivityResult(int requestCode, int resultCode, Intent data)
			{
				  if(requestCode == REQUEST_ENABLE_BT)
				  {
					 connect();
				  }
			}
			
	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){

				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					String action = intent.getAction();
					if(BluetoothDevice.ACTION_FOUND.equals(action)) {
			            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			            btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			            btArrayAdapter.notifyDataSetChanged();
			        }
		}};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
