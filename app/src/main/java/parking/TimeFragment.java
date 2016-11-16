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

import com.google.android.gms.samples.vision.ocrreader.R;

/**
 * Created by Ricardas on 2016-11-02.
 */

public class TimeFragment extends Fragment {
    View inflatedView;
    Button timePickerBtn;
    Button hourBtn;
    Button hour2Btn;
    Button hour3Btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView =  inflater.inflate(R.layout.time_fragment, container, false);

        timePickerBtn = (Button) inflatedView.findViewById(R.id.diferent_time);
        hourBtn = (Button) inflatedView.findViewById(R.id.after_hour);
        hour2Btn = (Button) inflatedView.findViewById(R.id.after_2hours);
        hour3Btn = (Button) inflatedView.findViewById(R.id.after_3hours);

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                TimePickerFragment timePickerFragment = new TimePickerFragment();

                fragmentTransaction.replace(android.R.id.content, timePickerFragment);
                fragmentTransaction.addToBackStack("my_fragment8").commit();
            }
        });

        hourBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentMinute = calendar.get(Calendar.MINUTE);
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                currentHour = (currentHour + 1) % 24;

                Singleton.getInstance().setTime(currentHour, currentMinute);

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MainFields mainFields = new MainFields();

                fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                fragmentTransaction.replace(android.R.id.content, mainFields);
                fragmentTransaction.addToBackStack("my_fragment9").commit();
            }
        });
        hour2Btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentMinute = calendar.get(Calendar.MINUTE);
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                currentHour = (currentHour + 2) % 24;

                Singleton.getInstance().setTime(currentHour, currentMinute);

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MainFields mainFields = new MainFields();

                fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                fragmentTransaction.replace(android.R.id.content, mainFields);
                fragmentTransaction.addToBackStack("my_fragment9").commit();
            }
        });
        hour3Btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentMinute = calendar.get(Calendar.MINUTE);
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                currentHour = (currentHour + 3) % 24;

                Singleton.getInstance().setTime(currentHour, currentMinute);

                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MainFields mainFields = new MainFields();

                fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                fragmentTransaction.replace(android.R.id.content, mainFields);
                fragmentTransaction.addToBackStack("my_fragment9").commit();
            }
        });

        return inflatedView;
    }
}
