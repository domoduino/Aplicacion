package com.example.domoduino;

public class Alarma 
{
	private int id;
	private String nombre;
	private String hora;
	private String min;
	private int accion;
	private boolean activada;
	
	public Alarma (int id, String nombre,String hora, String min, int accion, boolean activada)
	{
		this.id=id;
		this.nombre = nombre;
		this.hora = hora;
		this.min = min;
		this.accion = accion;
		this.activada = activada;
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
	
	public boolean getActivada()
	{
		return (this.activada);
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
	
	public void setActivada(boolean estado)
	{
		this.activada = estado;
	}
}
