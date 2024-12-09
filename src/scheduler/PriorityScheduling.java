package scheduler;

import java.util.ArrayList;
import java.util.Comparator;

public class PriorityScheduling {

    public void run(ArrayList<Process> processes, int contextSwitching) {
        // Sort processes by arrival time initially
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0; // Current simulation time
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nNon-Preemptive Priority Scheduling:");

        // List to keep track of completed processes
        ArrayList<Process> completedProcesses = new ArrayList<>();
        boolean[] isCompleted = new boolean[processes.size()]; // Track completed processes

        while (completedProcesses.size() < processes.size()) {
            Process selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            // Select the highest-priority process that has arrived and is not completed
            for (int i = 0; i < processes.size(); i++) {
                Process p = processes.get(i);
                if (p.getArrivalTime() <= currentTime && !isCompleted[i]) {
                    if (p.getPriority() < highestPriority) {
                        highestPriority = p.getPriority();
                        selectedProcess = p;
                    } else if (p.getPriority() == highestPriority && p.getArrivalTime() < selectedProcess.getArrivalTime()) {
                        selectedProcess = p;
                    }
                }
            }

            // If no process is ready, move time to the next arrival
            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            // Apply context switching if this isn't the first process
            if (currentTime > 0 && !completedProcesses.isEmpty()) {
                currentTime += contextSwitching;
            }

            // Start execution of the selected process
            int startTime = currentTime;
            currentTime += selectedProcess.getBurstTime();
            int endTime = currentTime;

            // Calculate waiting and turnaround times
            selectedProcess.wait = startTime - selectedProcess.getArrivalTime();
            selectedProcess.turn = endTime - selectedProcess.getArrivalTime();

            // Record the execution details for GUI visualization
            selectedProcess.addExecution(startTime, endTime);

            // Update totals
            totalWaitingTime += selectedProcess.wait;
            totalTurnaroundTime += selectedProcess.turn;

            // Print process details
            System.out.println("Process " + selectedProcess.getProcess_name()
                    + " | Waiting Time: " + selectedProcess.wait
                    + " | Turnaround Time: " + selectedProcess.turn);

            // Mark the process as completed
            completedProcesses.add(selectedProcess);
            isCompleted[processes.indexOf(selectedProcess)] = true;
        }

        // Calculate and print average waiting time and turnaround time
        int totalProcesses = completedProcesses.size();
        if (totalProcesses > 0) {
            double averageWaitingTime = totalWaitingTime / totalProcesses;
            double averageTurnaroundTime = totalTurnaroundTime / totalProcesses;

            System.out.println("\nAverage Waiting Time: " + averageWaitingTime);
            System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        } else {
            System.out.println("No processes to schedule.");
        }
    }
}
