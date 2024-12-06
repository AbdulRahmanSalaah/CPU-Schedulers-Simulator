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
        
        System.out.printf("Average Waiting Time: %.2f\n", totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnaroundTime / processes.size());
    }
}
