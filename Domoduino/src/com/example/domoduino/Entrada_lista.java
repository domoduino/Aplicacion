package com.example.domoduino;

public class Entrada_lista
{
	private int idImagen; 
	private String horaAlarma; 
	private String nombreAlarma; 

	public Entrada_lista (int idImagen, String horaAlarma, String nombreAlarma) { 
	    this.idImagen = idImagen; 
	    this.horaAlarma = horaAlarma; 
	    this.nombreAlarma = nombreAlarma; 
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
	
	public void set_idImagen() { 
	    this.idImagen=idImagen;
	}

	public void set_nombreAlarma() { 
	    this.nombreAlarma=nombreAlarma;
	}

	public void set_horaAlarma() {
	    this.idImagen=idImagen;
	} 


}
