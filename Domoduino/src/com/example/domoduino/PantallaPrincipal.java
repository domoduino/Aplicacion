package com.example.domoduino;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PantallaPrincipal extends Activity 
{
	
	/************************ CONEXIÓN ****************************/
	
	public static final String TAG = "PantallaPrincipal";
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
	 
	//variables para el Menu de conexión
	private boolean seleccionado=false; 
	    
	// Adaptador local Bluetooth 
	private BluetoothAdapter AdaptadorBT = null; 
	    
	 //Nombre del dispositivo conectado
	private String mConnectedDeviceName = null;   
	    
	 //Objeto miembro para el servicio de ConexionBT 
	private ConexionBT Servicio_BT = null;	 
	
	/************************ CONEXIÓN ****************************/
	    
	private LogicaAlarma la=null;
	
	private ImageButton bt1;
	private ImageButton bt2;
	private ImageButton bt3;
	private ImageButton bt4;
	private ImageButton bt5;
	
	private TextView textAyuda;
	private TextView textAlarma;

	boolean b=false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantallaprincipal);
	
		//Crear conexión
		 AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
		 Servicio_BT = new ConexionBT(this, mHandler);
		 BluetoothDevice device = AdaptadorBT.getRemoteDevice("00:13:12:16:63:31");
         Servicio_BT.connect(device);
 		
 		//Asigna eventos a los botones del menú que aparece en Pantalla Principal
         
		bt1=(ImageButton) findViewById(R.id.imageButton1);
		bt1.setOnClickListener(btn1);
		bt2=(ImageButton) findViewById(R.id.imageButton2);
		bt2.setOnClickListener(btn2);
		bt3=(ImageButton) findViewById(R.id.imageButton3);
		bt3.setOnClickListener(btn3);
		bt4=(ImageButton) findViewById(R.id.imageButton4);
		bt4.setOnClickListener(btn4);
		bt5=(ImageButton) findViewById(R.id.imageButton5);
		bt5.setOnClickListener(btn5);
		
		RelativeLayout horaProximaAlarma = (RelativeLayout) findViewById(R.id.layout_alarma);
 		textAlarma =  (TextView) horaProximaAlarma.findViewById(R.id.textAlarma);
		textAyuda =  (TextView) findViewById(R.id.textAyuda);		
	}
	
	
	public void onResume()
	{
 		super.onResume();
 		
 		la = new LogicaAlarma(getApplicationContext());		
 		
 		if (la.getActivada()!=null)
 		{
 		// Si existe una alarma activada, muestra su hora en Pantalla Principal.
 			textAlarma.setText(la.getActivada().getHoraAlarma() + " : " + la.getActivada().getMinAlarma());
 		}
 		else
 		{
 			// Si no existe una alarma activada,  no se muestra ninguna hora
 			textAlarma.setText(" ");
 		}
	}
 		
	
	//Elementos del menú
	public  boolean onCreateOptionsMenu (Menu menu)
	{ 
	    MenuInflater inflater = getMenuInflater () ; 
	    inflater.inflate (R.menu.main, menu ); 
	    return  true ; 
	}
	
	public  boolean onOptionsItemSelected (MenuItem item )
	{ 
	    switch  (item . getItemId ()) 
	    { 
	        case R.id.Ajustes1: 
	        	Intent i = new Intent(getApplicationContext(), PantallaAcelerometro.class);
	       	 	startActivity(i);
	            return  true ;
	        case R.id.Ajustes2:
	        	Intent i1 = new Intent(getApplicationContext(), ListadoAlarmas.class);
	       	 	startActivity(i1);
	        	return  true ;
	        case R.id.Ayuda: 
	        	LayoutInflater li = LayoutInflater.from(this);
    			View prompt = li.inflate(R.layout.activity_ayuda, null);
    			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    			alertDialogBuilder.setView(prompt);
    			alertDialogBuilder.setTitle("Ayuda");
    			
    			// Mostramos el mensaje del cuadro de dialogo
    			alertDialogBuilder.setCancelable(false).setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
    			{
	    			public void onClick(DialogInterface dialog,int id)
	    			{
	    				dialog.cancel();
	    			}
    			});
    			 
    			// Creamos un AlertDialog y lo mostramos
    			AlertDialog alertDialog = alertDialogBuilder.create();
    			alertDialog.getListView().setFastScrollEnabled(true);
    			alertDialog.show();
    			
	            return  true ; 
	        default : 
	            return  super . onOptionsItemSelected (item); 
	    } 
	}
	
	private ImageButton.OnClickListener btn1 = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
			sendMessage("D\r");
		}
	};
	
	
	 private ImageButton.OnClickListener btn2 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			sendMessage("E\r");
			Toast.makeText(getApplicationContext(),"Stop", Toast.LENGTH_SHORT).show();
							
		}
	};
	
	 private ImageButton.OnClickListener btn3 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			if(b==false)
			{
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaapagada);
				bt3.setImageBitmap(bmp);
				sendMessage("B\r");
				b=true;
			}
			else if(b==true)
			{
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaencendida);
				bt3.setImageBitmap(bmp);
				sendMessage("C\r"); 
				b=false;
			}					
		}
	 };
	 
	 
	 private ImageButton.OnClickListener btn4 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			onDestroy();
			Intent i = new Intent(getApplicationContext(), ListadoAlarmas.class);
        	startActivity(i);
		}
	 };
	 
	 
	 private ImageButton.OnClickListener btn5 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			onDestroy();
			Toast.makeText(getApplicationContext(),"Acelerómetro encendido", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getApplicationContext(), PantallaAcelerometro.class);
       	 	startActivity(i);
		}
	 };

	 
	 public  void sendMessage(String message) 
	 {
	        if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED) //comprueba si está conectado a la tarjeta bluetooth
	        {   
		        if (message.length() > 0) 
		        {   // comprueba si ha enviado datos
		            byte[] send = message.getBytes();//Obtiene los bytes del mensaje
		            if(D) Log.e(TAG, "Mensaje enviado:"+ message);            
		                 Servicio_BT.write(send);     //Manda a escribir el mensaje     
		        }
		     }
	        else
	        {
	        	Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
	        }
	   }
	    
	 
	    final Handler mHandler = new Handler() 
	    {
	        @Override
	        public void handleMessage(Message msg) {
		            	
		                switch (msg.what) {
		       
		                case Mensaje_Escrito:
		                	  byte[] writeBuf = (byte[]) msg.obj;
		                      String writeMessage = new String(writeBuf);
		                      if(D) 
		                    	  Log.i(TAG, "Message_write  =w= "+ writeMessage);  
		                    break;
		                                      
		                case Mensaje_Leido:   	                 
		                	byte[] readBuf = (byte[]) msg.obj;		                    
		                  String readMessage = new String(readBuf, 0, msg.arg1);
		                  if(D)
		                	  Log.i(TAG, "Message_read   =w= "+ readMessage);              	                 
		                    break;
		     	           
		                case Mensaje_Nombre_Dispositivo:
		                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME); //Guarda el nombre del dispositivo
		                    Toast.makeText(getApplicationContext(), "Conectado con "+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
		                    seleccionado=true;
		                    break;
		                    
		                case Mensaje_TOAST:
		                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),Toast.LENGTH_SHORT).show();
		                    break;

		                case MESSAGE_Desconectado:	
		                  	 if(D) 
		                  		 Log.i("Conexion","DESConectados");
		                  	 seleccionado=false;             	
		                  	 break;
		                }
		            }
		        };


		        @Override
		        public void onDestroy()
		        {
		        	 super.onDestroy();
		        	 if (Servicio_BT != null) 
		        		 Servicio_BT.stop();//Detenemos servicio
		        }
		        
}
