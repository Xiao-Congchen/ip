package ducky.ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

/**
 * Ui handles all the elements the user sees, mainly including how
 * the responses are formated and displayed.
 */
public class Ui {
    private static final String DIV_LINE = "\t-------------------------------------";

    public String hello(String addOn) {
        return "Hi I'm Ducky! How can I help you?" + addOn;
    }

    public void helloCli(String addOn) {
        speakCli("Hi I'm Ducky!\n\tHow can I help you?" + addOn);
    }

    /**
     * GUI version
     * Reroutes all current function calls to speak to the dialog box
     * @param msg Ducky's response
     * @return Ducky's response
     */
    public String speak(String msg) {
        return msg;
    }

    /**
     * Command Line version's speak function that prints to the screen directly
     * @param msg Ducky's response
     */
    public void speakCli(String msg) {
        System.out.println(DIV_LINE);
        System.out.println("\t" + msg);
        System.out.println(DIV_LINE);
    }

    public String bye(Storage storage, TaskList taskList) {
        String msg = "Bye bye! See you soon!";
        speak(msg);
        if (!storage.write(taskList.getAll())) {
            msg = "Your tasks have been lost to the pond... Quack...";
            speak(msg);
        };
        return msg;
    }

}
