package q1;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a double linked list with a sentinel node
 *
 * Implement a double linked list with a sentinel element. The API should have four methods (no more) to:
 * i)   A method to create a new list instantiated with some type of elements (data stored in the list) defined at the time the list is created
 * ii)  A method to insert an element at the beginning of the list
 * iii) A method to insert an element at the end of the list
 * iv)  A method to remove and return the first element in the list
 * v)   A method to remove and return the last element of the list
 *
 * vi)  You should calculate the Big-Oh complexities for insertion and removal of elements
 *
 *
 * i)   create new list & insert in beginning   i) & ii)
 * ii)  insert in the end    iii)
 * iii) remove first    iv)
 * iv)  remove last     v)
 *
 */
public class DLL {
    private Node sentinel;
    private class Node<Item> {
        Item item;
        Node next;
        Node prev;
    }

    public <Item> void addBeg(Item value){
        if(this.sentinel.next == null && this.sentinel.prev == null){
            sentinel.next = new Node();
            sentinel.prev = sentinel.next;
            sentinel.next.item = value;
        }
        else{
            sentinel.next.prev = new Node();
            sentinel.next.prev.next = sentinel.next;
            sentinel.next = sentinel.next.prev;
        }
    }
    public void print(){
        System.out.println(sentinel.next);
        System.out.println(sentinel.prev);
    }
    /*
    public class Node {

        public int value;
        public Node prev;
        public Node next;

        public Node(){
            value = Integer.MIN_VALUE;
            prev = null;
            next = null;
        }
        public Node(int value) {
            this.value = value;
        }
        public void addAfter(Node newCell) {
            newCell.next = this.next;
            newCell.prev = this;

            this.next = newCell;
            newCell.next.prev = newCell;
        }
        public void addBefore(Node newCell) {
            newCell.next = this;        // a --- b
            newCell.prev = this.prev;   // a --- nC --- b

            this.prev.next = newCell;
            this.prev = newCell;
        }
        public void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }
    }*/


    public static void main(String[] args){
        System.out.println("Double linked list with a sentinel element: ");
        DLL dll = new DLL();




    }
}
