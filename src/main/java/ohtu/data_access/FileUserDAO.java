package ohtu.data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUserDAO implements UserDao {

    Scanner scanner;
    String filename;
    File file;
    
    @Autowired
    public FileUserDAO(@Value("salasanat.txt") String filename) throws FileNotFoundException, IOException {
        file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

    }

    private List<User> getUsers() throws FileNotFoundException, IOException {
        BufferedReader scanner = new BufferedReader(new FileReader(file));
        List<User> users = new ArrayList<User>();
        String ln;
        while ((ln = scanner.readLine()) != null) {
            String[] lns = ln.split(";");
        }
        scanner.close();
        return users;
    }

    @Override
    public List<User> listAll() {
        try {
            return getUsers();
        } catch (IOException ex) {
            Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private User findUser(String name) throws FileNotFoundException, IOException {
        String ln;BufferedReader scanner = new BufferedReader(new FileReader(file));
        while ((ln = scanner.readLine()) != null) {
            String[] lns = ln.split(";");
            if (name.matches(lns[0])) {
                return new User(lns[0], lns[1]);
            }
        }
        scanner.close();return null;
    }
    

    @Override
    public User findByName(String name) {
        try {
            return findUser(name);
        } catch (IOException ex) {
            Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(user.getUsername() + ";" + user.getPassword());
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}