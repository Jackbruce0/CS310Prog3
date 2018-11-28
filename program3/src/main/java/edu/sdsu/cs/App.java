/*
 * cssc0861
 * Project: program3
 * Created By: Jack Bruce & Jacob Romio
 * Date Created: 11/9/18
 * Date Last Edited: 11/9/18
 * Description: The driver program.
 */

package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {

    public static void main( String[] args ) {
        String fileName = "";
        DirectedGraph<String> graph = new DirectedGraph<>();

        /*
        1
        hard coded name when no argument, and argument .csv file when given argument
        this requires multiple runtime configs
        throw an exception if file can't open or it doesn't exist
        */
//        if (args.length > 0) {
//            inputFileName = args[0];
//        } else {
//            inputFileName="layout.csv";
//        }


        try {

            if (args.length > 0)
                fileName = args[0];
            else
                fileName = "./graphData/layout.csv";

            if(!fileName.endsWith(".csv")) {
                System.out.println("Error: " + fileName + " does not meet syntax requirements.");
                System.exit(0);
            }

            Scanner scanner = new Scanner(new File(fileName));

            graph = buildGraph(scanner);

            scanner.close();

        } catch (IOException e) {
            System.out.println("Error: Unable to open " + fileName + ". Verify the file exists and is accessible.");
            System.exit(0);
        }

        System.out.println(graph); //make a .toString() method
        //make an if statement checking the number of tokens per line

        /*
        3
        Reads in the input file and builds a graph matching the layout expectations.
        As it encounters a new vertex name, it shall instantiate the vertex and place it in the graph.
        New vertex names may appear as a single entry in a line,
        but they may also appear for the first time in a connection
         */

        /*
        4
        Displays Information about the Graph (e.g., vertices, connections, degree of each vertex)
        by printing it to the screen using the object’s toString() method.
         */

        /*
        5
        Allows the user to select the starting and ending vertices in the graph and computes the
        shortest path between them. The application shall Display both the distance for the route
        as well as the vertices or edges visited along the route.
         */

        /*
        6
        Terminates gracefully and without error
         */

    }

    private static DirectedGraph<String> buildGraph(Scanner csvReader) {
        DirectedGraph<String> graph = new DirectedGraph();

        while(csvReader.hasNext()) {
            String curLine = csvReader.nextLine();
            StringTokenizer toke = new StringTokenizer(curLine, ",");
            List<String> list = new LinkedList<>();

            while(toke.hasMoreTokens())
                list.add(toke.nextToken());

            if(list.size()==2) {
                if(!graph.contains(list.get(0)) || !graph.contains(list.get(1))) {
                    graph.add(list.get(0));
                    graph.add(list.get(1));
                }
                graph.connect(list.get(0), list.get(1));
            } else
                graph.add(list.get(0));

            //tests//
            System.out.println(list);
            System.out.println(list.size());
        }

        return graph;

    }

}