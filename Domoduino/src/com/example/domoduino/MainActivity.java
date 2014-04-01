package com.example.domoduino;




import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.annotation.SuppressLint;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;


@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	
    // Adaptador local Bluetooth 
    private BluetoothAdapter AdaptadorBT = null; 
    
	Button boton;
	private ArrayAdapter<String> btArrayAdapter;
	private ImageButton image;
	private ListView listDevicesFound;
	ListView list;
    Dialog listDialog;
	

	//\\//\\//\\//\\//\\//\\//\\//
	// Debugging
    public static final String TAG = "LEDv0";
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
    
    //\\//\\//\\//\\//\\//\\//\\//\\/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listDevicesFound = (ListView)findViewById(R.id.devicesfound);       
        image = (ImageButton)findViewById(R.id.btnConectar);
             
    
        btArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

       // listDevicesFound.setAdapter(btArrayAdapter);
       // listDevicesFound.setOnItemClickListener(btn1);
        image.setOnClickListener(btnScanDeviceOnClickListener);
        
        registerReceiver(ActionFoundReceiver, 
        		new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }
    
   private OnItemClickListener btn1 = new ListView.OnItemClickListener(){
	 public void onItemClick(AdapterView<?> a, View v, int position,long id) {
		 
		 ListAdapter la = (ListAdapter) a.getAdapter();
		 String address = "00:13:12:16:63:31";
		 String MAC = la.getItem(position).toString();
		 if(MAC.contains(address))
		 {
			 vibrador = (Vibrator) getSystemService(VIBRATOR_SERVICE);
             vibrador.vibrate(1000);
         	 Intent i = new Intent(getApplicationContext(), PantallaPrincipal.class);
         	 startActivity(i);
		 }
		 else
		 {
			Toast.makeText(getApplicationContext(),"No es la MAC del módulo", Toast.LENGTH_SHORT).show();
		 }
		 
	    } 
 };
    
    private ImageButton.OnClickListener btnScanDeviceOnClickListener
    = new ImageButton.OnClickListener()
	 {
	
			public void onClick(View v)
			{
						Toast.makeText(getApplicationContext(),"paso 1", Toast.LENGTH_SHORT).show();
	        			listarConAlert();

		    }
		};
			
			public void listarConAlert()
			{
				AdaptadorBT.startDiscovery();
    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    			builder.setTitle("Lista de dispositivos");
    			builder.setAdapter(btArrayAdapter, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int item) {
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
    					Toast.makeText(getApplicationContext(),"No es la MAC del módulo", Toast.LENGTH_SHORT).show();

    					vibrador = (Vibrator) getSystemService(VIBRATOR_SERVICE);
   		             vibrador.vibrate(1000);
   		         	 Intent i = new Intent(getApplicationContext(), PantallaPrincipal.class);
   		         	 startActivity(i);
    				 }
    			}
    			});

    			AlertDialog alert = builder.create();
    			alert.show();
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
   

   public  void onStart() 
   {
	    super.onStart();
	    ConfigBT();
    }

    public void ConfigBT()
    {
    	// Obtenemos el adaptador de bluetooth
        AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
        if (AdaptadorBT.isEnabled()) 
        {//Si el BT esta encendido,
//	       	 if (Servicio_BT == null)
//	       	 {//y el Servicio_BT es nulo, invocamos el Servicio_BT    		 
//	       		 Servicio_BT = new ConexionBT(this, mHandler);
//	       	 }      
        }
	   	 else
	   	 { 
	   		 if(D) Log.e("Setup", "Bluetooth apagado...");
		     Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		     startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT); 
	   	 }
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
  	  //Una vez que se ha realizado una actividad regresa un "resultado"...
  	      switch (requestCode) {
  	               
  	            case REQUEST_ENABLE_BT://Respuesta de intento de encendido de BT
  	                if (resultCode == Activity.RESULT_OK) {//BT esta activado,iniciamos servicio
  	                	 ConfigBT();
  	                }
  	                else
  	                {//No se activo BT, salimos de la app                 
  	                    finish();
  	                 } 	                
  	          }//fin de switch case
  	   }//fin de onActivityResult     
  	     
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
	} 
	
    @Override
    public void onDestroy()
    {
    	 super.onDestroy();
    }
}//Fin MainActivity






















