package com.example.domoduino;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
    // Adaptador local Bluetooth 
    private BluetoothAdapter AdaptadorBT = null; 
    
	Button boton;
	private ArrayAdapter<String> btArrayAdapter;
	private ImageButton image;
	ListView list;
    Dialog listDialog;
	
    /***************** CONEXI�N *************************/

    public static final boolean D = true;
   
    // Tipos de mensaje enviados y recibidos desde el Handler de ConexionBT
    public static final int Mensaje_Estado_Cambiado = 1;
    public static final int Mensaje_Leido = 2;
    public static final int Mensaje_Escrito = 3;
    public static final int Mensaje_Nombre_Dispositivo = 4;
    public static final int Mensaje_TOAST = 5;	        
    public static final int MESSAGE_Desconectado = 6;
    public static final int REQUEST_ENABLE_BT = 7;
    
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";   
    //Vibrador
    private Vibrator vibrador; 
    
    /***************** CONEXI�N *************************/
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
              
        image = (ImageButton)findViewById(R.id.btnConectar);
        
        btArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        
        image.setOnClickListener(btnScanDeviceOnClickListener);
        
        registerReceiver(ActionFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }
     
    private ImageButton.OnClickListener btnScanDeviceOnClickListener = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
	        listarConAlert();
		}
	};
			
	public void listarConAlert()
	{
		AdaptadorBT.startDiscovery();
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Lista de dispositivos");
    	builder.setAdapter(btArrayAdapter, new DialogInterface.OnClickListener() 
    	{
    			public void onClick(DialogInterface dialog, int item) 
    			{
    				String address = "00:13:12:16:63:31";
    				String MAC =  btArrayAdapter.getItem(item).toString();
    				 
    				if(MAC.contains(address))
    				 {
    					 vibrador = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    		             vibrador.vibrate(1000);
    		         	 Intent i = new Intent(getApplicationContext(), PantallaPrincipal.class);
    		         	 startActivity(i);
    				 }
    				 else
    				 {
    					Toast.makeText(getApplicationContext(),"No es la MAC del m�dulo", Toast.LENGTH_SHORT).show();
    					 vibrador = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	   		             vibrador.vibrate(1000);
	   		         	 Intent i = new Intent(getApplicationContext(), PantallaPrincipal.class);
	   		         	 startActivity(i);
    				 }
    			}
    	});

    	AlertDialog alert = builder.create();
    	alert.show();
		btArrayAdapter.clear();
	}
		
        
	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)) 
			{
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	            btArrayAdapter.notifyDataSetChanged();
	        }
		}
	};
   

   public  void onStart() 
   {
	    super.onStart();
	    ConfigBT();
    }

    public void ConfigBT()
    {
    	// Obtenemos el adaptador de bluetooth
        AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
        if (!AdaptadorBT.isEnabled())      
	   	 { 
	   		 if(D)
	   			 Log.e("MainActivity", "Bluetooth apagado...");
		     Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		     startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT); 
	   	 }
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
  	  //Una vez que se ha realizado una actividad regresa un "resultado"...
  	      switch (requestCode) 
  	      {    
  	            case REQUEST_ENABLE_BT://Respuesta de intento de encendido de BT
  	                if (resultCode == Activity.RESULT_OK) 
  	                {
  	                	 ConfigBT(); //Si el Bluetooth est� activado, inicia el servicio
  	                }
  	                else
  	                {               
  	                    finish(); //Si el Bluetooth no est� activado, detiene la aplicaci�n
  	                 } 	                
  	          }
  	   }    
  	     
    @Override
    public void onDestroy()
    {
    	 super.onDestroy();
    } 
}






















