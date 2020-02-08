package com.bhavaniprasad.recyclerview.model;

public class UserDetails {

    private static UserDetails single_instance = null;
    public String username,description;



    public static UserDetails getInstance()
    {
        if (single_instance == null)
            single_instance = new UserDetails();

        return single_instance;
    }

}
