package scheduler;

import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;

public class Main {

    // Global value for context switching
    private static int contextSwitching = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for their choice: example input or custom input
        System.out.println("Welcome to the Process Scheduling Program!");
        System.out.println("Choose an option:");
        System.out.println("1. Use default example processes");
        System.out.println("2. Enter custom process input");
        int inputChoice = scanner.nextInt();

        // Initialize the scheduling algorithms
        PriorityScheduling scheduling = new PriorityScheduling();
        FCAI fcai = new FCAI(); // FCAI Scheduling
        SJF sjf = new SJF(); // Shortest Job First
        SRTF srtf = new SRTF();

        // List to store processes for different algorithms
        ArrayList<Process> fcaiProcesses = new ArrayList<>();
        ArrayList<Process> sjfProcesses = new ArrayList<>();
        ArrayList<Process> srtfProcesses = new ArrayList<>();
        ArrayList<Process> priorityProcesses = new ArrayList<>();

        if (inputChoice == 1) {
            // Use default example processes
            fcaiProcesses = getDefaultProcessesForFCAI();
            sjfProcesses = getDefaultProcessesForSJF();
            srtfProcesses = getDefaultProcessesForSRTF();
            priorityProcesses = getDefaultProcessesForPriority();
        } else if (inputChoice == 2) {
            // Get custom input from the user
            System.out.println("Enter the context switching time:");
            contextSwitching = scanner.nextInt();

            System.out.println("Enter the number of processes:");
            int numProcesses = scanner.nextInt();

            for (int i = 0; i < numProcesses; i++) {
                System.out.println("Enter details for Process " + (i + 1));
                System.out.print("Process Name: ");
                String name = scanner.next();
                System.out.print("Arrival Time: ");
                int arrivalTime = scanner.nextInt();
                System.out.print("Burst Time: ");
                int burstTime = scanner.nextInt();
                System.out.print("Priority Number: ");
                int priority = scanner.nextInt();
                System.out.print("Quantum Time: ");  // Added Quantum Time input for each process
                int quantum = scanner.nextInt();

                Process processTask1 = new Process(name, burstTime, arrivalTime, priority);
                priorityProcesses.add(processTask1);
                Process processTask2 = new Process(name, burstTime, arrivalTime);
                sjfProcesses.add(processTask2);
                Process processTask3 = new Process(name, burstTime, arrivalTime);
                srtfProcesses.add(processTask3);
                Process processTask4 = new Process(name, arrivalTime, burstTime, priority, quantum);
                fcaiProcesses.add(processTask4);

            }

        }

        // Prompt user to select an algorithm
        System.out.println("Choose the scheduling algorithm:");
        System.out.println("1. Priority Scheduling");
        System.out.println("2. Shortest Job First");
        System.out.println("3. SRTF Scheduling");
        System.out.println("4. FCAI Scheduling");
        int algorithmChoice = scanner.nextInt();

        // Run the selected scheduling algorithm
        switch (algorithmChoice) {
            case 1:
                scheduling.run(priorityProcesses, contextSwitching);
                GUI.processes = priorityProcesses; // Adjust based on selected algorithm
                Application.launch(GUI.class, args);
                break;
            case 2:
                sjf.execute(sjfProcesses);
                GUI.processes = sjfProcesses;
                Application.launch(GUI.class, args);
                break;
            case 3:
                srtf.execute(srtfProcesses, contextSwitching);
                GUI.processes = srtfProcesses;
                Application.launch(GUI.class, args);
                break;
            case 4:
                fcai.execute(fcaiProcesses, contextSwitching);
                GUI.processes = fcaiProcesses;
                Application.launch(GUI.class, args);
                break;
            default:
                System.out.println("Invalid choice. Exiting program.");
                return;
        }

    }

    // Method to get the default processes for FCAI Scheduling
    private static ArrayList<Process> getDefaultProcessesForFCAI() {
        ArrayList<Process> defaultProcesses = new ArrayList<>();

        // Add predefined processes with quantum for each process
        Process p1 = new Process("P1", 0, 17, 4, 4);
        Process p2 = new Process("P2", 3, 6, 9, 3);
        Process p3 = new Process("P3", 4, 10, 3, 5);
        Process p4 = new Process("P4", 29, 4, 8, 2);

        defaultProcesses.add(p1);
        defaultProcesses.add(p2);
        defaultProcesses.add(p3);
        defaultProcesses.add(p4);

        return defaultProcesses;
    }

    // Method to get the default processes for SJF Scheduling
    private static ArrayList<Process> getDefaultProcessesForSJF() {
        ArrayList<Process> defaultProcesses = new ArrayList<>();

        // Add predefined processes (using arrival time, burst time, and quantum for each)
        Process p1 = new Process("P1", 17, 0);
        Process p2 = new Process("P2", 6, 3);
        Process p3 = new Process("P3", 10, 4);
        Process p4 = new Process("P4", 4, 29);

        defaultProcesses.add(p1);
        defaultProcesses.add(p2);
        defaultProcesses.add(p3);
        defaultProcesses.add(p4);

        return defaultProcesses;
    }

    // Method to get the default processes for SRTF Scheduling
    private static ArrayList<Process> getDefaultProcessesForSRTF() {
        ArrayList<Process> defaultProcesses = new ArrayList<>();

        // Add predefined processes (using arrival time, burst time)
        Process p1 = new Process("P1", 17, 0);
        Process p2 = new Process("P2", 6, 3);
        Process p3 = new Process("P3", 10, 4);
        Process p4 = new Process("P4", 4, 29);

        defaultProcesses.add(p1);
        defaultProcesses.add(p2);
        defaultProcesses.add(p3);
        defaultProcesses.add(p4);

        return defaultProcesses;
    }

    // Method to get the default processes for Priority Scheduling
    private static ArrayList<Process> getDefaultProcessesForPriority() {
        ArrayList<Process> defaultProcesses = new ArrayList<>();

        // Add predefined processes (using arrival time, burst time, and priority)
        Process p1 = new Process("P1", 17, 0, 4);
        Process p2 = new Process("P2", 6, 3, 9);
        Process p3 = new Process("P3", 10, 4, 3);
        Process p4 = new Process("P4", 4, 29, 8);

        defaultProcesses.add(p1);
        defaultProcesses.add(p2);
        defaultProcesses.add(p3);
        defaultProcesses.add(p4);

        return defaultProcesses;
    }
}
