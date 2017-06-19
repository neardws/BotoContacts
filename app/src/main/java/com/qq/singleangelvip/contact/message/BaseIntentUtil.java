package com.qq.singleangelvip.contact.message;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by singl on 2017/4/10.
 */
public class BaseIntentUtil {
    public static int DEFAULT_ENTER_ANIM;
    public static int DEFAULT_EXIT_ANIM;

    public static Intent intent;

    public static void intentDIY(Activity activity, Class<?> classes) {
        IntentDIY(activity, classes, null, DEFAULT_ENTER_ANIM,
                DEFAULT_EXIT_ANIM,0);
    }

    public static void IntentDIY(Activity activity, Class<?> classes,
                                 Map<String, String> paramMap, int enterAnim, int exitAnim, int theme) {
        intent = new Intent(activity, classes);

        organizeAndStart(activity, classes, paramMap,theme);
        if (enterAnim != 0 && exitAnim != 0) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public static void intentSysDefault(Activity activity, Class<?> classes,
                                        Map<String, String> paramMap,int theme) {
        organizeAndStart(activity, classes, paramMap,theme);
    }

    private static void organizeAndStart(Activity activity, Class<?> classes,
                                         Map<String, String> paramMap,int theme) {
        intent = new Intent(activity, classes);
        Bundle bundle = new Bundle();
        if (0 != theme){
            bundle.putInt("theme",theme);
            intent.putExtras(bundle);
        }
        if (null != paramMap) {
            Set<String> set = paramMap.keySet();
            for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
                String key = iterator.next();
                intent.putExtra(key, paramMap.get(key));
            }
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}