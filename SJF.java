import java.util.ArrayList;

public class SJF {
    private ArrayList<Process> SJFqueue = new ArrayList<Process>();
    private ArrayList<Process> activeProcesses = new ArrayList<Process>();
    private ArrayList<Process> inactiveProcesses;
    private Process currentlyWorkingProcess;
    private Process previousWorkingProcess;
    private int clock = 0;
    private boolean switchOccurred = false;
    private int switchTime = 0;


    public SJF(ArrayList<Process> processes, int contextSwitchTime) {
        inactiveProcesses = new ArrayList<>(processes);
        switchTime = contextSwitchTime;

        while(activeProcesses.size()!=0 || clock==0){
            moveInactiveToActive();
            calculateRemainingTime();
            chooseCurrentlyWorkingProcess();

            if(switchOccurred && switchTime!=0)
                clock+=contextSwitchTime;
            else {
                SJFqueue.add(currentlyWorkingProcess);
                //System.out.println("current working process " + currentlyWorkingProcess.getProcessName() + " At clock = " + clock);
                clock+=1;
            }
        }
        printOrder();
        calculateTime(processes);
    }

    private void moveInactiveToActive(){
        for (int i = 0; i < inactiveProcesses.size(); i++) {
            Process tempProcess = inactiveProcesses.get(i);
            if(tempProcess.getArrivalTime() <= clock){
                activeProcesses.add(tempProcess);
                inactiveProcesses.remove(tempProcess);
            }
        }
    }

    private void calculateRemainingTime(){
        if (clock != 0){
            int currentRemainingTime = currentlyWorkingProcess.getRemainingTime();

            if(!switchOccurred || switchTime==0){
                currentRemainingTime -=1;
                currentlyWorkingProcess.setRemainingTime(currentRemainingTime);
                if(currentRemainingTime==0){
                    currentlyWorkingProcess.setEndTime(clock);
                    activeProcesses.remove(currentlyWorkingProcess);
                }
            }
        }
    }

    private void chooseCurrentlyWorkingProcess() {
        if (activeProcesses.size() != 0) {
            int index = 0;
            int min = activeProcesses.get(index).getRemainingTime();
            switchOccurred = false;

            if (clock != 0)
                previousWorkingProcess = currentlyWorkingProcess;

            for (int i = 0; i < activeProcesses.size(); i++) {
                if (activeProcesses.get(i).getRemainingTime() < min && activeProcesses.get(i).getRemainingTime() !=0) {
                    index = i;
                    min = activeProcesses.get(index).getRemainingTime();
                }
            }

            currentlyWorkingProcess = activeProcesses.get(index);

            if (clock != 0 && !currentlyWorkingProcess.equals(previousWorkingProcess))
                switchOccurred = true;
        }
    }

    private void calculateTime(ArrayList<Process> processes){
        int waitSum = 0;
        int waitTime;

        int turnAroundSum = 0;
        int turnAround;

        for (int i = 0; i < processes.size(); i++) {
            waitTime = processes.get(i).getEndTime() - processes.get(i).getArrivalTime() - processes.get(i).getBurstTime();
            turnAround = processes.get(i).getEndTime() - processes.get(i).getArrivalTime();

            System.out.println("waiting time for process " + processes.get(i).getProcessName() + " is " + waitTime);
            System.out.println("turnAround time for process " + processes.get(i).getProcessName() + " is " +turnAround);

            waitSum+= waitTime;
            turnAroundSum+=turnAround;
        }
        float avgWaitTime = (float)waitSum/(float)processes.size();
        float avgTurnAroundTime = (float)turnAroundSum/(float)processes.size();
        System.out.println("average waiting time for SJF: " + avgWaitTime);
        System.out.println("average turnAround time for SJF: " + avgTurnAroundTime);
    }

    private void printOrder(){
        for (int i = 0; i < SJFqueue.size()-1; i++) {
            System.out.print(SJFqueue.get(i).getProcessName() + " ");
        }
        System.out.println();
    }
}