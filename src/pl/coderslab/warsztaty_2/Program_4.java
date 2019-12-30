package pl.coderslab.warsztaty_2;

import org.apache.commons.lang3.StringUtils;
import pl.coderslab.warsztaty_2.daos.ExerciseDao;
import pl.coderslab.warsztaty_2.daos.SolutionDao;
import pl.coderslab.warsztaty_2.daos.UserDao;
import pl.coderslab.warsztaty_2.models.Solution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program_4 {

    public static void main(String[] args) {

        int option;
        boolean quitOptionIsChosen = false;
        while (!quitOptionIsChosen) {
            option = getOption();
            switch (option) {
                case 1:
                    addOption();
                    break;
                case 2:
                    viewOption();
                    break;
                case 3:
                    quitOptionIsChosen = true;
            }
        }
    }

    public static int getOption(){
        int answer = 0;
        boolean answerIsCorrect = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one of the follow option: ");
        System.out.println("1 - add | 2 - view | 3 - quit ");
        do {
            if(scanner.hasNextInt()){
                answer = scanner.nextInt();
                if((answer == 1) || (answer == 2) || (answer == 3)){
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

    public static void addOption(){
        int user_id;
        int exercise_id;
        Program_1.printUsers();
        UserDao userDao = new UserDao();
        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Give user ID: ");
        user_id = Integer.parseInt(Program_1.getData("user_ID"));
        if(userDao.checkIfIdIsProper(user_id)){
            Program_2.printExercises();
            System.out.println("Give exercise ID: ");
            exercise_id = Integer.parseInt(Program_1.getData("exercise_ID"));
            if(exerciseDao.checkIfIdIsProper(exercise_id)){
                System.out.println("Give solution: ");
                String userAnswer = getData("solution");
                Solution solution = new Solution(getDate(), exercise_id, user_id, userAnswer);
                SolutionDao solutionDao = new SolutionDao();
                solutionDao.create(solution);
            } else {
                System.out.println("Exercise with given id does not exist");
            }

        } else {
            System.out.println("User with given id does not exist");
        }

    }

    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(date);
    }

    public static void viewOption(){
        int user_id;
        UserDao userDao = new UserDao();
        Program_1.printUsers();
        System.out.println("Give a proper id number of user whose solutions you want to review");
        user_id = Integer.parseInt(Program_1.getData("user_ID"));
        if(userDao.checkIfIdIsProper(user_id)){
            SolutionDao solutionDao = new SolutionDao();
            Solution[] solutions = solutionDao.findAllByUserId(user_id);
            System.out.printf("%-13s|%-23s|%-23s|%-200s%n","EXERCISE_ID","CREATED","UPDATED","DESCRIPTION");
            String separateLine = StringUtils.repeat("-",259);
            System.out.println(separateLine);
            for (Solution solution : solutions){
                System.out.printf("%-13s|%-23s|%-23s|%-200s%n",solution.getExerciseId(),solution.getCreated(),solution.getUpdated(),solution.getDescription());
                System.out.println(separateLine);
            }
        } else {
            System.out.println("User with given id does not exist");
        }
    }

    public static String getData(String dataName){
        Scanner scanner = new Scanner(System.in);
        System.out.print(dataName + ": ");
        return scanner.nextLine();
    }

}
