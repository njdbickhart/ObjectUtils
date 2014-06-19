/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GetCmdOpt;

/**
 * This is a very simple implementation of my GetCmdOpt abstract class. Relatively inflexible, but functional
 * @author bickhart
 */
public class SimpleCmdLineParser extends GetCmdOpt{
    
    /**
     * The constructor
     * @param usage The program's usage statement
     */
    public SimpleCmdLineParser(String usage){
        this.usage = usage;
    }
    
    /**
     * This wraps the parity checking and argument parsing of the GetCmdOpt abstract class.
     * It does not allow mode designation for programs, but it is a very fast and simple 
     * implementation designed to create fast command line options for a program.
     * It contains a help parser (-h, -H, or --help) and usage printer.
     * @param args The program's command line options
     * @param flags The flag string (see javadocs for GetCmdOpt for this two character scheme)
     * @param required A concatenated string containing the required flags for the program
     */
    public void GetAndCheckOpts(String[] args, String flags, String required){
        try{
            if(args.length == 0){
                System.out.println(this.usage);
                System.exit(0);
            }
                
            for(String a : args){
                if(a.equals("-h")
                        || a.equals("-H")
                        || a.equals("--help")){
                    System.out.println(this.usage);
                    System.exit(0);
                }
            }
            
            this.ProcessCmdString(args, flags);
            if(!this.SimpleParityCheck(required)){
                System.out.println("Missing key command line arguments!");
                System.out.println(this.usage);
                System.exit(0);
            }
        }catch(Exception ex){
            System.out.println("Error with programmer input!" + ex.getMessage());
            System.exit(-1);
        }
    }
}
