/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StrUtils;

/**
 *
 * @author bickhart
 */
public class NumericCheck {
    public static boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static boolean isFloating(String str){
        return str.matches("-?\\d+\\.\\d+");
    }
}
