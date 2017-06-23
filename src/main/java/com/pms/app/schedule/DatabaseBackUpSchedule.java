package com.pms.app.schedule;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Date;

@Component
public class DatabaseBackUpSchedule {

    private final DatabaseSetting databaseSetting;


    @Value("${backup.databaseName}")
    String dbName;

    @Value("${backup.mysqlDumppath}")
    String dumpPath;

    @Autowired
    public DatabaseBackUpSchedule(DatabaseSetting databaseSetting) {
        this.databaseSetting = databaseSetting;
    }

//    @Scheduled(cron = "0 0 24 * * ?")

    @Scheduled(fixedRate = 50000)
    public void backUpDatabase() {
        System.out.println("entered scheduled");
        try {
         /*NOTE: Creating Database Constraints*/

            String dbUser = databaseSetting.getUsername();
            String dbPass = databaseSetting.getPassword();

            String executeCmd = "mysqldump.exe -u " + dbUser + " -p " + dbPass + " " + dbName + " > " + dumpPath + File.separator + new Date().toString();
            System.out.println(executeCmd);
            Runtime rt1 = Runtime.getRuntime();
            System.out.println("Command to execute :: " + executeCmd);
            Process p = rt1.exec(executeCmd);
            final BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = is.readLine()) != null) {
                System.out.println(line);
            }
            final BufferedReader is2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = is2.readLine()) != null) {
                System.out.println(line);
            }


        } catch (Exception exception)

        {
            System.out.println("Error at Backup store" + exception.getMessage());
        }
    }
}
