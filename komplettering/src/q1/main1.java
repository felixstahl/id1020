package q1;

import java.util.Scanner;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 */

public class main1 {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Double linked list with a sentinel element, follow these guidelines: ");
        System.out.println("type 'quit' to to quit");

        System.out.println("type 'create 0' to create a list");

        System.out.println("type 'add x 1' to add value x in the back");
        System.out.println("type 'add x 2' to add value x in the front");

        System.out.println("type 'remove x' to remove value x from the back");
        System.out.println("type 'remove x' to remove value x from the front");

        System.out.println("Now lets start");

        DLL dll = null;

        while(scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] inputArray = input.split(" ");
            if(input == "quit")
                break;

            if(inputArray.length <= 2) {
                StdOut.println("Wrong input");
                continue;
            }

            switch(inputArray[0]){
                case "quit":
                    break;

                case "0":
                    dll = new DLL();
                    break;

                case "1":
                    dll.add(inputArray[1], true);
                    break;

                case "2":
                    dll.add(inputArray[1], false);
                    break;

                case "3":
                    dll.remove(true);
                    break;

                case "4":
                    dll.remove(false);
                    break;

                default:
                    StdOut.println("wrong input");
            }
        }

        /*DLL dll = new DLL();

        dll.add("4", true);
        dll.add("5", true);
        dll.add("6", true);
       // dll.print();

        DLL.Node remove = dll.remove(true);
        */
    }
}