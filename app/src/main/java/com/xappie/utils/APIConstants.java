package com.xappie.utils;

/**
 * Created by shankar on 4/30/2017.
 */

public class APIConstants {
    public enum REQUEST_TYPE {
        GET, POST, MULTIPART_GET, MULTIPART_POST, DELETE, PUT, PATCH
    }

    private static final String STATUS = "status";
    public final static String SERVER_NOT_RESPONDING = "We are unable to connect the internet. " +
            "Please check your connection and try again.";

    public static String ERROR_MESSAGE = "We could not process your request at this time. Please try again later.";
    //public static String BASE_URL = "http://test.xappie.com/api/";
    public static String BASE_URL = "http://xappie.com/api/";
    public static String GET_LANGUAGES = BASE_URL + "get_languages";
    public static String GET_COUNTRIES = BASE_URL + "get_countries";
    public static String GET_STATES = BASE_URL + "get_states";
    public static String GET_CITIES = BASE_URL + "get_cities";
    public static String GET_LOCALITIES = BASE_URL + "get_localities";
    public static String GET_VIDEOS = BASE_URL + "get_videos";
    public static String GET_STORIES = BASE_URL + "get_stories";
    public static String GET_ENTERTAINMENTS = BASE_URL + "get_entertainments";
    public static String GET_NEWS_DETAILS = BASE_URL + "get_news_details/";
    public static String GET_HOME_CONTENT = BASE_URL + "get_home_content";
    public static String LOGIN = BASE_URL + "login";
    public static String SIGN_UP = BASE_URL + "sign_up";
    public static String SIGN_UP_VERIFY = BASE_URL + "sign_up_verify";
    public static String GET_GALLERY = BASE_URL + "get_gallery";
    public static String GET_LATEST_GALLERY = BASE_URL + "get_latest_gallery";
    public static String GET_GALLERY_DETAILS = BASE_URL + "get_gallery_details/";
    public static String GET_GALLERY_ALBUM = BASE_URL + "get_gallery_album/";
    public static String FORGOT_PASSWORD = BASE_URL + "forgot_password";
    public static String RESET_PASSWORD = BASE_URL + "reset_password";
    public static String UPDATE_PASSWORD = BASE_URL + "update_password";
    public static String UPDATE_PROFILE = BASE_URL + "update_profile";
    public static String GET_HOME_BANNER = BASE_URL + "get_home_banner";
    public static String GET_GALLERY_CATEGORIES = BASE_URL + "get_gallery_categories";
    public static String GET_VIDEO_CATEGORIES = BASE_URL + "get_video_categories";
    public static String UPDATE_PROFILE_PHOTO = BASE_URL + "update_profile_photo";
    public static String GET_EVENTS = BASE_URL + "get_events";
    public static String MY_EVENTS = BASE_URL + "my_events";
    public static String GET_EVENT_DETAILS = BASE_URL + "get_event_details";
    public static String EVENT_GOING = BASE_URL + "event_going";
    public static String ADD_EVENT = BASE_URL + "add_event";
    public static String UPDATE_EVENT = BASE_URL + "update_event";
    public static String DELETE_EVENT = BASE_URL + "delete_event/";
    public static String DELETE_CLASSIFIED = BASE_URL + "delete_classified/";
    public static String DELETE_CLASSIFIED_IMAGE = BASE_URL + "delete_classified_image/";
    public static String WHOIS_GOING = BASE_URL + "whois_going/";
    public static String GET_HOME_CONTENT_LOC = BASE_URL + "get_home_content_loc";
    public static String GET_ADS = BASE_URL + "get_ads";
    public static String GET_JOBS = BASE_URL + "get_jobs";
    public static String GET_JOB_DETAILS = BASE_URL + "get_job_details";
    public static String GET_MY_JOBS = BASE_URL + "get_my_jobs";
    public static String GET_CLASSIFIED_CATEGORIES = BASE_URL + "get_classified_categories";
    public static String GET_CLASSIFIED_SUB_CATEGORIES = BASE_URL + "get_classified_sub_categories/";
    public static String GET_CLASSIFIEDS = BASE_URL + "get_classifieds";
    public static String GET_CLASSIFIED_DETAILS = BASE_URL + "get_classified_details";
    public static String UPDATE_CLASSIFIED = BASE_URL + "update_classified";
    public static String GET_MY_CLASSIFIEDS = BASE_URL + "get_my_classifieds";
    public static String ADD_CLASSIFIED = BASE_URL + "add_classified";
    public static String JOB_CATEGORY_URL = BASE_URL + "get_jobs_categories";
    public static String POST_JOB_URL = BASE_URL + "post_job";
    public static String MY_APPLIED_JOBS_URL = BASE_URL + "get_applied_jobs";
    public static String MY_APPLIED_JOBS_UPDATE_JOB_STATUS = BASE_URL + "update_status";
    public static String DELETE_JOB = BASE_URL + "delete_job/";
    public static String APPLY_JOB = BASE_URL + "apply_job";
    public static String UPDATE_JOB = BASE_URL + "update_job";
    public static String UPDATE_DEVICE = BASE_URL + "update_device";
    public static String UPDATE_PREFERENCE = BASE_URL + "update_preference";
    public static String UPDATE_DEVICE_PREFERENCE = BASE_URL + "update_device_preference";
}
