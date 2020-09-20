class Request{
    private int freq,minResponseTime,maxResponseTime,TotalSum;
    private String key=null;
    Request(int responseTime,String key){
        freq=1;
        minResponseTime=maxResponseTime=TotalSum=responseTime;
        this.key = key;
    }
    public int getMinResponseTime(){return minResponseTime;}
    public void setMinResponseTime(int minResponseTime){this.minResponseTime = minResponseTime;}
    
    public int getMaxResponseTime(){return maxResponseTime;}
    public void setMaxResponseTime(int maxResponseTime){this.maxResponseTime = maxResponseTime;}
    
    public int getTotalSum(){return TotalSum;}
    public void setTotalSum(int TotalSum){this.TotalSum = TotalSum;}
    
    public int getFreqency(){return freq;}
    public void increaseFrequency(){freq++;}
    
    public String getKey(){return key;}
    
}