public class Main{
    static ClearTax clearTaxObj;
    Main(){
        clearTaxObj = new ClearTax();
    }
   
    private void processRecords(){
        clearTaxObj.readFile();
        
        clearTaxObj.sortRecords();   
        
        clearTaxObj.writeIntoFile();
        
        //clearTaxObj.printOnConcole();
    }
    public static void main(String[] arg) throws Exception {
        
        
        Main Obj = new Main();
        
        Obj.processRecords(); 
        
    }
}