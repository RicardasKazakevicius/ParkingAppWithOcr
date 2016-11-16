package parking;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
    Button licencePlate;
    EditText editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.license_plate_fragment, container, false);

        editText = (EditText) inflatedView.findViewById(R.id.license_plate_input);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    licencePlate = (Button) inflatedView.findViewById(R.id.license_plate1);
                    licencePlate.setText(editText.getText());
                    editText.setText("");
                    handled = true;
                }
                return handled;
            }
        });
        licencePlate = (Button) inflatedView.findViewById(R.id.license_plate1);

        licencePlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (licencePlate.getText() != "") {
                    Singleton.getInstance().setLicesePlate("" + licencePlate.getText());

                    final FragmentManager fragmentManager = getFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    MainFields mainFields = new MainFields();

                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                    fragmentTransaction.replace(android.R.id.content, mainFields);
                    fragmentTransaction.addToBackStack("my_fragment5").commit();
                }
            }
        });

        photoBtn = (Button) inflatedView.findViewById(R.id.photo_btn);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);

                ProgressBar spinner = (ProgressBar)inflatedView.findViewById(R.id.progressBar1);
                getActivity().startActivity(intent);
            }
        });

        return inflatedView;
    }
}
