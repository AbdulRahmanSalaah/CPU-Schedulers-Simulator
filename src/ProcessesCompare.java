import java.util.Comparator;

public class ProcessesCompare implements Comparator<Process> {
    //Check Min Arrival Time
    @Override
    public int compare(Process o1, Process o2) {  //override for Process usage
        if (o2.getArrivalTime()<=o1.getArrivalTime()){
            return 1;
        }else return -1;
    }
}
