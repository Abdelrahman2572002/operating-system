import java.util.LinkedList;

public class Compaction {
    LinkedList<Partition> pages;
    int total_wight_space ;
    Compaction(){
        pages = new LinkedList<Partition>();
    }

    public LinkedList<Partition> compact(LinkedList<Partition> p){
        total_wight_space = 0;
        for(int i=0;i<p.size();++i){
            if(p.get(i).process_index == -1){
                total_wight_space += p.get(i).sz;
            }else{
                pages.addLast(p.get(i));
            }
        }
        Partition pp = new Partition();
        pp.sz = total_wight_space;
        pp.process_index = -1;
        pp.name = "Partition"+p.size();

        if(pp.sz > 0){
            pages.addLast(pp);
        }
        return pages;
    }




}
