/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GetCmdOpt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Derek.Bickhart
 */
public class ArrayModeCmdLineParser extends GetCmdOpt{
    private Map<String, ArrayModeParser> holder;
    
    /**
     * The current mode selected by the user for the program.
     */
    public String CurrentMode = null;

    public ArrayModeCmdLineParser(String defaultUsage, String... modes) {
        this.usage = defaultUsage;
        this.holder = new HashMap<>();
        for(String s : modes){
            this.holder.put(s, new ArrayModeParser());
        }
    }
    
    /**
     * This method must be invoked for each mode that the programmer wants to 
     * implement in the program. It has the same parameters as the 
     * SimpleCmdLineParser.java class, and it stores those parameters in a 
     * private subclass for later retrieval.
     * @param mode The mode for the program
     * @param usage A usage statement for that mode
     * @param flags The two-character key codes for that mode (ie. A:B|, where 
     * -A expects a value and -B is a boolean flag).
     * @param required A list of required arguments for the program
     * @param associate An ordered string of flags that will be associated with 
     * the subsequent larger "strings" in the parser key codes
     * @param largerkeys An ordered array of "strings" that will store arguments
     * for the programmer's retrieval
     */
    public void AddMode(String mode, String usage, String flags, String required, String associate, String ... largerkeys){
        if(holder.isEmpty()){
            System.err.println("Programmer error with commandline parser structure! Must create modes before using!");
            System.exit(-1);
        }
            
        if(!holder.containsKey(mode))
            holder.put(mode, new ArrayModeParser());
        
        holder.get(mode).SetUsage(usage);
        holder.get(mode).SetMetaData(flags, required, associate, largerkeys);
    }
    
    /**
     * This method uses the raw "args[]" array from the main routine to set
     * and check mode and argument entry for the program. Modes must be initialized
     * (using "AddMode") prior to getting and checking them!
     * @param args The string array that contains the argument input from the
     * programs "main" routine.
     */
    public void GetAndCheckMode(String[] args){
        if(args.length == 0){
            // We have no input args, lets print the default usage
            PrintDefaultUsage();
        }
        try{
            if(!holder.containsKey(args[0])){
                System.out.println("Error! Must input a mode for the program!");
                System.out.println(this.usage);
                System.exit(-1);
            }
            
            CurrentMode = args[0];
            holder.get(args[0]).GetAndCheckOpts(args);
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    
    @Override
    public boolean HasOpt(String k){
        if(CurrentMode == null){
            System.err.println("Programmer error with commandline parser structure! Must select a mode before retrieving values!");
            System.exit(-1);
        }
        return holder.get(CurrentMode).HasOpt(k);
    }
    
    @Override
    public String GetValue(String k){
        if(CurrentMode == null){
            System.err.println("Programmer error with commandline parser structure! Must select a mode before retrieving values!");
            System.exit(-1);
        }
        return holder.get(CurrentMode).GetValue(k);
    }
    
    public List<String> GetArray(String k){
        if(CurrentMode == null){
            System.err.println("Programmer error with commandline parser structure! Must select a mode before retrieving values!");
            System.exit(-1);
        }
        return holder.get(CurrentMode).GetArray(k);
    }
    
    private void PrintDefaultUsage(){
        System.err.println(usage);
        System.exit(0);
    }
    
    private class ArrayModeParser extends ArrayCmdLineParser{
        private String flags = null;
        private String required = null;
        private String associate = null;
        private String[] largerkeys = null;
        
        
        public void GetAndCheckOpts(String[] args) throws Exception{
            if(args.length == 1 && flags != null)
                PrintUsageExit();
            if(flags == null || required == null || associate == null || largerkeys == null)
                throw new Exception("Programmer error with ModeParser class instantiation!");
            String[] slice = Arrays.copyOfRange(args, 1, args.length);
            this.GetAndCheckOpts(slice, flags, required, associate, largerkeys);
        }
        
        public void SetUsage(String usage){
            this.usage = usage;
        }
        
        public void SetMetaData(String flags, String required, String associate, String[] largerkeys){
            this.flags = flags;
            this.required = required;
            this.associate = associate;
            this.largerkeys = largerkeys;
        }
        
        private void PrintUsageExit(){
            System.err.println(usage);
            System.exit(0);
        }
    }
}
