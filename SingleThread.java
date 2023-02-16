package org.ProgrammingTest;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SingleThread {
    public static boolean WriteLines(ArrayList<String> lines, String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Successful " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("Error.");
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> ReadLines(String fileName){
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            return lines;
        } catch (IOException e) {
            System.out.println("An error occurred while reading the input file.");
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public static void mergeSort(ArrayList<String> a1, ArrayList<String> a2,
                                 int left, int right,
                                 Comparator<String> cmp) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(a2, a1, left, middle, cmp);
            mergeSort(a2, a1, middle + 1, right, cmp);
            merge(a2, a1, left, middle, right, cmp);
        }
    }
    public static void mergeSort(ArrayList<String> lines,  Comparator<String> cmp) {
        mergeSort(lines, new ArrayList<String>(lines), 0, lines.size()-1, cmp);
    }

    public static void merge(ArrayList<String> a1, ArrayList<String> a2, int left, int middle, int right, Comparator<String> cmp) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Invariant L[i] = a1[left + i]
        // Invariant R[j] = a1[middle + 1 + j]
        int i = left;
        int j = middle + 1;
        int k = left;
        // Save t1 and t2 so that in the loop only one element
        // is read per iteration.

        // The original loop was reading three element every iteration
        String t1 = a1.get(i);
        String t2 = a1.get(j);
        for(;;) {
            if (cmp.compare(t1, t2) <= 0) {
                a2.set(k, t1);
                i++;
                k++;
                if(i <= middle){
                    t1 = a1.get(i);
                    continue;
                }else{
                    break;
                }
            } else {
                a2.set(k, t2);
                j++;
                k++;
                if(j <= right){
                    t2 = a1.get(j);
                    continue;
                }else{
                    break;
                }
            }
        }
        while (i <= middle) {
            a2.set(k, a1.get(i));
            i++;
            k++;
        }
        while (j <= right) {
            a2.set(k, a1.get(j));
            j++;
            k++;
        }
    }


    public static void main(String[] args) {
        String inputFile = "C:\\Users\\jaime\\IdeaProjects\\ProgrammingTest\\InputDocument.txt";  // name of the input file
        String ascendingOutput = "C:\\Users\\jaime\\IdeaProjects\\ProgrammingTest\\SingleAscending.txt";  // name of the output file
        String descendingOutput = "C:\\Users\\jaime\\IdeaProjects\\ProgrammingTest\\SingleDescending.txt";  // name of the output file
        String singleLastLetterOutput = "C:\\Users\\jaime\\IdeaProjects\\ProgrammingTest\\SingleLastLetter.txt";  // name of the output file

        // Reading the lines from input file and storing an Array
        ArrayList<String> lines = ReadLines(inputFile);

        // Sorting list in ascending order
        mergeSort(lines, Comparator.naturalOrder());
        WriteLines(lines, ascendingOutput);

        // Reversing the previous sorted array
        Collections.reverse(lines);
        WriteLines(lines, descendingOutput);

        // Sorting by the last letter
        mergeSort(lines, new Comparator<String>() {
            @Override
            public int compare(String a, String b){
                if(a == "")return -1; // corner case
                else if(b == "") return +1; // corner case
                return Character.compare(
                        a.charAt(a.length() - 1),
                        b.charAt(b.length() - 1)
                );
            }
        });
        WriteLines(lines, singleLastLetterOutput);
    }
}