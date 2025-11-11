import java.util.*;
class DisasterReliefKnapsack{
    static class Item{
        String name;
        int weight;
        int utility;
        boolean perishable;
        Item(String name,int weight,int utility,boolean perishable){
            this.name=name;
            this.weight=weight;
            this.utility=utility;
            this.perishable=perishable;
        }
    }
    
    public static void main(String[] args){
        Scanner a=new Scanner(System.in);
        System.out.println("Enter no of items ");
        int n=a.nextInt();
        List<Item> items=new ArrayList<>();
        System.out.println("Enter item details (name weight utility perishable(yes/no)");
        for(int i=0;i<n;i++){
            String name=a.next();
            int weight=a.nextInt();
            int utility=a.nextInt();
            String p=a.next();
            boolean perishable=p.equalsIgnoreCase("yes");
            if(perishable) utility+=5;
            items.add(new Item(name,weight,utility,perishable));
        }
        System.out.println("Enter truck capacity (W)");
        int W=a.nextInt();
        int[][] dp=new int[n+1][W+1];
        for(int i=1;i<=n;i++){
            for(int w=1;w<=W;w++){
                if(items.get(i-1).weight<=w){
                    dp[i][w]=Math.max(
                            dp[i-1][w],
                            dp[i-1][w-items.get(i-1).weight]+items.get(i-1).utility
                    );
                }else{
                    dp[i][w]=dp[i-1][w];
                }
            }
        }
        System.out.println("Maximum Utility "+dp[n][W]);
        int w=W;
        for(int i=n;i>0;i--){
            if(dp[i][w]!=dp[i-1][w]){
                Item it=items.get(i-1);
                System.out.println(" - "+it.name+" (weight :"+ it.weight+")");
                w-=it.weight;
            }
        }
    }
}
/*
sample 
rice 10 60 no
medkit 5 30 yes 
water 4 20 no 
blanket 3 15 no
babyfood 2 10 yes 
medicine 1 50 yes 
 */
