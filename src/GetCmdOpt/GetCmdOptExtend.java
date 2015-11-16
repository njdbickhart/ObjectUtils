/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GetCmdOpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bickhart
 */
public abstract class GetCmdOptExtend extends GetCmdOpt{
    protected Map<String, ArrayList<String>> arrayVals = new HashMap<>();
    protected Map<String, Map<String, String>> hashVals = new HashMap<>();
    
    /**
     * This method strips the command line argument string array down to its basic elements
     * to search for requested options.
     * @param args The argument String array from the main routine
     * @param keys A String of command line options to search for in the main args array. Must conform
     * to a two character code, with the first character indicating the flag to search for, and the second
     * character indicating the type of argument it is (a string value option (":"), a boolean flag
     * ("|"), an array ("@") or a hash ("%")). Example: "A|B:" searches for -A as a boolean flag and 
     * -B as an option with a value.
     * @throws Exception
     */
    @Override
    public void ProcessCmdString(String[] args, String keys) throws Exception{
        // keys will be in a two character format like so:
        // A: or A|
        // A: <- tag will contain a string
        // A| <- tag is a boolean flag
        // A@ <- tag is an arraylist
        // A% <- tag is a hashmap
        if(keys.length() % 2 != 0)
            throw new Exception("[GETOPT] Keys do not follow two character convention!");
        String[] tags = keys.split("(?!^)");
        for(int i = 0; i < tags.length; i += 2){
            String k = tags[i];
            String format = tags[i+1];
            if(!format.equals(":") && !format.equals("|"))
                throw new Exception("[GETOPT] The key value must be a \":\" or a \"|\"!");
            
            // Set boolean flags to false by default
            if(format.equals("|"))
                this.values.put(k, "false");
            
            for(int x = 0; x < args.length; x++){
                if(args[x].equals("-" + k)){
                    if(format.equals(":"))
                        super.populateValueString(k, args[x+1]);
                    else
                        super.populateBooleanFlag(k);
                }
            }
        }
    }
    
    protected void populateArrayValue(String k, String value){
        
    }
}
