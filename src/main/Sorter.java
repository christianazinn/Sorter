import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sorter {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("../txt/SorterInput.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("../txt/SorterOutput.txt"));
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> sortedNames = new ArrayList<String>();
        while(r.ready()) names.add(r.readLine());
        r.close();
        sortedNames.add(names.remove(0));
        for(String n : names) placeIntoLocation(n, sortedNames);
        for(int i = sortedNames.size() - 1; i >= 0; i--) pw.println(sortedNames.get(i));
        pw.close();
    }

    public static void placeIntoLocation(String name, ArrayList<String> sortedNames) {
        List<String> sort = sortedNames.subList(0,sortedNames.size());
        int check = sort.size() / 2;
        int origPos = 0;
        Scanner s = new Scanner(System.in);
        while(true) {
            String compare = sort.get(check);
            System.out.println("Which is ranked higher: (1) " + name + " or (2) " + compare + "?");
            String input = s.nextLine();
            if(input.equals("1")) {
                sort = sort.subList(check + 1, sort.size());
                origPos += check + 1;
                check = sort.size() / 2;
            }
            else if(input.equals("2")) {
                sort = sort.subList(0,check);
                check = sort.size() / 2;
            }
            else System.out.println("That's invalid input!");

            if(sort.size() == 0) break;
        }
        sortedNames.add(origPos, name);
    }
}