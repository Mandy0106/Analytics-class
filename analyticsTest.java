public class analyticsTest {
    
    public static void main(String[ ] args)
    {
        //Read dataset2.csv, save in a 2 dimensional array
        analytics dataset = new analytics();
        
        String dataset2 = "/Users/yihengshen/Desktop/dataset2.csv";
        
        dataset.analysis(dataset2);
    }    
        
}