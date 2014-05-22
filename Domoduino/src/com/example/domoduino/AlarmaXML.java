package com.example.domoduino;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import android.content.Context;
import android.provider.DocumentsContract.Document;
import android.util.Log;

public class AlarmaXML 
{
	private static String FICHERO = "alarmas.xml";
	private Context contexto;
	private org.w3c.dom.Document documento;
	private boolean cargadoDocumento;
	
	public AlarmaXML(Context contexto)
	{
		this.contexto = contexto;
		cargadoDocumento = false;
	}
	
	public void nuevaAlarma(Alarma a ) {

	       Element alarma = documento.createElement("alarma");

	      // alarma.setAttribute("id", a.id);
	       alarma.setAttribute("id", String.valueOf(a.getIdAlarma()));

	       Element e_nombre = documento.createElement("nombre");

	       Text texto = documento.createTextNode(a.getNombreAlarma());

	       e_nombre.appendChild(texto);

	       alarma.appendChild(e_nombre);
	     
	       
	       Element e_hora = documento.createElement("hora");

	       texto = documento.createTextNode(a.getHoraAlarma());

	       e_hora.appendChild(texto);

	       alarma.appendChild(e_hora);
	       
	       
	       Element e_min = documento.createElement("min");

	       texto = documento.createTextNode(a.getMinAlarma());

	       e_min.appendChild(texto);

	       alarma.appendChild(e_min);
	       
	       
	       Element e_accion = documento.createElement("accion");

	       texto = documento.createTextNode(String.valueOf(a.getAccionAlarma()));

	       e_accion.appendChild(texto);

	       alarma.appendChild(e_accion);
	       
	       

	       Element e_activada = documento.createElement("activada");
 
 	       texto = documento.createTextNode(String.valueOf(a.getActivada()));
 
 	       e_activada.appendChild(texto);
 
 	       alarma.appendChild(e_activada);
 
	       

	       Element raiz = documento.getDocumentElement();

	       raiz.appendChild(alarma);

	}
	
	public void guardarAlarma (Alarma a) 
	{
		try
		{
			if(!cargadoDocumento)
			{
				leerXML(contexto.openFileInput(FICHERO));
			}
		}	
		catch(FileNotFoundException e)
		{
			crearXML();
		}
		catch (Exception e)
		{
			Log.e("AlarmaXML", e.getMessage(), e);
		}
		
		nuevaAlarma(a);
		
		Log.i("Domoduino","Nueva alarma");
	
		try
		{
			escribirXML(contexto.openFileOutput(FICHERO, Context.MODE_PRIVATE));
			Log.i("Domoduino","Alarma guardada");
		}
		catch(Exception e)
		{
			Log.e("AlarmaXML", e.getMessage(),e);
		}
	}
		
	public boolean cancelarAlarma (Alarma a) {
			return false;
		}
	
	public boolean eliminarAlarma (int idAlarma)
		{
			String id = null;
			boolean borrada = false;
			int i=0;
			
			if(!cargadoDocumento)
			{
				try 
				{
					leerXML(contexto.openFileInput(FICHERO));
				}
				catch (FileNotFoundException e) 
				{
					return borrada;
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
			Element raiz = documento.getDocumentElement();
		
			NodeList alarmas = raiz.getElementsByTagName("alarma");
		
			while(!borrada || i < alarmas.getLength())
			{
				Node alarma = alarmas.item(i);
				
				 Element alarma2 = (Element) alarmas.item(i);
	             id= alarma2.getAttribute("id");
	             
	             if (Integer.valueOf(id) == idAlarma)
	             {
	            	 try 
	            	 {
	            		raiz.removeChild(alarma);
						escribirXML(contexto.openFileOutput(FICHERO, Context.MODE_PRIVATE));
						borrada=true;
						
					 }
	            	 catch (FileNotFoundException e)
	            	 {
						e.printStackTrace();
					 }
	            	 catch (Exception e)
	            	 {
						e.printStackTrace();
	            	 }
	             }
	             
	             i++;      
			}
	return borrada;
	}
		
	public Vector<Alarma> listadoAlarmas()
	{
		if(!cargadoDocumento)
		{
			try 
			{
				if(contexto.getFileStreamPath(FICHERO).exists())
				{
					leerXML(contexto.openFileInput(FICHERO));
				}
				else
				{
					return null;
				}
				
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		Vector<Alarma> result = new Vector<Alarma>();
	
		String nombre = null, hora = null, min= null, accion = null, activada=null, id=null;
	
		Element raiz = documento.getDocumentElement();
	
		NodeList alarmas = raiz.getElementsByTagName("alarma");
	
		for(int i = 0; i < alarmas.getLength(); i++)
		{
			Node alarma = alarmas.item(i);
			
			 Element alarma2 = (Element) alarmas.item(i);
             id= alarma2.getAttribute("id");
             
	        NodeList propiedades = alarma.getChildNodes();
	        for(int j = 0; j < propiedades.getLength(); j++) 
			{
			    Node propiedad = propiedades.item(j);
			    String etiqueta = propiedad.getNodeName();
			    if(etiqueta.equals("nombre")) 
			    {
			    	nombre = propiedad.getFirstChild().getNodeValue();
			    }
			    else if(etiqueta.equals("hora"))
			    {
			        hora= propiedad.getFirstChild().getNodeValue();
			    }
			    else if(etiqueta.equals("min"))
			    {
			        min= propiedad.getFirstChild().getNodeValue();
			    }
			    else if(etiqueta.equals("accion"))
			    {
			         accion = propiedad.getFirstChild().getNodeValue();
			    }

			    else if(etiqueta.equals("activada"))
 			    {
 			    	activada = propiedad.getFirstChild().getNodeValue();
 			    }
			}
	
			 result.add(new Alarma (Integer.valueOf(id),nombre,hora,min,Integer.valueOf(accion),Boolean.valueOf(activada))); //DeStringADate(hora),Integer.valueOf(accion)));
		 }
		
		
	return result;
	}
	
	public void crearXML()
	{
		
		try
		{
			DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructor = fabrica.newDocumentBuilder();
			documento = constructor.newDocument();
			Element raiz = documento.createElement("lista_alarmas");
			documento.appendChild(raiz);
			cargadoDocumento = true;
		}
		catch(Exception e)
		{
			Log.e("AlarmaXML", e.getMessage(), e);

		}
	}
	
	public void leerXML(InputStream entrada) throws Exception 
	{

	       DocumentBuilderFactory fabrica =

	DocumentBuilderFactory.newInstance();

	       DocumentBuilder constructor = fabrica.newDocumentBuilder();

	       documento = constructor.parse(entrada);

	       cargadoDocumento = true;

	}
		
	public void escribirXML(OutputStream salida) throws Exception 
	{
	       TransformerFactory fabrica = TransformerFactory.newInstance();

	       Transformer transformador = fabrica.newTransformer();

	       transformador.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");

	       transformador.setOutputProperty(OutputKeys.INDENT, "yes");

	       DOMSource fuente = new DOMSource(documento);

	       StreamResult resultado = new StreamResult(salida);

	       transformador.transform(fuente, resultado);
	}
	
	public Vector<String> aVectorString() {

	       Vector<String> result = new Vector<String>();

	       String nombre = "", hora = "", accion= "", min="", id="", activada="";

	       Element raiz = documento.getDocumentElement();

	       NodeList alarmas = raiz.getElementsByTagName("alarma");
//	       Element alarmaEle = (Element) raiz.getElementsByTagName("alarma");
//	        id = alarmaEle.getAttribute("id");
//	        alarmaEle.
	       

	       for(int i = 0; i < alarmas.getLength(); i++) {

	             Node alarma = alarmas.item(i);
	             
	             Element alarma2 = (Element) alarmas.item(i);
	             id= alarma2.getAttribute("id");
	             
	             NodeList propiedades = alarma.getChildNodes();

	             for(int j = 0; j < propiedades.getLength(); j++) 
	             {
	                    Node propiedad = propiedades.item(j);

	                    String etiqueta = propiedad.getNodeName();

	                    if(etiqueta.equals("nombre")) 
	                    {
	                       nombre = propiedad.getFirstChild().getNodeValue();

	                    }
	                    else if(etiqueta.equals("min"))
	                    {
	                        min= propiedad.getFirstChild().getNodeValue();
	                    }
	                    else if(etiqueta.equals("hora"))
	                    {
	                        hora= propiedad.getFirstChild().getNodeValue();
	                    }
	                    else if(etiqueta.equals("accion"))
	                    {
	                    	accion = propiedad.getFirstChild().getNodeValue();
	                    }
	                    else if(etiqueta.equals("activada"))
	                    {	                    
	                    	activada = propiedad.getFirstChild().getNodeValue();
	                   	}
	             }

	             result.add(id + " " + nombre + " " + hora + " " + min + " " + accion +  " " + activada);

	       }

	       return result;

	}
	
	public Vector<String> listaAlarmas()  
    {
        try 
        {
            if (!cargadoDocumento) 
            {
                leerXML(contexto.openFileInput (FICHERO));
            }
        } 
        catch (FileNotFoundException e) 
        {
            crearXML();

        } 
        catch (Exception e) 
        {
            Log.e("Domoduino", e.getMessage(), e);
        }
        
        return aVectorString();

  }
	
//	private String DeHoraAString (Date date) 
//	{
//		 	 
//				// Date puede se convertido a String con el método toString()
//				// Usa una sintaxis general del tipo DD MM dd HH:mm:ss
//				/*Date date = new Date();
//				System.out.println(date.toString());
//		 
//				// Se pueden definir formatos diferentes con la clase DateFormat
//				// Obtenemos la fecha y la hora con el formato yyyy-MM-dd HH:mm:ss
//				DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String convertido = fechaHora.format(date);
//				System.out.println(convertido);
//		 
//				// Obtenemos solamente la fecha pero usamos slash en lugar de guiones
//				DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
//				convertido = fecha.format(date);
//				System.out.println(convertido);*/
//		 
//				// Tambien se puede obtener solo la hora
//				DateFormat hora = new SimpleDateFormat("HH:mm:ss");
//				String convertido = hora.format(date);
//				//System.out.println(convertido);
//				return convertido;
//		 
//	}
	
//	public Date DeStringADate(String fecha)
//	{
//        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
//        String strFecha = fecha;
//        Date fechaDate = null;
//        try {
//            fechaDate = formato.parse(strFecha);
//                        //System.out.println(fechaDate.toString());
//            return fechaDate;
//        } 
//        catch (ParseException ex)
//        {
//            ex.printStackTrace();
//            return fechaDate;
//        }
//    }
	
//	public boolean existeFichero ()
//	{
//		if (contexto.getFileStreamPath(FICHERO).exists())
//		{
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}
}


