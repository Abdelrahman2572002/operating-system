import java.util.LinkedList;
import java.util.Iterator;
public class First_fit{
    Partition parts[];
    Process proc[];
    int siz = 0;
    LinkedList<Partition> pages = new LinkedList<Partition>();
    LinkedList cannot = new LinkedList();
    First_fit(Partition[] p,Process[] pr){
        parts = p;
        proc = pr;
        for(int i=0;i<parts.length;i++){
            pages.addLast(parts[i]);
            siz++;
        }
    }

    public boolean allocate(int ind){
       for(int i=0;i<pages.size();++i){
           Partition pp = pages.get(i);
            if(pp.sz >= proc[ind].sz && pp.process_index == -1){
                pages.get(i).process_index = ind;

                Partition newapage = new Partition();
                newapage.process_index = -1;
                newapage.sz = pages.get(i).sz - proc[ind].sz;
                pages.get(i).sz = proc[ind].sz;

                if(newapage.sz != 0){
                    pages.add(i+1,newapage);

                    pages.get(i+1).name = "partition" + siz;

                    siz++;
                }
                return true;

            }

       }
        return false;
    }
    public void exec(){
        for(int i=0;i<proc.length;i++){


            if(!allocate(i)){
                cannot.addLast(i);
            }
        }
    }

    public void showall(){
        System.out.println("---------------");

        for(int i=0;i<pages.size();i++){
            System.out.print("");
            System.out.print(pages.get(i).name);
            System.out.print("(");
            System.out.print(pages.get(i).sz);
            System.out.print("KB)");
            System.out.print(" => ");
            if(pages.get(i).process_index == -1){
                System.out.print("External fragment");
            }else{
                System.out.print(proc[pages.get(i).process_index].name);
            }
            System.out.print("\n");
        }

        for(int i=0;i<cannot.size();i++){
            System.out.print(proc[(int)cannot.get(i)].name);
            System.out.print(" can not be allocated\n");
        }

    }

    public void compact(){
        Compaction c = new Compaction();
        pages = c.compact(pages);

        for(int i=0;i<cannot.size();i++){
            boolean can = allocate((int)cannot.get(i));
            if(can) {
                cannot.remove(i);
            }
        }
    }


}
