import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sorter {
    public static void main(String[] args) throws IOException {

        // get i/o files
        BufferedReader r = new BufferedReader(new FileReader("../txt/SorterInput.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("../txt/SorterOutput.txt"));

        // initialize unsorted and sorted lists
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> sortedNames = new ArrayList<String>();

        // read into unsorted list from input file and close input
        while(r.ready()) names.add(r.readLine());
        r.close();

        // add a singular element to the sorted list to start it
        sortedNames.add(names.remove(0));

        // sort the list
        for(String n : names) placeIntoLocation(n, sortedNames);

        // write sorted list to output file and close output
        for(int i = sortedNames.size() - 1; i >= 0; i--) pw.println(sortedNames.get(i));
        pw.close();
    }


    public static void placeIntoLocation(String name, ArrayList<String> sortedNames) {

        // copy sorted list
        List<String> sort = sortedNames.subList(0,sortedNames.size());
        // position in the sorted list to be checked against (binary search)
        int check = sort.size() / 2;
        // relative position in the original sorted list
        int origPos = 0;
        // input
        Scanner s = new Scanner(System.in);

        while(true) {
            
            // ask user for comparison and obtain input (random order)
            int order = (int) (Math.random() * 2);
            if(order == 0) System.out.println("Which is ranked higher: (1) " + name + " or (2) " + sort.get(check) + "?");
            else System.out.println("Which is ranked higher: (1) " + sort.get(check) + " or (2) " + name + "?");
            String input = s.nextLine();

            // case 1
            if((input.equals("1") && order == 0) || (input.equals("2") && order == 1)) {

                // truncate list before and including check
                sort = sort.subList(check + 1, sort.size());
                // update relative position
                origPos += check + 1;
                // split the list in half (binary search)
                check = sort.size() / 2;
            }

            // case 2
            else if((input.equals("2") && order == 0) || (input.equals("1") && order == 1)) {

                // truncate list after and including check
                sort = sort.subList(0,check);
                // split the list in half (binary search)
                check = sort.size() / 2;
            }

            // invalid input handling (repeat)
            else System.out.println("That's invalid input!");

            // end condition: everything has been checked and position is known
            if(sort.size() == 0) break;
        }

        // add new element at the relative position
        sortedNames.add(origPos, name);
    }
}