public class Process implements Comparable<Process>
{
    String processName;
    int arrivalTime;
    private int queueNumber;
    int burstTime;
    private int endTime;
    private int remainingTime;
    int priorityTime;

    int quantam;
     int turnAroundTime =0;
     int waitingTime=0;
    private boolean isActive;
    private int startExecutionTime;
    int btime;



    public Process() {
        setActive(false);
        setRemainingTime(burstTime);
    }

    public Process(int queueNumber, int arrivalTime,String processName,  int burstTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.queueNumber = queueNumber;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;

    }

    public Process(String processName, int arrivalTime, int burstTime){
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
    public Process( int arrivalTime,String processName, int burstTime,int priority,int quantam){
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityTime = priority;
        this.quantam = quantam;
    }

    public Process(String processName, int Arrival_time, int brust_time, int priority){
        this.processName = processName;
        this.arrivalTime = Arrival_time;
        this.burstTime = brust_time;
        this.priorityTime = priority;
        btime = brust_time;

    }

    public String printRRData()
    {
        return "Process [processName=" + processName +  ", turnAroundTime=" + turnAroundTime + ", waitingTime=" + waitingTime +  "]";
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    @Override
    public String toString() {
        return "Process [processName=" + processName + ", arrivalTime=" + arrivalTime + ", queueNumber=" + queueNumber
                + ", burstTime=" + burstTime + ", endTime=" + endTime + ", remainingTime=" + remainingTime
                + ", turnAroundTime=" + turnAroundTime + ", waitingTime=" + waitingTime + ", isActive=" + isActive
                + ", startExecutionTime=" + startExecutionTime + "]";
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getStartExecutionTime() {
        return startExecutionTime;
    }

    public void setStartExecutionTime(int startExecutionTime) {
        this.startExecutionTime = startExecutionTime;
    }
    @Override
    public int compareTo(Process process) {
        return Integer.compare(this.arrivalTime, process.arrivalTime);
    }

    public int getPriorityTime() {
        return priorityTime;
    }

    public void setPriorityTime(int priorityTime) {
        this.priorityTime = priorityTime;
    }

}