import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Movie{
    String name;
    double imdbrating;
    int year;
    Movie(String name,double imdbrating,int year){
        this.name=name;
        this.imdbrating=imdbrating;
        this.year=year;
    }
    @Override
    public String toString() {
        return name + " | Rating: " + imdbrating + " | Year: " + year;
    }
}
public class p {
    public static int partition(Movie[] movies,int low,int high,String criteria){
        Movie pivot=movies[high];
        int i=low-1;
        for(int j=low;j<high;j++){
            if(compare(movies[j],pivot,criteria)){
                i++;
                Movie temp=movies[i];
                movies[i]=movies[j];
                movies[j]=temp;
            }
        }
        Movie temp=movies[i+1];
        movies[i+1]=movies[high];
        movies[high]=temp;
        return i+1;
    }
    public static boolean compare(Movie a,Movie b,String criteria){
        switch(criteria.toLowerCase()){
            case "rating":
                return a.imdbrating>b.imdbrating;
            case "year":
                return a.year>b.year;
            default:
                return false;
        }
    }
    public static void quicksort(Movie[] movies,int low,int high,String criteria){
        if(low<high){
            int pi=partition(movies,low,high,criteria);
            quicksort(movies,low,pi-1,criteria);
            quicksort(movies,pi+1,high,criteria);
        }

    }
    public static void main(String[] args){
        Scanner a=new Scanner(System.in);
        Movie[] movies= {new Movie("A",8.1,2005),new Movie("B",9.7,2015),new Movie("C",8.3,2001)};
        System.out.println("Enter criteria (rating/year)");
        String criteria=a.nextLine();

        quicksort(movies,0,movies.length-1,criteria);
        for (Movie m : movies) {
            System.out.println(m);
        }
    }
}
