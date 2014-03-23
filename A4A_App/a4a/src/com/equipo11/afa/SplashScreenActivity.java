package com.equipo11.afa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class SplashScreenActivity extends Activity {
	// Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.activity_splash_screen);
        // Compruebo que tiene camara
        if(this.checkCameraHardware(getApplicationContext())){
        	//Vamos a declarar un nuevo thread
            Thread timer = new Thread(){
                //El nuevo Thread exige el metodo run
                public void run(){
                    try{
                    	sleep(DURACION_SPLASH);
                    }catch(InterruptedException e){
                        //Si no puedo ejecutar el sleep muestro el error
                        e.printStackTrace();
                    }finally{
                        //Llamo a la nueva actividad
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    	startActivity(intent);
                    	finish();
                    }                
                }
            };
            // Comienza la espera
            timer.start();
        } else { // Si no tengo camara cierro la app
        	Toast.makeText(getApplicationContext(), "No tienes camara", Toast.LENGTH_LONG).show();
        	finish();
        } 
    }
    // Compruebo si el dispositivo tiene camara
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true; // Tiene camara
        } else {
            return false;// No tiene camara
        }
    }
}
