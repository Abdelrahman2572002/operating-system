//Ahmed Hany Ibrahim Ahmed : 20200054
//Ezz aldin Ahmed : 20200325
// Abdallah Mohsen Abdelhafez : 20200304
// Abdrahman Ramadan Abo elela: 20200284
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numOfProcesses;
        int arrivalTime;
        int Qnumber;
        String nameOfProcess;
        int contextSwitchTime;
        int priorityTime;
        int quantam_time;
        ArrayList<Process> process = new ArrayList<Process>();

        Scanner input = new Scanner(System.in);

        System.out.println("please choose the algorithm you want to use" );
        System.out.println("1-SJF ");
        System.out.println("2-Round Robin");
        System.out.println("3-Priority");
        System.out.println("4-AG");
        int choice = input.nextInt();

        if(choice == 1)
        {
            System.out.println("Please enter number of processes ");
            numOfProcesses = input.nextInt();

            for (int i = 0; i < numOfProcesses; i++) {
                System.out.println("Please enter name of process " + (i+1));
                nameOfProcess = input.next();

                System.out.println("Please enter arrival time of process " + nameOfProcess);
                arrivalTime = input.nextInt();

                System.out.println("Please enter burst time of process " + nameOfProcess);
                int burstTime = input.nextInt();

                System.out.println("Please enter Queue number that the process will enter ");
                Qnumber =  input.nextInt();


                Process temp = new Process(Qnumber,arrivalTime,nameOfProcess,burstTime);
                process.add(temp);
            }

            System.out.println("Please enter context switching time ");
            contextSwitchTime = input.nextInt();

            SJF sjf = new SJF(process, contextSwitchTime);

        }
        if(choice == 2)
        {

            System.out.println("Please enter number of processes ");
            numOfProcesses = input.nextInt();
            Process proc[] = new Process[numOfProcesses];
            for (int i = 0; i < numOfProcesses; i++) {
                System.out.println("Please enter name of process " + (i+1));
                nameOfProcess = input.next();

                System.out.println("Please enter arrival time of process " + nameOfProcess);
                arrivalTime = input.nextInt();

                System.out.println("Please enter burst time of process " + nameOfProcess);
                int burstTime = input.nextInt();

                proc[i] = new Process(nameOfProcess,arrivalTime,burstTime);



            }
            System.out.println("Please enter quantam time ");
            quantam_time = input.nextInt();
            System.out.println("Please enter context switching time ");
            contextSwitchTime = input.nextInt();

            RR rr= new RR(proc, numOfProcesses,quantam_time,contextSwitchTime);

        }
        if(choice == 3)
        {

            System.out.println("Please enter number of processes ");
            numOfProcesses = input.nextInt();
            Process proc[] = new Process[numOfProcesses];
            for (int i = 0; i < numOfProcesses; i++) {
                System.out.println("Please enter name of process " + (i+1));
                nameOfProcess = input.next();

                System.out.println("Please enter arrival time of process " + nameOfProcess);
                arrivalTime = input.nextInt();

                System.out.println("Please enter burst time of process " + nameOfProcess);
                int burstTime = input.nextInt();

                System.out.println("Please enter priority time ");
                priorityTime = input.nextInt();

                proc[i] = new Process(nameOfProcess,arrivalTime,burstTime,priorityTime);

            }

            PPScheduler pp= new PPScheduler(proc, numOfProcesses);
            pp.execute();
            System.out.println("AVG Waiting Time = "+pp.get_avg_waiting());
            System.out.println("AVG Turn around Time = "+pp.get_avg_turn_round());

        }
        if(choice == 4)
        {

            System.out.println("Please enter number of processes ");
            numOfProcesses = input.nextInt();
            Process proc[] = new Process[numOfProcesses];
            for (int i = 0; i < numOfProcesses; i++) {
                System.out.println("Please enter name of process " + (i+1));
                nameOfProcess = input.next();

                System.out.println("Please enter arrival time of process " + nameOfProcess);
                arrivalTime = input.nextInt();

                System.out.println("Please enter burst time of process " + nameOfProcess);
                int burstTime = input.nextInt();

                System.out.println("Please enter priority time ");
                priorityTime = input.nextInt();
                System.out.println("Please enter quantam time ");
                quantam_time = input.nextInt();

                proc[i] = new Process(arrivalTime,nameOfProcess,burstTime,priorityTime,quantam_time);



            }

            AG ag= new AG(proc, numOfProcesses);


        }

    }

}