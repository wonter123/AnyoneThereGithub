package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Science complex) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(42.366574, -71.258194);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private static ArrayList<Post> allPosts;


    private DatabaseReference mDataBase;

    private Map<String, double[]> locationIndex;
    private Map<String, ArrayList<Post>> mapToPosts;
    private String[] allLocations;
    private double[][] positions;

    private ListView requestList;
    private ListInDialogAdapter listInDialogAdapter;
    private ListView listInDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.d(TAG, "123456789012345678901234567890");

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        mDataBase = FirebaseDatabase.getInstance().getReference("posts");


        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

       //  Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

       //  Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        allPosts = new ArrayList<Post>();
        allLocations = getResources().getStringArray(R.array.locations);
        locationIndex = new HashMap<>();
        mapToPosts = new HashMap<String, ArrayList<Post>>();
        for(String loc: allLocations){
            mapToPosts.put(loc, new ArrayList<Post>());
        }
        positions = new double[allLocations.length][2];
        positions[0] = new double[]{42.365938, -71.253759 };
        positions[1] = new double[]{42.364940, -71.254762 };
        positions[2] = new double[]{42.366207, -71.255398 };
        positions[3] = new double[]{42.367665, -71.254871 };
        positions[4] = new double[]{42.369348, -71.255645 };
        positions[5] = new double[]{42.369592, -71.257693 };
        positions[6] = new double[]{42.369226, -71.259192 };
        positions[7] = new double[]{42.368012, -71.256807 };
        positions[8] = new double[]{42.368191, -71.258212 };
        positions[9] = new double[]{42.366958, -71.258760 };
        positions[10] = new double[]{42.365929, -71.258290 };
        positions[11] = new double[]{42.359766, -71.257016 };
        positions[12] = new double[]{42.364772, -71.264716 };
        int i=0;
        for(String loc: allLocations){
            locationIndex.put(loc, positions[i++]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(mDefaultLocation).title("Marker in Brandeis"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

        // Prompt the user for permission.
        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        //uncomment this and the app will CRASH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!...
//        getDeviceLocation();

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Post curP = ds.getValue(Post.class);
                    allPosts.add(curP);
                    mapToPosts.get(curP.getFrom()).add(curP);
                }

                // add a marker to show the location with at least one outgoing request
                for(String loc: mapToPosts.keySet()){
                    int count = mapToPosts.get(loc).size();
                    if(count!=0){
                        Marker marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(locationIndex.get(loc)[0], locationIndex.get(loc)[1]))
                                .title(count+"" )
                                .alpha(0.8f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                        marker.setTag(loc);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String loc = marker.getTag().toString();
                if(loc == null || loc.length() == 0) return false;
                Dialog dialog = new Dialog(MapsActivity.this);
                dialog.setContentView(R.layout.list_in_dialog);
                Window window = dialog.getWindow();
                window.setLayout(1000, 1200 );
                ArrayList<Post> postsAtCurrentLocation = mapToPosts.get(loc);

                listInDialogAdapter = new ListInDialogAdapter(MapsActivity.this, R.layout.request_list, postsAtCurrentLocation);
                listInDialog = (ListView)dialog.findViewById(R.id.list_in_dialog_listview);
                Toast.makeText(MapsActivity.this, "lalala: "+ listInDialog,Toast.LENGTH_LONG).show();
                listInDialog.setAdapter(listInDialogAdapter);

                dialog.show();
                return true;
            }
        });



    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Toast.makeText(this, "This is my Toast message1!",
                        Toast.LENGTH_LONG).show();
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {

                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


}
