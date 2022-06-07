package com.duckrace.app;
import com.duckrace.Board;
import com.duckrace.Reward;

import java.util.Locale;
import java.util.Scanner;

/**
 * This is the application "controller."
 * It directs the overall of the application.
 * It interacts with the user (promp)  and sends those responses into the system("model").
 */
public class DuckRaceApp {
    private Board board = Board.getInstance();
    private Scanner scanner = new Scanner(System.in); // Reads from console input

    /**
     * Directs the high-level application flow.
     */
    public void execute() {
        welcome();
        showBoard();
        int id = prompForId();
        Reward reward = prompForReward();
        updateBoard(id,reward);
        showBoard(); // This is the updated board, with the new winner info in there
    }

    private void updateBoard(int id, Reward reward) {
        board.update(id, reward);
    }

    //DONE: implement this
    private Reward prompForReward() {
        Reward reward =  null;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please enter [D]ebit card or [P]rizes: ");
            String input = scanner.nextLine().toUpperCase();
            if (input.matches("P|D")){
                validInput = true;

                // Ternary expression - use this for assignment in an if-else context
                reward = ("D".equals(input)) ? Reward.DEBIT_CARD : Reward.PRIZES;
            }
        }
        return reward;
    }

    private int prompForId() {
        int id = 0;

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Please enter id of the winner [1-22]: ");
            String input = scanner.nextLine(); // BLOCKS for input
            if (input.matches("\\d{1,2}")) {  // any digit, one or two times
                id = Integer.parseInt(input); // now it's safe to parse it into int
                if (1 <= id && id <= 22) {
                    validInput = true;
                }
            }
        }
        return id;
    }

    private void showBoard() {
        System.out.println();
        board.show();
        System.out.println();
    }

    private void welcome() {
        System.out.println("\n\n");
        System.out.println("W E L C O M E  T O  T H E  D U C K  R A C E  A P P L I C A T I O N");
        System.out.println("\n\n");

    }
}