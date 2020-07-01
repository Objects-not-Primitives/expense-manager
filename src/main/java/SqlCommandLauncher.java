import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqlCommandLauncher {


        public SqlCommandLauncher() {
        }

        public void launchSQLscript (String sqlName) throws IOException {

            Process p2 = Runtime.getRuntime().exec
                    ("psql -U postgres -f " +sqlName);


        }

    }

