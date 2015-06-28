package me.bman7842.socialcitizens.utils;

import java.util.ArrayList;

/**
 * Created by brand_000 on 6/5/2015.
 */
public class ChatAbuseCheck {

    //TODO: Implement report method for reporting users

    private static ArrayList<String> words = new ArrayList<String>();
    private static boolean antiSwearEnabled;

    public static void setAntiSwearEnabled(Boolean value)
    {
        antiSwearEnabled = value;
    }

    public static void addSwearWords(String word)
    {
        words.add(word);
    }
    public static void addSwearWords(ArrayList<String> words)
    {
        for (String word : words)
        {
            words.add(word);
        }
    }

    public static String removeProfanity(String m)
    {
        if (antiSwearEnabled == false) return m;
        String message = m;
        String lmsg = m.toLowerCase();
        for (String w : words) {
            if (lmsg.contains(w)) {
                message = message.toLowerCase().replaceAll(w, "****");
            }
        }
        return message;
    }

    public static boolean containsProfanity(String m)
    {
        if (antiSwearEnabled == false) return false;
        String message = m;
        String lmsg = m.toLowerCase();
        for (String w: words) {
            if (lmsg.contains(w))
            {
                return true;
            }
        }
        return false;
    }
}
