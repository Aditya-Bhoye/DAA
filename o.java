import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Item{
    String name;
    int weight;
    int value;
    Item(String name,int weight,int value){
        this.name=name;
        this.weight=weight;
        this.value=value;
    }
}
public class o {
    public static void solve(List<Item> items,int capacity){
        items.sort((a,b)->Double.compare((double)b.value/b.weight,(double)a.value/a.weight));
        double totalvalue=0.0;
        int currentweight=0;
        for(Item item:items){
            if(currentweight+item.weight<=capacity){
                currentweight+=item.weight;
                totalvalue+=item.value;
                System.out.println(item.name+" is added ");
            }
            else{
                double rc=capacity-currentweight;
                if(rc>0){
                    totalvalue+=(double)item.value*((double) rc/item.weight);
                    System.out.println(item.name+" is also added in fraction ");
                }
                break;

            }
        }
        System.out.println("The max utility is "+totalvalue);

    }
    public static void main(String[] args){
        List<Item> items= Arrays.asList(new Item("Food",20,60),new Item("Cloth",30,50),new Item("Water",10,30));
        int capacity=30;
        solve(items,capacity);
    }
}
