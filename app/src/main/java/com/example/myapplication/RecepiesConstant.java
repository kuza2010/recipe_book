package com.example.myapplication;

public class RecepiesConstant {
    //User id
    public static final int USER_ID = 1;
    public static final int USER_UNAUTHORIZED_ID = -1;

    //AWS
    public static final String BASE_URL="https://vy5trnewne.execute-api.eu-central-1.amazonaws.com/";
    public static final String IMAGE_API_URL="Prod/api/images/getimage";
    public static final String IMAGE_PARAMETER="?image_id=";


    //Units
    public static final long Kb = 1024L;
    public static final long Mb = 1024 * Kb;


    //Network
    public static final String CACHE_CONTROL_HEADER = "Cache-Control";
    public static final String NO_CACHE = "no-cache";
    public static final String CACHE = "";

    public static final long CACHE_IMAGE_SIZE = 30 * Mb;
    public static final long CACHE_HTTP_SIZE = 10 * Mb;

    public static final int CALL_TIMEOUT = 60;
    public static final int CONNECTION_TIMEOUT = 10;


    //Limits
    public static final int SUGGEST_LENGTH_MIN = 3;
    public static final int LIMIT_POPUP_SUGGEST = 4;
    public static final int LIMIT_POPUP_SUGGEST_ADD_PRODUCT = 10;

    public static final int LIMIT_CHARACTERS_IN_SEARCH = 20;
    public static final int LIMIT_CHARACTERS_IN_SEARCH_INGREDIENTS= 128;

    public static final int DELAY_CHARACTER_ENTERED = 500; //1.5 sec


    //Split char
    public static final String SPLIT_CHAR = ",";

}
