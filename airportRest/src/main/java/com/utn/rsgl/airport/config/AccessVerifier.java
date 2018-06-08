package com.utn.rsgl.airport.config;

public class AccessVerifier {

    public static boolean hasPermission(){
        boolean hasPermission = isLogued();
        //TODO : security for user permission.

        return hasPermission;
    }

    public static boolean isLogued(){
        boolean isLogued = true;
        //TODO : checks if an user is logued.
        return isLogued;
    }
}
