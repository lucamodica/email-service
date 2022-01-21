package com.projprogiii.servermail.model.db;

import com.projprogiii.lib.objects.Email;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
                        .isDirectory());

        return dirs != null && dirs.length != 0 &&
                Arrays.stream(dirs).toList().contains(user);
    }

    public boolean addUser(String user){
        File f = new File(dbPath + user);
        return f.mkdirs();
    }

    //TODO implement sync
    public void saveEmail(Email email){
        ArrayList<String> emailAddresses = new ArrayList<>(email.getReceivers());

        //Store the email into the sender folder first
        storeEmail(email, email.getSender());

        //Setting the email as it's to be read for all
        //other receivers users
        email.setToRead(true);
        for (String s : emailAddresses) {
            if(!checkUser(s)) addUser(s);
            storeEmail(email, s);
        }
    }

    private void storeEmail(Email email, String auth){
        try {
            String path = findEmailPath(email, auth);
            FileOutputStream fout;
            fout = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(email);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Email> readEmails(String user){
        File[] emails = new File(dbPath + "/" + user +  "/")
                .listFiles();

        FileInputStream fin;
        ObjectInputStream obj;
        List<Email> list = new ArrayList<>();

        try {
            for (File file : Objects.requireNonNull(emails)) {
                fin = new FileInputStream(file);
                obj = new ObjectInputStream(fin);
                list.add((Email) obj.readObject());
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored){}
        return list;
    }

    private String findEmailPath(Email email, String auth){
        int id = email.getId();
        File f = new File(dbPath + "/" + auth +  "/" +  id + ".txt");
        return f.getAbsolutePath();
    }

    public boolean deleteEmail(Email email, String auth){
        String path = findEmailPath(email, auth);
        File f = new File(path);
        return f.delete();
    }

    public void markAsReadEmail(Email email, String auth){
        email.setToRead(false);
        storeEmail(email, auth);
    }
}
