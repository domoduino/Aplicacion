package com.example.domoduino;

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
		
		nuevo(a);
		
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
	
    public Vector<String> listaAlarmas()  //(int cantidad) 
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
		
		public boolean cancelarAlarma (Alarma a) {
			return false;
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
		
		public void nuevo(Alarma a ) {

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
		       

		       Element raiz = documento.getDocumentElement();

		       raiz.appendChild(alarma);

		}
		
		public int numAlarmas ()
		{
			if(!cargadoDocumento)
			{
				try {
					leerXML(contexto.openFileInput(FICHERO));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			   Element raiz = documento.getDocumentElement();

		       NodeList alarmas = raiz.getElementsByTagName("alarma");
		       
		       return alarmas.getLength();
		}
		
		public Vector<String> aVectorString() {

		       Vector<String> result = new Vector<String>();

		       String nombre = "", hora = "", accion= "", min="", id="";

		       Element raiz = documento.getDocumentElement();

		       NodeList alarmas = raiz.getElementsByTagName("alarma");
//		       Element alarmaEle = (Element) raiz.getElementsByTagName("alarma");
//		        id = alarmaEle.getAttribute("id");
//		        alarmaEle.
		       

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
		             }

		             result.add(id + " " + nombre + " " + hora + " " + min + " " + accion);

		       }

		       return result;

		}

	public Vector<Alarma> listadoAlarmas()
	{
		if(!cargadoDocumento)
		{
			try 
			{
				leerXML(contexto.openFileInput(FICHERO));
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
	
		String nombre = null, hora = null, min= null, accion = null, id=null;
	
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
			}
	
			 result.add(new Alarma (Integer.valueOf(id),nombre,hora,min,Integer.valueOf(accion))); //DeStringADate(hora),Integer.valueOf(accion)));
		 }
		
		
	return result;
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
}


//http://www.androidcurso.com/index.php/tutoriales-android-fundamentos/42-unidad-9-almacenamiento-de-datos/335-procesando-xml-con-dom
