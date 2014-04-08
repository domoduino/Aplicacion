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
	
	public void guardarAlarmar (Alarma a)
	{
		alarmaXML.guardarAlarma(a);
	}
	
	public Vector<Alarma> alarmas()
	{
		return alarmaXML.listadoAlarmas();
	}
	
	public int obtenerId()
	{
		return (alarmaXML.numAlarmas()+1);
	}

}
