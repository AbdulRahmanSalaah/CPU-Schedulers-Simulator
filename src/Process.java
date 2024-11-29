public class Process {
    int process_num;
    String process_name;
    int burstTime;
    int burstTimecalc;
    int arrivalTime;
    int priority;
    double quantum;
    int ExitTime;
    public int wait;
    public int turn;
    public int compeletion;

    private int responseTime;
    private int contextCost;
    public int remaining;
    public int updateATime;




    public Process(String process_name, int arrivalTime, int burstTime, int priority , double quantum) {
        this.quantum=quantum;
        this.process_name = process_name;
        this.burstTime = burstTime;
        burstTimecalc = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }
    public Process(String process_name, int burstTime, int arrivalTime) {
        this.process_name = process_name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.updateATime = arrivalTime;
        this.remaining = burstTime;
    }

    public Process(String process_name, int arrivalTime, int burstTime, int pr ) {
        this.process_name = process_name;
        this.burstTime = burstTime;
        burstTimecalc = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority=pr;

    }
    public Process(int num, String process_name, int burstTime, int arrivalTime) {
        this.process_num = num;
        this.process_name = process_name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }


    public void setProcess_num(int process_num) {
        this.process_num = process_num;
    }

    public int getProcess_num() {
        return process_num;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getContextCost() {
        return contextCost;
    }

    public void setContextCost(int contextCost) {
        this.contextCost = contextCost;
    }
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setQuantum(double quantum) {
        this.quantum = quantum;
    }
    public double getQuantum() {
        return quantum;
    }


    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public int getExitTime() {
        return ExitTime;
    }

    public void setExitTime(int exitTime) {
        ExitTime = exitTime;
    }

}
