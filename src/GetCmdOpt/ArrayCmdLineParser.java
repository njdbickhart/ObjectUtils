/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GetCmdOpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Derek.Bickhart
 */
public class ArrayCmdLineParser extends SimpleCmdLineParser{
    protected Map<String, List<String>> arrays = new HashMap<>();
    protected Map<String, CmdType> cmdtypes = new HashMap<>();
    
    public ArrayCmdLineParser(String usage){
        this.usage = usage;
    }
    
    public ArrayCmdLineParser(){
        
    }
    
    /**
     * This method strips the command line argument string array down to its basic elements
     * to search for requested options.
     * @param args The argument String array from the main routine
     * @param keys A String of command line options to search for in the main args array. Must conform
     * to a two character code, with the first character indicating the flag to search for, and the second
     * character indicating the type of argument it is (a value option (":"), a boolean flag
     * ("|") or an array of several inputs/outputs ("-"). Example: "A|B:C-" searches for -A as a boolean flag, -B 
     * as an option with a value and -C as an array.
     * @throws Exception
     */
    @Override
    public void ProcessCmdString(String[] args, String keys) throws Exception{
        // keys will be in a two character format like so:
        // A:, A| or A-
        // A: <- tag will contain a string
        // A| <- tag is a boolean flag
        // A- <- tag is an array of inputs
        if(keys.length() % 2 != 0)
            throw new Exception("[GETOPT] Keys do not follow two character convention!");
        String[] tags = keys.split("(?!^)");
        for(int i = 0; i < tags.length; i += 2){
            String k = tags[i];
            String format = tags[i+1];
            if(!format.equals(":") && !format.equals("|") && !format.equals("-"))
                throw new Exception("[GETOPT] The key value must be a \":\", a \"-\" or a \"|\"!");
            
            // Set boolean flags to false by default
            if(format.equals("|"))
                this.values.put(k, "false");
            
            for(int x = 0; x < args.length; x++){
                if(args[x].equals("-" + k)){
                    switch(format){
                        case ":":
                            super.populateValueString(k, args[x+1]);
                            this.cmdtypes.put(k, CmdType.MAP);
                            break;
                        case "-":
                            if(!this.arrays.containsKey(k))
                                this.arrays.put(k, new ArrayList<String>());
                            this.arrays.get(k).add(args[x+1]);
                            this.cmdtypes.put(k, CmdType.ARRAY);
                            break;
                        case "|":
                            super.populateBooleanFlag(k);
                            this.cmdtypes.put(k, CmdType.FLAG);
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * This is a simple check to ensure that the user has input the mandatory
     * command line keys into the command line arguments
     * @param keys a string containing the concatenated keys the programmer wants to check. ie. "ACGT" checks
     * to see if the "-A", "-C", "-G" and "-T" flags/values are set
     * @return "true" if mandatory values are all set. "false" if a value is missing
     */
    @Override
    public boolean SimpleParityCheck(String keys){
        String[] tags = keys.split("(?!^)");
        for(String k : tags){
            if(!this.values.containsKey(k) && !this.arrays.containsKey(k))
                return false;
        }
        return true;
    }
    
    /**
     * Checks to see if an option has been set for this flag
     * @param k cmd line option to check
     * @return "True" if the option exists; "False" if it does not
     */
    @Override
    public boolean HasOpt(String k){
        return this.cmdtypes.containsKey(k);
    }
    
    /**
     * This method associates existing command line keys with input string values.
     * This is done simply for programmer readability when calling the "GetValue" method
     * @param k A concatenated string of key values that will be replaced by larger strings
     * @param a The larger strings to replace the aforementioned "k" keys. WARNING: must be the same size as
     * the number of characters in the "k" string!
     * @throws Exception 
     */
    @Override
    public void AssociateKeyWithLargerString(String k, String ... a) throws Exception{
        String[] keys = k.split("(?!^)");
        if(keys.length != a.length)
            throw new Exception("[GETOPT] Have not assigned appropriate number of keys!");
        
        for(int x = 0; x < keys.length; x++){
            switch(this.cmdtypes.get(keys[x])){
                case FLAG:
                    this.cmdtypes.put(a[x], CmdType.FLAG);
                    this.values.put(a[x], this.values.get(keys[x]));
                    break;
                case MAP:
                    this.cmdtypes.put(a[x], CmdType.MAP);
                    this.values.put(a[x], this.values.get(keys[x]));
                    break;
                case ARRAY:
                    this.cmdtypes.put(a[x], CmdType.ARRAY);
                    this.arrays.put(a[x], new ArrayList<String>());
                    this.arrays.get(a[x]).addAll(this.arrays.get(keys[x]));
                    break;
            }            
        }
    }
    
    /**
     * Returns a key if it is in the hash
     * @param k The key to search for in the command line options
     * @return The string representation of the argument input value
     */
    @Override
    public String GetValue(String k){
        if(this.values.containsKey(k) && (this.cmdtypes.get(k) == CmdType.FLAG || this.cmdtypes.get(k) == CmdType.MAP))
            return this.values.get(k);
        else
            return null;
    }
    
    /**
     * Returns a list of values if the key is in the hash
     * @param k The key to search for in the command line options
     * @return The list of strings in the input
     */
    public List<String> GetArray(String k){
        if(this.cmdtypes.containsKey(k))
            if(this.cmdtypes.get(k) == CmdType.ARRAY)
                return this.arrays.get(k);
        return new ArrayList<>();
    }
    
    protected enum CmdType{
        FLAG, ARRAY, MAP
    }
}
