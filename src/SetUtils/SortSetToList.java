/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author bickhart
 */
public class SortSetToList {
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
      List<T> list = new ArrayList<>(c);
      java.util.Collections.sort(list);
      return list;
    }
    
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c, Comparator<T> a){
        List<T> list = new ArrayList<>(c);
        java.util.Collections.sort(list, a);
        return list;
    }
}
