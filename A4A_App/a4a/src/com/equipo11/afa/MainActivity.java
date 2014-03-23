package com.equipo11.afa;

import java.io.File;

import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// Constante para indicar que hacemos una foto
	private static int TAKE_PICTURE = 1;
	// Variable para el nombre del archivo donde escribiremos la fotografía
	private String name = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Definimos donde almacenar y el nombre de la foto
		name = Environment.getExternalStorageDirectory() + "/test.jpg";
		// Boton para echar la foto
	       Button btnAction = (Button)findViewById(R.id.btnFoto);
	       btnAction.setOnClickListener(new OnClickListener() {
	       	@Override
	       	public void onClick(View v) {
	       		// Intent con ACTION_IMAGE_CAPTURE para llamar a la camara
	       		Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	       		// Archivo para guardar la foto
	       		Uri output = Uri.fromFile(new File(name));
	       		// Cargo el intent con el archivo de salida
	        		intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
	        		// Llamo a la camara
	        		startActivityForResult(intent, TAKE_PICTURE);				
	        	}
	        });
		}
		// Metodo donde obtengo la foto
	    @Override 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// Si la imagen viene de la camara
	    	if (requestCode == TAKE_PICTURE) {
	    		// Obtengo la foto de la camara y la cargo en la activity
	    		ImageView iv = (ImageView)findViewById(R.id.imgView);
	    		iv.setImageBitmap(BitmapFactory.decodeFile(name));
	    		// Guardo la imagen
	    		new MediaScannerConnectionClient() {
	    			private MediaScannerConnection msc = null; {
	    				msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
	    			}
	    			public void onMediaScannerConnected() { 
	    				msc.scanFile(name, null);
	    			}
	    			public void onScanCompleted(String path, Uri uri) { 
	    				msc.disconnect();
	    			} 
	    		};				
	    	}
	    }
}
