import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
class Order{
    int order_id;
    LocalDateTime timestamp;
    public Order(int order_id,LocalDateTime timestamp){
        this.order_id=order_id;
        this.timestamp=timestamp;
    }
    @Override
    public String toString(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");
        return "Order "+order_id+" | Time Stamp "+timestamp.format(formatter);
    }
}
public class Order_Management {
    public static void mergesort(Order[] order){
        if(order.length<2) return;
        int mid= order.length/2;
        Order[] left=new Order[mid];
        Order[] right=new Order[order.length-mid];
        System.arraycopy(order,0,left,0,mid);
        System.arraycopy(order,mid,right,0,order.length-mid);
        mergesort(left);
        mergesort(right);
        merge(order,left,right);
    }
    public static void merge(Order[] order,Order[] left,Order[] right){
        int i=0,j=0,k=0;
        while(i< left.length && j< right.length){
            if(left[i].timestamp.isBefore(right[j].timestamp)){
                order[k++]=left[i++];
            }else{
                order[k++]=right[j++];
            }
        }
        while(i< left.length) order[k++]=left[i++];
        while (j<right.length) order[k++]=right[j++];
    }
    public static void main(String[] args){
        Scanner a=new Scanner(System.in);
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");
        System.out.println("Enter how many orders you want ");
        int n=0;
        while(true){
            try {
                n=a.nextInt();
                if(n<=0) throw new Exception("Number must be Positive ");
                break;
            }catch (InputMismatchException e){
                System.out.println("Invalid input ");
                a.next();
                break;
            }
            catch (Exception e){
                System.out.println(e.getMessage()+" Try again ");
            }
        }
        Order[] order=new Order[n];
        for(int i=0;i<n;i++){
            int order_id=0;
            while(true){
                System.out.println("Enter Order Id");
                try{
                    order_id=a.nextInt();
                    a.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    a.next();
                }
            }
            LocalDateTime timestamp=null;
            while(true){
                System.out.println("Enter TimeStamp in Format (dd MM yyyy HH:mm)");
                try{
                    String input=a.nextLine();
                    LocalDateTime start=LocalDateTime.of(2025,11,11,0,0);
                    LocalDateTime end=LocalDateTime.of(2025,11,21,0,0);
                    timestamp=LocalDateTime.parse(input,formatter);
                    if(timestamp.isBefore(start) || timestamp.isAfter(end)){
                        System.out.println("Date Should be between 11-11-2025 and 21-11-2025");
                        continue;
                    }
                    break;
                }catch (DateTimeParseException e){
                    System.out.println("Invalid Input");
                }
            }
            order[i]=new Order(order_id,timestamp);
        }
        mergesort(order);
        for (Order o:order){
            System.out.println(o);
        }
    }
}
