import java.util.*;

class Intern_2 {
    public static List<Integer> bfsMazeSolver(Map<Integer, List<Integer>> mazeGraph, int startNode, int endNode) {
        
        if (startNode == endNode) {
            return Collections.singletonList(startNode);
        }

   
        Queue<Integer> queue = new LinkedList<>();
      
        Set<Integer> visited = new HashSet<>();
      
        Map<Integer, Integer> parent = new HashMap<>();

        queue.add(startNode);
        visited.add(startNode);
        parent.put(startNode, null);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            List<Integer> neighbors = mazeGraph.get(currentNode);

            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        parent.put(neighbor, currentNode);

                        // If we reach the end node, reconstruct the path
                        if (neighbor == endNode) {
                            return constructPath(parent, endNode);
                        }
                    }
                }
            }
        }
       
        return new ArrayList<>();
    }

    private static List<Integer> constructPath(Map<Integer, Integer> parent, int endNode) {
        List<Integer> path = new ArrayList<>();
        for (Integer at = endNode; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
       
        Map<Integer, List<Integer>> mazeGraph = new HashMap<>();
        mazeGraph.put(1, Arrays.asList(2));
        mazeGraph.put(2, Arrays.asList(1, 3, 4));
        mazeGraph.put(3, Arrays.asList(2, 7));
        mazeGraph.put(4, Arrays.asList(2, 5, 6));
        mazeGraph.put(5, Arrays.asList(4, 9));
        mazeGraph.put(6, Arrays.asList(4, 7));
        mazeGraph.put(7, Arrays.asList(3, 6, 8));
        mazeGraph.put(8, Arrays.asList(7, 9));
        mazeGraph.put(9, Arrays.asList(5, 8, 10));
        mazeGraph.put(10, Arrays.asList(9));

        int startNode = 1;
        int endNode = 10;

        List<Integer> path = bfsMazeSolver(mazeGraph, startNode, endNode);

        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path found: " + path);
        }
    }
}
