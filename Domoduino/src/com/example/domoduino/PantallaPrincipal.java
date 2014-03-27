package com.example.domoduino;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class PantallaPrincipal extends Activity {
	
	private ImageButton bt1;
	private ImageButton bt2;
	private ImageButton bt3;
	private ImageButton bt4;
	private ImageButton bt5;

	boolean b=false;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantallaprincipal);
		bt1=(ImageButton) findViewById(R.id.imageButton1);
		bt1.setOnClickListener(btn1);
		bt2=(ImageButton) findViewById(R.id.imageButton2);
		bt2.setOnClickListener(btn2);
		bt3=(ImageButton) findViewById(R.id.imageButton3);
		bt3.setOnClickListener(btn3);
		bt4=(ImageButton) findViewById(R.id.imageButton4);
		bt4.setOnClickListener(btn4);
		bt5=(ImageButton) findViewById(R.id.imageButton5);
		bt5.setOnClickListener(btn5);
	}
	
	private ImageButton.OnClickListener btn1 = new ImageButton.OnClickListener()
	{
		public void onClick(View v)
		{
			//sendMessage("1\r");
			Toast.makeText(getApplicationContext(),"Encendido", Toast.LENGTH_SHORT).show();
		}
	};
	 private ImageButton.OnClickListener btn2 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			//sendMessage("2\r");
			Toast.makeText(getApplicationContext(),"Stop", Toast.LENGTH_SHORT).show();
							
		}
	};
	 private ImageButton.OnClickListener btn3 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			if(b==false)
			{
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaapagada);
				bt3.setImageBitmap(bmp);
				b=true;
				//sendMessage("3\r");
				Toast.makeText(getApplicationContext(),"Bombilla encendida", Toast.LENGTH_SHORT).show();
			}
			else if( b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
			{
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaencendida);
				bt3.setImageBitmap(bmp);
				//sendMessage("4\r");
				Toast.makeText(getApplicationContext(),"Bombilla apagada", Toast.LENGTH_SHORT).show();
				b=false;
			}
							
		}
	 };
	 private ImageButton.OnClickListener btn4 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			Intent i = new Intent(getApplicationContext(), PantallaAcelerometro.class);
        	startActivity(i);
		}
	 };
	 private ImageButton.OnClickListener btn5 = new ImageButton.OnClickListener()
	 {
		public void onClick(View v)
		{
			Intent i = new Intent(getApplicationContext(), PantallaReloj.class);
        	startActivity(i);
			Toast.makeText(getApplicationContext(),"Acelerómetro encendido", Toast.LENGTH_SHORT).show();
							
		}
	 };
//	 public  void sendMessage(String message)
//	 {
//		 
//		 if (Servicio_BT.getState() == ConexionBT.STATE_CONNECTED) {//checa si estamos conectados a BT   
//		        if (message.length() > 0) 
//		        {   // checa si hay algo que enviar
//		            byte[] send = message.getBytes();//Obtenemos bytes del mensaje
//		            if(D) Log.e(TAG, "Mensaje enviado:"+ message);            
//		                 Servicio_BT.write(send);     //Mandamos a escribir el mensaje     
//		        }
//		     }
//	        else 
//	        {
//	        	Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
//	        }
//	    }//fin de sendMessage

}
