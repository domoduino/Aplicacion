package com.example.domoduino;

public class Entrada_lista
{
	private int idAlarma;
	private int idImagen; 
	private String horaAlarma; 
	private String nombreAlarma;
	private int accion;

	public Entrada_lista (int idAlarma,int idImagen, String horaAlarma, String nombreAlarma,int accion) 
	{ 
	    this.idImagen = idImagen; 
	    this.horaAlarma = horaAlarma; 
	    this.nombreAlarma = nombreAlarma; 
	    this.idAlarma = idAlarma;
	    this.accion = accion;
	}
	
	public int get_idAlarma()
	{
		return idAlarma;
	}
	
	public String get_horaAlarma() { 
	    return horaAlarma; 
	}

	public String get_nombreAlarma() { 
	    return nombreAlarma; 
	}

	public int get_idImagen() {
	    return idImagen;
	} 
	
	public int get_accion()
	{
		return accion;
	}
	
	public void set_accion(int accion)
	{
		this.accion = accion;
	}
	
	public void set_idImagen(int idImagen) { 
	    this.idImagen=idImagen;
	}

	public void set_nombreAlarma(String nombreAlarma) { 
	    this.nombreAlarma=nombreAlarma;
	}

	public void set_horaAlarma(String horaAlarma) {
	    this.horaAlarma=horaAlarma;
	} 


}
