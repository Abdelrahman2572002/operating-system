import java.util.*;
public class RR {
    Process proc[];
    int noofProcesses;
    int quantam_time;
    int context;

    public RR(Process proc[],int noofProcesses,int quantam_time,int context){
        this.noofProcesses = noofProcesses;
        this.proc = proc;
        this.quantam_time=quantam_time;
        this.context = context;
        int completionTime[] = new int[noofProcesses];
        int  average_WT = 0, average_TT = 0;
        System.out.println("-----------------------------");
        System.out.println("Process SR. \t Arrival Time \t Burst Time \t Completion Time \t Turnaround Time \t Wating Time");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        solve(proc,noofProcesses,completionTime,quantam_time,average_WT,average_TT,context);
    }
    public static void solve(Process proc[],int noofProcesses,int completionTime[],int tq,int average_WT,int average_TT,int context ){
        int remainingbursTime[]=new int[noofProcesses];
        int tempArrivalTime[]=new int[noofProcesses];
        ArrayList<Integer> readyQueue= new ArrayList<Integer>();
        // ASSUME PROCESS ARE SORTED ACCORDING TO ARRIVAL TIME (IF NOT ADD A SORT FUNCTION)
        readyQueue.add(0);

        for(int i=0;i<noofProcesses;i++){
            remainingbursTime[i]=proc[i].burstTime;
            tempArrivalTime[i]=proc[i].arrivalTime;
        }

        int counter=0;

        while(true){
            boolean done=true;

            // SELECT PROCESS
            for (int j = 0; j < readyQueue.size(); j++){

                int i=readyQueue.get(j);

                if(remainingbursTime[i]>0){

                    done=false;

                    if(remainingbursTime[i]>tq){
                        counter+=tq;

                        // INSERT ARRIVED PROCESS IN READY_QUEUE
                        for(int k=1;k<noofProcesses;k++){
                            if(tempArrivalTime[k]<=counter){
                                readyQueue.add(k);
                                // ONCE THE PROCESS IS ARRIVED MARK IT WITH MAX INTEGER VALUE SO IT DOES GO IN THE READY QUEUE AGAIN
                                tempArrivalTime[k]=Integer.MAX_VALUE;
                            }
                        }
                        counter +=context;
                        remainingbursTime[i]=remainingbursTime[i]-tq;
                        // ADD INCOMPLETE PROCESS IN READY_QUEUE
                        readyQueue.add(i);
                    }
                    else{
                        counter=counter+remainingbursTime[i];
                        completionTime[i]=counter;
                        counter+=context;
                        remainingbursTime[i]=0;

                        int turnAroundTime = completionTime[i]-proc[i].arrivalTime;
                        int watingTime = turnAroundTime-proc[i].burstTime;
                        average_TT=average_TT+turnAroundTime;
                        average_WT=average_WT+watingTime;
                        System.out.println("Process "+proc[i].processName+"\t\t\t"+proc[i].arrivalTime+"\t\t\t\t"+proc[i].burstTime+"\t\t\t\t"+completionTime[i]+"\t\t\t\t\t"+turnAroundTime+"\t\t\t\t\t"+watingTime);
                    }
                }
            }

            if(done==true){
                System.out.println("--------------------------------------------------------------------------------------------------------------");
                System.out.println("AVERAGE WATING TIME: "+(average_WT/(float)noofProcesses)+" units");
                System.out.println("AVERAGE TURNAROUND TIME: "+(average_TT/(float)noofProcesses)+" units");
                break;
            }
        }

//         DISPLAYING THE READY QUEUE
        System.out.print("\nGrantChart: ");
        System.out.print("[");
        for(int i=0;i<readyQueue.size();i++){
            System.out.print("Process"+(readyQueue.get(i)+1)+", ");
        }
        System.out.println("]");

    }

}
