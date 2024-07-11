import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

class Student {
    String name;
    int id;
    String stream;
    String book1;
    String book2;
    int issuedBooks;

    Student(String name, int id, String stream) {
        this.name = name;
        this.id = id;
        this.stream = stream;
        this.book1 = "";
        this.book2 = "";
        this.issuedBooks = 0;
    }

    public String toString() {
        return "Name: " + name + ", ID: " + id + ", Stream: " + stream +
                ", Issued Books: " + issuedBooks +
                (book1.isEmpty() ? "" : ", Book 1: " + book1) +
                (book2.isEmpty() ? "" : ", Book 2: " + book2);
    }
}

public class library_management {

    static HashMap<String, Integer> bookQuantities = new HashMap<>(); 
    static HashMap<Integer, Student> students = new HashMap<>(); 
    static Scanner input = new Scanner(System.in);
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static final String studentDataFile = "students.txt"; 
    static final String bookDataFile = "books.txt"; 

    public static class Node {
        String key;
        Node left, right;

        public Node(String item) {
            key = item;
            left = null;
            right = null;
        }
    }

    static Node root;

    
    void insertBook(String key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, String key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key.compareTo(root.key) < 0) 
            root.left = insertRec(root.left, key);
        else if (key.compareTo(root.key) > 0) 
            root.right = insertRec(root.right, key);
        else
            System.out.println("Error: Duplicate book name.");

        return root;
    }

    
    void updateBook(String oldName, String newName, int quantity) {
        if (containsNode(oldName)) {
            deleteKey(oldName);
            insertBook(newName);
            updateQuantity(newName, quantity); 
        } else {
            System.out.println("Error: Book '" + oldName + "' not found for update.");
        }
    }

    
    void updateQuantity(String bookName, int quantity) {
        int index = bookQuantities.get(bookName);
        if (index >= 0) {
            int currentQuantity = getQuantity(bookName);
            int newQuantity = currentQuantity + quantity;
            bookQuantities.put(bookName, newQuantity);
        } else {
            System.out.println("Error: Book '" + bookName + "' not found for quantity update.");
        }
    }

    
    int getQuantity(String bookName) {
        int index = bookQuantities.get(bookName);
        if (index >= 0) {
            return bookQuantities.get(bookName);
        } else {
            System.out.println("Error: Book '" + bookName + "' not found for quantity retrieval.");
            return -1; 
        }
    }

    
    public boolean containsNode(String value) {
        return containsNodeRecursive(root, value)
    }