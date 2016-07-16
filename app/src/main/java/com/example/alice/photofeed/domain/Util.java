package com.example.alice.photofeed.domain;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by alice on 7/5/16.
 * Obtain avatar(url)
 * Ontain  Address from  GeoPoint
 *
 */

public class Util {

    private Geocoder geocoder;
    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";


    public Util(Geocoder geocoder) {
        this.geocoder = geocoder;
    }


    public String getAvatarURL(String email) {
        return GRAVATAR_URL + md5(email) + "?s=64";
    }

    /**
     * Get Address from Lat, Long
     * @param lat
     * @param lon
     * @return
     */
    public String getFromLocation(double lat, double lon) {
        String result = "";
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            Address address = addresses.get(0);
            for ( int i = 0 ; i < address.getMaxAddressLineIndex() ; i++ ){
                result += address.getAddressLine(i) + ", ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Grabatar  MD5 of Email
     * @param email
     * @return
     */
    private static final String md5(final String email) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(email.getBytes());
            byte mesaggeDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessaggeDigest : mesaggeDigest) {
                String h = Integer.toHexString(0xFF & aMessaggeDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
