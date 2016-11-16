package parking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.R;

/**
 * Created by Ricardas on 2016-11-01.
 */

public class MainFields extends Fragment {
    View inflatedView;
    TextView licensePlate;
    TextView zone;
    TextView time;
    TextView cost;
    Button licensePlateBtn;
    Button timeBtn;
    Button zoneBtn;
    Button start;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.main_fields, container, false);

        zone = (TextView) inflatedView.findViewById(R.id.zone_text_field);
        time = (TextView) inflatedView.findViewById(R.id.duration_text);
        licensePlate = (TextView) inflatedView.findViewById(R.id.license_plate_field);


        zone.setText(Singleton.getInstance().getZone());
        licensePlate.setText(Singleton.getInstance().getLicesePlate());
        time.setText(Singleton.getInstance().getTime());

        if (zone.getText() != "" && time.getText() != "") {
            cost = (TextView) inflatedView.findViewById(R.id.cost);
            cost.setText("Kaina: " + getPrice() + " EUR");
        }

        start = (Button) inflatedView.findViewById(R.id.start_btn);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( zone.getText() != "" && time.getText() != "" && licensePlate.getText()!= "" ){
                    start.setText("Pradėta!");
                }
            }
        });

        licensePlateBtn = (Button) inflatedView.findViewById(R.id.license_plate_btn);

        licensePlateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                LicensePlateFragment licensePlateFragment = new LicensePlateFragment();

                fragmentTransaction.replace(android.R.id.content, licensePlateFragment);
                fragmentTransaction.addToBackStack("main_buttons_fragment1").commit();
            }
        });

        zoneBtn = (Button) inflatedView.findViewById(R.id.zone_btn);

        zoneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ZoneFragment zoneFragment = new ZoneFragment();

                fragmentTransaction.replace(android.R.id.content, zoneFragment);
                fragmentTransaction.addToBackStack("main_buttons_fragment2").commit();
            }
        });

        timeBtn = (Button) inflatedView.findViewById(R.id.time_btn);

        timeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                TimeFragment timeFragment = new TimeFragment();

                fragmentTransaction.replace(android.R.id.content, timeFragment);
                fragmentTransaction.addToBackStack("main_buttons_fragment3").commit();
            }
        });

        return inflatedView;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Double getPrice()
    {
        String zone = Singleton.getInstance().getZone();
        Integer hours = Singleton.getInstance().getHours();
        Integer minutes = Singleton.getInstance().getMinutes();
        Calendar calendar = Calendar.getInstance();
        int currentMinutes = calendar.get(Calendar.MINUTE);
        int currentHours = calendar.get(Calendar.HOUR_OF_DAY);
        Double price = null;
        Double duration = null;
        Long tempPrice;
        hours = hours - currentHours;
        if (hours < 0) {
            hours += 24;
        }
        minutes = minutes - currentMinutes;
        if (minutes < 0) {
            hours -= 1;
            minutes = minutes + 60;
        }
        duration = hours + ((minutes / 12)) * 0.2;
        if (duration == 0)
            duration = 0.2;

        if (zone.contains("0.3")){
            price = 0.3;
        } else if (zone.contains("0.6")){
            price =  0.6;
        } else if (zone.contains("0.9")){
            price = 0.9;
        } else if (zone.contains("1.2")) {
            price = 1.2;
        }

        tempPrice = Math.round(price * duration * 100);
        price = tempPrice / 100.00;
        return price;
    }
}
