package com.example.domoduino;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
	private ImageButton imagen_plus;
	private static LogicaAlarma logica;
	private int idAlarmaABorrar = -1;
	private String horaEntera = "";
	private String nombreAlarma = "";
	private int accion = 0;
	private View vistaSeleccionada= null;
	public Entrada_lista item2 = null;
	ArrayList<Entrada_lista> datos = new ArrayList<Entrada_lista>();  
	
	protected static final int CONTEXTMENU_OPTION1 = 1;
	protected static final int CONTEXTMENU_OPTION2 = 2;
	protected static final int CONTEXTMENU_OPTION3 = 3;
	private Adaptador_listado adapListado =null;
	ListView lista=null;
	
	private ImageView bt1;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj);
        
        imagen_plus =(ImageButton) findViewById(R.id.img_plus);
        imagen_plus.setOnClickListener(imagenPlus);

        logica = new LogicaAlarma(getApplicationContext());
        //logica.guardarAlarma(new Alarma(20,"Alarma 1","8","45",1));
        //logica.eliminarAlarma(1); SI borra
        
//        Vector<Alarma> alarmas = logica.alarmas();
// 
//        
//        if(alarmas!=null)
//        {
//	        	for(int i=0; i< alarmas.size();i++)
//		        {
//		        	datos.add(new Entrada_lista(alarmas.get(i).getIdAlarma(),R.drawable.alarma, alarmas.get(i).getHoraAlarma()+":"+ alarmas.get(i).getMinAlarma(), alarmas.get(i).getNombreAlarma(),alarmas.get(i).getAccionAlarma()));
//		        }
//        }
//        else
//        {
//        	Toast.makeText(getApplicationContext(), "no hay alarmas", Toast.LENGTH_LONG).show();
//        }

        
        
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setOnItemClickListener(lista1);
        
        listar();
//         adapListado = new Adaptador_listado(this, R.layout.entrada_lista, datos){
//			@Override
//			public void onEntrada(Object entrada, View view) {
//				// TODO Auto-generated method stub
//				TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
//	            texto_superior_entrada.setText(((Entrada_lista) entrada).get_horaAlarma()); 
//
//	            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
//	            texto_inferior_entrada.setText(((Entrada_lista) entrada).get_nombreAlarma()); 
//
//	            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.im_reloj); 
//	            imagen_entrada.setImageResource(((Entrada_lista) entrada).get_idImagen());
//				
//			}};
//        lista.setAdapter(adapListado);
		
       
        registerForContextMenu(lista);
        
       // logica.eliminarAlarma(1); No borra
        
        
	 }  
	
	public void listar()
	{
		 Vector<Alarma> alarmas = logica.alarmas();
		 
	        
	        if(alarmas!=null)
	        {
		        	for(int i=0; i< alarmas.size();i++)
			        {
			        	datos.add(new Entrada_lista(alarmas.get(i).getIdAlarma(),R.drawable.alarma, alarmas.get(i).getHoraAlarma()+":"+ alarmas.get(i).getMinAlarma(), alarmas.get(i).getNombreAlarma(),alarmas.get(i).getAccionAlarma()));
			        }
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), "no hay alarmas", Toast.LENGTH_LONG).show();
	        }
		
		adapListado = new Adaptador_listado(this, R.layout.entrada_lista, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
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
	   
	    Entrada_lista itemSeleccionado = (Entrada_lista) listView.getItemAtPosition(position);
	    
	    item2 = itemSeleccionado; 
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
	 public boolean onContextItemSelected(MenuItem item) { 
 
		 // Get extra info about list item that was long-pressed
	     AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
	     
	     
	     // Perform action according to selected item from context menu
	     switch (item.getItemId()) {
	  
	     case CONTEXTMENU_OPTION1:
	         // Show message
	    	 Intent i = new Intent(getApplicationContext(), PantallaAlarma.class);
             i.putExtra("idAlarma", idAlarmaABorrar);
             i.putExtra("nombreAlarma", nombreAlarma);
         	 i.putExtra("horaEntera", horaEntera);
         	 i.putExtra("accion", accion);
        	 startActivity(i);
        	 finish();
	    	 
	         break;
	         
	  
	     case CONTEXTMENU_OPTION2:
	         // Show message
	         //Toast.makeText(getApplicationContext(), "Option 2: ID "+menuInfo.id+", position "+menuInfo.position, Toast.LENGTH_SHORT).show();
	    	 //Toast.makeText(getApplicationContext(), "id alarma a borrar: " + idAlarmaABorrar, Toast.LENGTH_LONG).show();
	         boolean b = logica.eliminarAlarma(idAlarmaABorrar); 
	         //Toast.makeText(getApplicationContext(),Boolean.toString(b), Toast.LENGTH_LONG).show();
	         
	         adapListado.clear();
	         adapListado.notifyDataSetChanged();

	         Intent i1 = new Intent(getApplicationContext(), ListadoAlarmas.class);
	       	 startActivity(i1);
	       	finish();
	         
	    	 break;
	    	 
	    	 
	     case CONTEXTMENU_OPTION3:
	    	 
	    	//Cambiar el XML
	    	 //Enviar la hora al arduino (horas y minitos que faltan)
	    	 
	    	 Calendar now = Calendar.getInstance();
	 		
			int horaActual = now.get(Calendar.HOUR_OF_DAY);
			int minutosActual = now.get(Calendar.MINUTE);
			
			
	    	String[] retval = horaEntera.split(":", 2);
	    	
	    	int horaAlarma = Integer.parseInt(retval[0]);
			int minutosAlarma = Integer.parseInt(retval[1]);
	    	 
			
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
			
			int segundos = horaArd*3600 + minsArduino*60;
			
			Log.i("ListadoAlarmas", "Horas que quedan para la alarma: " + horaArd + " mins: " + minsArduino);
			Log.i("ListadoAlarmas", "Segundos hasta la alarma: " + segundos);
	    	 
	    	 //Cambiar la imagen
			
			//cambio imagen
	        
			Log.i("ListadoAlarmas","item:" + item2.get_horaAlarma());
			item2.set_idImagen(R.drawable.alarmaverde);
			//adapListado.clear(); //Borrar listado
			listar();
			
			//Entrada_lista item_lista = (Entrada_lista) item;
				
	    	 //item_lista.set_idImagen(R.drawable.alarmaverde);
			
			
			
//			Log.i("ListadoAlarma","Antes get");
//	    	 bt1=(ImageView) findViewById(item_lista.get_idImagen());
//	    	 Log.i("ListadoAlarma","Antes set");
//	    	 bt1.setImageResource(R.drawable.alarmaverde);
//	    	 Log.i("ListadoAlarma","Después set");
	    	 
//	    	 Log.i("ListadoAlarma","Antes ");
//	    	 ImageView imagReloj = (ImageView) vistaSeleccionada.findViewById(R.id.im_reloj); 
//	    	 Log.i("ListadoAlarma","Entre");
//	    	 imagReloj.setImageResource(R.drawable.alarmaverde);
//	    	 Log.i("ListadoAlarma","Después");
	    	 
	    	 
//	    	 bt1=(ImageView) findViewById(R.id.im_reloj);
//	        bt1.setImageResource(R.drawable.alarmaverde);
			
			
//			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.alarmaverde);
//			bt1.setImageBitmap(bmp);
			
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
		}
	};


//	 public  void sendMessage(String message) 
//	 {
//	        if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED) {//checa si estamos conectados a BT   
//		        if (message.length() > 0) 
//		        {   // checa si hay algo que enviar
//		            byte[] send = message.getBytes();//Obtenemos bytes del mensaje
//		            if(D) Log.e(TAG, "Mensaje enviado:"+ message);            
//		                 Servicio_BT.write(send);     //Mandamos a escribir el mensaje     
//		        }
//		     }
//	        else Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
//	    		}
	
//	public void onBackPressed()
//	{
//		this.startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
//	}
	

}
