import java.util.ArrayList;
import java.util.Comparator;

public class FCAI {

    public void execute(ArrayList<Process> processes) {
        int currentTime = 0, completed = 0;
        double V1 = calculateV1(processes); // Normalize Arrival Time
        double V2 = calculateV2(processes); // Normalize Burst Time

        ArrayList<Process> readyQueue = new ArrayList<>();
        
        // Sum of waiting and turnaround times to calculate averages
        double totalWaitingTime = 0, totalTurnaroundTime = 0;

        System.out.println("\n--- FCAI Scheduling ---");

        while (completed < processes.size()) {
            // Add processes that have arrived to the ready queue
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }

            // If the ready queue is empty, increment time and continue
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Sort the ready queue by FCAI factor
            readyQueue.sort(Comparator.comparingDouble(p -> calculateFCAIFactor(p, V1, V2)));

            // Select the process with the best FCAI factor
            Process currentProcess = readyQueue.remove(0);

            // Execute the process for 40% of its quantum (non-preemptive)
            int quantum = (int) currentProcess.getQuantum();
            int nonPreemptiveTime = (int) Math.ceil(0.4 * quantum);
            int executionTime = Math.min(nonPreemptiveTime, currentProcess.getRemainingBurstTime());

            System.out.println("Executing " + currentProcess.getProcess_name() + " from " + currentTime + " to " + (currentTime + executionTime));
            currentProcess.setRemainingBurstTime(currentProcess.getRemainingBurstTime() - executionTime);
            currentTime += executionTime;

            // If process still has work after non-preemptive execution
            if (currentProcess.getRemainingBurstTime() > 0) {
                // Allow preemption
                int remainingQuantum = quantum - executionTime;

                // Update quantum dynamically
                if (remainingQuantum > 0) {
                    currentProcess.setQuantum(currentProcess.getQuantum() + remainingQuantum);
                } else {
                    currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                }

                // Re-add process to ready queue
                readyQueue.add(currentProcess);
            } else {
                // Process completed
                completed++;

                // Calculate turnaround and waiting times
                int turnaroundTime = currentTime - currentProcess.getArrivalTime();
                int waitingTime = turnaroundTime - currentProcess.getBurstTime();

                // Accumulate the total waiting and turnaround times
                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;

                // Set these values in the process object
                currentProcess.setTurnaroundTime(turnaroundTime);
                currentProcess.setWaitingTime(waitingTime);

                System.out.println(currentProcess.getProcess_name() +
                        " | Completion Time: " + currentTime +
                        " | Turnaround Time: " + turnaroundTime +
                        " | Waiting Time: " + waitingTime);
            }
        }

        // Calculate averages
        calculateAverages(processes, totalWaitingTime, totalTurnaroundTime);
    }

    private double calculateFCAIFactor(Process process, double V1, double V2) {
        return (10 - process.getPriority()) +
                (process.getArrivalTime() / V1) +
                (process.getRemainingBurstTime() / V2);
    }

    private double calculateV1(ArrayList<Process> processes) {
        int maxArrivalTime = processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1);
        return maxArrivalTime / 10.0;
    }

    private double calculateV2(ArrayList<Process> processes) {
        int maxBurstTime = processes.stream().mapToInt(Process::getBurstTime).max().orElse(1);
        return maxBurstTime / 10.0;
    }

    private void calculateAverages(ArrayList<Process> processes, double totalWaitingTime, double totalTurnaroundTime) {
        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();

        System.out.println("-----------------------------------------");
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
