package es.uniovi.ds.lab.main;

/**
 * IMPORTANT: The code provided for this exercise is the minimum necessary to understand
 * the exercise and should NEVER be taken as an example of proper use of exceptions,
 * assertions, and tests. All of the above, which should be done in a real program, has been
 * intentionally omitted to simplify the exercise.
 */

import java.io.*;
import java.util.*;

public class Main {

    private static int ip = 0;
    private static int[] memory = new int[1024];
    private static int[] stack = new int[32];
    private static int sp = 0;

    private static List<String[]> instructions = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        var fileName = "factorial.txt";
        // var fileName = "fibonacci.txt";

        try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = file.readLine()) != null)
                loadInstruction(line);
        }

        executeProgram();
    }

    // $ Cargar programa --------------------------------
    private static void loadInstruction(String line) {

        if (line.isBlank())
            return;

        String[] words = line.split(" ");
        instructions.add(words);
    }

    // $ Métodos Auxiliares --------------------------------
    private static void push(int value) {
        stack[sp] = value;
        sp++;
    }

    private static int pop() {
        sp--;
        return stack[sp];
    }

    // $ Motor de Ejecución --------------------------------
    private static void executeProgram() {

        Scanner console = new Scanner(System.in);

        while (ip < instructions.size()) {

            String[] instruction = instructions.get(ip);

            if (instruction[0].equals("push")) {
                push(Integer.parseInt(instruction[1]));
                ip++;

            } else if (instruction[0].equals("add")) {
                push(pop() + pop());
                ip++;

            } else if (instruction[0].equals("sub")) {
                int b = pop();
                int a = pop();
                push(a - b);
                ip++;

            } else if (instruction[0].equals("mul")) {
                push(pop() * pop());
                ip++;

            } else if (instruction[0].equals("jmp")) {
                ip = Integer.parseInt(instruction[1]);

            } else if (instruction[0].equals("jmpg")) {
                int b = pop();
                int a = pop();
                if (a > b)
                    ip = Integer.parseInt(instruction[1]);
                else
                    ip++;

            } else if (instruction[0].equals("load")) {
                int address = pop();
                push(memory[address]);
                ip++;

            } else if (instruction[0].equals("store")) {
                int value = pop();
                int address = pop();
                memory[address] = value;
                ip++;

            } else if (instruction[0].equals("input")) {
                System.out.println("Enter an integer: ");
                push(console.nextInt());
                ip++;

            } else if (instruction[0].equals("output")) {
                System.out.println(pop());
                ip++;
            }
        }
    }
}
