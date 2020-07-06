import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.Properties;

public class SqlCommandLauncher {

    public void launchSQLscript(String sqlName, String propertiesPath) throws IOException {
        Properties property = PropertyLoader.load(propertiesPath);
        String line;
        Process p = Runtime.getRuntime().exec("psql -U " +property.getProperty("db.login")+" -d "+property.getProperty("db.dbname")+" -f " + sqlName);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }
}

