/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StrUtils;

import java.util.ArrayList;

/**
 *
 * @author bickhart
 */
public class StrArray {
    public static String Join(ArrayList<String> a, String delimiter){
        if(a.isEmpty())
            return "";
        String ret = a.get(0);
        for(int i = 1; i < a.size(); i++){
            ret += delimiter + a.get(i);
        }
        return ret;
    }
    
    public static String Join(String[] a, String delimiter){
        String ret = a[0];
        for(int i = 1; i < a.length; i++){
            ret += delimiter + a[i];
        }
        return ret;
    }
    public static String Join(String[] a, String delimiter, int start){
        if(start >= a.length)
            return "";
        String ret = a[start];
        for(int i = start + 1; i < a.length; i++){
            ret += delimiter + a[i];
        }
        return ret;
    }
}
