package banking;

public class Main {
    public static void main(String[] args) {
        int input = Gui.openingPage();
        while (input != 0) {
            if (input == 1) {
                Gui.registerPage();
                input = Gui.openingPage();
            } else if (input == 2) {
                boolean flag = Gui.logInPage();
                if (!flag) {
                    input = Gui.openingPage();
                }
                else {
                    int homePageInput = Gui.homePage();
                    while (homePageInput != 0) {
                        if (homePageInput == 1) {
                            homePageInput = Gui.homePage();
                        } else if (homePageInput == 2) {
                            input = Gui.openingPage();
                            break;
                        }
                    }
                    if (homePageInput == 0) {
                        input = 0;
                    }
                }
            } else {
                System.out.println("Wrong input. Try again.");
                System.out.println();
                input = Gui.openingPage();
            }
        }
        System.out.println("Bye!");
    }
}