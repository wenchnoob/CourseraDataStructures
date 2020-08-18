import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (first == null) {
            first = new Node();
            first.val = item;
            size++;
        } else {
            if (last == null) {
                last = first;
                first = new Node();
                first.val = item;
                first.next = last;
                last.prev = first;
                size++;
            } else {
                Node temp = first;
                first = new Node();
                first.val = item;
                first.next = temp;
                temp.prev = first;
                size++;
            }
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        if (first == null) {
            addFirst(item);
         } else  {
            if (last == null) {
                last = new Node();
                last.val = item;
                first.next = last;
                last.prev = first;
                size++;
            } else {
                Node temp = last;
                last = new Node();
                last.val = item;
                temp.next = last;
                last.prev = temp;
                size++;
            }
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        if (size == 1) {
            if (first == null) {
                return removeLast();
            } else {
                Item temp = first.val;
                first = null;
                last = null;
                size = 0;
                return temp;
            }
            
        } else {
            if (first != null) {
                Item item = first.val;
                first = first.next;
                if (first != null) first.prev = null;
                size--;
                return item;
            } else {
                return removeLast();
            }
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        if (size == 1) {
            if (last == null) {
                return removeFirst();
            } else {
                Item temp = last.val;
                first = null;
                last = null;
                size = 0;
                return temp;
            }
        } else {
            if (last != null) {
                Item item = last.val;
                last = last.prev;
                if (last != null) last.next = null;
                size--;
                return item;
            } else {
                return removeFirst();
            }
        }
    }
    
    public String toString() {
        String s = "";
        for (Item i: this) {
           s += i + " ";
        }
        return s;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DequeIter();
    }

    private class DequeIter implements Iterator<Item> {
        private Node cur = first;

        @Override
        public boolean hasNext() {
            if (cur != null) return true;
            return false;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = cur.val;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> iDeque = new Deque<>();

        System.out.println(iDeque.isEmpty() + " " + iDeque.size());

        iDeque.addLast(12);
        iDeque.addFirst(33);
        iDeque.addFirst(2);
        iDeque.addLast(32);
        iDeque.addLast(38);
        iDeque.addFirst(22);
        iDeque.addFirst(24);
        System.out.println(iDeque);
        
        System.out.println("Removed: " + iDeque.removeFirst());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeFirst());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeLast());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeLast());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeLast());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeLast());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeLast());
        System.out.println(iDeque);
        
        iDeque.addLast(39);
        System.out.println(iDeque);
        iDeque.addFirst(12);
        System.out.println(iDeque);
        
        System.out.println("Removed: " + iDeque.removeFirst());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeLast());
        System.out.println(iDeque);
        System.out.println("Removed: " + iDeque.removeFirst());
        System.out.println(iDeque);
        
        iDeque.addFirst(1223);
        System.out.println(iDeque);


        System.out.println(iDeque.isEmpty() + " " + iDeque.size());

        for (Integer i: iDeque) {
           System.out.print(i + " ");
        }

    }

    private class Node {
        private Item val;
        private Node next, prev;
    }
}


