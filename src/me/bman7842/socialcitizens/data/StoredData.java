package me.bman7842.socialcitizens.data;

/**
 * Created by brand_000 on 6/27/2015.
 */
public class StoredData {

    private static String news;
    private static boolean newsEnabled;
    public static void setNewsEnabled(boolean value) { newsEnabled = value; }
    public static boolean isNewsEnabled() {return newsEnabled; }
    public static void setNews(String str) { news = str; }
    public static String getNews() { return news; }

    private static Integer pokeDelayTime = 10;
    public static void setPokeDelayTime(Integer amount) { pokeDelayTime = amount; }
    public static Integer getPokeDelayTime() { return pokeDelayTime; }

}
