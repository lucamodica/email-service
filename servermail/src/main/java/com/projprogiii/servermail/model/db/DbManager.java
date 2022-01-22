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


    /** Check if a specific user exists, by verifying
     *  if exists its own folder in the db one. */
    public boolean checkUser(String user){
        String[] dirs = new File(dbPath).list(
                (current, name) -> new File(current, name)
                        .isDirectory());

        return dirs != null && dirs.length != 0 &&
                Arrays.stream(dirs).toList().contains(user);
    }

    /** Add a user to the db, creating its own folder in
     *  the db one. */
    public boolean addUser(String user){
        File f = new File(dbPath + user);
        return f.mkdirs();
    }


    /** Find the path of a specific email, giving
     *  the email itself and the user folder. */
    private String findEmailPath(Email email, String user){
        int id = email.getId();
        File f = new File(dbPath + "/" + user +  "/" +  id + ".txt");
        return f.getAbsolutePath();
    }

    /** Storing single email in the specified user
     * folder, in a .txt file format. */
    public boolean saveEmail(Email email, String user){
        boolean result;
        try {
            String path = findEmailPath(email, user);
            FileOutputStream fout;
            fout = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(email);
            out.flush();
            out.close();
            fout.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /** Retrieve the list of all emails of a specified
     *  user. */
    public List<Email> retrieveEmails(String user){

        File[] emailsFiles = new File(dbPath + "/" +
                user +  "/").listFiles();
        FileInputStream fin;
        ObjectInputStream obj;
        List<Email> emails = new ArrayList<>();

        try {
            for (File file : Objects.requireNonNull(emailsFiles)) {
                fin = new FileInputStream(file);
                obj = new ObjectInputStream(fin);
                emails.add((Email) obj.readObject());
                obj.close();
                fin.close();
            }


        } catch (ClassNotFoundException | IOException |
                NullPointerException e) {
            e.printStackTrace();
            emails = null;
        }

        return emails;
    }

    /** Delete a specific email (so, the file), of a
     *  specific user. */
    public boolean deleteEmail(Email email, String user){
        String path = findEmailPath(email, user);
        File f = new File(path);

        return f.delete();
    }

    /** Mark an email as read, changing its specific
     *  boolean attribute and rewriting it in file. */
    public boolean markAsReadEmail(Email email, String user){
        email.setToRead(false);
        return saveEmail(email, user);
    }
}
