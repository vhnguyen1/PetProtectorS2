package edu.orangecoastcollege.cs273.vnguyen629.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Displays information about the pet that the user selected from
 * the list in a separate details page including the current pets name,
 * details, contact phone number, and also an image of the pet.
 *
 * @author Vincent Nguyen
 */
public class PetDetailsActivity extends AppCompatActivity {

    private TextView petDetailsNameTextView;
    private TextView petDetailsDetailsTextView;
    private TextView petDetailsPhoneTextView;
    private ImageView petDetailsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        petDetailsNameTextView = (TextView) findViewById(R.id.petDetailsNameTextView);
        petDetailsDetailsTextView = (TextView) findViewById(R.id.petDetailsDetailsTextView);
        petDetailsPhoneTextView = (TextView) findViewById(R.id.petDetailsPhoneTextView);
        petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);

        Intent detailsIntent = getIntent();

        String petName = detailsIntent.getStringExtra("Name");
        String petDetails = detailsIntent.getStringExtra("Details");
        String petPhone = detailsIntent.getStringExtra("Phone");
        Uri petImageUri = Uri.parse(detailsIntent.getStringExtra("Image"));

        petDetailsNameTextView.setText(petName);
        petDetailsDetailsTextView.setText(petDetails);
        petDetailsPhoneTextView.setText(petPhone);
        petDetailsImageView.setImageURI(petImageUri);

        //this.finish();
    }
}