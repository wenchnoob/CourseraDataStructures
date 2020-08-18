import edu.princeton.cs.algs4.StdIn;

public class Permutation {

   public static void main(String[] args) {
       RandomizedQueue<String> rQStrings = new RandomizedQueue<>();
       
       int amountToPrint = Integer.parseInt(args[0]);
       
       while (!StdIn.isEmpty()) {
            rQStrings.enqueue(StdIn.readString());
       }
       
       for (int i = 0; i < amountToPrint; i++) {
           System.out.println(rQStrings.dequeue());
       }
       
       
   }
   
}