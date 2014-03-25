package com.example.domoduino;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class pantallaPrincipal extends Activity implements OnClickListener{
	
	ImageButton bt;
	ImageButton bt1;
	boolean b=false;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantallaprincipal);
		bt=(ImageButton) findViewById(R.id.imageButton3);
		bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(b==false)
		{
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaapagada);
			bt.setImageBitmap(bmp);
			b=true;
		}
		else if(b==true)// no se como comprobar que ahora es otro id, porque no lo puedo poner
		{
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ruedabombillaencendida);
			bt.setImageBitmap(bmp);
			b=false;
		}
		
	}

}
