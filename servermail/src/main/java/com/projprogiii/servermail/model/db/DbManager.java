package com.projprogiii.servermail.model.db;

import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class DbManager {

    private final String dbPath;

    private DbManager(){
        dbPath = new File("").getAbsolutePath() + "/servermail/src/main/data/";
        File f = new File(dbPath);

        if (!f.exists()){
            f.mkdirs();
        } else {
            //TODO remove this for final version, as data should not be removed after server startup
            //removeDir will still be used to remove files or folders
            removeDir(dbPath);
            f.mkdirs();
        }
    }

    public static DbManager getInstance(){
        return new DbManager();
    }

    private boolean makeDir(String path){
        File f = new File(dbPath + path);
        return f.mkdirs();
    }

    private void removeDir(String path){
        try {
            Files.walk(Paths.get(path))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean logUser(User user){
        return makeDir(user.emailAddress());
    }
    //TODO implement sync - no need to implement user log checks here, already asked during client sendEmail
    public void saveEmail(Email email){
        ArrayList<String> emailAddresses = new ArrayList<>(email.getReceivers());
        emailAddresses.add(email.getSender());

        for (String s : emailAddresses) {
            logUser(new User(s));
            saveEmailAux(s, email);
        }
    }

    private void saveEmailAux(String s, Email email){
        try {
            FileOutputStream fout;
            fout = new FileOutputStream(dbPath + "/" + s +  "/" + email.getId() + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(email);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
