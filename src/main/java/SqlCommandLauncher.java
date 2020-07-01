import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqlCommandLauncher {


        public SqlCommandLauncher() {
        }

        public void launchSQLscript (String sqlName) throws IOException {

            Process p = Runtime.getRuntime().exec
                    ("psql -d postgres -u postgres -f " +sqlName);


        }

    }

