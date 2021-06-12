package com.example.countryapifetchrecyclerviewwithlocation.database;

import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class FirebaseRepo {
    //Calling recursive Methods
    CommonFunctions fun = new CommonFunctions();
    private final String TAG = this.getClass().getCanonicalName();
    //Initializing firebase connection
    private final FirebaseFirestore db;
    public FirebaseRepo() {
        db = FirebaseFirestore.getInstance();
        getExistingList();
    }

    /*
    Collections & their MutableLiveDate along with the List of model class
     */
    private final String COLLECTION_CU = "Country Details";
    public MutableLiveData<List<Country>> countryMutableLiveData = new MutableLiveData<List<Country>>();
    public List<Country> countryList = new ArrayList<>();

    public List<Country> getExistingList() {
        try {
            db.collection(COLLECTION_CU)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                fun.LogCatD(TAG,"Failed with some error");
                                return;
                            }

                            if (value.isEmpty()) {
                                fun.LogCatE(TAG,"No documents found in the collection");
                            } else {
                                for (DocumentChange docChange : value.getDocumentChanges()) {
                                    Country country = docChange.getDocument().toObject(Country.class);
                                    country.setId(docChange.getDocument().getId());
                                    switch(docChange.getType()){
                                        case ADDED:
                                            countryList.add(country);
                                            break;

                                        case MODIFIED:
                                            break;

                                        case REMOVED:
                                            countryList.remove(country);
                                            break;
                                    }
                                }
                            }countryMutableLiveData.postValue(countryList);
                        }
                    });

            return countryList;
        } catch (Exception e) {
            fun.LogCatE(TAG,"Unable to fetch data");
            return null;
        }

    }

    public void addFav(Country ac) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("countryName", ac.getCountryName());
            data.put("countryCode", ac.getCountryCode());
            data.put("countryPopulation", ac.getCountryPopulation());
            data.put("countryCapital", ac.getCountryCapital());

            List<Country> check = this.getExistingList();

            for(int i = 0; i<check.size(); i++) {
                db.collection(COLLECTION_CU).add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                fun.LogCatD(TAG, "Data added successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                fun.LogCatE(TAG, e.getLocalizedMessage());
                            }
                        });
                if (check.get(i).getCountryName().contentEquals(ac.getCountryName())) {
                    fun.LogCatD("Duplicate found","Trying to add duplicate");
                    //don't add
                } else {
                    //add

                }
            }
        } catch (Exception e) {
            fun.LogCatE(TAG, e.getLocalizedMessage());
        }
    }

    public void deleteFav(Country country){
        try{
            db.collection(COLLECTION_CU)
                    .document(country.getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            fun.LogCatD(TAG, "Deleted record successfully from Firebase");
                            countryList.remove(country);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            fun.LogCatE(TAG, "Failed to delete record from Firebase");
                        }
                    });
            countryMutableLiveData.postValue(countryList);
        }catch(Exception e){
            fun.LogCatE(TAG, "Got an exception inside function & error is - "+e.getLocalizedMessage());
        }
    }
}
