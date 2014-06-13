/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EnumSetUtils;

import java.util.EnumSet;

/**
 *
 * @author bickhart
 */
public class EnumStringParser {
    
    /**
     * Copied from Andremoniy, a StackOverflow user.
     * Will convert the value of EnumSet's "toString" method into Enum values
     * @param <E> An Enum class
     * @param eClass The Enum class being used
     * @param str Input string from the EnumSet.toString method. Typical form: 
     * "[Value1, Value2]"
     * @return
     */
    public static <E extends Enum<E>> EnumSet<E> valueOf(Class<E> eClass, String str) {
        String[] arr = str.substring(1, str.length() - 1).split(",");
        EnumSet<E> set = EnumSet.noneOf(eClass);
        for (String e : arr) set.add(E.valueOf(eClass, e.trim()));
        return set;
    }
}
