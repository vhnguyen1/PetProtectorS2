package edu.orangecoastcollege.cs273.vnguyen629.petprotector;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide a custom adapter for the <code>Pet</code> list.
 *
 * @author Vincent Nguyen
 */
public class PetListAdapter extends ArrayAdapter<Pet> {
    private int mResourceId;
    private Context mContext;
    private List<Pet> mPetsList = new ArrayList<>();

    private LinearLayout petListLinearLayout;
    private TextView petListNameTextView;
    private TextView petListDetailsTextView;
    private ImageView petListImageView;

    /**
     * Creates a new <code>PetListAdapter</code> given a mContext, resource id and list of pets.
     *
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param pets The list of pets to display
     */
    public PetListAdapter(Context c, int rId, List<Pet> pets) {
        super(c, rId, pets);
        this.mContext = c;
        this.mResourceId = rId;
        this.mPetsList = pets;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the Game selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) this.mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.mResourceId, null);

        final Pet pet = this.mPetsList.get(pos);
        petListLinearLayout.setTag(pet);

        petListLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        petListNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        petListDetailsTextView = (TextView) view.findViewById(R.id.petListDetailsTextView);
        petListImageView = (ImageView) view.findViewById(R.id.petListImageView);

        String petName = pet.getName();
        String petDetails = pet.getDetails();

        petListNameTextView.setText(petName);
        petListDetailsTextView.setText(petDetails);

        Uri petImageUri = pet.getImageURI();
        if (petImageUri != null)
            petListImageView.setImageURI(petImageUri);
        else {
            String defaultPetImageName = "none.png";
            AssetManager am = this.mContext.getAssets();
            try {
                InputStream stream = am.open(defaultPetImageName);
                Drawable event = Drawable.createFromStream(stream, petName);
                petListImageView.setImageDrawable(event);
            }
            catch (IOException err) {
                Log.e("Pet Protector", "Error Loading Image " + err.getMessage(), err);
            }
        }

        return view;
    }
}