package parking;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.android.gms.samples.vision.ocrreader.R;

/**
 * Created by Ricardas on 2016-11-07.
 */

public class TimePickerFragment extends Fragment {
    View inflatedView;
    TimePicker timePicker;
    Button differentTimeBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView =  inflater.inflate(R.layout.time_picker_fragment, container, false);

        timePicker = (TimePicker) inflatedView.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        differentTimeBtn = (Button)  inflatedView.findViewById(R.id.set_diff_time_button);

        differentTimeBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
            Singleton.getInstance().setTime(timePicker.getHour(), timePicker.getMinute());

            final FragmentManager fragmentManager = getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MainFields mainFields = new MainFields();

            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            fragmentTransaction.replace(android.R.id.content, mainFields);
            fragmentTransaction.commit();
            }
        });

        return inflatedView;
    }
}
