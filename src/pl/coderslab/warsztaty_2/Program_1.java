package pl.coderslab.warsztaty_2;


import org.apache.commons.lang3.StringUtils;
import pl.coderslab.warsztaty_2.daos.UserDao;
import pl.coderslab.warsztaty_2.models.User;

import java.util.Scanner;

public class Program_1 {

    public static void main(String[] args) {

        int option;
        boolean quitOptionIsChosen = false;
        while (!quitOptionIsChosen) {
            printUsers();
            option = getOption();
            switch (option) {
                case 1:
                    addOption();
                    break;
                case 2:
                    editOption();
                    break;
                case 3:
                    deleteOption();
                    break;
                case 4:
                    quitOptionIsChosen = true;
            }
        }
    }

    public static void printUsers(){
        UserDao userDao = new UserDao();
        User[] users = userDao.findAll();
        System.out.printf("%-6s|%-30s|%-50s|%-12s%n","ID","USERNAME","EMAIL","GROUP ID");
        String separateLine = StringUtils.repeat("-",98);
        System.out.println(separateLine);
        for(User user : users){
            System.out.printf("%-6s|%-30s|%-50s|%-12s%n",user.getId(),user.getUserName(), user.getEmail(),user.getUserGroupId());
            System.out.println(separateLine);
        }
    }

    public static int getOption(){
        int answer = 0;
        boolean answerIsCorrect = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one of the follow option: ");
        System.out.println("1 - add | 2 - edit | 3 - delete | 4 - quit");
        do {
            if(scanner.hasNextInt()){
                answer = scanner.nextInt();
                if((answer == 1) || (answer == 2) || (answer == 3) || (answer == 4)){
                    answerIsCorrect = true;
                } else {
                    System.out.println("invalid input data");
                }
            } else {
                scanner.nextLine();
                System.out.println("invalid input data");
            }
        } while (!answerIsCorrect);
        return answer;
    }

    public static String getData(String dataName){
        Scanner scanner = new Scanner(System.in);
        System.out.print(dataName + ": ");
        return scanner.nextLine();
    }

    public static void addOption(){
        System.out.println("Give a data of new user:");
        String username = getData("Username");
        String email = getData("Email");
        String password = getData("Password");
        int user_group = Integer.parseInt(getData("User_group"));
        UserDao userDao = new UserDao();
        User user = new User(username,email,password,user_group);
        userDao.create(user);
    }

    public static void editOption(){
        UserDao userDao = new UserDao();
        System.out.println("Give a proper id number of user whose you want to edit");
        int id_number = Integer.parseInt(getData("User_Id"));
        if(userDao.checkIfIdIsProper(id_number)){
            User user = userDao.read(id_number);
            System.out.println("Give a new data:");
            user.setUserName(getData("Username"));;
            user.setEmail(getData("Email"));;
            user.setPassword(getData("Password"));;
            user.setUserGroupId(Integer.parseInt(getData("User_group")));
            userDao.update(user);
        } else {
            System.out.println("User with given id does not exist");
        }
    }

    public static void deleteOption(){
        UserDao userDao = new UserDao();
        System.out.println("Give a proper id number of user whose you want to delete");
        int id_number = Integer.parseInt(getData("User_Id"));
        if(userDao.checkIfIdIsProper(id_number)){
            userDao.delete(id_number);
        } else {
            System.out.println("User with given id does not exist.");
        }
    }
}
