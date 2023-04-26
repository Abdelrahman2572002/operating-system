
import java.util.Scanner;


/*
20200325 Ezz elden Ahmed
20200304 Abdallah Mohsen
20200284 Abdelrahman ramadan
20200054 Ahmed Hany
* */



public class Main {
    public static void main(String[] args) {

        int numOfPart;
        Scanner inp = new Scanner(System.in);  // Create a Scanner object


        System.out.println("Enter number of partitions");
        numOfPart = inp.nextInt();

        Partition[] Partitions = new Partition[numOfPart];

        for(int i=0;i<numOfPart;++i){

            System.out.println("Enter name of partition");
            String name = inp.next();

            System.out.println("Enter size of partition");
            int sz = inp.nextInt();


            Partition pi = new Partition();
            pi.name = name;
            pi.sz = sz;
            Partitions[i] = pi;
        }



        int numOfProc;
        System.out.println("Enter number of Processes");
        numOfProc = inp.nextInt();

        Process[] Processes = new Process[numOfProc];

        for(int i=0;i<numOfProc;++i){
            System.out.println("Enter name of Process");
            String name = inp.next();

            System.out.println("Enter size of Process");
            int sz = inp.nextInt();


            Process pi = new Process(sz,name);
            Processes[i]=pi;
        }





        int choice;
        System.out.println("1. First fit");
        System.out.println("2. Worst fit");
        System.out.println("3. Best fit");
        choice = inp.nextInt();
        if(choice == 1){

            First_fit f = new First_fit(Partitions,Processes);
            f.exec();
            f.showall();

            System.out.println("Do you want to compact? 1.yes 2.no");
            int com = inp.nextInt();
            if(com == 1){
                f.compact();
                if(f.cannot.size()>0){
                    f.exec();
                }
                f.showall();
            }


        }else if(choice == 2){

            Best_fit f = new Best_fit(Partitions,Processes);
            f.exec();
            f.showall();

            System.out.println("Do you want to compact? 1.yes 2.no");
            int com = inp.nextInt();
            if(com == 1){
                f.compact();
                if(f.cannot.size()>0){
                    f.exec();
                }
                f.showall();
            }

        }else {

            Worst_fit f = new Worst_fit(Partitions,Processes);
            f.exec();
            f.showall();

            System.out.println("Do you want to compact? 1.yes 2.no");
            int com = inp.nextInt();
            if(com == 1){
                f.compact();
                if(f.cannot.size()>0){
                    f.exec();
                }
                f.showall();
            }

        }
    }
}

