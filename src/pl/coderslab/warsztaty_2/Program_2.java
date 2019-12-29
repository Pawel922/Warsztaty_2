package pl.coderslab.warsztaty_2;

import org.apache.commons.lang3.StringUtils;
import pl.coderslab.warsztaty_2.daos.ExerciseDao;
import pl.coderslab.warsztaty_2.models.Exercise;

import java.util.Scanner;

public class Program_2 {

    public static void main(String[] args) {

        int option;
        boolean quitOptionIsChosen = false;
        while (!quitOptionIsChosen) {
            printExercises();
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

    public static void printExercises(){
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] exercises = exerciseDao.findAll();
        System.out.printf("%-6s|%-30s|%-130s%n","ID","TITLE","DESCRIPTION");
        String separateLine = StringUtils.repeat("-",156);
        System.out.println(separateLine);
        for(Exercise exercise : exercises){
            System.out.printf("%-6s|%-30s|%-130s%n",exercise.getId(), exercise.getTitle(),exercise.getDescription());
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
        System.out.println("Give a data of new exercise:");
        String title = getData("Title");
        String description = getData("Description");
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise exercise = new Exercise(title, description);
        exerciseDao.create(exercise);
    }

    public static void editOption(){
        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Give a proper id number of exercise which you want to edit");
        int id_number = Integer.parseInt(getData("Exercise_Id"));
        if(exerciseDao.checkIfIdIsProper(id_number)){
            Exercise exercise = exerciseDao.read(id_number);
            System.out.println("Give a new data:");
            exercise.setTitle(getData("Title"));
            exercise.setDescription(getData("Description"));
            exerciseDao.update(exercise);
        } else {
            System.out.println("Exercise with given id does not exist.");
        }
    }

    public static void deleteOption(){
        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Give a proper id number of exercise which you want to delete");
        int id_number = Integer.parseInt(getData("Exercise_Id"));
        if(exerciseDao.checkIfIdIsProper(id_number)){
            exerciseDao.delete(id_number);
        } else {
            System.out.println("Exercise with given id does not exist.");
        }
    }
}
