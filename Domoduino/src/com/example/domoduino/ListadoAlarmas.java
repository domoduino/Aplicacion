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
		    	 
		    	//Modifica la alarma activada en el fichero XML
		    	 
		    	 String[] values = itemSeleccionado.get_horaAlarma().split(":", 2);
			     
		    	 logica.setActivada(new Alarma(itemSeleccionado.get_idAlarma(),itemSeleccionado.get_nombreAlarma(), values[0], values[1], itemSeleccionado.get_accion(), true));
		    	
		    	 //Enviar la hora al arduino (horas y minitos que faltan)
		    	
		    	 //Hora y minutos actuales
		    	 
		    	 Calendar now = Calendar.getInstance();
		 		
		    	 int horaActual = now.get(Calendar.HOUR_OF_DAY);
		    	 int minutosActual = now.get(Calendar.MINUTE);
				
		    	 
		    	 //Hora y minutos para los que est� programada la alarma
				 int horaAlarma = Integer.parseInt(values[0]);
			     int minutosAlarma = Integer.parseInt(values[1]);
		    	
		    	 
				//Calculo de las horas que faltan desde la hora actual hasta la hora a la que est� programada la alarma
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
				
				//Cambio la imagen asociada a la alarma por un reloj verde, que indica que la alarma est� activada
		        
				Log.i("ListadoAlarmas","item:" + itemSeleccionado.get_horaAlarma());
				itemSeleccionado.set_idImagen(R.drawable.alarmaverde);
				//adapListado.clear(); //Borrar listado
				datos.clear();
				listar();
				
				//Entrada_lista item_lista = (Entrada_lista) item;
			
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

}
