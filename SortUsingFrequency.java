import java.util.Comparator;
class SortUsingFrequency implements Comparator<Request>{
    public int compare(Request r1, Request r2){
        if(r2.getFreqency()!=r1.getFreqency()) return r2.getFreqency()-r1.getFreqency();
        else return (int)(r2.getTotalSum()/(double)r2.getFreqency() - r1.getTotalSum()/(double)r1.getFreqency()) ;
    }
}