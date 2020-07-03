import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqlCommandLauncher {

    public void launchSQLscript(String sqlName) throws IOException {
        String line;
        Process p = Runtime.getRuntime().exec
                ("psql -U asdas_test -d asdas_play -f " + sqlName);
        BufferedReader input =
                new BufferedReader
                        (new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }
}

