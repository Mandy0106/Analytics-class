import java.io.*;
import java.util.*;

public class analytics
{
    public void analysis(String dataset) {      

        //Read the CSV file above, save in a 2 dimensional array
        String[][] dim2Array = null;

        try {
                FileReader fr = new FileReader(dataset);                //open the file for reading
                LineNumberReader lnr    = new LineNumberReader(fr);     //use to get the num of lines 
                lnr.skip(Long.MAX_VALUE);                               //skip to end of file
                int nRows = lnr.getLineNumber();                        //obtain the num of lines
                fr.close();                                             //close the file
                
                dim2Array = new String[nRows][];                        //create a 2 dim array with as many rows as in file

                FileReader fr2 = new FileReader(dataset);               //re-open the file for reading again
                @SuppressWarnings("resource")
                BufferedReader br2 = new BufferedReader(fr2);           //buffer it

                int row=0;
                String line;

                while ((line = br2.readLine()) != null)                 //while there are lines in the file
                {
                    String[] dim1Array = line.split(",");               //split it on, (or use  "\\s*,\\s*" space , )
                    dim2Array[row] = dim1Array;                         //append 1 dim array into the 2 dim array
                    row++;                                              //next row
                }
         }
         catch (Exception e) {
                 System.out.println(e);
         }
        
        //convert dim2Array to 2d double array  
        
        //use ArrayList to decide the length and height
        ArrayList<Double>            cols = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> rows = new ArrayList<ArrayList<Double>>();     
        
        for (int i = 0; i < dim2Array.length; i++) {
            cols = new ArrayList<Double>();
            for (int j = 0; j < dim2Array[i].length; j++) 
                cols.add(Double.valueOf(dim2Array[i][j]));
            rows.add(cols);
        }
        
        int r = rows.size();                    //assign row number of dArray to r
        int c = cols.size();                    //assign col number of dArray to c
        
        // get the double array dArray               
        double[][] dArray = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++){                          
                dArray[i][j] = Double.valueOf(dim2Array[i][j]);
            }
        }

                    
        //1. Perform analytics for each row, and print
        System.out.println("analytics for each row:");
        int line = 0;

        for (int i = 0; i < r; i++) {
            
            Arrays.sort(dArray[i]);                                             //sort for search for median and mode
            
            line = line + 1;                                                    //get the line number
            int count = 0;                              
            int count2 = 1;
            float sum = 0;
            float average = 0;
            double total = 0;
            float median = 0;
            int maxCount = 1;
            double mode = 0;
            double value = dArray[i][0];
            
            for (int j = 0; j < c; j++){                              
                count = count + 1;                                              //count in each row
                sum = (float) (sum + dArray[i][j]);                             //get the sum of each row  
            }
            
            // calculate mean
            average = (float) (sum / count);            

            // calculate total for std
            for (int j = 0; j < c; j++){
                total = total + (dArray[i][j] - average) * (dArray[i][j] - average);
            }
            
            //find the mode
            for (int j = 1; j < c; j++){
                if(dArray[i][j] == value) {
                    count2++;
                }
                if(count2 > maxCount) {
                    maxCount = count2;
                    mode = value;
                }
                else {
                    value = dArray[i][j];
                    count2 = 1;
                }
            }
            
            double std = Math.sqrt((total / count));
            if (count % 2 == 1){
                median = (float) dArray[i][(count - 1) / 2];
            }
            if (count % 2 == 0){
                median = (float) (dArray[i][count / 2 - 1] + dArray[i][count / 2]) / 2;
            }
            if(maxCount >=2) {
                System.out.println("row  " + line + " count: " + count + " average: " + average + " median: " + median
                        + " mode: " + mode + " min: " + dArray[i][0] + " max: " + dArray[i][c - 1]
                        + " range: " + (dArray[i][cols.size() - 1] - dArray[i][0]) + " standard deviation: " + std);
            }
            else {
                System.out.println("row  " + line + " count: " + count + " average: " + average + " median: " + median
                        + " mode: null" + " min: " + dArray[i][0] + " max: " + dArray[i][c - 1] 
                        + " range: " + (dArray[i][c - 1] - dArray[i][0]) + " standard deviation: " + std);
            }            
        }
        
        System.out.println();
        
        //2. Perform same analytics as above for each column, and print
        System.out.println("analytics for each column:");
        
        //Transpose the 2 dimensional array (rows become columns, and columns become rows)
        //reassign the dArray
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++){                          
                dArray[i][j] = Double.valueOf(dim2Array[i][j]);
            }
        }
        
        double[][] tArray = new double[c][r];
        
        for(int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++){
                tArray[i][j] = dArray[j][i];
            }
        }
        
        int cLine = 0;
        for(int i = 0; i < c; i++) {
            
                Arrays.sort(tArray[i]);
                
                cLine = cLine + 1;
                int tCount = 0;
                int tCount2 = 1;
                float tSum = 0;
                float tAverage = 0;
                double tTotal = 0;
                float tMedian = 0;
                int tMaxCount = 1;
                double tMode = 0;
                double tValue = tArray[i][0];
                for (int j = 0; j < r; j++){                              
                    tCount = tCount + 1;
                    tSum = (float) (tSum + tArray[i][j]);                                     
                }            
                tAverage = (float) (tSum / tCount);
                
                for (int j = 1; j < r; j++){
                    if(tArray[i][j] == tValue) {
                        tCount2++;
                    }
                    if(tCount2 > tMaxCount) {
                        tMaxCount = tCount2;
                        tMode = tValue;
                    }
                    else {
                        tValue = tArray[i][j];
                        tCount2 = 1;
                    }
                }

                for (int j = 0; j < r; j++){
                    tTotal = tTotal + (tArray[i][j] - tAverage) * (tArray[i][j] - tAverage);
                }
                double tStd = Math.sqrt((tTotal / tCount));
                if (tCount % 2 == 1){
                    tMedian = (float) tArray[i][(tCount - 1) / 2];
                }
                if (tCount % 2 == 0){
                    tMedian = (float) (tArray[i][tCount / 2 - 1] + tArray[i][tCount / 2]) / 2;
                }                                 
                               
                if(tMaxCount >= 2) {
                    System.out.println("column " + cLine + " count: " + tCount + " average: " + tAverage + " median: " + tMedian
                            + " mode: " + tMode + " min: " + tArray[i][0] + " max: " + tArray[i][rows.size() - 1]
                            + " range: " + (tArray[i][rows.size() - 1] - tArray[i][0]) + " standard deviation: " + tStd);
                }
                else {
                    System.out.println("column " + cLine + " count: " + tCount + " average: " + tAverage + " median: " + tMedian
                            + " mode: null" + " min: " + tArray[i][0] + " max: " + tArray[i][rows.size() - 1]
                            + " range: " + (tArray[i][rows.size() - 1] - tArray[i][0]) + " standard deviation: " + tStd + " ");
                }
        }
        
        System.out.println();
        
        //3.Perform same analytics for the entire dataset, and print
        
        //Convert the 2D array into a 1D array
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < rows.size(); i++) {
            // tiny change 1: proper dimensions
            for (int j = 0; j < cols.size(); j++) { 
                // tiny change 2: actually store the values
                list.add(dArray[i][j]); 
            }
        }
        double[] vector = new double[rows.size() * cols.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }
        
        Arrays.sort(vector);
        
        int count = 0;
        int count2 = 0;
        double sum = 0;
        float average = 0;
        double total = 0;
        float median = 0;
        int maxCount = 1;
        double mode = 0;
        double value = vector[0];
        for (int i = 0; i < vector.length; i++) {
            count = count + 1;
            sum = sum + vector[i];                      
        }
        
        for (int i = 1; i < vector.length; i++){            
            if(vector[i] == value) {
                count2++;
            }
            if(count2 > maxCount) {
                maxCount = count2;
                mode = value;
            }
            else {
                value = vector[i];
                count2 = 1;
            }
        }
        
        average = (float) (sum / count);
        for (int i = 0; i < vector.length; i++) {                   
            total = total + (vector[i] - average) * (vector[i] - average);
        }
        double std = Math.sqrt((total / count));
        
        if (count % 2 == 1){
            median = (float) vector[(count - 1) / 2];
        }
        if (count % 2 == 0){
            median = (float) (vector[count / 2 - 1] + vector[count / 2]) / 2;
        }
 
        if(maxCount >=2) {
            System.out.println("entire dataset: " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: " + mode + " min: " + vector[0] + " max: " + vector[count - 1]
                    + " range: " + (vector[count - 1] - vector[0]) + " standard deviation: " + std);
        }
        else {
            System.out.println("entire dataset: " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: null" + " min: " + vector[0] + " max: " + vector[count - 1] 
                    + " range: " + (vector[count - 1] - vector[0]) + " standard deviation: " + std);
        }            
        System.out.println();                

        //4. Re-print all analytics for column 1 again
        double[] cArray = new double[r];
        for (int i = 0; i < r; i++){
            cArray[i] = dArray[i][0];
            }
        
        Arrays.sort(cArray);
        
        count = 0;
        count2 = 0;
        sum = 0;
        average = 0;
        total = 0;
        median = 0;
        maxCount = 1;
        mode = 0;
        value = cArray[0];
        
        for (int i = 1; i < r; i++){            
            if(cArray[i] == value) {
                count2++;
            }
            if(count2 > maxCount) {
                maxCount = count2;
                mode = value;
            }
            else {
                value = cArray[i];
                count2 = 1;
            }
        }                
        
        for (int i = 0; i < r; i++) {
            count = count + 1;
            sum = sum + cArray[i];                      
        }
        
        average = (float) (sum / count);
        
        for (int i = 0; i < r; i++) {                   
            total = total + (cArray[i] - average) * (cArray[i] - average);
        }
        std = Math.sqrt((total / count));
        
        if (count % 2 == 1){
            median = (float) cArray[(count - 1) / 2];
        }
        if (count % 2 == 0){
            median = (float) (cArray[count / 2 - 1] + cArray[count / 2]) / 2;
        }
 
        if(maxCount >=2) {
            System.out.println("fisrt column " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: " + mode + " min: " + cArray[0] + " max: " + cArray[r - 1]
                    + " range: " + (cArray[r - 1] - cArray[0]) + " standard deviation: " + std);
        }
        else {
            System.out.println("first column " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: null" + " min: " + cArray[0] + " max: " + cArray[r - 1]
                    + " range: " + (cArray[r - 1] - cArray[0]) + " standard deviation: " + std);
        }            
        System.out.println();
        
        //Recompute all analytics steps 1-4 for the transposed array, and print 
        System.out.println("for the transposed array:");
        
        //each row
        System.out.println("analytics for each row:");
        int tLine = 0;
        for(int i = 0; i < c; i++) {
            
                Arrays.sort(tArray[i]);
                
                tLine = tLine + 1;
                int tCount = 0;
                int tCount2 = 1;
                float tSum = 0;
                float tAverage = 0;
                double tTotal = 0;
                float tMedian = 0;
                int tMaxCount = 1;
                double tMode = 0;
                double tValue = tArray[i][0];
                for (int j = 0; j < r; j++){                              
                    tCount = tCount + 1;
                    tSum = (float) (tSum + tArray[i][j]);                                     
                }            
                tAverage = (float) (tSum / tCount);
                
                for (int j = 1; j < r; j++){
                    if(tArray[i][j] == tValue) {
                        tCount2++;
                    }
                    if(tCount2 > tMaxCount) {
                        tMaxCount = tCount2;
                        tMode = tValue;
                    }
                    else {
                        tValue = tArray[i][j];
                        tCount2 = 1;
                    }
                }

                for (int j = 0; j < r; j++){
                    tTotal = tTotal + (tArray[i][j] - tAverage) * (tArray[i][j] - tAverage);
                }
                double tStd = Math.sqrt((tTotal / tCount));
                if (tCount % 2 == 1){
                    tMedian = (float) tArray[i][(tCount - 1) / 2];
                }
                if (tCount % 2 == 0){
                    tMedian = (float) (tArray[i][tCount / 2 - 1] + tArray[i][tCount / 2]) / 2;
                }                                                    
                               
                if(tMaxCount >= 2) {
                    System.out.println("line " + tLine + " count: " + tCount + " average: " + tAverage + " median: " + tMedian
                            + " mode: " + tMode + " min: " + tArray[i][0] + " max: " + tArray[i][rows.size() - 1]
                            + " range: " + (tArray[i][rows.size() - 1] - tArray[i][0]) + " standard deviation: " + tStd);
                }
                else {
                    System.out.println("line " + tLine + " count: " + tCount + " average: " + tAverage + " median: " + tMedian
                            + " mode: null" + " min: " + tArray[i][0] + " max: " + tArray[i][rows.size() - 1]
                            + " range: " + (tArray[i][rows.size() - 1] - tArray[i][0]) + " standard deviation: " + tStd);
                }
        }
                        
        //each column        
        System.out.println("analytics for each column:");
        line = 0;

        for (int i = 0; i < r; i++) {
            
            Arrays.sort(dArray[i]);                                             //sort for search for median and mode
            
            line = line + 1;                                                    //get the line number
            count = 0;                              
            count2 = 1;
            sum = 0;
            average = 0;
            total = 0;
            median = 0;
            maxCount = 1;
            mode = 0;
            value = dArray[i][0];
            
            for (int j = 0; j < c; j++){                              
                count = count + 1;                                              //count in each row
                sum = (float) (sum + dArray[i][j]);                             //get the sum of each row  
            }
            
            // calculate mean
            average = (float) (sum / count);            

            // calculate total for std
            for (int j = 0; j < c; j++){
                total = total + (dArray[i][j] - average) * (dArray[i][j] - average);
            }
            
            //find the mode
            for (int j = 1; j < c; j++){
                if(dArray[i][j] == value) {
                    count2++;
                }
                if(count2 > maxCount) {
                    maxCount = count2;
                    mode = value;
                }
                else {
                    value = dArray[i][j];
                    count2 = 1;
                }
            }
            
            std = Math.sqrt((total / count));
            if (count % 2 == 1){
                median = (float) dArray[i][(count - 1) / 2];
            }
            if (count % 2 == 0){
                median = (float) (dArray[i][count / 2 - 1] + dArray[i][count / 2]) / 2;
            }
            if(maxCount >=2) {
                System.out.println("column  " + line + " count: " + count + " average: " + average + " median: " + median
                        + " mode: " + mode + " min: " + dArray[i][0] + " max: " + dArray[i][c - 1]
                        + " range: " + (dArray[i][cols.size() - 1] - dArray[i][0]) + " standard deviation: " + std);
            }
            else {
                System.out.println("column  " + line + " count: " + count + " average: " + average + " median: " + median
                        + " mode: null" + " min: " + dArray[i][0] + " max: " + dArray[i][c - 1] 
                        + " range: " + (dArray[i][c - 1] - dArray[i][0]) + " standard deviation: " + std);
            }            
        }
        
        System.out.println();
            
        //entire dataset
        count = 0;
        count2 = 0;
        sum = 0;
        average = 0;
        total = 0;
        median = 0;
        maxCount = 1;
        mode = 0;
        value = vector[0];
        for (int i = 0; i < vector.length; i++) {
            count = count + 1;
            sum = sum + vector[i];                      
        }
        
        for (int i = 1; i < vector.length; i++){            
            if(vector[i] == value) {
                count2++;
            }
            if(count2 > maxCount) {
                maxCount = count2;
                mode = value;
            }
            else {
                value = vector[i];
                count2 = 1;
            }
        }
        
        average = (float) (sum / count);
        for (int i = 0; i < vector.length; i++) {                   
            total = total + (vector[i] - average) * (vector[i] - average);
        }
        std = Math.sqrt((total / count));
        
        if (count % 2 == 1){
            median = (float) vector[(count - 1) / 2];
        }
        if (count % 2 == 0){
            median = (float) (vector[count / 2 - 1] + vector[count / 2]) / 2;
        }
 
        if(maxCount >=2) {
            System.out.println("entire dataset: " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: " + mode + " min: " + vector[0] + " max: " + vector[count - 1]
                    + " range: " + (vector[count - 1] - vector[0]) + " standard deviation: " + std);
        }
        else {
            System.out.println("entire dataset: " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: null" + " min: " + vector[0] + " max: " + vector[count - 1] 
                    + " range: " + (vector[count - 1] - vector[0]) + " standard deviation: " + std);
        }            
        System.out.println(); 
        
        //column 1
        for(int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++){
                tArray[i][j] = dArray[j][i];
            }
        }
        
        double[] rArray = new double[c];
        for (int i = 0; i < c; i++){
            rArray[i] = tArray[i][0];
            }
        
        Arrays.sort(rArray);
        
        count = 0;
        count2 = 0;
        sum = 0;
        average = 0;
        total = 0;
        median = 0;
        maxCount = 1;
        mode = 0;
        value = rArray[0];
        
        for (int i = 1; i < c; i++){            
            if(rArray[i] == value) {
                count2++;
            }
            if(count2 > maxCount) {
                maxCount = count2;
                mode = value;
            }
            else {
                value = rArray[i];
                count2 = 1;
            }
        }                
        
        for (int i = 0; i < c; i++) {
            count = count + 1;
            sum = sum + rArray[i];
        }
        
        average = (float) (sum / count);
        
        for (int i = 0; i < c; i++) {                   
            total = total + (rArray[i] - average) * (rArray[i] - average);
        }
        std = Math.sqrt((total / count));
        
        if (count % 2 == 1){
            median = (float) rArray[(count - 1) / 2];
        }
        if (count % 2 == 0){
            median = (float) (rArray[count / 2 - 1] + rArray[count / 2]) / 2;
        }
 
        if(maxCount >=2) {
            System.out.println("fisrt column " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: " + mode + " min: " + rArray[0] + " max: " + rArray[c - 1]
                    + " range: " + (rArray[c - 1] - rArray[0]) + " standard deviation: " + std);
        }
        else {
            System.out.println("first column " + " count: " + count + " average: " + average + " median: " + median
                    + " mode: null" + " min: " + rArray[0] + " max: " + rArray[c - 1]
                    + " range: " + (rArray[c - 1] - rArray[0]) + " standard deviation: " + std);
        } 
    } 
}
        
