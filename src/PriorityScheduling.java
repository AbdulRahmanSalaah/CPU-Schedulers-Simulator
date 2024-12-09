import java.util.ArrayList;
import java.util.Comparator;

public class PriorityScheduling {
    public void run(ArrayList<Process> processes, int contextSwitching) {
        // Sort processes by priority (lower number means higher priority)
        processes.sort(Comparator.comparingInt(Process::getPriority));

        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nNon-Preemptive Priority Scheduling:");

        for (Process p : processes) {
            // Wait until process arrives
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }

            // Apply context switching
            currentTime += contextSwitching;

            // Calculate waiting time
            p.wait = Math.max(0, currentTime - p.getArrivalTime());

            // Execute process
            currentTime += p.getBurstTime();

            // Calculate turnaround time
            p.turn = p.wait + p.getBurstTime();

            totalWaitingTime += p.wait;
            totalTurnaroundTime += p.turn;

            System.out.println("Process " + p.getProcess_name() +
                    " Waiting Time: " + p.wait +
                    ", Turnaround Time: " + p.turn);
        }

        
        // Calc Average Waiting Time, Average Turnaround Time
        if (!processes.isEmpty()) {
            double averageWaitingTime = totalWaitingTime / processes.size();
            double averageTurnaroundTime = totalTurnaroundTime / processes.size();

            System.out.println("Average Waiting Time: " + averageWaitingTime);
            System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        } else {
            System.out.println("No processes to schedule.");
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 7c8a81c4be038d31951ecb2d531fe24a72ee8eb5
