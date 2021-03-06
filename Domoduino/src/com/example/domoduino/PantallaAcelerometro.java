package com.example.domoduino;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaAcelerometro extends Activity implements SensorListener 
{
	TextView orientacion = null;
	SensorManager sm = null;
	
	/********************CONEXI�N******************************/
    public static final String TAG = "PantallaAcelerometro";
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
    
    //variables para el Menu de conexi�n
    private boolean seleccionado=false; 
    
    //Adaptador local Bluetooth 
    private BluetoothAdapter AdaptadorBT = null; 
    
    //Nombre del dispositivo conectado
    private String mConnectedDeviceName = null;
    
    //Objeto miembro para el servicio de ConexionBT 
    private ConexionBT Servicio_BT = null;	 
	
   /*****************CONEXI�N************************/
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_acelerometro);
		
		//Crear conexi�n
		 AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
		 Servicio_BT = new ConexionBT(this, mHandler);
		 BluetoothDevice device = AdaptadorBT.getRemoteDevice("00:13:12:16:63:31");
         Servicio_BT.connect(device);
        
        //SENSOR
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		orientacion = (TextView) findViewById(R.id.textview1);
	}


	public void onSensorChanged(int sensor, float[] values) 
	{
		synchronized (this) 
		{
			if (sensor == SensorManager.SENSOR_ORIENTATION) 
			{
				if((values[1]< (-60))&& (values[1]> (-95)))
				{			
					sendMessage("E\r");
				}
				else if((values[1]>(-10)) && (values[1]<10))
				{
					sendMessage("D\r");
				}
			}

		}
		
	}

	public void onAccuracyChanged(int sensor, int accuracy) 
	{
		
	}

	
	@Override
	protected void onResume() 
	{
		super.onResume();
		sm.registerListener(this, SensorManager.SENSOR_ORIENTATION,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	
	@Override
	protected void onStop() 
	{
		sm.unregisterListener(this);
		super.onStop();
	}
	
	 @Override
     public void onDestroy(){
     	 super.onDestroy();
     	 if (Servicio_BT != null) Servicio_BT.stop();//Detiene el servicio
     }
	
	 public  void sendMessage(String message) {
	        if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED)  //comprueba si est� conectado a la tarjeta bluetooth
	        {
		        if (message.length() > 0) 
		        {   // comprueba si ha enviado datos
		            byte[] send = message.getBytes();//Obtiene bytes del mensaje
		            if(D) Log.e(TAG, "Mensaje enviado:"+ message);            
		                 Servicio_BT.write(send);     //Manda a escribir el mensaje     
		        }
		     }
	        else
	        {
	        	Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
	        }
	    }
	    
	 
	    final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
		            	
		                switch (msg.what) {
		     
		                case Mensaje_Escrito:
		                	  byte[] writeBuf = (byte[]) msg.obj;
		                      String writeMessage = new String(writeBuf);
		                      if(D) Log.e(TAG, "Message_write  =w= "+ writeMessage);  
		                    break;
		                                    
		                case Mensaje_Leido:   	                 
		                  byte[] readBuf = (byte[]) msg.obj;
		                  String readMessage = new String(readBuf, 0, msg.arg1);
		                  if(D) Log.e(TAG, "Message_read   =w= "+ readMessage);              	                 
		                    break;
		     	           
		                case Mensaje_Nombre_Dispositivo:
		                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME); //Guardamos nombre del dispositivo
		                    seleccionado=true;
		                    break;
		                    
		                case Mensaje_TOAST:
		                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
		                    Toast.LENGTH_SHORT).show();
		                    break;
		                    
		                case MESSAGE_Desconectado:	
		                  	 if(D) Log.e("Conexion","DESConectados");
		                  	 seleccionado=false;             	
		          break;
		          
		                }
		            }
		        };
	

}
