package edu.orangecoastcollege.cs273.vnguyen629.petprotector;

import android.net.Uri;

/**
 * The <code>Pet</code> class maintains information about a specific pet
 * including its unique ID number, details, contact phone number, and image.
 *
 * @author Vincent Nguyen
 */
public class  Pet {
    private int mID;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageUri;

    /**
     * Creates a new <code>Pet</code> from its ID, details, contact phone
     * number, and image.
     * @param newID The new pet id
     * @param newName The new pet name
     * @param newDetails The new pet details
     * @param newPhone The new pet contact phone number
     * @param newImageUri The new pet image
     */
    public Pet(int newID, String newName, String newDetails, String newPhone, Uri newImageUri) {
        this.mID = newID;
        this.mName = newName;
        this.mDetails = newDetails;
        this.mPhone = newPhone;
        this.mImageUri = newImageUri;
    }

    /**
     * Creates a new <code>Pet</code> from user input, ID is irrelevant
     * and assigned by the database
     * @param newName The new pet name
     * @param newDetails The new pet details
     * @param newPhone The new pet contact phone number
     * @param newImageUri The new pet image
     */
    public Pet(String newName, String newDetails, String newPhone, Uri newImageUri) {
        this(-1, newName, newDetails, newPhone, newImageUri);
    }

    /**
     * Creates a default <code>Pet</code> with an id of -1, empty name,
     * empty details, empty contact phone number, and default image name of none.png.
     */
    public Pet() {
        this(-1, "", "", "", null);
    }

    /**
     * Gets the unique id of the <code>Pet</code>.
     * @return The unique id
     */
    public int getID() {
        return this.mID;
    }

    /**
     * Gets the name of the <code>Pet</code>.
     * @return The pet name
     */
    public String getName() {
        return this.mName;
    }

    /**
     * Gets the name of the <code>Pet</code>.
     * @return The pet details
     */
    public String getDetails() {
        return this.mDetails;
    }

    /**
     * Gets the name of the <code>Pet</code>.
     * @return The pet contact phone number
     */
    public String getPhone() {
        return this.mPhone;
    }

    /**
     * Gets the name of the <code>Pet</code>.
     * @return The pet image
     */
    public Uri getImageURI() {
        return this.mImageUri;
    }

    /**
     * Sets the name of the <code>Pet</code>.
     * @param newName The new pet description
     */
    public void setName(String newName) {
        this.mName = newName;
    }

    /**
     * Sets the details of the <code>Pet</code>.
     * @param newDetails The new pet details
     */
    public void setDetails(String newDetails) {
        this.mDetails = newDetails;
    }

    /**
     * Sets the contact phone number of the <code>Pet</code>.
     * @param newPhone The new pet contact phone number
     */
    public void setPhone(String newPhone) {
        this.mPhone = newPhone;
    }

    /**
     * Sets the image of the <code>Pet</code>.
     * @param newImageUri The new pet image
     */
    public void setImageUri(Uri newImageUri) {
        this.mImageUri = newImageUri;
    }

    /**
     * Creates a String representation of the entire object,
     * with all member variables displayed.
     * @return The string representation of the object
     */
    public String toString() {
        return "Game{" +
                "Id=" + mID +
                ", Name='" + mName + '\'' +
                ", Details='" + mDetails + '\'' +
                ", Phone=" + mPhone +
                ", Image='" + mImageUri.toString() + '\'' +
                '}';
    }
}