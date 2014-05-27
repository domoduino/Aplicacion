package com.example.domoduino;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	public void nuevaAlarma(Alarma a ) 
	{
	    //Crea el elemento alarma
		Element alarma = documento.createElement("alarma");

	    //Añade el atributo id a alarma
	    alarma.setAttribute("id", String.valueOf(a.getIdAlarma()));

	    //Añade nombre a alarma  
	    Element e_nombre = documento.createElement("nombre");
	    Text texto = documento.createTextNode(a.getNombreAlarma());
	    e_nombre.appendChild(texto);
	    alarma.appendChild(e_nombre);
	     
	    //Añade el elemento hora a alarma
	    Element e_hora = documento.createElement("hora");
	    texto = documento.createTextNode(a.getHoraAlarma());
	    e_hora.appendChild(texto);
	    alarma.appendChild(e_hora);
	       
	    //Añade el elemento min a alarma
	    Element e_min = documento.createElement("min");
	    texto = documento.createTextNode(a.getMinAlarma());
	    e_min.appendChild(texto);
	    alarma.appendChild(e_min);
	       
	    //Añade el elemento acción a alarma
	    Element e_accion = documento.createElement("accion");
	    texto = documento.createTextNode(String.valueOf(a.getAccionAlarma()));
	    e_accion.appendChild(texto);
	    alarma.appendChild(e_accion);

	    //Añade el elemento activada a alarma
	    Element e_activada = documento.createElement("activada");
 	    texto = documento.createTextNode(String.valueOf(a.getActivada()));
 	    e_activada.appendChild(texto);
 	    alarma.appendChild(e_activada);
 	       
 	    //Añade al documento XML, el elemento alarma creado anteriormente
	    Element raiz = documento.getDocumentElement();
	    raiz.appendChild(alarma);
	}
	
	
	public void guardarAlarma (Alarma a) 
	{
		try
		{
			if(!cargadoDocumento) // Si no está cargado el documento XML, lo lee
			{
				leerXML(contexto.openFileInput(FICHERO));
			}
		}	
		catch(FileNotFoundException e)
		{
			crearXML(); // Si no encuentra el documento XML cuando intenta leerlo, lo crea
		}
		catch (Exception e)
		{
			Log.e("AlarmaXML", e.getMessage(), e);
		}
		
		nuevaAlarma(a); // Crea la alarma a, como un elemento XML 
	
		try
		{
			escribirXML(contexto.openFileOutput(FICHERO, Context.MODE_PRIVATE)); // Escribe la alarma creada en el fichero XML
		}
		catch(Exception e)
		{
			Log.e("AlarmaXML", e.getMessage(),e);
		}
	}
	
	
	public boolean eliminarAlarma (int idAlarma)
	{
		String id = null;
		boolean borrada = false;
		int i=0;
						
		if(!cargadoDocumento) // Si no está cargado el documento XML
		{
			try 
			{
				leerXML(contexto.openFileInput(FICHERO)); // Lee el fichero XML
			}
			catch (FileNotFoundException e) 
			{
				return borrada; // Si no lo puede leer, devuleve que no ha podido borrar la alarma
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	
		Element raiz = documento.getDocumentElement();
		NodeList alarmas = raiz.getElementsByTagName("alarma");
		
		// Busca la alarma a borrar en el documento XML
		while(!borrada || i < alarmas.getLength())
		{
			Node alarma = alarmas.item(i);
				
			Element alarma2 = (Element) alarmas.item(i);
	        id= alarma2.getAttribute("id");
	             
	         if (Integer.valueOf(id) == idAlarma) // Si encuentra la alarma a borrar
	         {
	        	 try 
	        	 {
	            	raiz.removeChild(alarma); // Elimina la alarma del documento XML
					escribirXML(contexto.openFileOutput(FICHERO, Context.MODE_PRIVATE)); // Escribe el fichero XML con los cambios realizados
					borrada=true; // Pone a true el valor que devuelve la función
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
		if(!cargadoDocumento) // Si no está cargado el documento XML
		{
			try 
			{
				if(contexto.getFileStreamPath(FICHERO).exists())
				{
					leerXML(contexto.openFileInput(FICHERO)); // Lee el fichero XML
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
				e.printStackTrace();
			}
		}
		
		Vector<Alarma> result = new Vector<Alarma>();
	
		String nombre = null, hora = null, min= null, accion = null, activada=null, id=null;
	
		//Obtiene el elemento raíz del documento (lista_alarmas)
		Element raiz = documento.getDocumentElement();
	
		//Obtiene la lista de elementos "alarma" que contiene el elemento raíz
		NodeList alarmas = raiz.getElementsByTagName("alarma");
	
		
		//Recorre los elementos "alarma", almacenando su atributo "id" y sus propiedades, en un objeto alarma. 
		for(int i = 0; i < alarmas.getLength(); i++)
		{
			 Node alarma = alarmas.item(i);
			
			 Element alarma2 = (Element) alarmas.item(i);
             id= alarma2.getAttribute("id");
             
	        NodeList propiedades = alarma.getChildNodes();
	        
	        //Recorre la lista de propiedades de un elemento alarma
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
	
	
	public void crearXML() // Crea un nuevo documento XML
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
	
	
	public void leerXML(InputStream entrada) throws Exception // Lee el documento XML
	{
	       DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
	       DocumentBuilder constructor = fabrica.newDocumentBuilder();
	       documento = constructor.parse(entrada);
	       cargadoDocumento = true;
	}
	
		
	public void escribirXML(OutputStream salida) throws Exception // Escribe en el documento XML
	{
	       TransformerFactory fabrica = TransformerFactory.newInstance();
	       Transformer transformador = fabrica.newTransformer();
	       transformador.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
	       transformador.setOutputProperty(OutputKeys.INDENT, "yes");
	       
	       DOMSource fuente = new DOMSource(documento);
	       StreamResult resultado = new StreamResult(salida);
	       transformador.transform(fuente, resultado);
	}
}


