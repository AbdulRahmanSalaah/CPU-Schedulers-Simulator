package scheduler;

import java.util.ArrayList;

public class SRTF {

    void execute(ArrayList<Process> processes, int contextSwitching) {
        int currentTime = 0;
        int processCounter = 0;
        int threshold = 20;
        double sumWaitingTime = 0;
        double sumTurnaroundTime = 0;
        ArrayList<Process> readyQueue = new ArrayList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();
        Process lastProcess = null; // Track the last executed process

        while (processCounter < processes.size()) {
            // Add processes to ready queue if they have arrived and not finished
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }

            // If no process is ready, increment time
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            //check starved process
            Process starvedProcess = null;
            for (Process process : readyQueue) {
                if (currentTime - process.getArrivalTime() >= threshold) {
                    starvedProcess = process;
                }
            }
            // Find the process with the minimum remaining burst time
            Process minTimeProcess = null;

            if (starvedProcess != null) {
                minTimeProcess = starvedProcess;
            }

            int minTime = Integer.MAX_VALUE;
            for (Process process : readyQueue) {
                if (process.getRemainingBurstTime() < minTime) {
                    minTimeProcess = process;
                    minTime = process.getRemainingBurstTime();
                }
            }

            if (minTimeProcess != null) {
                // If the process changes, record the end time of the previous process
                if (lastProcess != null && lastProcess != minTimeProcess) {
                    lastProcess.setEndTime(currentTime); // Record end time for the previous process
                    lastProcess.addExecution(lastProcess.getStartTime(), lastProcess.getEndTime()); // Save execution interval
                    currentTime += contextSwitching; // Account for context switching
                }

                // Set start time if the process starts for the first time
                if (minTimeProcess.getStartTime() == -1 || lastProcess != minTimeProcess) {
                    minTimeProcess.setStartTime(currentTime);
                }

                // Execute the process for 1 unit of time
                minTimeProcess.setRemainingBurstTime(minTimeProcess.getRemainingBurstTime() - 1);
                System.out.println("Executing process " + minTimeProcess.getProcess_name() + " from " + currentTime + " to " + (currentTime + 1));
                currentTime++;

                // If the process is finished
                if (minTimeProcess.getRemainingBurstTime() == 0) {
                    minTimeProcess.setEndTime(currentTime); // Record final end time
                    minTimeProcess.addExecution(minTimeProcess.getStartTime(), minTimeProcess.getEndTime()); // Save execution interval

                    // Calculate waiting and turnaround times
                    minTimeProcess.compeletion = currentTime;
                    int waitTime = minTimeProcess.compeletion - minTimeProcess.getBurstTime() - minTimeProcess.getArrivalTime();
                    minTimeProcess.setWaitingTime(waitTime);
                    sumWaitingTime += waitTime;

                    int turnaroundTime = minTimeProcess.compeletion - minTimeProcess.getArrivalTime();
                    minTimeProcess.setTurnaroundTime(turnaroundTime);
                    sumTurnaroundTime += turnaroundTime;

                    completedProcesses.add(minTimeProcess);
                    readyQueue.remove(minTimeProcess);
                    processCounter++;
                }
                lastProcess = minTimeProcess; // Update the last executed process
            }
        }

        // Output results
        System.out.println("Processes   |   Waiting time  | Turnaround time");
        System.out.println("==================================================");
        for (Process process : completedProcesses) {
            System.out.println(process.process_name + "          |     " + process.getWaitingTime() + "          |           " + process.getTurnaroundTime());
//            System.out.println("Execution periods: " + process.getExecutionLogString());
        }
        System.out.println("Average Waiting Time: " + sumWaitingTime / processes.size());
        System.out.println("Average Turnaround Time: " + sumTurnaroundTime / processes.size());
    }
}
