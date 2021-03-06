package parking;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Ricardas on 2016-11-02.
 */

public class ZoneFragment extends Fragment {
    View inflatedView = null;
    TextView address;
    LinearLayout green;
    LinearLayout yellow;
    LinearLayout red;
    LinearLayout blue;
    Switch localizationBtn;
    LocationManager locationManager;
    LocationListener locationListener;

    private void setZone() {
        MainFields mainFields = new MainFields();

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);

        fragmentTransaction.replace(android.R.id.content, mainFields);
        fragmentTransaction.addToBackStack("zone_fragment").commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.zone_fragment, container, false);
        setLocation();
        green = (LinearLayout) inflatedView.findViewById(R.id.green);

        green.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setZone( R.string.green_zone );
                setZone();
            }
        });

        yellow = (LinearLayout) inflatedView.findViewById(R.id.yellow);

        yellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setZone( R.string.yellow_zone );
                setZone();
            }
        });

        red = (LinearLayout) inflatedView.findViewById(R.id.red);

        red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setZone( R.string.red_zone );
                setZone();
            }
        });

        blue = (LinearLayout) inflatedView.findViewById(R.id.blue);

        blue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setZone( R.string.blue_zone );
                setZone();
            }
        });


        localizationBtn = (Switch) inflatedView.findViewById(R.id.localization_btn);

        localizationBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setLocation();

                    address = (TextView) inflatedView.findViewById(R.id.coordinates);
                    address.setText(getCompleteAddressString(Singleton.getInstance().getLatitude(), Singleton.getInstance().getLongitude()));
                    if (address.getText() == "Nepavyko nustatyti adreso") {
                        localizationBtn.setChecked(false);
                    }
                } else {
                    address.setText("Nustatyti adresą");
                }
            }
        });

        return inflatedView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setLocation() {

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                Singleton.getInstance().setLatitude(location.getLatitude());
                Singleton.getInstance().setLongitude(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String []{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
        } else {
            configure();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 10:
                if  (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configure();
                return;
        }
    }

    public void configure() {
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);//LocationManager.GPS_PROVIDER
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "Nepavyko nustatyti adreso";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
//                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
//                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
}
