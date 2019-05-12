package com.example.myapplication;

public class RecepiesConstant {
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

    public static final long CACHE_IMAGE_SIZE = 10 * Mb;
    public static final long CACHE_HTTP_SIZE = 10 * Mb;

    public static final int CALL_TIMEOUT = 60;
    public static final int CONNECTION_TIMEOUT = 10;

}
