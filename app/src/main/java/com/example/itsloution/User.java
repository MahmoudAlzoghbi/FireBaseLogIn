package com.example.itsloution;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private DatabaseReference mDatabase;

    public int userId;
    public String name;
    public String username;
    public String password;

    public User(){}

    public User( String name , String username , String password ){
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public void WriteNewUser (int id , String name , String username , String password){
        User user = new User(name , username , password);

        mDatabase.child("users").setValue(user);

    }

}
