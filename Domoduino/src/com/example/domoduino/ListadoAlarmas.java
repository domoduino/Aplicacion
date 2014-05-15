package com.example.domoduino;

import java.util.ArrayList;
import java.util.Calendar;
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
	ArrayList<Entrada_lista> datos = new ArrayList<Entrada_lista>();  
	
	protected static final int CONTEXTMENU_OPTION1 = 1;
	protected static final int CONTEXTMENU_OPTION2 = 2;
	private Adaptador_listado adapListado =null;
	ListView lista=null;
	
	private ImageButton bt1;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj);
        
        imagen_plus =(ImageButton) findViewById(R.id.imageView_imagen);
        imagen_plus.setOnClickListener(imagenPlus);

        logica = new LogicaAlarma(getApplicationContext());
       // logica.guardarAlarma(new Alarma(20,"Alarma 1","8","45",1));
        
        //logica.eliminarAlarma(1); SI borra
        
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

        
        
        lista = (ListView) findViewById(R.id.ListView_listado);
        
         adapListado = new Adaptador_listado(this, R.layout.entrada_lista, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
				// TODO Auto-generated method stub
				TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
	            texto_superior_entrada.setText(((Entrada_lista) entrada).get_horaAlarma()); 

	            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
	            texto_inferior_entrada.setText(((Entrada_lista) entrada).get_nombreAlarma()); 

	            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
	            imagen_entrada.setImageResource(((Entrada_lista) entrada).get_idImagen());
				
			}};
        lista.setAdapter(adapListado);
		
        lista.setOnItemClickListener(lista1);
        registerForContextMenu(lista);
        
       // logica.eliminarAlarma(1); No borra
        
        
        //cambio de imagen
        bt1=(ImageButton) findViewById(R.id.imageViewA);
		bt1.setOnClickListener(btn1);
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
	 public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) { 
	     super.onCreateContextMenu(menu, v, menuInfo); 
	  
	   ListView listView = (ListView) v;
	
	   	// Get the list item position    
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
	    int position = info.position;  
	   
	    Entrada_lista item = (Entrada_lista) listView.getItemAtPosition(position);
	    
	    idAlarmaABorrar = item.get_idAlarma();
	    nombreAlarma = item.get_nombreAlarma();
	    horaEntera = item.get_horaAlarma();
	    accion = item.get_accion();
	    
	    
	    // Set title for the context menu
	   menu.setHeaderTitle(item.get_nombreAlarma()); 
	  
	     // Add all the menu options
	     menu.add(Menu.NONE, CONTEXTMENU_OPTION1, 0, "Editar"); 
	     menu.add(Menu.NONE, CONTEXTMENU_OPTION2, 1, "Eliminar"); 
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
	private ImageButton.OnClickListener btn1 = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
			Bitmap bmp =  BitmapFactory.decodeResource(getResources(), R.drawable.alarmaverde);
			bt1.setImageBitmap(bmp);
			
			//activar la arlarmaaaa, se la mandamos al arduinooooo
			Calendar now = Calendar.getInstance();
			int hora=now.get(Calendar.HOUR_OF_DAY);
			int minutos=now.get(Calendar.MINUTE);
		}
	};


	
//	public void onBackPressed()
//	{
//		this.startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
//	}
	

}
