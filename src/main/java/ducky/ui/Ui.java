package ducky.ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public class Ui {
    private static final String DIVLINE = "\t-------------------------------------";

    public void hello(String addOn) {
        speak("Hi I'm Ducky!\n\tHow can I help you?" + addOn);
    }

    public void speak(String msg) {
        System.out.println(DIVLINE);
        System.out.println("\t" + msg);
        System.out.println(DIVLINE);
    }

    public String bye(Storage storage, TaskList taskList) {
        String msg = "Bye bye! See you soon!";
        speak(msg);
        if (!storage.save(taskList.getAll())) {
            msg = "Your tasks have been lost to the pond... Quack...";
            speak(msg);
        };
        return msg;
    }

}
