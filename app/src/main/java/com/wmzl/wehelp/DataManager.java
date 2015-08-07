package com.wmzl.wehelp;

import android.view.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import as.leap.LASAnalytics;
import as.leap.LASDataManager;
import as.leap.LASObject;
import as.leap.LASQuery;
import as.leap.LASQueryManager;
import as.leap.LASUser;
import as.leap.LASUserManager;
import as.leap.callback.FindCallback;
import as.leap.callback.SaveCallback;
import as.leap.exception.LASException;

/**
 * Created by leo on 15-7-8.
 */
public class DataManager {
    private static final int ACTIVITY_LOGIN = 0;
    //	private static final int ACTIVITY_EDIT = 1;
    public static final int LOGIN_ID = Menu.FIRST;
    public static final int SELECT_PHOTO_ID = 10;
    public static final int ADDHELP_ID = 20;
    public static final int ShowSS_ID = 30;

    private static final String ANONYMOUS_USERNAME = "#SECRET#";

    private static boolean isUserLoggedIn = false;
    private static String currentPageName = "#None#";


    private static DataManager instance;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            if (LASUser.getCurrentUser() != null) isUserLoggedIn = true;
            instance = new DataManager();
        }
        return instance;
    }

//    public List<LASObject> list=new ArrayList<>();
//
//    public List<LASObject> GetList(String objName){
//
//        LASQuery<LASObject> query = LASQuery.getQuery("Help");
//
//        LASQueryManager.findAllInBackground(query, new FindCallback<LASObject>() {
//            public void done(List<LASObject> helpList, LASException e) {
//                if (e == null) {
//                    for (LASObject o : helpList) {
//                        list.add(o);
//                    }
//                }
//                else
//                {
//                }
//            }
//        });
//        return list;
//    }

    public boolean GetIsUserLogedIn() {
        return isUserLoggedIn;
    }

    public void SetIsUserLogedIn(boolean b) {
        isUserLoggedIn = b;
    }

    public LASUser GetCurrentUser() {
        return LASUser.getCurrentUser();
    }

    public String GetCurrentUserName() {
        LASUser user=LASUser.getCurrentUser();
        if(user!=null)
        return user.getUserName();
        else return ANONYMOUS_USERNAME;
    }

    public void SaveHelpInBackground(HelpModel model) {
        LASObject help = new LASObject("Help");
        help.put("HelpTitle", model.getHelpTitle());
        help.put("PublisherName", model.getName());
        LASDataManager.saveInBackground(help);
    }

    public void SignOutCurrentUser() {
        LASUser.logOut();
    }

    public void TrackCurrentPage(String pageName) {

//        if (currentPageName.equals("#None#")) {
//            currentPageName = pageName;
//            LASAnalytics.onPageStart(pageName);
//        } else if (currentPageName.equals(pageName)) {
//
//        } else {
//            LASAnalytics.onPageEnd(currentPageName);
//            currentPageName = pageName;
//            LASAnalytics.onPageStart(pageName);
//        }
    }
}
