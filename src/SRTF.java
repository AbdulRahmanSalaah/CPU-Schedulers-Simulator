import java.util.ArrayList;

public class SRTF {
    void execute(ArrayList<Process>processes,int contextSwitching) {
        int currentTime = 0;
        int processCounter = 0;
        int threshold = 20;
        ArrayList<Process>readyQueue = new ArrayList<>();
        ArrayList<Process>completedProcesses = new ArrayList<>();
        Process lastProcess = null;  // to track the last process
        while (processCounter < processes.size()) {
            for (Process process : processes) {
                // Add processes to ready queue if they have arrived and not finished
                if(process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }
            // If no process is ready, increment time
            if(readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }
            //check starved process
            Process starvedProcess = null;
            for(Process process:readyQueue) {
                if(currentTime - process.getArrivalTime() >= threshold){
                    starvedProcess = process;
                }
            }
            Process minTimeProcess = null;
            if(starvedProcess != null) {
                minTimeProcess = starvedProcess;
            }
            // Find the process with the minimum remaining burst time
            else {
                int minTime = Integer.MAX_VALUE;
                for (Process process : readyQueue) {
                    //gets the process with the minimum remaining time
                    if (process.getRemainingBurstTime() < minTime) {
                        minTimeProcess = process;
                        minTime = process.getRemainingBurstTime();
                    }
                }
            }
            if(minTimeProcess != null) {
                if(lastProcess != minTimeProcess) {
                    currentTime += contextSwitching;
                }
                //Execute the process for 1 unit of time and decrement the remaining time by 1
                minTimeProcess.setRemainingBurstTime(minTimeProcess.getRemainingBurstTime() - 1);
                System.out.println("Executing process " + minTimeProcess.getProcess_name() + " from " + currentTime + " to " + (currentTime + 1));
                currentTime++;
                //if the process is finished
                if(minTimeProcess.getRemainingBurstTime() == 0){
                    //time when the process is completed
                    minTimeProcess.compeletion = currentTime;
                    //waiting time
                    int waitTime = minTimeProcess.compeletion - minTimeProcess.getBurstTime() - minTimeProcess.getArrivalTime();
                    minTimeProcess.setWaitingTime(waitTime);
                    //turnaround time
                    int turnaroundTime = minTimeProcess.compeletion - minTimeProcess.getArrivalTime();
                    minTimeProcess.setTurnaroundTime(turnaroundTime);
                    //adds the process to the completed list of processes
                    completedProcesses.add(minTimeProcess);
                    //remove it from the ready queue
                    readyQueue.remove(minTimeProcess);
                    processCounter++;
                }
            }
            //update last executed prcess
            lastProcess = minTimeProcess;
        }
        System.out.println("Processes   |   Waiting time  | Turnaround time");
        System.out.println("==================================================");
        for (Process process : completedProcesses) {
            System.out.println(process.process_name+"          |     " + process.getWaitingTime()+"          |           "+process.getTurnaroundTime()+"\n");
        }
    }
}
