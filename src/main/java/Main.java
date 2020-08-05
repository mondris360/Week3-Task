
import java.io.IOException;
public class Main {

    public static void main(String[] args) throws Exception {
        // default to production if no command line argument was passed
        final String configFileName = (args.length != 0) ? args[0]+"-config.txt" : "production-config.txt";
        // to run this application from command line, please pass the configFileName variable  to the constructor
          try {
            ConfigParser config = new ConfigParser(configFileName);
              System.out.println(config.getConfigValue("application.name"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
