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
package org.secuso.privacyfriendlymemory.model;

import org.secuso.privacyfriendlymemory.R;

/**
 * Created by Hannes on 20.05.2016.
 */
public enum CardDesign {

    FIRST(1, R.string.carddesign_displayname_first),
    SECOND(2, R.string.carddesign_displayname_second),
    CUSTOM(3, R.string.carddesign_displayname_custom);

    private final int value;
    private final int displayNameResId;

    CardDesign(int value, int displayNameResId){
        this.value = value;
        this.displayNameResId = displayNameResId;
    }

    public int getDisplayNameResId(){ return displayNameResId; }
    public int getValue(){
        return value;
    }

    public boolean isCustom(){
        if(value == 3) {
            return true;
        }else{
            return false;
        }
    }

    public static CardDesign get(int value){
        switch(value){
            case 1:
                return FIRST;
            case 2:
                return SECOND;
            case 3:
                return CUSTOM;
            default:
                return FIRST;
        }
    }

}
