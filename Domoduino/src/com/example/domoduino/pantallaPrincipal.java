package com.example.domoduino;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class pantallaPrincipal extends Activity {
	
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
	  //variables para el Menu de conexión
	    private boolean seleccionador=false; 
	 // Adaptador local Bluetooth 
	    private BluetoothAdapter AdaptadorBT = null; 
	  //Nombre del dispositivo conectado
	    private String mConnectedDeviceName = null;   
	    //Objeto miembro para el servicio de ConexionBT 
	    private ConexionBT Servicio_BT = null;	 

	
	private ImageButton bt1;
	private ImageButton bt2;
	private ImageButton bt3;
	private ImageButton bt4;
	private ImageButton bt5;

	boolean b=false;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantallaprincipal);
		
		Toast.makeText(getApplicationContext(),"entra", Toast.LENGTH_SHORT).show();
		//Crear conexión
		 AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
		 Servicio_BT = new ConexionBT(this, mHandler);
		 BluetoothDevice device = AdaptadorBT.getRemoteDevice("00:13:12:16:63:31");
         Servicio_BT.connect(device);
         Toast.makeText(getApplicationContext(),"entra2", Toast.LENGTH_SHORT).show();
		
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
	}
	
	private ImageButton.OnClickListener btn1 = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
			//sendMessage("3\r");
			Toast.makeText(getApplicationContext(),"Encendido", Toast.LENGTH_SHORT).show();
		}
	};
	
	
	 private ImageButton.OnClickListener btn2 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			//sendMessage("4\r");
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
				sendMessage("1\r"); //funciona
				b=true;
			}
			else if(b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
			{
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaencendida);
				bt3.setImageBitmap(bmp);
				sendMessage("2\r"); //funciona
				b=false;
			}
							
		}
	 };
	 
	 
	 private ImageButton.OnClickListener btn4 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			//sendMessage("5\r");
		}
	 };
	 
	 
	 private ImageButton.OnClickListener btn5 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			//sendMessage("6\r");
			Toast.makeText(getApplicationContext(),"Acelerómetro encendido", Toast.LENGTH_SHORT).show();
							
		}
	 };

	 
	 
	 public  void sendMessage(String message) {
	        if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED) {//checa si estamos conectados a BT   
		        if (message.length() > 0) 
		        {   // checa si hay algo que enviar
		            byte[] send = message.getBytes();//Obtenemos bytes del mensaje
		            if(D) Log.e(TAG, "Mensaje enviado:"+ message);            
		                 Servicio_BT.write(send);     //Mandamos a escribir el mensaje     
		        }
		     }
	        else Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
	    		}//fin de sendMessage
	    
	 
	    final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
		            	
		                switch (msg.what) {
		     //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   
		                case Mensaje_Escrito:
		                	  byte[] writeBuf = (byte[]) msg.obj;//buffer de escritura...
		                      // Construye un String del Buffer
		                      String writeMessage = new String(writeBuf);
		                      if(D) Log.e(TAG, "Message_write  =w= "+ writeMessage);  
		                    break;
		      //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>                                
		                case Mensaje_Leido:   	                 
		                	byte[] readBuf = (byte[]) msg.obj;//buffer de lectura...
		                    //Construye un String de los bytes validos en el buffer
		                  String readMessage = new String(readBuf, 0, msg.arg1);
		                  if(D) Log.e(TAG, "Message_read   =w= "+ readMessage);              	                 
		                    break;
		     //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	           
		                case Mensaje_Nombre_Dispositivo:
		                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME); //Guardamos nombre del dispositivo
		     Toast.makeText(getApplicationContext(), "Conectado con "+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
		     seleccionador=true;
		                    break;
		    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>                  
		                case Mensaje_TOAST:
		                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
		                    Toast.LENGTH_SHORT).show();
		                    break;
		     //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
		                case MESSAGE_Desconectado:	
		                  	 if(D) Log.e("Conexion","DESConectados");
		                  	 seleccionador=false;             	
		          break;
		    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  
		                }//FIN DE SWITCH CASE PRIMARIO DEL HANDLER
		            }//FIN DE METODO INTERNO handleMessage
		        };//Fin de Handler


}
