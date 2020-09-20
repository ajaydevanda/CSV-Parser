import java.util.*;
import java.text.DecimalFormat;
import java.io.*; 

class ClearTax {
    
    static Scanner sc = null;
    static Map<String,Request> recordsMap;
    static ArrayList<Request> records;
    static DecimalFormat df = new DecimalFormat(".000");
    static Map<String,Integer> headerMap;
    static FileWriter fw = null;
    static PrintWriter pw = null;
    static BufferedWriter bw = null;
    
    public ClearTax(){
        recordsMap = new HashMap<>();
        records = new ArrayList<>();
        headerMap = new HashMap<>();
        try{
            fw = new FileWriter("output.csv",false);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            //pw.println("ajay");
        }
        catch(Exception ex){}
        
    }
    
    private static boolean isStringInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
    
    private static String getUniqueKey(String url,String method){
        String parseUrl[] = url.split("/");
        for(int i=0;i<parseUrl.length;i++) {
            if(isStringInt(parseUrl[i])){
                parseUrl[i] = "{id}";
            }
        }
        String key = String.join("/",  parseUrl);
        
        return key+"_"+method;
    }
    
    private static void processHeaders(String[] header){
        for(int i=0;i<header.length;i++){
            headerMap.put(header[i],i);
        }
    }
    
    private static void parseRecord(String[] record){
        
        String method = record[headerMap.get("method")],url = record[headerMap.get("url")],responseTime = record[headerMap.get("response_time")],timeStamp = record[headerMap.get("timestamp")];
            
        String uniqueKey = getUniqueKey(url,method);
        
        
        Request currRequest = recordsMap.getOrDefault(uniqueKey,null);
        if(currRequest==null){
            currRequest = new Request(Integer.parseInt(responseTime),uniqueKey);   
        }
        else{
            int currResponseTime = Integer.parseInt(responseTime);
            
            currRequest.setMinResponseTime(Math.min(currResponseTime,currRequest.getMinResponseTime()));
            currRequest.setMaxResponseTime(Math.max(currResponseTime,currRequest.getMaxResponseTime()));
            
            currRequest.setTotalSum(currResponseTime+currRequest.getTotalSum());
            currRequest.increaseFrequency();
        }
        
        recordsMap.put(uniqueKey,currRequest); 
    }
        
        
    // Read the input file 
    public void readFile(){
        
        try{
            sc = new Scanner(new File(System.getProperty("user.dir") +"/input.csv"));
            if(sc.hasNext()) {
                processHeaders(sc.next().split(","));
            }
            while(sc.hasNext()){
                String record[] = sc.next().split(",");
                parseRecord(record);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
    }
    
    
    
    // sort the records based on their frequency
    public void sortRecords(){
        for(Map.Entry<String,Request> entry : recordsMap.entrySet()){
            records.add(entry.getValue());
        }
        Collections.sort(records,new SortUsingFrequency());
    }
    
    

    
    //Write int File
    private void writeTopHighestThroughput(){
        StringBuilder sb = new StringBuilder("");
        sb.append("Method,URL,Frequency\n");
            
        for(int i=0;i<5 && i<records.size();i++){
            Request request = records.get(i);
            String key[] = request.getKey().split("_");
            sb.append(key[1] + "," + key[0] + "," + String.valueOf(request.getFreqency()) + "\n");
        }
        
        pw.println(sb.toString());
        pw.flush();
    }
    
    private void writeEachEndpoint(){
        StringBuilder sb = new StringBuilder("");
        sb.append("Method,URL,Min Time,Max Time,Average Time\n");
            
        for(Request request : records){
            String key[] = request.getKey().split("_");
            sb.append(key[1]+"," + key[0]+","+Integer.valueOf(request.getMinResponseTime())+"," + Integer.valueOf(request.getMaxResponseTime())+","+ String.valueOf(df.format(request.getTotalSum()/(double)request.getFreqency()))+ "\n");
        }
        
        pw.println(sb.toString());
        pw.flush();
        pw.close();
    }
    
    public void writeIntoFile(){
        
        writeTopHighestThroughput();
        
        writeEachEndpoint();
        
    }
    
    
    
    //Write on console
    private  void printEachEndpoint(){
        if(records.size()>0){
            System.out.println("\n");
            System.out.println("Method  :  URL  : Min Time  :  Max Time  : Average Time");
        }
        for(Request request : records){
            String key[] = request.getKey().split("_");
            System.out.println(key[1]+"  :  " + key[0]+"  :  "+request.getMinResponseTime()+"  :   " + request.getMaxResponseTime()+"  :  "+ df.format(request.getTotalSum()/(double)request.getFreqency()));
        }
    }
    
    private void printTopHighestThroughput(){
        if(records.size()>0){
            System.out.println("Method  :  URL  : Frequency");
        }
        for(int i=0;i<5 && i<records.size();i++){
            String key[] = records.get(i).getKey().split("_");
            System.out.println(key[1]+"  :  " + key[0]+"  :  "+records.get(i).getFreqency());
        }
    }
    
    public void printOnConcole(){
        
        printTopHighestThroughput();
        
        
        printEachEndpoint();
        
    }
    
}