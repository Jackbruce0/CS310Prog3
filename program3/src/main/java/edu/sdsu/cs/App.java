/*
 * cssc0861
 * Project: program3
 * Created By: Jack Bruce & Jacob Romio
 * Date Created: 11/9/18
 * Date Last Edited: 11/27/18
 * Description: The driver program.
 */

package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {

    public static void main( String[] args ) {
        String fileName = "";
        DirectedGraph<String> graph = new DirectedGraph<>();

        //WHAT IS THE DEFAULT FILE PATH IF WE ONLY SEND .src

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

        System.out.println(graph);

        Scanner userIn = new Scanner(System.in);
        System.out.print("Find the shortest path!\nOrigin: ");

        String origin = userIn.nextLine();
        while (!graph.contains(origin)) {
            System.out.print("Graph does not contain " + origin + ".\nOrigin: ");
            origin = userIn.nextLine();
        }
        System.out.print("Destination: ");
        String destination = userIn.nextLine();
        while (!graph.contains(destination)) {
            System.out.print("Graph does not contain " + destination + ".\nDestination:");
            destination = userIn.nextLine();
        }

        List<String> shortestPath = graph.shortestPath(origin, destination);
        if (shortestPath.isEmpty())
            System.out.println("There is no path from " + origin + " to " + destination);
        else {
            String pathOutput = "";
            for (String vertex: shortestPath) {
                if (vertex.equals(destination))
                    pathOutput += vertex;
                else
                    pathOutput += vertex + " >> ";
            }
            System.out.print("The shortest path from " + origin + " to " + destination + " is: " + pathOutput +
                    "\nDistance: " + (shortestPath.size() - 1));
        }

        System.exit(0);

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
        }
        return graph;
    }

}