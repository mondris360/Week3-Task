import java.io.*;
import java.util.HashMap;

// parse a config file and store   the values in a Map
public class ConfigParser {
    // to store config settings
    private static HashMap<String, String> config;
    private String ConfigFileLocation;

    // constructor
    public ConfigParser(String configFileName) throws Exception {
        this.config = new HashMap<String, String>();
        parseConfigFile(configFileName); // parse the config file once the object is created
    }

    //overloaded constructor to default to production when no config file was passed
    public ConfigParser() throws Exception{
        this.config = new HashMap<String, String>();
        parseConfigFile("production-config.txt"); // default environment when no environment is specified
    }

    // method to parse the config file and save the settings in a hashmap
    private void parseConfigFile(String configFilName) throws Exception {
        // get the user current working directory
        String currentHomeDir = System.getProperty("user.dir");
        // get the file separator of the current OS
        String fileSeparator = System.getProperty("file.separator");

        // check if the user launched the program via command line or via IDE and form a platform independent
//         absolute config file path
        if (currentHomeDir.endsWith("\\Week3")) { // if the user launched the program directly from IDE
            ConfigFileLocation = currentHomeDir + fileSeparator + "src" + fileSeparator
                    + "main" + fileSeparator + "java" + fileSeparator
                    + "config" + fileSeparator + configFilName;
        } else {
            // if the program was launched via command line
            ConfigFileLocation = currentHomeDir + fileSeparator + "config" + fileSeparator + configFilName;
        }
        // check if the Config filename is valid and readable
        boolean fileNameIsValidAndReadable = validateConfigFileName(ConfigFileLocation, configFilName);
        if (!fileNameIsValidAndReadable){
            throw new IllegalArgumentException("Invalid ConfigFile Name or File Is Not Readable");
        }
        // read  config file values and store them in  hashMap as key-value pair
        try {
            readAndStoreConfigFileValues(ConfigFileLocation);
        } catch (IOException e){
            e.printStackTrace();
        }


    }



    // method to get a config value
    public String getConfigValue(String configKey){
        return config.get(configKey);
    }

    // method to check if  the specified config fileName is valid and readable
    private  static boolean validateConfigFileName(String ConfigFileLocation, String configFileName){
        System.out.println(configFileName);
        File file =  new File(ConfigFileLocation);
        // if the file name is not valid
        if(!(configFileName.equals("development-config.txt") || configFileName.equals("production-config.txt")|| configFileName.equals("staging-config.txt"))){
            return false;
            // check if we can  read from the file
        } else return file.canRead();
    }

    // method to read the config file and store the settings as key and value pair in a hashMap
    public static void readAndStoreConfigFileValues(String ConfigFileLocation) throws Exception {
        // create a new buffer reader object and use it to read the appropriate config file
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(ConfigFileLocation))) {
            String eachLineInFile;
            // stop reading file lines once we have gotten to the end of the file
            while ((eachLineInFile = bufferedReader.readLine()) != null) {

                // if the  current file line is not empty and  the line  does not contain the word [application]
                if ((eachLineInFile.trim().length() != 0) && !(eachLineInFile.equals("[application]"))) {
                    // split the  current file line into an array of strings
                    String[] eachLineInFileToArr = eachLineInFile.split("=");
                    String configKey = eachLineInFileToArr[0];
                    String configValue = eachLineInFileToArr[1];

                    // if the configKey is name, rename the key
                    if (configKey.equals("name")) {
                        configKey = "application.name";
                        if (!config.containsKey(configKey)) {
                            config.put(configKey, configValue);
                        } else {
                            System.out.println("key already exist");
                        }

                    } else {
                        if (!config.containsKey(configKey)) {
                            config.put(configKey, configValue);
                        } else {
                            System.out.println("key already exist");
                        }
                    }
                } else {
                    System.out.println("skipping empty line or line containing [application]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(); // throw an error
        }
    }

}
