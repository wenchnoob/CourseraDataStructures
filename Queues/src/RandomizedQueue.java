import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Object[] queue;
    private int back;
    private int removedItems;
    
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = new Object[10];
        back = 0;
        removedItems = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (size() == 0) return true;
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return back - removedItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (back == queue.length) resizeUp();
        queue[back++] = item;
    }

    // remove and return a random item
    @SuppressWarnings("unchecked")
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        removedItems++;
        
        int index =  0;
        
        Item item = null;
        do {
            index = StdRandom.uniform(back);
            item = (Item) queue[index];
        } while (item == null);
        
        queue[index] = null;
        
        if (size() < queue.length/4) resizeDown();
        
        return item;
        
    }

    // return a random item (but do not remove it)
    @SuppressWarnings("unchecked")
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        Item item = null;
        do {
            item = (Item) queue[StdRandom.uniform(back)];
        } while (item == null);
        
        return item;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RQueueIter();
    }
    
    private class RQueueIter implements Iterator<Item> {
        private Object[] queueItems;
        private final int end;
        private int returned = 0;
        
        public RQueueIter() {
            int temp = 0;
            queueItems = new Object[queue.length];
            for (int i = 0; i < queue.length; i++) {
                if (queue[i] != null) {
                    queueItems[temp++] = queue[i];
                }
            }
            end = temp;
        }

        @Override
        public boolean hasNext() {
            if (end - returned != 0) return true;
            return false;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            returned++;
            int index =  0;
            
            Item item = null;
            do {
                index = StdRandom.uniform(back);
                item = (Item) queueItems[index];
            } while (item == null);
            
            queueItems[index] = null;
            
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }

    private void resizeUp() {
        Object[] newQueue = new Object[queue.length * 2];
        int index = 0;
        
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != null) newQueue[index++] = queue[i];
        }
        queue = newQueue;
        back = index;
        removedItems = 0;
    }
    
    private void resizeDown() {
        Object[] newQueue = new Object[queue.length/2];
        int index = 0;
        
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != null) newQueue[index++] = queue[i];
        }
        queue = newQueue;
        back = index;
        removedItems = 0;
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQ = new RandomizedQueue<>();
        
        for (Integer i: rQ) {
            System.out.println(i);
        }
        
        System.out.println(rQ.isEmpty() + " " + rQ.size());

        rQ.enqueue(20);
        rQ.enqueue(40);
        rQ.enqueue(10);
        rQ.enqueue(15);
        rQ.dequeue();
        
        System.out.println(rQ.isEmpty() + " " + rQ.size());
        
        for (Integer i: rQ) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        System.out.println(rQ.sample());
        
        System.out.println(rQ.isEmpty() + " " + rQ.size());
        
    }

}
