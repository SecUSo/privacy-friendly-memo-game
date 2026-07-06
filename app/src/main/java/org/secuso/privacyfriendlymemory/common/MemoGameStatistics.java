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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hannes on 04.08.2016.
 */
public class MemoGameStatistics {

    private Map<String, Integer> nameCountMapping = new HashMap<>();

    public MemoGameStatistics(Set<String> statisticsSet){
        createNameCountMapping(statisticsSet);
    }

    public void incrementCount(List<String> resourceNames){
        for(String resourceName : resourceNames){
            Integer count = nameCountMapping.get(resourceName);
            count++;
            nameCountMapping.put(resourceName, count);
        }
    }

    public Set<String> getStatisticsSet(){
        Set<String> statisticsSet = new HashSet<>();
        for(Map.Entry<String, Integer> statisticsEntry : nameCountMapping.entrySet()){
            String nameWithCounter = statisticsEntry.getKey() + "_" + statisticsEntry.getValue();
            statisticsSet.add(nameWithCounter);
        }
        return statisticsSet;
    }

    private void createNameCountMapping(Set<String> statisticsSet){
        for(String statisticsEntry : statisticsSet){
            String resourceName = statisticsEntry.substring(0, statisticsEntry.lastIndexOf("_"));
            Integer count = Integer.parseInt(statisticsEntry.substring(statisticsEntry.lastIndexOf("_")+1, statisticsEntry.length()));
            nameCountMapping.put(resourceName, count);
        }
    }

    public static Set<String> createInitStatistics(List<String> resourceNames){
        Set<String> initialStatistics = new HashSet<>();
        for(String resourceName : resourceNames){
            String initialStatsEntry = resourceName + "_" + 0;
            initialStatistics.add(initialStatsEntry);
        }
        return initialStatistics;
    }

    public Integer[] getFalseSelectedCounts(){
        return nameCountMapping.values().toArray(new Integer[nameCountMapping.values().size()]);
    }

    public String[] getResourceNames(){
        return nameCountMapping.keySet().toArray(new String[nameCountMapping.keySet().size()]);
    }
}
