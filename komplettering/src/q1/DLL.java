package q1;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a double linked (circular if wanted) list with a sentinel node
 *
 * Implement a double linked list with a sentinel element. The API should have four methods (no more) to:
 * 1) A method to create a new list instantiated with some type of elements (data stored in the list) defined at the time the list is created
 * 2) A method to insert an element at the beginning of the list
 * 3) A method to insert an element at the end of the list
 * 4) A method to remove and return the first element in the list
 * 5) A method to remove and return the last element of the list
 *
 * 1) is constructor
 * 2/3 is add
 * 4/5 is remove and return
 *
 * Time complexity:
 * Insert = O(1)
 * Remove = O(1)
 */
public class DLL {
    private Node sentinel = new Node();
    public DLL(){
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }
    public class Node<Item> {
        Item item;
        Node next;  // first for sentinel
        Node prev;  // last for sentinel
    }

    public <Item> void add(Item value, boolean location){
        Node tmp = new Node();
        if (location) {                 // true = add node front
            sentinel.next.prev = tmp;
            tmp.next = sentinel.next;
           //tmp.prev = sentinel;       // uncomment this to make it circular
            sentinel.next = tmp;
            tmp.item = value;
        } else {                        // false = add node back
            sentinel.prev.next = tmp;
            tmp.prev = sentinel.prev;
            //tmp.next = sentinel;      // uncomment this to make it circular
            sentinel.prev = tmp;
            tmp.item = value;
        }
    }
    public <Item> Node remove(boolean location){
        if(location) {                      // true = remove and return front node
            Node tmp = sentinel.next;
            sentinel.next = tmp.next;
            sentinel.next.prev = sentinel;
            return tmp;
        }else{                              // false = remove and return back node
            Node tmp = sentinel.prev;
            sentinel.prev = tmp.prev;
            sentinel.prev.next = sentinel;
            return tmp;
        }
    }

    public void print(){
        System.out.println(sentinel.next.item);
        System.out.println(sentinel.prev.item);
    }

   /* public static void main(String[] args){
        System.out.println("Double linked list with a sentinel element: ");
        DLL dll = new DLL();


    }*/
}
