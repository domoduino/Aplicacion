package com.example.domoduino;

public class Entrada_lista
{
	private int idAlarma;
	private int idImagen; 
	private String horaAlarma; 
	private String nombreAlarma; 

	public Entrada_lista (int idAlarma,int idImagen, String horaAlarma, String nombreAlarma) { 
	    this.idImagen = idImagen; 
	    this.horaAlarma = horaAlarma; 
	    this.nombreAlarma = nombreAlarma; 
	    this.idAlarma = idAlarma;
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
