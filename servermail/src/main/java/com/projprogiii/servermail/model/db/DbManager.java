package com.projprogiii.servermail.model.db;

import com.projprogiii.lib.objects.Email;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.exit;

public class DbManager {

    private final String dbPath;

    private DbManager(){
        dbPath = new File("").getAbsolutePath() + "/servermail/src/main/data/";
        File f = new File(dbPath);

        try {
            if (!f.exists() && !f.mkdirs()){
                throw new IOException();
            }
        } catch (IOException e){
            System.out.println("Cannot create the database folder!");
            exit(1);
        }
    }

    public static DbManager getInstance(){
        return new DbManager();
    }

    public boolean checkUser(String user){
        String[] dirs = new File(dbPath).list(
                (current, name) -> new File(current, name)
                        .isDirectory()
        );

        return dirs != null && dirs.length != 0 &&
                Arrays.stream(dirs).toList().contains(user);
    }

    public void addUser(String user){
        File f = new File(dbPath + user);

        try {
            if (!f.mkdirs()){
                throw new IOException();
            }
        } catch (IOException e){
            System.out.println("Cannot create " + user + " user folder!");
        }
    }

    //TODO implement sync
    public void saveEmail(Email email){
        ArrayList<String> emailAddresses = new ArrayList<>(email.getReceivers());

        //Store the email into the sender folder first
        storeEmail(email.getSender(), email);

        //Setting the email as it's to be read for all
        //other receivers users
        Email emailToBeRead = Email.setToRead(email);
        for (String s : emailAddresses) {
            addUser(s);
            storeEmail(s, emailToBeRead);
        }
    }

    private void storeEmail(String s, Email email){
        try {
            FileOutputStream fout;
            fout = new FileOutputStream(dbPath + "/" + s +  "/" + email.getId() + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(email);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
