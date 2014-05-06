package com.example.domoduino;

import java.util.Calendar;
import java.util.Vector;

import android.content.Context;

public class LogicaAlarma 
{
	AlarmaXML alarmaXML = null;
	
	public LogicaAlarma(Context c)
	{
		alarmaXML = new AlarmaXML(c);
	}
	
	public void guardarAlarma (Alarma a)
	{
		alarmaXML.guardarAlarma(a);
	}
	
	public boolean eliminarAlarma(int id)
	{
		return alarmaXML.eliminarAlarma(id);
	}
	
	public void modificarAlarma (int idAlarmaAnt, Alarma aNueva)
	{
		alarmaXML.eliminarAlarma(idAlarmaAnt);
		alarmaXML.guardarAlarma(aNueva);
	}
	
	public Vector<Alarma> alarmas()
	{
		return alarmaXML.listadoAlarmas();
	}
	
	public int obtenerId()
	{
		//return (alarmaXML.numAlarmas()+1);
		return alarmas().size()+1;
	}
	public Alarma getProximaAlarma()
	{
		Vector<Alarma> listadoAlarma= alarmaXML.listadoAlarmas();
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
		
		return listadoAlarma.get(j);
		
		
		
	}
	
	

}
