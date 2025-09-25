package edu.aueb.ds.lab3.SimpleList;

import edu.aueb.ds.exceptions.EmptyListException;

public class ListMain {
    public static void main(String[] args) {
        // Create an empty list
        ListInterface list = new List();

        try {

            // Print empty list
            System.out.println(list.toString());

            // Add two elements at front
            list.insertAtFront(1);
            System.out.println(list.toString());

            list.insertAtFront(2);
            System.out.println(list.toString());

            // Add two elements at back
            list.insertAtBack(3);
            System.out.println(list.toString());

            list.insertAtBack(4);
            System.out.println(list.toString());

            // Remove from front
            int removed = list.removeFromFront();
            System.out.println(removed + " just got removed...");

            // Remove from back
            removed = list.removeFromBack();
            System.out.println(removed + " just got removed...");

            System.out.println(list.toString());

            // Insert at front
            list.insertAtFront(5);

            // Insert ta back
            list.insertAtBack(6);
            System.out.println(list.toString());


            // keep removing until an exception is thrown
            System.out.println("Keep removing from list!");
            // 100 in case something goes wrong, to prevent endless loop
            for (int i = 0; i < 100; ++i) {
                removed = list.removeFromFront();
                System.out.println(removed + " just got removed...");

                removed = list.removeFromBack();
                System.out.println(removed + " just got removed...");
            }
        } catch (EmptyListException ex) {
            System.out.println("Tried to remove from an empty list!");
        }
    }
}
