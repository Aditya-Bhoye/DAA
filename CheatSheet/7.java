import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ExamScheduler {
    static class Course{
        String name;
        int student;
        Course(String name,int student){
            this.name=name;
            this.student=student;
        }
    }
    public static void main(String[] args){
        Scanner a=new Scanner(System.in);
        System.out.println("Enter no of Courses ");
        int n=a.nextInt();
        a.nextLine();
        Course[] courses=new Course[n];
        for(int i=0;i<n;i++){
            System.out.println("Enter Course name");
            String name=a.next();
            System.out.println("Enter no of students enrolled ");
            int student=a.nextInt();
            courses[i]=new Course(name,student);
        }
        List<List<Integer>> graph=new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }
        System.out.println("enter no of conflicating pairs");
        int co=a.nextInt();
        System.out.println("Enter each conflict as index1 index 2");
        for(int i=0;i<co;i++){
            int u=a.nextInt();
            int v=a.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        int[] result=graphcoloring(graph);
        System.out.println("---------------------");
        for(int i=0;i<n;i++){
            System.out.println("Course "+courses[i].name+" Students "+courses[i].student+" Slot "+result[i]);
        }
        int maxslot=-1;
        for(int slot:result){
            if(slot>maxslot) maxslot=slot;
        }
        System.out.println("MINIMUM SLOT REQUIRED "+(maxslot+1));
    }
    public static int[] graphcoloring(List<List<Integer>> graph){
        int n=graph.size();
        int[] color=new int[n];
        Arrays.fill(color,-1);
        color[0]=0;
        boolean[] used=new boolean[n];
        for(int u=1;u<n;u++){
            Arrays.fill(used,false);
            for(int v:graph.get(u)){
                if (color[v]!=-1) used[color[v]]=true;
            }
            int c;
            for(c=0;c<n;c++){
                if(!used[c]) break;
            }
            color[u]=c;
        }
        return color;
    }
}
/*
Enter no of Courses 
5
Enter Course name
DSA
Enter no of students enrolled 
100
Enter Course name
DBMS
Enter no of students enrolled 
120
Enter Course name
CN
Enter no of students enrolled 
110
Enter Course name
AI
Enter no of students enrolled 
90
Enter Course name
ML
Enter no of students enrolled 
80
enter no of conflicating pairs
6
Enter each conflict as index1 index 2
0 1
0 2
1 2
1 3
2 4
3 4

*/
