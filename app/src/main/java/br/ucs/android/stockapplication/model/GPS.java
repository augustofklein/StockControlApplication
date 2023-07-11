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

    public void setLatitude(Double latitude){
        this.Latitude = latitude;
    }

    public void setLongitude(Double longitude){
        this.Longitude = longitude;
    }

    public Double getLatitude(){
        if(this.Latitude == null)
            configurarServico();

        return Latitude;

    }

    public Double getLongitude(){
        if(this.Longitude == null)
            configurarServico();

        return Longitude;

    }

    public void configurarServico(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    atualizar(location);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void atualizar(Location location)
    {
        this.setLatitude(location.getLatitude());
        this.setLongitude(location.getLongitude());
    }

}