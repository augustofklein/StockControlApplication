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
        if(this.Latitude == null){
            return 0.0;
        }else{
            return Latitude;
        }
    }

    public Double getLongitude(){
        if(this.Longitude == null){
            return 0.0;
        }else{
            return Longitude;
        }
    }
}