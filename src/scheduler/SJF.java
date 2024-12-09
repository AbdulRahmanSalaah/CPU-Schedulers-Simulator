package scheduler;
import java.util.ArrayList;


public class SJF {
    private static final int STARVATION_LIMIT = 10; // Time threshold to handle starvation

    public void execute(ArrayList<Process> processes) {
        int time = 0, completedProcesses = 0;
        int n = processes.size();
        int totalWaitingTime = 0, totalTurnaroundTime = 0;

        System.out.println("\nExecuting Non-Preemptive Shortest Job First (SJF) with Starvation Prevention...");

        while (completedProcesses < n) {
            ArrayList<Process> availableProcesses = new ArrayList<>();
            for (Process p : processes) {
                if (p.getRemainingBurstTime() > 0 && p.getArrivalTime() <= time) {
                    availableProcesses.add(p);
                }
            }

            if (availableProcesses.isEmpty()) {
                time++;
                continue;
            }

            // Check for starvation and adjust priorities if necessary
            Process selectedProcess = null;
            for (Process p : availableProcesses) {
                int waitingTime = time - p.getArrivalTime() - (p.getBurstTime() - p.getRemainingBurstTime());
                if (waitingTime > STARVATION_LIMIT) {
                    selectedProcess = p; // Promote the starving process
                    break;
                }
            }

            if (selectedProcess == null) {
                // No starvation; select process with shortest burst time
                //availableProcesses.sort((p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));
                availableProcesses.sort((p1, p2) -> {
                    if (p1.getBurstTime() == p2.getBurstTime()) {
                        return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                    }
                    return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
                });

                selectedProcess = availableProcesses.get(0);
            }
            int startTime = time;

            // Execute the selected process
            time += selectedProcess.getBurstTime();
            selectedProcess.setRemainingBurstTime(0); // Mark process as completed
            selectedProcess.setExitTime(time);

            selectedProcess.addExecution(startTime, time);

            int turnaroundTime = time - selectedProcess.getArrivalTime();
            int waitingTime = turnaroundTime - selectedProcess.getBurstTime();

            selectedProcess.setTurnaroundTime(turnaroundTime);
            selectedProcess.setWaitingTime(waitingTime);

            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;

            completedProcesses++;

            System.out.println("Process " + selectedProcess.getProcess_name() + ": Waiting Time = "
                    + waitingTime + ", Turnaround Time = " + turnaroundTime);
        }

        // Print total waiting and turnaround times
        System.out.println("\nTotal Waiting Time: " + totalWaitingTime);
        System.out.println("Total Turnaround Time: " + totalTurnaroundTime);

        // Calculate averages
        double avgWaitingTime = (double) totalWaitingTime / n;
        double avgTurnaroundTime = (double) totalTurnaroundTime / n;

        // Print averages
        System.out.printf("\nAverage Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
    }
}