package com.example.domoduino;

import java.util.Date;

import android.R.bool;

public class Alarma 
{
	private int id;
	private String nombre;
	//private Date hora;
	private String hora;
	private String min;
	private int accion;
	private final static int START = 1;
	private final static int STOP = 2;
	private final static int LUZ = 3;
	private final static int LUZ_APAGADA = 4;
	
	public Alarma (int id, String nombre,String hora, String min, int accion)
	{
		this.id=id;
		this.nombre = nombre;
		this.hora = hora;
		this.min = min;
		this.accion = accion;
	}
	public int getIdAlarma()
	{
		return (this.id);
	}
	
	public String getNombreAlarma()
	{
		return (this.nombre);
	}
	
	public String getHoraAlarma ()
	{
		return (this.hora);
	}
	
	public String getMinAlarma ()
	{
		return (this.min);
	}
	
	public int getAccionAlarma ()
	{
		return (this.accion);
	}
	public void setIdAlarma (int id)
	{
		this.id = id;
	}
	
	public void setNombreAlarma (String nombre)
	{
		this.nombre = nombre;
	}
	
	public void setHoraAlarma (String hora)
	{
		this.hora = hora;
	}
	
	public void setMinAlarma (String min)
	{
		this.min = min;
	}
	
	public void setAccionAlarma (int accion)
	{
		this.accion = accion;
	}
}
