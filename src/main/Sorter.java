import java.io.*;
import java.util.*;

public class Sorter {
    public static void main(String[] args) throws IOException {

        // initialize unsorted and sorted lists
        ArrayList<String> names = readInput("../txt/SorterInput.txt");
        ArrayList<String> sortedNames = new ArrayList<String>();

        // add a singular element to the sorted list to start it
        sortedNames.add(names.remove(0));

        // sort the list
        for(String n : names) placeIntoLocation(n, sortedNames);

        // write sorted list
        writeFile(sortedNames, "../txt/SorterOutput.txt");

        // read csv
        TreeMap<String,Integer> dict = readCsv("../static_txt/TotalValues.csv");

        // update values with rating (higher is harder)
        for(int i = 0; i < sortedNames.size(); i++) {
            try {
                String key = sortedNames.get(i);
                dict.put(key, dict.get(key) + i);
            }
            catch(Exception e) {
                System.out.println("uh something went wrong with updating dict values???");
            }
        }

        // write csv
        writeCsv(dict, "../static_txt/TotalValues.csv");
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


    public static ArrayList<String> readInput(String filename) throws FileNotFoundException, IOException {

        // initialize arraylist and filereader
        ArrayList<String> names = new ArrayList<String>();
        BufferedReader r = new BufferedReader(new FileReader(filename));

        // add all lines to arraylist
        while(r.ready()) names.add(r.readLine());

        // randomize order
        for(int i = 0; i < names.size() / 2; i++) {
            int idx1 = (int) (Math.random() * names.size());
            int idx2 = (int) (Math.random() * names.size());
            String temp = names.get(idx1);
            names.set(idx1, names.get(idx2));
            names.set(idx2, temp);
        }

        // flush and return
        r.close();
        return names;
    }


    public static void writeFile(ArrayList<String> sortedNames, String filename) throws FileNotFoundException, IOException {

        // initialize filewriter
        PrintWriter pw = new PrintWriter(new FileWriter(filename));

        // write in reverse order (highest to lowest)
        for(int i = sortedNames.size() - 1; i >= 0; i--) pw.println(sortedNames.get(i));

        // flush and exit
        pw.close();
    }


    public static TreeMap<String,Integer> readCsv(String filename) throws FileNotFoundException, IOException {

        // initialize treemap and filereader
        TreeMap<String,Integer> dict = new TreeMap<String,Integer>();
        BufferedReader r = new BufferedReader(new FileReader(filename));

        // add all lines to treemap
        while(r.ready()) {
            String line = r.readLine();

            // split lines into key and value
            int idx = line.indexOf(",");
            String name = line.substring(0,idx);
            int rating = Integer.parseInt(line.substring(idx+1));

            // put key-val pair into treemap
            dict.put(name,rating);
        }

        // flush and return
        r.close();
        return dict;
    }


    public static void writeCsv(TreeMap<String,Integer> dict, String filename) throws FileNotFoundException, IOException {

        // initialize filewriter
        PrintWriter pw = new PrintWriter(new FileWriter(filename));

        // initialize comparator for treemap sorting
        Comparator<String> comp = new Comparator<String>() {
            public int compare(String o1, String o2) {
                return dict.get(o2).compareTo(dict.get(o1));
            }
        };

        // create new treemap using comparator and copy all values over
        TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(comp);
        sortedMap.putAll(dict);

        // write each value in the sorted treemap
        for(String s : sortedMap.keySet()) pw.println(s + "," + sortedMap.get(s));

        // flush and exit
        pw.close();
    }
}