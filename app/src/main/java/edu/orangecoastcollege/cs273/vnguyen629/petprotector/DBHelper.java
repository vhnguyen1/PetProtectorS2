package edu.orangecoastcollege.cs273.vnguyen629.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 *
 *
 * @author Vincent Nguyen
 */
class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE_NUMBER = "phone";
    private static final String FIELD_IMAGE_URI = "image_uri";

    /**
     *
     * @param context
     */
    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate (SQLiteDatabase db){
        String petTable = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DETAILS + " TEXT, "
                + FIELD_PHONE_NUMBER + " TEXT, "
                + FIELD_IMAGE_URI + " TEXT" + ")";
        db.execSQL(petTable);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    /**
     * Adds a new <code>Pet</code> into the current database
     * @param newPet New <code>Pet</code> to be added too the database
     */
    public void addPet(Pet newPet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String petName = newPet.getName();
        String petDetails = newPet.getDetails();
        String petPhone = newPet.getPhone();
        String petImageUri = newPet.getImageURI().toString();

        values.put(FIELD_NAME, petName);
        values.put(FIELD_DETAILS, petDetails);
        values.put(FIELD_PHONE_NUMBER, petPhone);
        values.put(FIELD_IMAGE_URI, petImageUri);

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    /**
     * Returns a list of all the current pets inside the database
     * @return The list of all the current pets
     */
    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DETAILS, FIELD_PHONE_NUMBER,
                        FIELD_IMAGE_URI},
                null, null, null, null, null, null );

        if (cursor.moveToFirst()){
            do {
                int petID = cursor.getInt(0);
                String petName = cursor.getString(1);
                String petDetails = cursor.getString(2);
                String petPhone = cursor.getString(3);
                Uri petImageUri = Uri.parse(cursor.getString(4));

                Pet pet = new Pet(petID, petName, petDetails, petPhone, petImageUri);
                petArrayList.add(pet);

            } while (cursor.moveToNext());
        }

        db.close();
        return petArrayList;
    }

    /**
     * Retrieves a specified <code>Pet</code> from the database
     * @param id The unique id of the <code>Pet</code>
     * @return The pet with the matching unique ID
     */
    public Pet getPet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DETAILS, FIELD_PHONE_NUMBER,
                        FIELD_IMAGE_URI},
                KEY_FIELD_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        int petID = cursor.getInt(0);
        String petName = cursor.getString(1);
        String petDetails = cursor.getString(2);
        String petPhone = cursor.getString(3);
        Uri petImageUri = Uri.parse(cursor.getString(4));

        Pet pet = new Pet(petID, petName, petDetails, petPhone, petImageUri);
        db.close();

        return pet;
    }

    /**
     * Applies changes/updates to a <code>Pet</code>
     * @param pet The <code>Pet</code> to be updated in the database
     */
    public void updatePet(Pet pet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String petName = pet.getName();
        String petDetails = pet.getDetails();
        String petPhone = pet.getPhone();
        String petImageUri = pet.getImageURI().toString();

        values.put(FIELD_NAME, petName);
        values.put(FIELD_DETAILS, petDetails);
        values.put(FIELD_PHONE_NUMBER, petPhone);
        values.put(FIELD_IMAGE_URI, petImageUri);

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(pet.getID())});
        db.close();
    }

    /**
     * Delete all the pets in the current database
     */
    public void deleteAllPets() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}