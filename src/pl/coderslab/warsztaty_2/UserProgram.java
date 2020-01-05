package pl.coderslab.warsztaty_2;

import org.apache.commons.lang3.StringUtils;
import pl.coderslab.warsztaty_2.daos.ExerciseDao;
import pl.coderslab.warsztaty_2.daos.SolutionDao;
import pl.coderslab.warsztaty_2.daos.UserDao;
import pl.coderslab.warsztaty_2.models.Exercise;
import pl.coderslab.warsztaty_2.models.Solution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserProgram {

    public static void main(String[] args) {

        if(args.length == 0){
            System.out.println("There is not any arguments in command line. To run a programme giving a user email is needed.");
        } else if (args.length > 1) {
            System.out.println("There are too many arguments in command line");
        } else {
            UserDao userDao = new UserDao();
            int user_id = userDao.checkIfUserExist(args[0]);
            if(user_id == 0){
                System.out.println(String.format("User with email: %s does not exist", args[0]));
            } else {
                int option;
                boolean quitOptionIsChosen = false;
                while (!quitOptionIsChosen) {
                    option = getOption();
                    switch (option) {
                        case 1:
                            addOption(user_id);
                            break;
                        case 2:
                            viewOption(user_id);
                            break;
                        case 3:
                            quitOptionIsChosen = true;
                    }
                }
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

    public static void addOption(int user_id){
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] exercises = new Exercise[0];
        exercises = exerciseDao.findAllIncompleted(user_id);
        if(exercises.length != 0){
            PrintExercises(exercises);
            int exercise_id;
            System.out.println("Which exercise do you want to solve ?");
            exercise_id = Integer.parseInt(Program_1.getData("exercise_ID"));
            if(belongTo(exercise_id,exercises)){
                String description;
                System.out.println("Give a solution: ");
                description = Program_1.getData("Solution");
                Solution solution = new Solution(getDate(), exercise_id, user_id, description);
                SolutionDao solutionDao = new SolutionDao();
                solutionDao.create(solution);
            } else {
                System.out.println("Exercise with given id does not exist or was solved");
            }
        } else {
            System.out.println("There is nothing to show. All exercises solved");
        }
    }



    public static void viewOption(int user_id){
        SolutionDao solutionDao = new SolutionDao();
        Solution[] solutions = solutionDao.findAllByUserId(user_id);
        PrintSolutions(solutions);
    }

    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(date);
    }

    public static void PrintSolutions(Solution[] solutions){
        System.out.printf("%-13s|%-23s|%-23s|%-200s%n","EXERCISE_ID","CREATED","UPDATED","DESCRIPTION");
        String separateLine = StringUtils.repeat("-",259);
        System.out.println(separateLine);
        for (Solution solution : solutions){
            System.out.printf("%-13s|%-23s|%-23s|%-200s%n",solution.getExerciseId(),solution.getCreated(),solution.getUpdated(),solution.getDescription());
            System.out.println(separateLine);
        }
    }

    public static void PrintExercises(Exercise[] exercises){
        System.out.printf("%-6s|%-30s|%-130s%n","ID","TITLE","DESCRIPTION");
        String separateLine = StringUtils.repeat("-",156);
        System.out.println(separateLine);
        for(Exercise exercise : exercises){
            System.out.printf("%-6s|%-30s|%-130s%n",exercise.getId(), exercise.getTitle(),exercise.getDescription());
            System.out.println(separateLine);
        }
    }

    public static boolean belongTo(int exercise_id, Exercise[] exercises){
        for(Exercise exercise : exercises){
            if(exercise.getId() == exercise_id){
                return true;
            }
        }
        return false;
    }


}
