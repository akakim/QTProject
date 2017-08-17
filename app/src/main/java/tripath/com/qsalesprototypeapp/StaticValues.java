package tripath.com.qsalesprototypeapp;

import android.os.Build;

/**
 * Created by SSLAB on 2017-06-22.
 */

public class StaticValues {
    public static String basicURL = "http://qsales.autoground.tripath.work";


    public static final int HANDLER_MESSAGE = 100;
    public static final int JAVASCRIPT_MESSAGE = 200;

    // legacy
    public static final int SHOW_DIALOG_MESSAGE = 99;
    public static final int REMOVE_DIALOG_MESSAGE = 100;
    // new
    public  static final int SHOW_DILATING_DIALOG_MESSAGE = 101;
    public static final int REMOVE_DILATING_DIALOG_MESSAGE = 102;


    public static final int JAVA_SCRIPT_CALLBACK = 200;
    public static final int JAVA_SCRIPT_BACK_CALLBACK = 200;
    public static final int ON_PAGE_FINISHED = 201;


    public static final int LOGIN = 1;
    public static final int SPLASHE_ANIMATION_MESSAGE = 1;

    public static final String SHARED_FCM_TOKEN = "FCM_TOKEN";

    public static final String ADVISOR_SEQUENCE = "advisorSeq";
    public static final String CHATROOM_AUTH_CODE = "chatroomAuthorizationCode";
    public static final String MESSAGE = "message";

    public static final String USER_CACHE = "userCache";
    public static final String SYSTEM_CACHE = "systemCache";
    public static final String AUTO_LOGIN = "autoLogin";
    public static final String ID = "id";
    public static final String PASSWORD = "password";
    public static final String FCM_TOKEN = "fcm_token";


    public static class FragmentTAG{
        public static String ITEM_LIST_FRAGMENT= "ItemListFragment";
    }


    public static final int SYSTEM_SETTING = 999;

    public static boolean IS_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    public static final String REGISTRATION_READY = "registrationReady";
    public static final String REGISTRATION_GENERATING = "registrationGenerating";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String REFRESH_CHATROOMS = "refreshChatRooms";

}
