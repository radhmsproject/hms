package com.radproject.hms.subActivities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import com.radproject.hms.R;

public class AddActivityCustomDialog extends Dialog {

    private Bundle dataBundle;

    public AddActivityCustomDialog(Context context, Bundle dataBundle) {
        super(context);
        this.dataBundle = dataBundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_activity); // Replace with the actual name of your layout file



    }
}