package com.example.domoduino;

import java.util.ArrayList;
import java.util.Set;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listDevicesFound;
	private ImageButton image;
	private ArrayAdapter<String> btArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        	listDevicesFound = (ListView)findViewById(R.id.devicesfound);
		    image = (ImageButton)findViewById(R.id.btnConectar);
	        
	        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	        btArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
	        listDevicesFound.setAdapter(btArrayAdapter);

	        image.setOnClickListener(btnScanDeviceOnClickListener);

	        registerReceiver(ActionFoundReceiver, 
	        		new IntentFilter(BluetoothDevice.ACTION_FOUND));
	}
	
	 private ImageButton.OnClickListener btnScanDeviceOnClickListener
	    = new ImageButton.OnClickListener(){


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
		        			Toast.makeText(getApplicationContext(),"Bluetooth ya activado", Toast.LENGTH_SHORT).show();
		        		}
		        	}
		        	else
		        	{
		        		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		        	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		        	}
		        }
				btArrayAdapter.clear();
				bluetoothAdapter.startDiscovery();
			}};
			
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
