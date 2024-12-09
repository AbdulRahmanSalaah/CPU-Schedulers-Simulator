
import java.util.List;

public class Process {

    int process_num; // Process number
    String process_name; // Process name
    int burstTime; // Original burst time
    int burstTimecalc; // Unused, but kept as per original class
    int arrivalTime; // Arrival time of the process
    int priority; // Priority of the process
    int quantum =0; // Quantum for Round Robin or FCAI
    int ExitTime; // Completion or exit time
    public int wait; // Waiting time of the process
    public int turn; // Turnaround time of the process
    public int compeletion; // Completion time, might duplicate ExitTime

    private int responseTime; // Response time for the process
    private int contextCost; // Context switching cost
    public int remaining; // Remaining burst time
    public int updateATime; // Updated arrival time (unused, but kept for your compatibility)

    // Added fields for FCAI Scheduling
    public int fcaiFactor; // FCAI Factor for scheduling
    public int waitingTime; // Dynamic waiting time for the process
    public int turnaroundTime; // Dynamic turnaround time for the process
    private List<int[]> executionLog; // Holds start and end times for each execution

    // Constructor for FCAI Scheduling
    public Process(String process_name, int arrivalTime, int burstTime, int priority, int quantum) {
        this.process_name = process_name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        // this.burstTimecalc = burstTime; // To preserve original burst time
        this.remaining = burstTime; // Initialize remaining burst time
        this.priority = priority;
        this.quantum = quantum;
        // this.executionLog = new ArrayList<>(); // Initialize the execution log

    }

    // Constructor for processes with burst time and arrival time only
    public Process(String process_name, int burstTime, int arrivalTime) {
        this.process_name = process_name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.updateATime = arrivalTime;
        this.remaining = burstTime;
    }

    // Constructor for processes with arrival time, burst time, and priority
    public Process(String process_name, int arrivalTime, int burstTime, int priority) {
        this.process_name = process_name;
        this.burstTime = burstTime;
        this.burstTimecalc = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    // Constructor with process number
    public Process(int num, String process_name, int burstTime, int arrivalTime) {
        this.process_num = num;
        this.process_name = process_name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    // Getter and Setter for Process Number
    public void setProcess_num(int process_num) {
        this.process_num = process_num;
    }

    public int getProcess_num() {
        return process_num;
    }

    // Getter and Setter for Response Time
    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    // Getter and Setter for Context Switching Cost
    public int getContextCost() {
        return contextCost;
    }

    public void setContextCost(int contextCost) {
        this.contextCost = contextCost;
    }

    // Getter and Setter for Burst Time
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    // Getter and Setter for Process Name
    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public String getProcess_name() {
        return process_name;
    }

    // Getter and Setter for Quantum
    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getQuantum() {
        return quantum;
    }

    // Getter and Setter for Arrival Time
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    // Getter and Setter for Priority
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    // Getter and Setter for Exit Time
    public int getExitTime() {
        return ExitTime;
    }

    public void setExitTime(int exitTime) {
        ExitTime = exitTime;
    }

    // Getter and Setter for FCAI Factor
    public double getFcaiFactor() {
        return fcaiFactor;
    }

    public void setFcaiFactor(int fcaiFactor) {
        this.fcaiFactor = fcaiFactor;
    }

    // Getter and Setter for Waiting Time
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    // Getter and Setter for Turnaround Time
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    // Calculate FCAI Factor
    public void calculateFcaiFactor(double V1, double V2) {
        this.fcaiFactor = (10 - priority) + (int)Math.ceil(arrivalTime / V1) + (int)Math.ceil(remaining / V2);
    }

    // Increment Quantum Dynamically
    public void incrementQuantum(int increment) {
        this.quantum += increment;
    }

    // Adjust Quantum for Preemption
    public void adjustQuantum(double unusedQuantum) {
        this.quantum += unusedQuantum;
    }
        // Quantum Update
    public void updateQuantum(int unusedQuantum) {
        if (unusedQuantum > 0) {
            // If some quantum was unused, increase dynamically
            quantum += unusedQuantum ; 
        } else {
            // Fixed increment if the quantum is completely consumed
            quantum += 2; // Increment quantum by 2 units
        }
    }

    // Update Waiting Time Dynamically
    public void updateWaitingTime(int currentTime) {
        this.waitingTime = currentTime - arrivalTime - (burstTime - remaining);
    }

    // Update Turnaround Time Dynamically
    public void updateTurnaroundTime(int currentTime) {
        this.turnaroundTime = currentTime - arrivalTime;
    }

    // Getter and Setter for Remaining Burst Time
    public int getRemainingBurstTime() {
        return remaining;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remaining = remainingBurstTime;
    }

    public void executeForOneSecond() {
        if (remaining > 0) {
            remaining--;
        }
    }

    // Add an execution interval to the log
    public void addExecution(int start, int end) {
        executionLog.add(new int[]{start, end});
    }

    // Get the execution log as a formatted string
    public String getExecutionLog() {
        StringBuilder log = new StringBuilder();
        for (int[] interval : executionLog) {
            log.append("{start=").append(interval[0]).append(", end=").append(interval[1]).append("} ");
        }
        return log.toString().trim();
    }
}
