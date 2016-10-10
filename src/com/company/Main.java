package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Integer islandCount = 0;
        islandCount = scanner.nextInt();
        scanner.nextLine();

        List<Island> islandList = new ArrayList<>();
        for (int i = 0; i < islandCount; i++) {
            String line = "";
            List<String> lineComponents;

            line = scanner.nextLine();
            lineComponents = new ArrayList<>(Arrays.asList(line.split(" ")));
            if (lineComponents.size() != 2) {
                System.out.println("incorrect input");
                System.exit(1);
            }
            Integer N = Integer.parseInt(lineComponents.get(0));
            Integer M = Integer.parseInt(lineComponents.get(1));
            List<List<Integer>> source = new ArrayList<>();

            for (int j = 0; j < N; j++) {
                line = scanner.nextLine();
                lineComponents = new ArrayList<>(Arrays.asList(line.split(" ")));
                if (lineComponents.size() != M) {
                    System.out.println("incorrect input");
                    System.exit(1);
                }
                List<Integer> sourceLine = new ArrayList<>();
                for (int k = 0; k < M; k++) {
                    sourceLine.add(Integer.parseInt(lineComponents.get(k)));
                }
                source.add(sourceLine);
            }

            islandList.add(new Island(source, N, M));
        }

        for (int i = 0; i < islandList.size(); i++) {
            System.out.println(islandList.get(i).calculate());
        }

        /*
        source = new ArrayList<>();
        source.add(Arrays.asList(new Integer[] {5, 8, 7, 6, 4, 3}));
        source.add(Arrays.asList(new Integer[] {6, 4, 3, 3, 3, 6}));
        source.add(Arrays.asList(new Integer[] {5, 4, 1, 3, 7, 7}));
        source.add(Arrays.asList(new Integer[] {3, 7, 3, 3, 3, 8}));
        source.add(Arrays.asList(new Integer[] {1, 1, 3, 3, 3, 8}));
        source.add(Arrays.asList(new Integer[] {9, 3, 8, 7, 5, 6}));
        N = 6;
        M = 6;
        */

    }
}
