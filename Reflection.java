package com.example.betterhelp;

import com.google.firebase.firestore.Exclude;

public class Reflection {
    private String documentID;
    private String reflection;

    public Reflection() {
        //public no-arg constructor needed
    }

    public Reflection(String reflection) {
        
        this.reflection = reflection;
    }

    public String getReflection() {
        return reflection;
    }

    @Exclude
    public String getDocumentID() {

        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
