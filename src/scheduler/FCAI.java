package scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/// ////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// ///////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///
///
///
///
///
///
///
///
///
class FCAI {

    public void execute(List<Process> processes, int contextSwitchingTime) {
        StringBuilder quantumLogs = new StringBuilder();

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort processes by arrival time
        Map<Process, Boolean> processed = new HashMap<>(); // Track processed status

        for (Process p : processes) {
            processed.put(p, false); // Initialize all processes as unprocessed
        }

        int currentTime = processes.stream().mapToInt(p -> p.arrivalTime).min().orElse(0);
        int completedProcesses = 0;
        // Priority queue to store processes based on FCAI factor
        PriorityQueue<Process> processPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.fcaiFactor));

        // Queue to store processes that have arrived
        List<Process> queue = new ArrayList<>();
        double v1 = 0.0, v2 = 0.0;

        // Initialize the FCAI factors and the queue with processes that have arrived
        for (Process p : processes) {

            v1 = Math.max(v1, p.arrivalTime);
            v2 = Math.max(v2, p.burstTime);
        }
        v1 /= 10;
        v2 /= 10;

        for (Process p : processes) {
            if (p.arrivalTime <= currentTime && !processed.get(p)) {
                p.calculateFcaiFactor(v1, v2);
                processed.put(p, true); // Mark as processed
                queue.add(p);
                processPriorityQueue.add(p);
                System.out.println("Process " + p.getProcess_name() + " added to queue at time " + currentTime);
            }

        }

        // Ensure the queue is not empty before polling
        if (processPriorityQueue.isEmpty()) {
            System.out.println("Error: processPriorityQueue is empty. Cannot proceed with polling.");
            return;
        }

        // Initial process selection based on FCAI factor
        Process currentProcess = processPriorityQueue.poll();
        queue.remove(currentProcess);

        System.out.println("Processes execution order:");

        while (completedProcesses < processes.size()) {
            int starTime = currentTime;
            int endTime = 0;

            //  Check if the current process is the last one to execute and break the loop
            if (completedProcesses == processes.size() - 1) {
                starTime = currentTime;
                currentTime += currentProcess.remaining;
                endTime = currentTime;

                while (currentProcess.remaining > 0) {

                    int executedTime = currentProcess.quantum;
                    currentProcess.remaining -= executedTime;

                    System.out.println("Quantum executed for process: " + currentProcess.getProcess_name()
                            + " for " + executedTime + " units of time. Remaining burst time: " + currentProcess.remaining);
                }
                currentProcess.addExecution(starTime, endTime);
                completedProcesses++;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                // Log information
                System.out.println("Executed process: " + currentProcess.getProcess_name()
                        + ", Waiting Time: " + currentProcess.waitingTime + ", Turnaround Time: " + currentProcess.turnaroundTime);
                break;
            }

            // Check for new processes arriving at this time
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !processed.get(p)) {
                    p.calculateFcaiFactor(v1, v2);
                    processed.put(p, true); // Mark as processed
                    queue.add(p);
                    processPriorityQueue.add(p);
                    System.out.println("Process " + p.getProcess_name() + " added to queue at time " + currentTime);
                }
            }

            int executedTime = (int) Math.ceil(0.4 * currentProcess.quantum);

            // If the process can be completed within the quantum time
            if (executedTime >= currentProcess.remaining) {
                starTime = currentTime;
                currentTime += currentProcess.remaining;
                if (executedTime == currentProcess.remaining) {
                    endTime = currentTime;
                    currentProcess.remaining -= executedTime;
                    System.out.println("Quantum executed for process: " + currentProcess.getProcess_name()
                            + " for " + executedTime + " units of time. Remaining burst time: " + currentProcess.remaining);
                } else if (executedTime > currentProcess.remaining) {
                    int tmp = currentProcess.remaining;
                    endTime = currentTime;
                    currentProcess.remaining = 0;
                    System.out.println("Quantum executed for process: " + currentProcess.getProcess_name()
                            + " for " + tmp + " units of time. Remaining burst time: " + currentProcess.remaining);
                }
                currentProcess.addExecution(starTime, endTime);
                completedProcesses++;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                // Log information
                System.out.println("Executed process: " + currentProcess.getProcess_name()
                        + ", Waiting Time: " + currentProcess.waitingTime + ", Turnaround Time: " + currentProcess.turnaroundTime);

                // During that time, new processes may arrive
                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && !processPriorityQueue.contains(p) && p.remaining > 0 && !processed.get(p)) {
                        p.calculateFcaiFactor(v1, v2);
                        processed.put(p, true); // Mark as processed
                        queue.add(p);
                        processPriorityQueue.add(p);
                        System.out.println("Process " + p.getProcess_name() + " added to queue at time " + currentTime);
                    }
                }

                // Move to the next process, if any
                if (!queue.isEmpty()) {
                    currentProcess = queue.get(0);
                    processPriorityQueue.remove(currentProcess);
                    queue.remove(currentProcess);
                    continue;
                }
            }

            // Execute for 40% of the quantum
            int temptime = currentTime + 1;

            currentTime += executedTime;
            endTime = currentTime;
            currentProcess.addExecution(starTime, endTime);
            currentProcess.remaining -= executedTime;

            // Log quantum execution
            System.out.println("Quantum executed for process: " + currentProcess.getProcess_name()
                    + " for " + executedTime + " units of time. Remaining burst time: " + currentProcess.remaining);

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !processPriorityQueue.contains(p) && p.remaining > 0 && !processed.get(p)) {
                    p.calculateFcaiFactor(v1, v2);
                    processed.put(p, true); // Mark as processed
                    queue.add(p);
                    processPriorityQueue.add(p);
                    System.out.println("Process " + p.getProcess_name() + " added to queue at time " + temptime);
                }
            }

            int unusedQuantum = currentProcess.quantum - executedTime;
            Process highestFcaiProcess = processPriorityQueue.peek();

            // Execute the process until the quantum is consumed or the process is completed
            while (highestFcaiProcess == null || (highestFcaiProcess != null && highestFcaiProcess.fcaiFactor >= currentProcess.fcaiFactor && unusedQuantum > 0 && currentProcess.remaining > 0)) {
                int additionalStartTime = currentTime; // Start time for this part of execution

                currentTime++;
                unusedQuantum--;
                currentProcess.remaining--;
                currentProcess.calculateFcaiFactor(v1, v2);

                // Log quantum execution
                System.out.println("Quantum executed for process: " + currentProcess.getProcess_name()
                        + " for 1 unit of time. Remaining burst time: " + currentProcess.remaining);

                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && !processPriorityQueue.contains(p) && p.remaining > 0 && !processed.get(p)) {
                        p.calculateFcaiFactor(v1, v2);
                        processed.put(p, true); // Mark as processed
                        queue.add(p);
                        processPriorityQueue.add(p);
                        System.out.println("Process " + p.getProcess_name() + " added to queue at time " + currentTime);
                    }
                }

                int additionalEndTime = currentTime; // End time for this part of execution
                currentProcess.addExecution(additionalStartTime, additionalEndTime); // Upda
                highestFcaiProcess = processPriorityQueue.peek();
            }
            // If the process is completed, update the waiting and turnaround times
            if (currentProcess.remaining == 0) {
                completedProcesses++;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                // Log information
                System.out.println("Executed process: " + currentProcess.getProcess_name()
                        + ", Waiting Time: " + currentProcess.waitingTime + ", Turnaround Time: " + currentProcess.turnaroundTime);

                // During that time, new processes may arrive
                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && !processPriorityQueue.contains(p) && p.remaining > 0 && !processed.get(p)) {
                        p.calculateFcaiFactor(v1, v2);
                        processed.put(p, true); // Mark as processed
                        queue.add(p);
                        processPriorityQueue.add(p);
                        System.out.println("Process " + p.getProcess_name() + " added to queue at time " + currentTime);
                    }
                }

                // Move to the next process
                if (!queue.isEmpty()) {
                    currentProcess = queue.get(0);
                    processPriorityQueue.remove(currentProcess);
                    queue.remove(currentProcess);
                }
            } // If the process is still not finished, update the quantum and proceed with the next process in the queue
            else {
                currentProcess.calculateFcaiFactor(v1, v2);
                int oldQuantum = currentProcess.quantum; // Capture current quantum before updating
//                System.err.println(currentProcess.process_name + " Old quantum: " + oldQuantum);
                currentProcess.updateQuantum(unusedQuantum); // Update the quantum
                int newQuantum = currentProcess.quantum; // Capture updated quantum

//                System.err.println(currentProcess.process_name + " quantum updated to " + newQuantum);
                // Append quantum update log
                quantumLogs.append(String.format(
                        "At time %d, process %s had its quantum updated: Old Quantum = %d, New Quantum = %d%n",
                        currentTime, // Current time
                        currentProcess.process_name, // Process name
                        oldQuantum, // Old quantum
                        newQuantum // New quantum
                ));
                if (unusedQuantum == 0) {
                    queue.add(currentProcess);
                    processPriorityQueue.add(currentProcess);

                    currentProcess = queue.get(0);
                    processPriorityQueue.remove(currentProcess);
                    queue.remove(currentProcess);

                } // If the process is not completed and there are other processes with higher FCAI factors, switch to the next process
                else {

                    Process tmp = processPriorityQueue.poll();
                    queue.remove(tmp);

                    processPriorityQueue.add(currentProcess);
                    queue.add(currentProcess);
                    currentProcess = tmp;
                }
            }
        }

        // Log average waiting time and average turnaround time
        double avgWaitingTime = processes.stream().mapToInt(p -> p.waitingTime).average().orElse(0.0);
        double avgTurnaroundTime = processes.stream().mapToInt(p -> p.turnaroundTime).average().orElse(0.0);

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        show(processes);

        // Log quantum updates
        System.out.println("\nQuantum Updates:");
        System.out.println(quantumLogs);

    }

    // Show the results after execution
    public void show(List<Process> processes) {
        for (Process p : processes) {
            System.out.println("Process: " + p.getProcess_name() + ", Waiting Time: " + p.waitingTime + ", Turnaround Time: " + p.turnaroundTime);
        }
    }
}
