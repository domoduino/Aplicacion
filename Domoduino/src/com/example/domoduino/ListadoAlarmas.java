package com.example.domoduino;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListadoAlarmas extends Activity
{
	
	/********************CONEXIÓN******************************/
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
	
   /*****************CONEXIÓN************************/
	
	
	private ImageButton imagen_plus;
	private static LogicaAlarma logica;
	private int idAlarmaABorrar = -1;
	private String horaEntera = "";
	private String nombreAlarma = "";
	private int accion = 0;
	public  Entrada_lista itemSeleccionado = null;
	ArrayList<Entrada_lista> datos = new ArrayList<Entrada_lista>();  
	
	protected static final int CONTEXTMENU_OPTION1 = 1;
	protected static final int CONTEXTMENU_OPTION2 = 2;
	protected static final int CONTEXTMENU_OPTION3 = 3;
	private Adaptador_listado adapListado =null;
	ListView lista=null;
	
	private ImageView bt1;
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj);
        
        imagen_plus =(ImageButton) findViewById(R.id.img_plus);
        imagen_plus.setOnClickListener(imagenPlus);
        
        //Crear conexión
		 AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
		 Servicio_BT = new ConexionBT(this, mHandler);
		 BluetoothDevice device = AdaptadorBT.getRemoteDevice("00:13:12:16:63:31");
         Servicio_BT.connect(device);

        logica = new LogicaAlarma(getApplicationContext());
            
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setOnItemClickListener(lista1);
        
        listar();
        
        registerForContextMenu(lista);   
	 }  
	
	public void listar()
	{
		 Vector<Alarma> alarmas = logica.alarmas();		 
	     
	        if(alarmas!=null)
	        {
	        	int imagenAlarma = R.drawable.alarma;	
	        	
	        	for(int i=0; i< alarmas.size();i++)
			    {
			        	
	        		if(alarmas.get(i).getActivada())
		        	{
		        		imagenAlarma = R.drawable.alarmaverde;
		        	}
		        	else
		        	{
		        		imagenAlarma = R.drawable.alarma;
		        	}	
		        		
		        	datos.add(new Entrada_lista(alarmas.get(i).getIdAlarma(),imagenAlarma, alarmas.get(i).getHoraAlarma()+":"+ alarmas.get(i).getMinAlarma(), alarmas.get(i).getNombreAlarma(),alarmas.get(i).getAccionAlarma()));
			    }
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), "no hay alarmas", Toast.LENGTH_LONG).show();
	        }
		
		adapListado = new Adaptador_listado(this, R.layout.entrada_lista, datos)
		{
			@Override
			public void onEntrada(Object entrada, View view) 
			{
				// TODO Auto-generated method stub
				TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
	            texto_superior_entrada.setText(((Entrada_lista) entrada).get_horaAlarma()); 

	            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
	            texto_inferior_entrada.setText(((Entrada_lista) entrada).get_nombreAlarma()); 

	            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.im_reloj); 
	            imagen_entrada.setImageResource(((Entrada_lista) entrada).get_idImagen());
				
			}};
       lista.setAdapter(adapListado);
	}
	

	private OnItemClickListener lista1 = new ListView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			 Entrada_lista la = (Entrada_lista) arg0.getItemAtPosition(position);
             Intent i = new Intent(getApplicationContext(), PantallaAlarma.class);
             i.putExtra("idAlarma", la.get_idAlarma());
             i.putExtra("nombreAlarma", la.get_nombreAlarma());
         	 i.putExtra("horaEntera", la.get_horaAlarma());
         	 i.putExtra("accion", la.get_accion());
        	 startActivity(i);	
        	 finish();
		}
	 };
	 
	
	 @Override 
	 public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	 { 
	     super.onCreateContextMenu(menu, v, menuInfo); 
	     
	     ListView listView = (ListView) v;
	
	   	// Get the list item position    
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
	    int position = info.position;  
	   
	    itemSeleccionado = (Entrada_lista) listView.getItemAtPosition(position);
	    
	     
	    idAlarmaABorrar = itemSeleccionado.get_idAlarma();
	    nombreAlarma = itemSeleccionado.get_nombreAlarma();
	    horaEntera = itemSeleccionado.get_horaAlarma();
	    accion = itemSeleccionado.get_accion();

	    // Set title for the context menu
	   menu.setHeaderTitle(itemSeleccionado.get_nombreAlarma()); 
	  
	     // Add all the menu options
	     menu.add(Menu.NONE, CONTEXTMENU_OPTION1, 0, "Editar"); 
	     menu.add(Menu.NONE, CONTEXTMENU_OPTION2, 1, "Eliminar"); 
	     menu.add(Menu.NONE, CONTEXTMENU_OPTION3, 2, "Activar");  
	 } 
	 
	 
	 @Override 
	 public boolean onContextItemSelected(MenuItem item) 
	 { 
		 // Get extra info about list item that was long-pressed
	     AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
	         
	     // Perform action according to selected item from context menu
	     switch (item.getItemId()) 
	     {
	  
		     case CONTEXTMENU_OPTION1:
		    	 Intent i = new Intent(getApplicationContext(), PantallaAlarma.class);
	             i.putExtra("idAlarma", idAlarmaABorrar);
	             i.putExtra("nombreAlarma", nombreAlarma);
	         	 i.putExtra("horaEntera", horaEntera);
	         	 i.putExtra("accion", accion);
	        	 startActivity(i);
	        	 finish();
		    	 
		         break;
	         
	  
		     case CONTEXTMENU_OPTION2:
		         boolean b = logica.eliminarAlarma(idAlarmaABorrar); 
		         
		         adapListado.clear();
		         adapListado.notifyDataSetChanged();
	
		         Intent i1 = new Intent(getApplicationContext(), ListadoAlarmas.class);
		       	 startActivity(i1);
		       	finish();
		         
		    	 break;
	    	 
	    	 
		     case CONTEXTMENU_OPTION3:
		    	 
		    	//Modifica la alarma activada en el fichero XML
		    	 
		    	 String[] values = itemSeleccionado.get_horaAlarma().split(":", 2);
			     
		    	 logica.setActivada(new Alarma(itemSeleccionado.get_idAlarma(),itemSeleccionado.get_nombreAlarma(), values[0], values[1], itemSeleccionado.get_accion(), true));
		    	
		    	 //Enviar la hora al arduino (horas y minitos que faltan)
		    	
		    	 //Hora y minutos actuales
		    	 
		    	 Calendar now = Calendar.getInstance();
		 		
		    	 int horaActual = now.get(Calendar.HOUR_OF_DAY);
		    	 int minutosActual = now.get(Calendar.MINUTE);
				
		    	 
		    	 //Hora y minutos para los que está programada la alarma
				 int horaAlarma = Integer.parseInt(values[0]);
			     int minutosAlarma = Integer.parseInt(values[1]);
		    	
		    	 
				//Calculo de las horas que faltan desde la hora actual hasta la hora a la que está programada la alarma
				int horaArd = - 1;
				
				if(horaAlarma > horaActual)
				{
					 horaArd = horaAlarma - horaActual;
				}
				else if(horaAlarma>12)
				{
					horaArd = 24 - (horaActual - horaAlarma);
				}
				else
				{
					horaArd = (24 - horaActual) + horaAlarma;
				}
				
				int minsArduino = Math.abs (minutosAlarma - minutosActual);
				
				//Envío a ARDUINO
				switch(minsArduino) {
				 case 1: 
					 sendMessage("F\r");
				     break;
				 case 2: 
					 sendMessage("G\r");
				     break;
				 case 3: 
					 sendMessage("H\r");
				     break;
				 case 4: 
					 sendMessage("I\r");
				     break;
				 case 5: 
					 sendMessage("J\r");
					 break;
				 case 6: 
					 sendMessage("K\r");
				     break;
				 case 7:
					 sendMessage("L\r");
					 break;
				 case 8:
					 sendMessage("M\r");
					 break;
				 case 9:
					 sendMessage("N\r");
					 break;
				 case 10:
					 sendMessage("Ñ\r");
					 break;
				 case 20:
					 sendMessage("O\r");
					 break;
				 case 30:
					 sendMessage("P\r");
					 break;
				}
				
				//Cambio la imagen asociada a la alarma por un reloj verde, que indica que la alarma está activada
		        
				Log.i("ListadoAlarmas","item:" + itemSeleccionado.get_horaAlarma());
				itemSeleccionado.set_idImagen(R.drawable.alarmaverde);

				datos.clear();
				listar();
				
			
		    	 break;
	     }
	  
	     return true; 
	 }  
	 
	
	private ImageButton.OnClickListener imagenPlus = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
			Intent i = new Intent(getApplicationContext(), PantallaAlarma.class);
       	    startActivity(i);
       	    finish();
		}
	};
	
	public  void sendMessage(String message) {
        if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED)  //comprueba si está conectado a la tarjeta bluetooth
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
					     //Toast.makeText(getApplicationContext(), "Conectado con "+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
					     seleccionador=true;
		                    break;
		                     
		                case Mensaje_TOAST:
		                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
		                    Toast.LENGTH_SHORT).show();
		                    break;
		    
		                case MESSAGE_Desconectado:	
		                  	 if(D) Log.e("Conexion","DESConectados");
		                  	 seleccionador=false;             	
		                  	 break;
		   
		                }
		            }
		        };
	
		       
		      @Override
		      public void onDestroy(){
		        	 super.onDestroy();
		        	 if (Servicio_BT != null) Servicio_BT.stop();//Detenemos servicio
		        }

}
