package org.objectsNotPrimitives.expenseManager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ScriptExecutor {

    public void executeSQL(String scriptPath, Properties dbProp) throws IOException {
        Process process = Runtime.getRuntime()
                .exec("psql -U " + dbProp.getProperty("spring.datasource.username") + " -d "
                        + dbProp.getProperty("spring.datasource.url").split("/")[3] + " -f " + scriptPath);
        writeLogs(process);
    }

    private void writeLogs(Process process) throws IOException {
        String log;
        try (BufferedReader logStream = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((log = logStream.readLine()) != null) {
                System.out.println(log);
            }
        }

    }
}

