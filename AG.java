import java.util.*;
public class AG {
    Process proc[];
    int noofProcesses;

    public AG(Process proc[],int noofProcesses){
        this.proc = proc;
        this.noofProcesses = noofProcesses;
        int completionTime[] = new int[noofProcesses];
        int  average_WT = 0, average_TT = 0;
        System.out.println("-----------------------------");
        System.out.println("Process SR. \t Arrival Time \t Burst Time \t Priority \tCompletion Time \t Turnaround Time \t Wating Time");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        solve(proc,noofProcesses,completionTime,average_WT,average_TT);
    }





    public  static boolean checkBurst(Process proc[],int remainingbursTime[],int currentProcessing,int noofProcesses,Queue<Integer> processing,Queue<Integer> readyQueue,int timer){
        int current = currentProcessing;
        boolean valid = true;
        for(int i=0;i<noofProcesses;i++){
            if(remainingbursTime[i]<remainingbursTime[current] && proc[i].arrivalTime<= timer){
                current = i;
                valid = false;
            }
        }
        if(current != currentProcessing){
            processing.add(current);
            if(readyQueue.contains(current)){
                readyQueue.remove(current);
            }
        }
        return valid;
    }
    public  static boolean checkPriority(Process proc[],int currentProcessing,int noofProcesses,Queue<Integer> processing,Queue<Integer> readyQueue,int timer){
        int current = currentProcessing;
        boolean valid = true;
        for(int i=0;i<noofProcesses;i++){
            if(proc[i].priorityTime<proc[current].priorityTime && proc[i].arrivalTime<= timer ){
                current = i;
                valid = false;
            }
        }
        if(current != currentProcessing){
            processing.add(current);
            if(readyQueue.contains(current)){
                readyQueue.remove(current);
            }
        }
        return valid;
    }

    public static void solve(Process proc[],int noofProcesses,int completionTime[],int average_WT,int average_TT ){
        int remainingbursTime[]=new int[noofProcesses];
        Queue<int[]> Quantam = new LinkedList<>();
        Queue<Integer> readyQueue = new LinkedList<Integer>();
        Queue<Integer> processing = new LinkedList<Integer>();

        processing.add(0);

        for(int i=0;i<noofProcesses;i++){
            remainingbursTime[i]=proc[i].burstTime;
        }
        int y;
        int timer=0;
        int operations = noofProcesses;

        while(operations>=0){
            int tempQuantum[]=new int[noofProcesses];
            for(int i=0;i<noofProcesses;i++){
                tempQuantum[i] = proc[i].quantam;
            }
            Quantam.add(tempQuantum);
            boolean done=true;

            // SELECT PROCESS
            for (int j = 0; j < processing.size(); j++){
                int currentProcessing =processing.poll();
                if(remainingbursTime[currentProcessing]>0){
                    done=false;
                    y = proc[currentProcessing].quantam;
                    if(remainingbursTime[currentProcessing]>Math.ceil(y*0.25)){
                        int x = (int) Math.ceil(y*0.25);
                        y-=x;
                        timer += x;

                        remainingbursTime[currentProcessing] -=x;
                        if(checkPriority(proc,currentProcessing,noofProcesses,processing,readyQueue,timer)){
                            x = (int) Math.ceil(y*0.25);
                            y-=x;
                            timer += x;
                            remainingbursTime[currentProcessing] -=x;
                            if(checkBurst(proc,remainingbursTime,currentProcessing,noofProcesses,processing,readyQueue,timer)){
                                if (remainingbursTime[currentProcessing]>y){
                                    timer +=y;
                                    remainingbursTime[currentProcessing] -= y;
                                    proc[currentProcessing].quantam += 2;
                                    tempQuantum[currentProcessing]=  proc[currentProcessing].quantam;
                                    Quantam.add(tempQuantum);
                                    readyQueue.add(currentProcessing);
                                }
                                else {
                                    timer += remainingbursTime[currentProcessing];
                                    if (readyQueue.contains(currentProcessing)) {
                                        readyQueue.remove(currentProcessing);
                                    }
                                    proc[currentProcessing].quantam = 0;
                                    operations -= 1;
                                    completionTime[currentProcessing] = timer;
                                    if(readyQueue.size()>0) {
                                        int f = readyQueue.poll();
                                        processing.add(f);
                                    }
                                    int turnAroundTime = completionTime[currentProcessing] - proc[currentProcessing].arrivalTime;
                                    int watingTime = turnAroundTime - proc[currentProcessing].burstTime;
                                    average_TT = average_TT + turnAroundTime;
                                    average_WT = average_WT + watingTime;

                                    System.out.println("Process " + proc[currentProcessing].processName + "\t\t\t" + proc[currentProcessing].arrivalTime + "\t\t\t\t" + proc[currentProcessing].burstTime + "\t\t\t\t" + proc[currentProcessing].priorityTime +"\t\t\t\t" + completionTime[currentProcessing] + "\t\t\t\t\t" + turnAroundTime + "\t\t\t\t\t" + watingTime);
                                    proc[currentProcessing].priorityTime = Integer.MAX_VALUE;
                                    remainingbursTime[currentProcessing] = Integer.MAX_VALUE;

                                }
                            }
                            else{
                                proc[currentProcessing].quantam += y;
                                readyQueue.add(currentProcessing);
                            }
                        }
                        else{
                            proc[currentProcessing].quantam +=  Math.ceil(y*1.0/2);
                            readyQueue.add(currentProcessing);
                        }

                    }
                    else{
                        timer += remainingbursTime[currentProcessing];
                        if(readyQueue.contains(currentProcessing)){
                            readyQueue.remove(currentProcessing);
                        }
                        proc[currentProcessing].quantam = 0;
                        completionTime[currentProcessing] = timer;
                        if(readyQueue.size()>0) {
                            int f = readyQueue.poll();
                            processing.add(f);
                        }
                        operations -=1;
                        int turnAroundTime = completionTime[currentProcessing]-proc[currentProcessing].arrivalTime;
                        int watingTime = turnAroundTime-proc[currentProcessing].burstTime;
                        average_TT=average_TT+turnAroundTime;
                        average_WT=average_WT+watingTime;
                        System.out.println("Process " + proc[currentProcessing].processName + "\t\t\t" + proc[currentProcessing].arrivalTime + "\t\t\t\t" + proc[currentProcessing].burstTime + "\t\t\t\t" + proc[currentProcessing].priorityTime +"\t\t\t\t" + completionTime[currentProcessing] + "\t\t\t\t\t" + turnAroundTime + "\t\t\t\t\t" + watingTime);
                        proc[currentProcessing].priorityTime = Integer.MAX_VALUE;
                        remainingbursTime[currentProcessing] = Integer.MAX_VALUE;
                    }

                }
            }

            if(done==true){
                System.out.println("--------------------------------------------------------------------------------------------------------------");
                System.out.println("AVERAGE WATING TIME: "+(average_WT/(float)noofProcesses)+" units");
                System.out.println("AVERAGE TURNAROUND TIME: "+(average_TT/(float)noofProcesses)+" units");
                System.out.println("\nHistory Update of Quantam Time: ");
                for (int i[] : Quantam) {
                    System.out.println(Arrays.toString(i));
                }
                break;
            }
        }

    }


}
