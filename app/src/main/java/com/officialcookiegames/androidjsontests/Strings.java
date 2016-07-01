package com.officialcookiegames.androidjsontests;

/**
 * Created by Kacper on 2016-06-30.
*/
public final class Strings {
    public static class pass{
        static String netConnection = "Połączono!",
               sendMessage = "Wysłano!",
               downloadData = "Udało się pobrać wszystkie dane!";

    }
    public static class failure{
        static String errorSendMessage = "Wiadomość nie została wysłana!",
               errorUniversal = "Coś poszło nie tak!";
    }
    public static class error{
        static String netConnection = "Brak połączenia z internetem!";
    }
    public static class other{
        static String emptyMessage = "Uzupełnij pseudonim lub wiadomość!";
        static String[] menu = {"Czat","Menu"};
    }
    public static String version(){return BuildConfig.VERSION_NAME;}
}
