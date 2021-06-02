package com.example.workoutapp.ui.home;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QRActivity extends AppCompatActivity {
    Activitat activity = new Activitat();
    int pos;
    String activity_token;
    ImageView qrImage;
    TextView activity_name, activity_date, activity_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);
        pos = getIntent().getIntExtra("Position recycler",0);
        activity = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo().get(pos);
        activity_token = getIntent().getStringExtra("Updated Token");
        qrImage=findViewById(R.id.qr_image);
        activity_name=findViewById(R.id.activity_name);
        activity_date=findViewById(R.id.activity_date);
        activity_place=findViewById(R.id.activity_place);

        activity_name.setText(activity.getName() + " (" + activity.getOrganizerName() + ")");
        activity_date.append(" " + activity.getDateTimeString());

        String[] locations = activity.getLocation().split(", ");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert addresses != null;
        activity_place.append(" " + addresses.get(0).getAddressLine(0));

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(activity_token, BarcodeFormat.QR_CODE, 850, 850);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}