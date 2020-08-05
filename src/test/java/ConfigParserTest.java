
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigParserTest {

    @Test
    // should throw an error when an invalid configFilename is passed
   void InvalidConfigFileNameShouldThrowAnError()  { // throw an IllegalArgumentException  when an invalid config filename was passed
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            ConfigParser config = new ConfigParser("invalidFileName");
        });
    }
    @Test // should read and store values when a valid config file name is passed
    void shouldReadAndStoreConfigValues() throws Exception {
        ConfigParser config = new ConfigParser("development-config.txt");
        Assertions.assertEquals("development", config.getConfigValue("mode"));
    }

    @Test
    // should not override an existing key value
    void shouldNotOverrideExistingKeyValue() throws  Exception {
        ConfigParser config = new ConfigParser("development-config.txt");
        Assertions.assertNotEquals("fintrack-development!", config.getConfigValue("application.name"));
    }

    @Test
        // should use  the production config file as default if no config file is passed to the constructor
    void shouldDefaultToProductionEnv() throws  Exception {
        ConfigParser config = new ConfigParser();
        Assertions.assertEquals("production", config.getConfigValue("mode"));
    }

}


