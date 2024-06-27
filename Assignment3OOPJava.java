/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment3oop.java;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author joshg
 */
public class Assignment3OOPJava {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       double[][] data = new double [569][569];
       double[][] distance = new double[569][569];
        try {
            // TODO code application logic here
            BufferedReader reader = new BufferedReader(new FileReader("C://Users//joshg//fileread//Experiment.csv"));
            String line;
            int k = 0;
            while((line = reader.readLine()) != null){
                String[] splitted = line.split(",");
                int j =0;
                for (int i =2; i < splitted.length; i++){
                    double d = Double.parseDouble(splitted[i]);
                    data[k][j] = d;
                    j++;
                }   
                 k++;
               
            }
                
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assignment3OOPJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        calculation test = new calculation();
        for(int i=0; i < data.length; i++){
            for(int j=0; j < data.length; j++){
                double[] t1 = data[i];
                double[] t2 = data[j];
                double d = test.distance(t1, t2);
                distance[i][j] = d;
//                System.out.printf("\nDistnace: %.2f", distance[i][j]);
             }
            
        }
                   
        
        for(int N=3; N<=13; N+=2){
        for (int i=0;i<distance.length;i++)
        {
            double[] temp = new double[distance.length];
            for (int k=0;k<temp.length;k++)
            {
                temp[k] = distance[i][k];
            }
            
           int [] closestValues = test.closestValues(N, temp);
           
           test.testResults(closestValues, N, i);
           
        }
        double correct = test.getCorrect();
        double incorrect = test.getIncorrect();
        
        double accuracy = correct/569 * 100;
        
        System.out.println("N = "+N);
        System.out.printf("Accuracy: %.2f%%", accuracy);
        System.out.println("");
        test.setCorrect(0);
        test.setIncorrect(0);
        accuracy=0;
       }
        
    }
    
}


class calculation{
  private static double correct = 0;
  private static double incorrect = 0;
    
    public double distance(double[] t1, double[] t2){
        double distance = 0;
        double sum = 0;
        for (int i = 0; i < t1.length; i++){
            sum = sum + (Math.pow(Math.abs(t1[i]-t2[i]),2));
            
        }
        distance = Math.sqrt(sum);
        
        return distance;
    }
    public int[] closestValues(int k, double[] distance){
    
    //declared varaible with high value to use as a reference point
    int[] neighbours= new int[k];
    for (int i = 0; i < k; i++)
    {
        double minimum=Double.MAX_VALUE;
        int closestIndex = 0;
        for (int j=1;j<distance.length;j++)
        {
            if (distance[j]<minimum)
            {
                minimum=distance[j];
                closestIndex=j;
            }
        }
        neighbours[i]=closestIndex;
        distance[closestIndex]=Double.MAX_VALUE;
    }
    return neighbours;
       
   }
    public void testResults(int[] closestValues, int v, int h) throws IOException {
        String[][] resultsMain = new String[569][569];
        try {
             // TODO code application logic here
            BufferedReader reader = new BufferedReader(new FileReader("C://Users//joshg//fileread//Experiment.csv"));
            String line;
            int k = 0;
            while((line = reader.readLine()) != null){
                String[] splitted = line.split(",");
                int j =0;
                for (int i =1; i < splitted.length; i++){
                    resultsMain[k][j] = splitted[i];
                    j++;
                }   
                 k++;
            }
                
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assignment3OOPJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] results = new String[v];
        
        for(int i=0;i<closestValues.length;i++)
        {
            results[i] =  resultsMain[closestValues[i]][0]; 
            
        }
        
        int Mcount =0;
        int Bcount =0;
        String m = "M";
        String b = "B";
        
           for (int j =0;j<results.length;j++){
               if(results[j].equals("M")){
                Mcount++;
            } else if(results[j].equals("B")){
                Bcount++;
            }
//               System.out.println("Result: "+results[j]);
               
           }
           
//           System.out.println("B: "+Bcount + " M: "+Mcount);
           if(Mcount > Bcount && resultsMain[h][0].equals(m)){
//               System.out.println("M is the correct evaluation");
               this.correct++;
               
           } else if(Mcount > Bcount && !resultsMain[h][0].equals(m)){
//               System.out.println("Incorrect evaluation");
               this.incorrect++;
           }
            else if(Mcount < Bcount && resultsMain[h][0].equals(b)){
//               System.out.println("B is the correct evaluation");
               this.correct++;
           }
            else if(Mcount < Bcount && !resultsMain[h][0].equals(b)){
//               System.out.println("Incorrect evaluation");
               this.incorrect++;
           } 

//            System.out.println("");

   
    }
    public double getCorrect(){
        return this.correct;
    }
    public double getIncorrect(){
        return this.incorrect;
    }
    public void setCorrect(int reset){
        this.correct = reset;
    }
    public void setIncorrect(int reset){
        this.incorrect = reset;
    }
}

