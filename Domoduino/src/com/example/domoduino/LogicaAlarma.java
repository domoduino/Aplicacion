package com.example.domoduino;

import java.util.Calendar;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

public class LogicaAlarma 
{
	AlarmaXML alarmaXML = null;
	
	public LogicaAlarma(Context c)
	{
		alarmaXML = new AlarmaXML(c);
	}
	
	// Guarda la alarma
	public void guardarAlarma (Alarma a) 
	{
		alarmaXML.guardarAlarma(a);
	}
	
	// Elimina la alarma
	public boolean eliminarAlarma(int id)
	{
		return alarmaXML.eliminarAlarma(id);
	}
	
	// Modifica la alarma
	public void modificarAlarma (int idAlarmaAnt, Alarma aNueva)
	{
		alarmaXML.eliminarAlarma(idAlarmaAnt);
		alarmaXML.guardarAlarma(aNueva);
	}
	
	// Devuelve un vector con todas las alarmas
	public Vector<Alarma> alarmas()
	{
		return alarmaXML.listadoAlarmas();
	}
	
	// Devuelve el identificador para la nueva alarma
	public int obtenerId()
	{
		return alarmas().size()+1;
	}
	
	public Alarma getProximaAlarma()
	{
		Alarma a = null;
		
		Vector<Alarma> listadoAlarma= alarmaXML.listadoAlarmas();
		
		if(listadoAlarma != null)
		{
		
		Calendar now = Calendar.getInstance();
		
		int hora=now.get(Calendar.HOUR_OF_DAY);
		int minutos=now.get(Calendar.MINUTE);
		int minh=0;
		int minm=0;
		int j = 0;
		for(int i=0; i<listadoAlarma.size(); i++)
		{
			int h= hora - Integer.parseInt((listadoAlarma.get(i).getHoraAlarma()));
			int m= minutos - Integer.parseInt((listadoAlarma.get(i).getMinAlarma()));
			
			if(minh>=h && minm>m)
			{
				minh=h;
				minm=m;
				j=i;
			}
		}
	
		a= listadoAlarma.get(j);	
		
		}
		return a;
	}
	
	// Devuelve la alarma activada
	public Alarma getActivada()
	{
		Alarma a = null;
		Vector<Alarma> alarmas = alarmaXML.listadoAlarmas();

		boolean encontrado = false;
		int i = 0;

		if(alarmas !=null)
		{
		
			while (!encontrado && i < alarmas.size())
			{
				if(alarmas.get(i).getActivada() == true)
				{
					encontrado = true;	
					a = alarmas.get(i);
				}
	
				i++;	
			}
		}

		return (a);
	}

	//Desactiva la anterior alarma activada y activa la alarma indicada por el usuario
	public void setActivada (Alarma nuevaActivada)
	{
		Vector<Alarma> alarmas = alarmaXML.listadoAlarmas();

		Alarma a = getActivada();

		if (a != null)
		{
			modificarAlarma(a.getIdAlarma(), new Alarma(a.getIdAlarma(), a.getNombreAlarma(), a.getHoraAlarma() , a.getMinAlarma(), a.getAccionAlarma(), false));
		}
		
		modificarAlarma(nuevaActivada.getIdAlarma(), nuevaActivada);
	}
}
