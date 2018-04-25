package edu.android.mainmen.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import edu.android.mainmen.Controller.AllFoodDTO;

public class Function {

    private FirebaseAuth auth;

    public void onStarClicked(DatabaseReference postRef) {
        auth = FirebaseAuth.getInstance();
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                AllFoodDTO firebaseData = mutableData.getValue(AllFoodDTO.class);
                if (firebaseData == null) {
                    return Transaction.success(mutableData);
                }

                if (firebaseData.stars.containsKey(auth.getCurrentUser().getUid())) {
                    // Unstar the post and remove self from stars
                    firebaseData.starCount = firebaseData.starCount - 1;
                    firebaseData.stars.remove(auth.getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    firebaseData.starCount = firebaseData.starCount + 1;
                    firebaseData.stars.put(auth.getCurrentUser().getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(firebaseData);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed

            }
        });
    }

}
