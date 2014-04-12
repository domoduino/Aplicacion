package com.example.domoduino;

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
	
	
	

}
