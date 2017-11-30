package com.xappie.db;

/**
 * Created by Santosh on 29-11-2017.
 */

public class DBConstants  {
    public static final String TABLE_NOTIFICATION_HISTORY = "notificationHistory";

    public static final String NOTIFICATION_ID = "_id";
    public static final String NOTIFICATION_CATEGORY = "category";
    public static final String NOTIFICATION_TITLE = "title";
    public static final String NOTIFICATION_TIME = "time";
    public static final String NOTIFICATION_SOURCE = "source";
    public static final String NOTIFICATION_IMAGE_URL = "image_url";
    public static final String NOTIFICATION_ISOPENED = "isopened";


    public static final String CREATE_TABLE_NOTIFICATION_HISTORY = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NOTIFICATION_HISTORY
            + "(" + NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTIFICATION_CATEGORY + "  TEXT NOT NULL, "
            + NOTIFICATION_TITLE + "  TEXT NOT NULL,"
            + NOTIFICATION_TIME + "  TEXT NOT NULL"
            + NOTIFICATION_SOURCE + " TEXT NOT NULL"
            + NOTIFICATION_ISOPENED + " TEXT NOT NULL"
            + NOTIFICATION_IMAGE_URL + "TEXT NOT NULL"
            + ")";
}
