import java.util.*;
public class PPScheduler {
    LinkedList ReadyQueue = new LinkedList();
    LinkedList<Interval> intervals = new LinkedList<Interval>();
    LinkedList starQueue = new LinkedList();

    int time_of_all_procesess;
    Process[] processes;
    int N;

    public PPScheduler(Process Processes[], int n) {
        this.N = n;
        this.processes = Processes;
        sort_by_arrival_time();
        time_of_all_procesess = 0;
        for (int i = 0; i < n; i++) {
            time_of_all_procesess += processes[i].burstTime;
        }
    }


    public int getStarvingProcess(){
        int removed = -1;
        int res = -1;
        for(int i=0;i<starQueue.size();i++){
            int index = (int)starQueue.get(i);
            processes[index].priorityTime--;
            if(processes[index].priorityTime == 0){
                res = index;
                removed = i;
            }
        }
        if(removed != -1) starQueue.remove(removed);
        return res;
    }
    public int progress() {
        int cur_time = 0;
        int s = 0;
        for (int i = 0; i < N; i++) {
            cur_time += this.processes[i].burstTime;
        }
        return (this.time_of_all_procesess - cur_time);
    }

    public void sort_by_arrival_time() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (processes[j].arrivalTime > processes[j + 1].arrivalTime) {
                    Process temp = processes[j + 1];
                    processes[j + 1] = processes[j];
                    processes[j] = temp;
                }
            }
        }
    }
    public int getReadyQueue() {
        if (ReadyQueue.size() == 0) {
            return -1;
        }

        int n = 0;
        int p = 2000;
        int I = 0;
        int removed = 0;
        for (int i = 0; i < ReadyQueue.size(); i++) {
            if (processes[I].priorityTime < p) {
                I = (int) ReadyQueue.get(i);
                p = processes[I].priorityTime;
                n = I;
                removed = i;
            }
        }
        ReadyQueue.remove(removed);
        return n;
    }
    public void execute() {
        int timer = 0;
        int next = 1;
        int cur = 0;
        int star = 0;

        while (true) {

            if (next == N && processes[cur].burstTime == 0 && ReadyQueue.size() == 0) {
                break;
            }

            if (cur == N - 1) {
                while (processes[cur].burstTime != 0) {
                    timer++;
                    intervals.addLast(new Interval(timer, cur));
                    processes[cur].burstTime--;
                }
                break;
            }

            if (cur == -1 && next < N) {
                if (timer >= processes[next].arrivalTime) {
                    cur = next;
                }else{
                    if(timer - 1 == processes[next].arrivalTime)
                        starQueue.addLast(next);
                }
                next++;
            }

            if (cur != -1 && processes[cur].burstTime > 0){
                processes[cur].burstTime--;
            }

            if (cur != -1) {
                if (processes[cur].burstTime == 0) {
                    int ready = getReadyQueue();
                    if(ready != -1) cur = ready;

                }
            }
            if (next < N) {
                if (timer >= processes[next].arrivalTime) {
                    if (cur == -1) {

                        cur = next;
                        next++;
                    }
                    if (processes[next].priorityTime < processes[cur].priorityTime) {
                        ReadyQueue.addLast(cur);
                        cur = next;
                        next++;
                    } else {
                        ReadyQueue.addLast(next);
                        next++;
                    }
                }
            }
            timer++;
            star++;
            if(star == 5){
                star = 0;
                int s = getStarvingProcess();
                if(s != -1){
                    next = cur;
                    cur = s;
                }
            }
            intervals.addLast(new Interval(timer, cur));
            if(timer > 100){
                System.out.println(ReadyQueue.size());
                System.out.println(starQueue.size());
                break;
            }

        }

        for (int i = 0; i < N; i++) {
            if (processes[i].burstTime > 0) {
                while (processes[i].burstTime > 0) {
                    timer++;
                    intervals.addLast(new Interval(timer, i));
                    processes[i].burstTime--;
                }
            }
        }

        System.out.print("\n");
        for (int i = 0; i < N; i++) {
            processes[i].waitingTime = get_wt_of_process_i(i);
            processes[i].turnAroundTime = calcTurnRound(i);
        }
        int firstcome = intervals.get(0).ind;
        if (processes[firstcome].arrivalTime == 0) {
            processes[firstcome].waitingTime--;
        }
        System.out.print("\n");

        //output handle
        System.out.print("Arraival time     ");
        System.out.print("Waiting time      ");
        System.out.print("Priority          ");
        System.out.print("Brust Time        ");
        System.out.print("Turn Round        ");
        System.out.print("\n---------------------------------------------------------------------------\n");
        for (int i = 0; i < N; i++) {
            System.out.print(processes[i].arrivalTime);
            System.out.print("                          ");
            System.out.print(processes[i].waitingTime);
            System.out.print("               ");
            System.out.print(processes[i].priorityTime);
            System.out.print("                ");
            System.out.print(processes[i].btime);
            System.out.print("                ");
            System.out.print(processes[i].turnAroundTime);
            System.out.print("\n");
        }
        System.out.print("\n");

    }
    public int get_wt_of_process_i(int I) {
        int sum = 0;
        int at = processes[I].arrivalTime;
        for (int i = 0; i < intervals.size() - 1; i++) {
            if (intervals.get(i).ind == I && intervals.get(i + 1).ind != I) {
                at = intervals.get(i + 1).t - 1;
            }
            if (intervals.get(i).ind != I && intervals.get(i + 1).ind == I) {
                sum += ((intervals.get(i + 1).t - 1) - at);
            }
        }
        return sum;
    }
    public float get_avg_waiting() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += processes[i].waitingTime;
        }
        return (float) (sum / N);
    }

    public float get_avg_turn_round(){
        float sum = 0;
        for(int i=0;i<N;i++){
            sum += processes[i].turnAroundTime;
        }
        return sum/N;
    }

    public int calcTurnRound(int i){
        int done = 0;
        int res = 0;
        for(int I=0;I<intervals.size();I++){
            if(intervals.get(I).ind == i){
                done++;
            }
            if(done == processes[i].btime) {
                res = intervals.get(I).t - processes[i].arrivalTime;
                break;
            }
        }
        return res;
    }


}


