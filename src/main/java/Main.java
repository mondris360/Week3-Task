
import java.io.File;
import java.io.IOException;
public class Main {

    public static void main(String[] args) throws Exception {
        // check if a valid environment name was passed via command line
        if ( args.length > 0){
            final boolean environment = validateCommandLineEnvArg(args[0]);
            if (!environment){ // if the argument is not a valid environment
                throw new IllegalArgumentException("Invalid environment name");
            }
        }
        // default to production if no command line argument was passed
        final String configFileName = (args.length != 0) ? args[0]+"-config.txt" : "production-config.txt";
          try {
            ConfigParser config = new ConfigParser(configFileName);
              System.out.println(config.getConfigValue("application.name"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

//    validate the environment name that was passed via command line
    public static boolean validateCommandLineEnvArg(String commandLineArgument){
            if(!(commandLineArgument.equals("development") || commandLineArgument.equals("production")|| commandLineArgument.equals("staging"))) {
                return false;
            }
            return true;
    }
}
