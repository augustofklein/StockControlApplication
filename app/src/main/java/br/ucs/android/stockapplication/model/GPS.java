package br.ucs.android.stockapplication.model;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import br.ucs.android.stockapplication.main.MainActivity;

public class GPS extends Activity {

    private Double Latitude;
    private Double Longitude;

    public GPS(){}

    public Double getLatitude(){
        if(this.Latitude == null){
            return 0.0;
        }else{
            return this.Latitude;
        }
    }

    public Double getLongitude(){
        if(this.Longitude == null){
            return 0.0;
        }else{
            return this.getLongitude();
        }
    }

    public void PedirPermissoes(Context context) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else
        if(!configurarServico(context)){
            // MENSAGEM DE ERRO
        }
    }

    public boolean configurarServico(Context context){
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    AtualizarPosicao(location);
                }
                public void onStatusChanged(String provider, int status, Bundle extras) { }
                public void onProviderEnabled(String provider) { }
                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            return true;
        }catch(SecurityException ex){
            return false;
        }
    }

    public void AtualizarPosicao(Location location){
        Latitude = location.getLatitude();
        Longitude = location.getLongitude();
    }

    private Double ReturnLatitudeData(Location location)
    {
        Double latPoint = location.getLatitude();

        return latPoint;
    }

    private Double ReturnLongitudeData(Location location)
    {
        Double lonPoint = location.getLongitude();

        return lonPoint;
    }

}