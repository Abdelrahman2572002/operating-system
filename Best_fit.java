import java.util.LinkedList;
import java.util.Iterator;
public class Best_fit{

    Partition parts[];
    Process proc[];
    int siz = 0;
    LinkedList<Partition> pages = new LinkedList<Partition>();

    LinkedList cannot = new LinkedList();
    Best_fit(Partition[] p,Process[] pr){
        parts = p;
        proc = pr;
        for(int i=0;i<parts.length;i++){
            pages.addLast(parts[i]);
            siz++;
        }
    }

    public int find_best(int p){
        int min = 1000000;
        int index = -1;
        for(int i=0;i<pages.size();++i){
            int cur = pages.get(i).sz;
            if(cur >= proc[p].sz && cur < min && pages.get(i).process_index == -1){
                min = cur;
                index = i;

            }
        }
        return index;
    }

    public boolean allocate(int ind){
        int best = find_best(ind);

        if(best == -1) {
            cannot.add(ind);
            return false;
        }
        Partition newapage = new Partition();
        newapage.sz = pages.get(best).sz - proc[ind].sz;
        pages.get(best).sz = proc[ind].sz;
        pages.get(best).process_index = ind;
        newapage.process_index = -1;
        if(newapage.sz != 0){
            pages.add(best+1,newapage);
            pages.get(best+1).name = "partition" + siz;
            siz++;
        }
        return true;
    }
    public void exec(){
        for(int i=0;i<proc.length;i++){
            allocate(i);
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
