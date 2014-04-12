package com.example.domoduino;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaReloj extends Activity
{
	private ImageButton imagen_plus;
	private static LogicaAlarma logica;
	ArrayList<Entrada_lista> datos = new ArrayList<Entrada_lista>();  
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj);
        
        imagen_plus =(ImageButton) findViewById(R.id.imageView_imagen);
        imagen_plus.setOnClickListener(imagenPlus);

        logica = new LogicaAlarma(getApplicationContext());
        //logica.guardarAlarma(new Alarma(20,"Alarma 1","8","45",1));
        Vector<Alarma> alarmas = logica.alarmas();
 
        
        if(alarmas!=null)
        {
	        	for(int i=0; i< alarmas.size();i++)
		        {
		        	datos.add(new Entrada_lista(alarmas.get(i).getIdAlarma(),R.drawable.alarma, alarmas.get(i).getHoraAlarma()+":"+ alarmas.get(i).getMinAlarma(), alarmas.get(i).getNombreAlarma()));
		        }
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "no hay alarmas", Toast.LENGTH_LONG).show();
        }

        
        
        ListView lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Adaptador_listado(this, R.layout.entrada_lista, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
				// TODO Auto-generated method stub
				TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
	            texto_superior_entrada.setText(((Entrada_lista) entrada).get_horaAlarma()); 

	            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
	            texto_inferior_entrada.setText(((Entrada_lista) entrada).get_nombreAlarma()); 

	            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
	            imagen_entrada.setImageResource(((Entrada_lista) entrada).get_idImagen());
				
			}
		});
        lista.setOnItemClickListener(lista1);

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
        	 startActivity(i);	
		}
		
	 };
	
	
	private ImageButton.OnClickListener imagenPlus = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
			Intent i = new Intent(getApplicationContext(), PantallaAlarma.class);
       	    startActivity(i);	
		}
	};
	
//	public void onBackPressed()
//	{
//		this.startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
//	}
	

}
