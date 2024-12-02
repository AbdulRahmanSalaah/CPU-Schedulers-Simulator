
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner input = new Scanner(System.in);

    // Global values for context switching and quantum
    private static int contextSwitching = 0;
    private static int roundRobinQuantum = 0;

    public static void main(String[] args) {
        // Initialize the scheduling algorithms
        PriorityScheduling priorityScheduling = new PriorityScheduling();
        SJF sjf = new SJF(); // Shortest Job First
        SRTF srtf = new SRTF(); // Shortest Remaining Time First
        FCAI fcai = new FCAI(); // FCAI Scheduling

        // List to store processes
        ArrayList<Process> priorityprocesses = new ArrayList<>();

        ArrayList<Process> sjfprocesses = new ArrayList<>();
        ArrayList<Process> srtfprocesses = new ArrayList<>();
        ArrayList<Process> fcaiprocesses = new ArrayList<>();

        System.out.println("Process Scheduling Program");
        System.out.println("---------------------------");

        // Input global values
        System.out.print("Enter Number of Processes: ");
        int numProcesses = input.nextInt();

        System.out.print("Enter Context Switching Time: ");
        contextSwitching = input.nextInt();

        // Input process details
        System.out.println("\nEnter Process Details:");
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Process " + i + " details:");
            System.out.print("Name: ");
            String name = input.next();
            System.out.print("Arrival Time: ");
            int arrivalTime = input.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = input.nextInt();
            System.out.print("Priority Number: ");
            int priority = input.nextInt();
            System.out.print("Initial Quantum: ");
            double quantum = input.nextDouble();

            // Create and add process to the list


            Process p1 = new Process(name, arrivalTime, burstTime, priority);
            priorityprocesses.add(p1);

            Process p2 = new Process(name, arrivalTime, burstTime);
            sjfprocesses.add(p2);

            Process p3 = new Process(name, arrivalTime, burstTime);
            srtfprocesses.add(p3);

            Process p = new Process(name, arrivalTime, burstTime, priority, quantum);
            fcaiprocesses.add(p);
        }

        // Scheduling menu
        while (true) {
            System.out.println("\nChoose Scheduling Algorithm:");
            System.out.println("1- Run Non-preemptive Priority Scheduling");
            System.out.println("2- Run Non-preemptive Shortest Job First (SJF)");
            System.out.println("3- Run Shortest-Remaining Time First (SRTF)");
            System.out.println("4- Run FCAI Scheduling");
            System.out.println("5- Exit");

            int opt = input.nextInt();

            if (opt == 1) {
                // Option 1: Run Non-preemptive Priority Scheduling
                // priorityScheduling.setProcesses(processes);
                // priorityScheduling.run(contextSwitching);

            } else if (opt == 2) {
                // Option 2: Run Non-preemptive Shortest Job First (SJF)
                // sjf.execute(processes);

            } else if (opt == 3) {
                // Option 3: Run Shortest-Remaining Time First (SRTF)
                // srtf.execute(processes, contextSwitching);

            } else if (opt == 4) {
                // Option 5: Run FCAI Scheduling
                fcai.execute(fcaiprocesses);

            } else if (opt == 5) {
                // Exit the program
                System.out.println("Exiting Program...");
                break;

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
