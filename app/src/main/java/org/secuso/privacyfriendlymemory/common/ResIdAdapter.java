/*
    This file is part of Privacy Friendly Memo Game.

    Privacy Friendly Memo Game is free software: you can redistribute it
    and/or modify it under the terms of the GNU General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
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
