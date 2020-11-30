package comp231.s5g2.tindeappproject.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

public class GPSTracker implements LocationListener {

    private final Context context;

    public boolean gpsEnabled = false;

    public boolean canGetLocation = false;

    Location l;
    double latitude;
    double longitude;
    
    public GPSTracker(Context c){
        this.context = c;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
