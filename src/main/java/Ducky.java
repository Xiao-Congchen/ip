public class Ducky {
    private static final String DIVLINE = "------------------------------";

    public static void main(String[] args) {
        greet();
        exit();
    }

    public static void greet() {
        System.out.println(DIVLINE);
        System.out.println("Quack! I am Ducky!\nHow can I help you?");
        System.out.println(DIVLINE);
    }

    public static void exit() {
        System.out.println("Bye! See you soon!");
        System.out.println(DIVLINE);
    }
}
