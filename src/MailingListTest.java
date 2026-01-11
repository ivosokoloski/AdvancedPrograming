import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO
interface User{
    void notify(String mailingListName, String text);
}

class MailingListUser implements User{
    String name;
    String email;
    Map<String, String> mails;

    public MailingListUser(String name, String email) {
        this.name = name;
        this.email = email;
        mails= new HashMap<>();
    }

    @Override
    public void notify(String mailingListName, String text) {
        System.out.println("[USER] "+name+" received email from "+mailingListName+": "+text);
    }
}
class FilteredMailingListUser implements User{
    String name;
    String email;
    String keyWord;
    Map<String, String> mails;

    public FilteredMailingListUser(String name, String email, String keyWord) {
        this.name = name;
        this.email = email;
        this.keyWord = keyWord.toLowerCase();
        mails= new HashMap<>();
    }

    @Override
    public void notify(String mailingListName, String text) {
        if(text.toLowerCase().contains(keyWord)){
//            [FILTERED USER] Bojan received filtered email from FINKI: Exam schedule is available
            System.out.println("[FILTERED USER] "+name+" received filtered email from "+mailingListName+": "+text);

        }
    }
}
class AdminUser implements User{
    String name;
    String email;
    Map<String, String> mails;
    public AdminUser(String name, String email) {
        this.name = name;
        this.email = email;
        mails= new HashMap<>();
    }

    @Override
    public void notify(String mailingListName, String text) {
//        [ADMIN LOG] MailingList=FINKI | Message=New lab exercises are published
        System.out.println("[ADMIN LOG] MailingList="+mailingListName+" | Message="+text);

    }
}
interface MailingList{
    void subscribe(User user);
    void unsubscribe(User user);
    void publish(String text);
}

class SimpleMailingList implements MailingList{
    List<User> userList;
    String listName;


    public SimpleMailingList(String listName) {
        this.listName=listName;
        this.userList = new ArrayList<>();
    }

    @Override
    public void subscribe(User user) {
        userList.add(user);
    }

    @Override
    public void unsubscribe(User user) {
        userList.remove(user);
    }

    @Override
    public void publish(String text) {
        userList.forEach(u->{
            u.notify(listName,text);
        });
    }
}

public class MailingListTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        Map<String, MailingList> mailingLists = new HashMap<>();
        Map<String, User> usersByEmail = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            String[] parts = line.split(" ");

            String command = parts[0];

            switch (command) {

                case "CREATE_LIST": {
                    String listName = parts[1];
                    mailingLists.put(listName, new SimpleMailingList(listName));
                    break;
                }

                case "ADD_USER": {
                    String listName = parts[1];
                    String type = parts[2];
                    String name = parts[3];
                    String email = parts[4];

                    User user;
                    if (type.equals("NORMAL")) {
                        user = new MailingListUser(name, email);
                    } else if (type.equals("FILTERED")) {
                        String keyword = parts[5];
                        user = new FilteredMailingListUser(name, email, keyword);
                    } else { // ADMIN
                        user = new AdminUser(name, email);
                    }

                    usersByEmail.put(email, user);
                    mailingLists.get(listName).subscribe(user);
                    break;
                }

                case "REMOVE_USER": {
                    String listName = parts[1];
                    String email = parts[2];

                    User user = usersByEmail.get(email);
                    mailingLists.get(listName).unsubscribe(user);
                    break;
                }

                case "PUBLISH": {
                    String listName = parts[1];
                    String text = line.substring(
                            line.indexOf(listName) + listName.length() + 1
                    );
                    mailingLists.get(listName).publish(text);
                    break;
                }
            }
        }
    }
}
