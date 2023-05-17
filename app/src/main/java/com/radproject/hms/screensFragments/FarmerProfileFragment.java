package com.radproject.hms.screensFragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import static com.radproject.hms.global.GlobalMethods.convertImage;
import static com.radproject.hms.global.GlobalMethods.getDistrictName;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.radproject.hms.MapActivity;
import com.radproject.hms.R;
import com.radproject.hms.global.GlobalMethods;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.models.UserModel;

public class FarmerProfileFragment extends Fragment implements View.OnClickListener {
    TextView farmer_id, verification_status;
    ImageView editNameIcon, editMobileIcon, editEmailIcon, editLocationIcon;
    ImageView iv_farmerQR, profile_picture;
    private EditText nameEditText, mobileEditText, emailEditText, locationEditText, nicPassportEditText, utilityBillEditText;
    // Declare the ActivityResultLauncher object
    private ActivityResultLauncher<Intent> mapActivityLauncher;
    LatLng selectedLocation;

    public FarmerProfileFragment() {
        // Required empty public constructor
    }

    private Button button1, button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_profile, container, false);

        // Initialize EditText fields
        farmer_id = view.findViewById(R.id.farmer_id);
        verification_status = view.findViewById(R.id.verification_status);
        iv_farmerQR = view.findViewById(R.id.iv_farmerQR);
        profile_picture = view.findViewById(R.id.profile_picture);

        nameEditText = view.findViewById(R.id.name_edit_text);
        mobileEditText = view.findViewById(R.id.mobile_edit_text);
        emailEditText = view.findViewById(R.id.email_edit_text);
        locationEditText = view.findViewById(R.id.location_edit_text);
        nicPassportEditText = view.findViewById(R.id.nic_passport_edit_text);
        utilityBillEditText = view.findViewById(R.id.utility_bill_edit_text);
        // Initialize edit icons
        editNameIcon = view.findViewById(R.id.edit_name_IC);
        editMobileIcon = view.findViewById(R.id.edit_mobile_IC);
        editEmailIcon = view.findViewById(R.id.edit_email_IC);
        editLocationIcon = view.findViewById(R.id.edit_location_IC);

        // Initialize verification status TextView

        farmer_id.setText(GlobalVariables.currentUser.getUserId()+"");
        nameEditText.setText(GlobalVariables.currentUser.getUsername());
        mobileEditText.setText(GlobalVariables.currentUser.getMobile());
        emailEditText.setText(GlobalVariables.currentUser.getEmail());
        locationEditText.setText(GlobalVariables.currentUser.getLocation()
                + "(" + GlobalVariables.currentUser.getLatitude() + ","
                + "(" + GlobalVariables.currentUser.getLongitude() + ")"
        );
        profile_picture.setImageBitmap(convertImage(GlobalVariables.currentUser.getBase64Profile()));
        profile_picture.setBackgroundResource(R.drawable.round_image_view);
        // find the buttons in the layout
        button1 = view.findViewById(R.id.updateFarmer_BTN);
        button2 = view.findViewById(R.id.logoutFarmer_BTN);
        // set the OnClickListener to the buttons
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        editNameIcon.setOnClickListener(this);
        editMobileIcon.setOnClickListener(this);
        editEmailIcon.setOnClickListener(this);
        editLocationIcon.setOnClickListener(this);
        // Generate QR code image
        GenerateQR();
        // Add a listener to the GlobalVariables.currentUser object
        GlobalVariables.currentUser.addListener(new UserModel.UserModelListener() {
            @Override
            public void onVerificationStatusChanged(boolean isVerified) {
                updateVerificationStatus(); // Call this method to update the verification status TextView
            }
        });
        disableOtherEditTexts(null);
        //getCurrentLocation();
        updateVerificationStatus();
        mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                selectedLocation = data.getParcelableExtra("selected_location");
                // Do something with the selected location here
            }
        });

        return view;
    }


    private void updateVerificationStatus() {
        if (GlobalVariables.currentUser.isVerified()) {
            verification_status.setTextColor(Color.GREEN);
            verification_status.setText("Verified");
        } else {
            verification_status.setTextColor(Color.RED);
            verification_status.setText("Not Verified");
        }
    }

    private void disableOtherEditTexts(EditText editText) {
        nameEditText.setEnabled(false);
        mobileEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        locationEditText.setEnabled(false);
        if (editText != null) {
            editText.setEnabled(true);
            editText.setSelection(editText.getText().length());
            editText.requestFocus();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateFarmer_BTN:
                // perform action for button 1 click
                break;
            case R.id.logoutFarmer_BTN:
                GlobalMethods.Logout(getActivity());
                break;
            case R.id.edit_name_IC:
                disableOtherEditTexts(nameEditText);
                break;
            case R.id.edit_mobile_IC:
                disableOtherEditTexts(mobileEditText);
                break;
            case R.id.edit_email_IC:
                disableOtherEditTexts(emailEditText);
                break;
            case R.id.edit_location_IC:
                disableOtherEditTexts(locationEditText);
//                ActivityResultLauncher<Intent> mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        LatLng selectedLocation = data.getParcelableExtra("selected_location");
//                        // Do something with the selected location here
//                        Log.e(TAG, "onClick: " + selectedLocation);
//                    }
//                });
                Intent mapActivityIntent = new Intent(getActivity(), MapActivity.class);
                startActivityForResult(mapActivityIntent, MAP_REQUEST_CODE);
                mapActivityLauncher.launch(mapActivityIntent);

                //   getCurrentLocation();
                break;

            // rest of the code
            default:
                break;
        }
    }

    private static final int MAP_REQUEST_CODE = 100;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: ");
        if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected location from the result intent
            selectedLocation = data.getParcelableExtra("selected_location");
            Log.e(TAG, "onActivityResult: " + selectedLocation.latitude);
            // Use the selected location as needed
            getDistrictName(selectedLocation.latitude, selectedLocation.longitude, getActivity());
        }
    }


    private void GenerateQR() {
        MultiFormatWriter writer = new MultiFormatWriter();
        String Verified = "Not Verified";
        if (GlobalVariables.currentUser.isVerified()) {
            Verified = "Verified";
        }
        String vCardString = String.format(
//                "BEGIN:VCARD\n" +
//                        "VERSION:3.0\n" +
//                        "N:;%s;;;\n" +
//                        "FN:%s\n" +
//                        "ORG:\n" +
//                        "TEL;TYPE=CELL:%s\n" +
//                        "EMAIL;TYPE=INTERNET:%s\n" +
//                        "UID:%s\n" +
//                        "X-VERIFIED: %s\n" + // add verified status as custom field
//                        "END:VCARD",
//
                GlobalVariables.currentUser.getUsername() + "\n" +
                        GlobalVariables.currentUser.getMobile() + " \n" +
                        GlobalVariables.currentUser.getEmail() + "\n" +
                        GlobalVariables.currentUser.getUserId() + "\n" +
                        Verified
        );
        try {
            BitMatrix matrix = writer.encode(vCardString, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            iv_farmerQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}