package pl.coderslab.warsztaty_2;

import org.apache.commons.lang3.StringUtils;
import pl.coderslab.warsztaty_2.daos.GroupDao;
import pl.coderslab.warsztaty_2.models.Group;

import java.util.Scanner;

public class Program_3 {

    public static void main(String[] args) {

        int option;
        boolean quitOptionIsChosen = false;
        while (!quitOptionIsChosen) {
            printGroups();
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

    public static void printGroups(){
        GroupDao groupDao = new GroupDao();
        Group[] groups = groupDao.findAll();
        System.out.printf("%-6s|%-50s%n","ID","NAME");
        String separateLine = StringUtils.repeat("-",56);
        System.out.println(separateLine);
        for(Group group : groups){
            System.out.printf("%-6s|%-50s%n",group.getId(),group.getName());
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
        System.out.println("Give a data of new group:");
        String name = getData("Name");
        GroupDao groupDao = new GroupDao();
        Group group = new Group(name);
        groupDao.create(group);
    }

    public static void editOption(){
        GroupDao groupDao = new GroupDao();
        System.out.println("Give a proper id number of group which you want to edit");
        int id_number = Integer.parseInt(getData("Group_Id"));
        if(groupDao.checkIfIdIsProper(id_number)){
            Group group = groupDao.read(id_number);
            System.out.println("Give a new data:");
            group.setName(getData("Name"));
            groupDao.update(group);
        } else {
            System.out.println("Group with given id does not exist");
        }
    }

    public static void deleteOption(){
        GroupDao groupDao = new GroupDao();
        System.out.println("Give a proper id number of group which you want to delete");
        int id_number = Integer.parseInt(getData("Group_Id"));
        if(groupDao.checkIfIdIsProper(id_number)){
            groupDao.delete(id_number);
        } else {
            System.out.println("Group with given id does not exist");
        }
    }
}

