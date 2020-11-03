package q1;
import java.util.Scanner;
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
        if(location) {                 // true = add node front
            sentinel.next.prev = tmp;
            tmp.next = sentinel.next;
            tmp.prev = sentinel;       // uncomment this to make it circular
            sentinel.next = tmp;
            tmp.item = value;
        } else {                        // false = add node back
            sentinel.prev.next = tmp;
            tmp.prev = sentinel.prev;
            tmp.next = sentinel;      // uncomment this to make it circular
            sentinel.prev = tmp;
            tmp.item = value;
        }
    }
    public <Item> Node remove(boolean location){
        if(sentinel.next == sentinel && sentinel.prev == sentinel){
            return null;
        }
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

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Double linked list with a sentinel element, follow these guidelines: ");
        System.out.println("type 'quit' to to quit");

        System.out.println("type 'create' the first thing you do to create a list");

        System.out.println("type '0 x' to add value x in the front");     // 0 x add back
        System.out.println("type '1 x' to add value x in the back");      // 1 x add front

        System.out.println("type '2' to remove value x from the front");  // 2 remove back
        System.out.println("type '3' to remove value x from the back");   // 3 remove front

        DLL dll = null;

        while(scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] inputArray = input.split(" ");

            switch(inputArray[0]){
                case "quit":
                    break;

                case "create":
                    dll = new DLL();
                    StdOut.println("created a new list");
                    break;

                case "0":
                    if(dll == null || inputArray.length == 1){
                        StdOut.println("create a list before adding, type 'create list', or no item parameter");
                        break;
                    }
                    dll.add(inputArray[1], true);
                    StdOut.println("Added " + inputArray[1] + " to list, anything else?");
                    break;

                case "1":
                    if(dll == null || inputArray.length == 1){
                        StdOut.println("create a list before adding, type 'create list', or no item parameter");
                        break;
                    }

                    dll.add(inputArray[1], false);

                    StdOut.println("Added " + inputArray[1] + " to list, anything else?");
                    break;

                case "2":
                    if(dll == null){
                        StdOut.println("create a list before removing, type 'create list'");
                        break;
                    }

                    Node node = dll.remove(true);

                    if(node == null) {
                        StdOut.println("Empty list, nothing removed. try adding something");
                        break;
                    }
                    StdOut.println("removed " + node.item);
                    break;

                case "3":
                    if(dll == null){
                        StdOut.println("create a list before removing, type 'create list'");
                        break;
                    }

                    Node node1 = dll.remove(false);

                    if(node1 == null) {
                        StdOut.println("Empty list, nothing removed. try adding something");
                        break;
                    }
                    StdOut.println("removed " + node1.item);
                    break;

                default:
                    StdOut.println("Wrong input, try again");
            }
        }
    }
}