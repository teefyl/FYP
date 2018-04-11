package teefyl.wastlee;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=0;
    private GoogleMap mMap;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    @Override
    @RequiresApi(26)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    @RequiresApi(26)
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(5000);
        googlePlacesUrl.append("&types=").append("charities+that+accept+food+donations");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyB2p7huV_7vCWTX-n0yNI3KwdznY16iroA");



        // Add a marker at current location.
        LatLng curLoc = new LatLng(latitude, longitude);
        LatLng crossCare = new LatLng(53.375433, -6.293528);
        LatLng capuchin = new LatLng(53.349309, -6.275864);
        LatLng foodCloud = new LatLng(53.297749, -6.358847);
        LatLng mcVerry = new LatLng(53.356801, -6.256045);
        LatLng bru = new LatLng(53.343303, -6.282078);
        LatLng holyCross = new LatLng(53.364486, -6.253315);
        LatLng human = new LatLng(53.297182, -6.247024 );

        //Cork
        LatLng simon = new LatLng(51.899545, -8.463658);
        LatLng missions = new LatLng(51.898586, -8.466383);
        LatLng fcCork = new LatLng(51.899378, -8.349795);

        mMap.addMarker(new MarkerOptions().position(curLoc).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(crossCare).title("CrossCare Food Bank").snippet("97 Lagan Road\n" +
                "UNIT 2\n" +
                "Dublin Industrial Estate\n" +
                "Glasnevin\n" +
                "Dublin 11"));
        mMap.addMarker(new MarkerOptions().position(capuchin).title("Capuchin Day Centre").snippet("29 Bow St, Arran Quay, Dublin 7"));
        mMap.addMarker(new MarkerOptions().position(foodCloud).title("FoodCloud").snippet("8, Broomhill Business Park, Broomhill Road, Dublin 24"));
        mMap.addMarker(new MarkerOptions().position(mcVerry).title("Peter McVerry Trust").snippet(" 29 Mountjoy Square E, Mountjoy, Dublin, D01 C2N4"));
        mMap.addMarker(new MarkerOptions().position(bru).title("Bru Aimsir").snippet("13 Thomas St, Ushers, Dublin"));
        mMap.addMarker(new MarkerOptions().position(holyCross).title("Holy Cross College").snippet("Clonliffe 3, County Dublin"));
        mMap.addMarker(new MarkerOptions().position(capuchin).title("Capuchin Day Centre").snippet("29 Bow St, Arran Quay, Dublin 7"));
        mMap.addMarker(new MarkerOptions().position(human).title("Human Appeal International").snippet("1, Frankfort Centre, Dundrum Rd, Dundrum, Dublin 14, D14 E4A4"));

        //Cork
        mMap.addMarker(new MarkerOptions().position(simon).title("Cork Simon Emergency Appeal").snippet("Anderson's Quay, Centre, Cork"));
        mMap.addMarker(new MarkerOptions().position(missions).title("Global Missions Ireland").snippet("3 Connell St, Centre, Cork"));
        mMap.addMarker(new MarkerOptions().position(fcCork).title("Food Cloud Hub").snippet("Unit 3, OC Commercial Park, Ballytrasna, Little Island, Cork"));


        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(curLoc));
    }

    @RequiresApi(26)
    protected void getLocation() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }

            locationManager = (LocationManager)  this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();


            }
            else{

                locationManager.requestLocationUpdates(bestProvider,1000,0,this);
                System.out.println("HEREEESSS YOUR LAT LNG"+latitude+longitude);
            }

    }



    @Override
    @RequiresApi(26)
    public void onLocationChanged(Location location) {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        locationManager.removeUpdates(this);

        //open the map:
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}

