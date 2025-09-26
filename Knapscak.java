import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
public class Knapscak {
    public static void solve(int[] w,int[] p,int c){
        double[][] items=new double[w.length][3];
        for(int i=0;i<w.length;i++){
            items[i][0]=w[i];
            items[i][1]=p[i];
            items[i][2]=(double)p[i]/w[i];
        }
        Arrays.sort(items, Comparator.comparingDouble((double[] o)->o[2]).reversed());
        double totalvalue=0.0;
        int currentweight=0;
        for(double[] item:items){
            double itemweight=item[0];
            double itemvalue=item[1];
            if(currentweight+itemweight<=c){
                currentweight+=itemweight;
                totalvalue+=itemvalue;
            }
            else{
                double rc=c-currentweight;
                totalvalue+=item[2]*rc;
                break;
            }
        }
        System.out.println(totalvalue);
    }
    public static void main(String[] args){
        Scanner a=new Scanner(System.in);
        System.out.println("Enter number of elements you want");
        int n=a.nextInt();
        int[] profits=new int[n];
        int[] weights=new int[n];
        for(int i=0;i<n;i++){
            System.out.println("Enter "+(i+1)+" for profits ");
            profits[i]=a.nextInt();
            System.out.println("Enter "+(i+1)+" for weights ");
            weights[i]=a.nextInt();
        }
        System.out.println("Enter capacity of bag ");
        int capacity= a.nextInt();
        solve(weights,profits,capacity);
    }
}
