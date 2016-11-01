package edu.orangecoastcollege.cs273.vnguyen629.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The front page of the app, allowing the user to add new pets into the pet lists as well as
 * view the rest of the current pets that are already inside the list.
 *
 * @author Vincent Nguyen
 */
public class PetListActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView petImageView;
    private EditText petNameEditText;
    private EditText petDetailsEditText;
    private EditText petPhoneEditText;

    private DBHelper db;
    private List<Pet> petList;
    private PetListAdapter petListAdapter;
    private ListView petListView;

    private static final int REQUEST_CODE = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        //db.addPet(new Pet("Toby", "Runs like the wind.", "(714) 555-1234", getUriToResource(this, R.drawable.none)));
        //db.addPet(new Pet("Mittens", "Out of this world cute.", "(714) 555-4321", getUriToResource(this, R.drawable.none)));
        //db.addPet(new Pet("Hops The Goat", "Who doesn't want a pet goat?", "(714) 555-1212", getUriToResource(this, R.drawable.none)));

        petImageView = (ImageView) findViewById(R.id.petImageView);
        petNameEditText = (EditText) findViewById(R.id.petNameEditText);
        petDetailsEditText = (EditText) findViewById(R.id.petDetailsEditText);
        petPhoneEditText = (EditText) findViewById(R.id.petPhoneEditText);
        petListView = (ListView) findViewById(R.id.petListView);

        imageUri = getUriToResource(this, R.drawable.none);
        petImageView.setImageURI(imageUri);

        petList = db.getAllPets();
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);
        petListView.setAdapter(petListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            petImageView.setImageURI(imageUri);
        }
    }

    /**
     * Adds a new <code>Pet</code> into the pet lists and databases.
     * @param view The add pet Button that the user clicks on
     */
    public void addPet(View view) {
        String petName = petNameEditText.getText().toString();
        // Checks for whitespace characters
        String temp = petName.replaceAll("\\s+","");

        if (temp.isEmpty())
            Toast.makeText(this, "Pet name cannot be empty.", Toast.LENGTH_SHORT).show();
        else {
            String petDetails = petDetailsEditText.getText().toString();
            temp = petDetails.replaceAll("\\s+","");

            if (temp.isEmpty())
                Toast.makeText(this, "Pet details cannot be empty.", Toast.LENGTH_SHORT).show();
            else {
                String petPhone = petPhoneEditText.getText().toString();
                temp = petPhone.replaceAll("\\s+","");

                if (temp.isEmpty())
                    Toast.makeText(this, "Contact phone number cannot be empty.",
                            Toast.LENGTH_SHORT).show();
                else {
                    Pet newPet = new Pet(petName, petDetails, petPhone, imageUri);

                    db.addPet(newPet);
                    petList.add(newPet);
                    //petListAdapter.add(newPet);
                    petListAdapter.notifyDataSetChanged();

                    Toast.makeText(this, petName + " added successfully!", Toast.LENGTH_SHORT).show();

                    petNameEditText.setText("");
                    petDetailsEditText.setText("");
                    petPhoneEditText.setText("");

                    Uri defaultImageURI = getUriToResource(this, R.drawable.none);
                    petImageView.setImageURI(defaultImageURI);
                }
            }
        }
    }

    /**
     * Allows the user to View the details page of the selected pet (according to whichever
     * pet they selected on the front screen in the ListView)
     * @param view The ListView that has been specifically selected
     */
    public void viewPetDetails(View view) {
        final Pet desiredPet = (Pet) view.getTag();
        Toast.makeText(this, "Viewing " + desiredPet.getName() + "...", Toast.LENGTH_SHORT).show();

        Intent listIntent = new Intent(this, PetDetailsActivity.class);

        String petName = desiredPet.getName();
        String petDetails = desiredPet.getDetails();
        String petPhone = desiredPet.getPhone();
        String petImageURI = desiredPet.getImageURI().toString();

        listIntent.putExtra("Name", petName);
        listIntent.putExtra("Details", petDetails);
        listIntent.putExtra("Phone", petPhone);
        listIntent.putExtra("Image", petImageURI);

        startActivity(listIntent);
    }

    /**
     * Allows the user to select and change the image for the current pet that they
     * want to add into the list and database.
     * @param view The ImageView for adding new pets
     */
    public void selectPetImage(View view) {
        ArrayList<String> permList =  new ArrayList<>();

        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        int writePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int readPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permList.size() > 0) {
            String[] perms = new String[permList.size()];
            ActivityCompat.requestPermissions(this, permList.toArray(perms), REQUEST_CODE);
        }

        if (cameraPermission == PackageManager.PERMISSION_GRANTED
                && writePermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE);
        }
        else {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED
                    && writePermission != PackageManager.PERMISSION_GRANTED
                    && readPermission != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Pet Protector requires camera and " +
                        "external storage permissions.", Toast.LENGTH_SHORT).show();
            else if (cameraPermission != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Pet Protector requires camera permissions.",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Pet Protector requires external storage permissions.",
                        Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Deletes all the current pets inside the list and databases.
     * @param view The view that has been selected
     */
    public void clearAllPets(View view) {
        if (!petList.isEmpty()) {
            petList.clear();
            db.deleteAllPets();
            petListAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(this, "Pet list is already empty.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Get uri to any resource type within an Android Studio project. Method is public static to
     * allow other classes to use it as a helper function
     *
     * @param context The current context
     * @param resID The resource identifier for drawable
     * @return Uri to resource by given id
     * @throws Resources.NotFoundException If the given resource id does not exist.
     */
    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resID)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package */
        Resources res = context.getResources();
        /** return URI */
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resID)
                + '/' + res.getResourceTypeName(resID)
                + '/' + res.getResourceEntryName(resID));
    }
}