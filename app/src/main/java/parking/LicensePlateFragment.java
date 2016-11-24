package parking;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.OcrCaptureActivity;
import com.google.android.gms.samples.vision.ocrreader.R;

/**
 * Created by Ricardas on 2016-11-02.
 */

public class LicensePlateFragment extends Fragment {
    View inflatedView;
    Button photoBtn;
    Button licencePlate1;
    Button licencePlate2;
    Button licencePlate3;
    EditText editText;
    String licensePlateText;
    String recent_license_plate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.license_plate_fragment, container, false);

        editText = (EditText) inflatedView.findViewById(R.id.license_plate_input);
        licencePlate1 = (Button) inflatedView.findViewById(R.id.license_plate1);
        licencePlate2 = (Button) inflatedView.findViewById(R.id.license_plate2);
        licencePlate3 = (Button) inflatedView.findViewById(R.id.license_plate3);

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences("parking", Context.MODE_PRIVATE);
        for (int i=1; i<4; ++i) {
            recent_license_plate = sharedPref.getString("license_plate" + i, "");
            if (recent_license_plate != "")
                setSharedPreferencesdAndButtons(recent_license_plate);
        }

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    licensePlateText = "" + editText.getText();
                    editText.setText("");
                    handled = true;

                    setSharedPreferencesdAndButtons(licensePlateText);
                }
                return handled;
            }
        });

        licencePlate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (licencePlate1.getText() != "") {
                    setLicencePlate(licencePlate1);
                }
            }
        });

        licencePlate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (licencePlate2.getText() != "") {
                    setLicencePlate(licencePlate2);
                }
            }
        });

        licencePlate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (licencePlate3.getText() != "") {
                    setLicencePlate(licencePlate3);
                }
            }
        });

        photoBtn = (Button) inflatedView.findViewById(R.id.photo_btn);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);

                getActivity().startActivity(intent);
            }
        });

        return inflatedView;
    }

    private void setLicencePlate(Button licencePlate) {
        Singleton.getInstance().setLicesePlate("" + licencePlate.getText());

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFields mainFields = new MainFields();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
        fragmentTransaction.replace(android.R.id.content, mainFields);
        fragmentTransaction.addToBackStack("my_fragment5").commit();
    }

    private void setSharedPreferencesdAndButtons(String text) {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences("parking", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (licencePlate1.getText() == "") {
            editor.putString("license_plate1", "" + text);
            licencePlate1.setText(text);
        } else if (licencePlate2.getText() == "") {
            editor.putString("license_plate2", "" + text);
            licencePlate2.setText(text);
        } else if (licencePlate3.getText() == "") {
            editor.putString("license_plate3", "" + text);
            licencePlate3.setText(text);
        } else {
            licencePlate3.setText(licencePlate2.getText());
            licencePlate2.setText(licencePlate1.getText());
            licencePlate1.setText(text);
            editor.putString("license_plate1", "" + licencePlate1.getText());
            editor.putString("license_plate2", "" + licencePlate2.getText());
            editor.putString("license_plate3", "" + licencePlate3.getText());
        }
        editor.commit();
    }
}
