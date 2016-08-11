package org.secuso.privacyfriendlymemory.common;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 04.08.2016.
 */
public class ResIdAdapter {

    public static List<String> getResourceName(List<Integer> resIds, Context context){
        List<String> resIdResourceNames = new LinkedList<>();
        for(Integer resId : resIds){
            resIdResourceNames.add(context.getResources().getResourceEntryName(resId));
        }
        return resIdResourceNames;
    }
}
