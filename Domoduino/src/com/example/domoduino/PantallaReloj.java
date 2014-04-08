package com.example.domoduino;

import java.util.ArrayList;

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
	ArrayList<Entrada_lista> datos = new ArrayList<Entrada_lista>();  
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj);
        
        imagen_plus =(ImageButton) findViewById(R.id.imageView_imagen);
        imagen_plus.setOnClickListener(imagenPlus);

        datos.add(new Entrada_lista(R.drawable.alarma, "8:45", "Alarma 1"));
        datos.add(new Entrada_lista(R.drawable.alarma, "10:30", "Alarma 2"));
        datos.add(new Entrada_lista(R.drawable.alarma, "11:50", "Alarma 3"));
        
        //Nuevas alarmas creadas
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
        	String nomAlarma = bundle.getString("nombreAlarma");
	    	
	    	//String tama�o = Integer.toString(datos.size());
	    	
	    	for(int i=0; i<datos.size();i++)
	    	{
	    		if(datos.get(i).get_nombreAlarma().equals(nomAlarma))
	        	{
	        		datos.get(i).set_horaAlarma(bundle.getString("hora")+":"+ bundle.getString("minutos"));
	        		Toast toast1 = Toast.makeText(getApplicationContext(), datos.get(i).get_nombreAlarma(), Toast.LENGTH_LONG);
	    	    	toast1.show();
	        	}
//     MIRAR BUCLE
//	        	else
//	        	{
//	        		datos.add(new Entrada_lista(R.drawable.alarma, bundle.getString("hora")+":"+ bundle.getString("minutos"), "Alarma 4"));
//	        	}
	    	}

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
			 //CharSequence texto = "Seleccionado: " + la.get_horaAlarma();
             //Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
             //toast.show();
             Intent i = new Intent(getApplicationContext(), PantallaAlarma.class);
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
	

}
