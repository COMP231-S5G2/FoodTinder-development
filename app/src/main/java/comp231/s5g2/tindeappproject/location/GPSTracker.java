package comp231.s5g2.tindeappproject.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;

public class GPSTracker implements LocationListener {

    private Context context;

    public boolean gpsEnabled = false;

    public boolean canGetLocation = false;

    Location l;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 1;
    protected LocationManager lm;

    public GPSTracker() {

    }

    public GPSTracker(Context c){

        this.context = c;
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(){
        try{
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("gpsEnabled", "=" + gpsEnabled);

            if(gpsEnabled == false){

            }
            else {
                this.canGetLocation = true;
                if(gpsEnabled){
                    l = null;
                    if(l == null){
                        lm.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                        );
                        Log.d("GPS Enabled", "GPS Enabled");
                        if(lm != null){
                            l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(l != null){
                                latitude = l.getLatitude();
                                longitude = l.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return l;
    }

    public void stopUsingGPS(){
        if(lm != null){
            lm.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location getL() {
        return l;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
