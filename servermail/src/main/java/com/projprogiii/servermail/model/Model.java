package com.projprogiii.servermail.model;

import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.User;
import com.projprogiii.servermail.model.db.DbManager;
import com.projprogiii.servermail.model.log.LogManager;
import com.projprogiii.servermail.model.sync.SyncManager;

import java.util.List;
import java.util.Random;


public class Model {

    private final DbManager dbManager;
    private final SyncManager syncManager;
    private final LogManager logManager;

    private Model(){
        dbManager = DbManager.getInstance();
        syncManager = SyncManager.getInstance();
        logManager = LogManager.getInstance();

        //---------------------------------------------------------------------------------------------------------------//
        //---------------------------------------------------------------------------------------------------------------//
        //TODO delete this, just example
         String[] people = new String[] {"Paolo@unito.it", "Alessandro@unito.it", "Enrico@unito.it",
                "Giulia@unito.it", "Gaia@unito.it", "Simone@unito.it"};
         String[] subjects = new String[] {
                "Importante", "A proposito della nostra ultima conversazione", "Tanto va la gatta al lardo",
                "Non dimenticare...", "Domani scuola" };
         String[] texts = new String[] {
                "È necessario che ci parliamo di persona, per mail rischiamo sempre fraintendimenti",
                "Ricordati di comprare il latte tornando a casa",
                "L'appuntamento è per domani alle 9, ci vediamo al solito posto",
                "Ho sempre pensato valesse 42, tu sai di cosa parlo"
        };


            Random r = new Random();
            for (int i=0; i<1; i++) {
                Email email = new Email(
                        people[r.nextInt(people.length)],
                        List.of(people[r.nextInt(people.length)]),
                        subjects[r.nextInt(subjects.length)],
                        texts[r.nextInt(texts.length)]);

                dbManager.saveEmail(email);
            }
        //---------------------------------------------------------------------------------------------------------------//
        //---------------------------------------------------------------------------------------------------------------//
        //---------------------------------------------------------------------------------------------------------------//


    }
    public static Model getInstance(){
        return new Model();
    }

    public DbManager getDbManager() {
        return dbManager;
    }
    public SyncManager getSyncManager() {
        return syncManager;
    }
    public LogManager getLogManager() {
        return logManager;
    }
}
