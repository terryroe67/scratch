import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result {

    public static class Point {
        public int index;
        public int x;
        public int y;
        public Point(int index, int x, int y) {
            p.index = index; p.x = x; p.y = y; return p; }
    }

    public static List<Integer> find_points(List<List<Integer>> points) {

        Set<Point> pointSet = new HashSet<>();
        for (int i = 0; i < points.size(); ++i) {
            Point point = new Point(i, points.get(i).get(0), points.get(i).get(1));
            pointSet.add(point);
        }

        Collections.sort(points, new Comparator<List<Integer>>() {
            public int compare(List<Integer> a, List<Integer> b) {
                return Integer.valueOf(a.get(0)) - Integer.valueOf(b.get(0));
            }
        });
        List<Integer> startingPoint = points.get(0);
        List<List<Integer>> verticies = new ArrayList<List<Integer>>();
        verticies.add(startingPoint);
        int currIndex = 0;
        double relAngle = 0.0;
        do {
            List<Double> angles = getAngles(currIndex, points, relAngle);
            double min = Collections.min(angles);
            int minIndex = angles.indexOf(min);
            verticies.add(points.get(minIndex));
            currIndex = minIndex;
            if (angles.get(currIndex) > Math.PI * 2)
                relAngle += angles.get(currIndex) - Math.PI * 2;
            else
                relAngle += angles.get(currIndex);
        } while (verticies.get(0) != verticies.get(verticies.size()-1));



        return null;
    }

    public static List<Double> getAngles(int startIndex, List<List<Integer>> points, double relAngle){
        List<Double> output = new ArrayList<Double>();
        List<Integer> startPoint = points.get(startIndex);
        for (int i = 0; i < points.size(); i++) {
            if (i == startIndex)
                output.add(4 * Math.PI);
            List<Integer> newPoint = points.get(i);
            int x2 = newPoint.get(0);
            int y2 = newPoint.get(1);
            int x1 = startPoint.get(0);
            int y1 = startPoint.get(1);
            double angle = Math.atan((double)(y2-y1)/(double)(x2-x1));
            if (angle < relAngle)
                angle += Math.PI * 2;
            output.add(angle);
        }
        return output;
    }

    public static void main(String[] args) {
        Integer[] point0 = { 1, 10 };
        Integer[] point1 = { 2, 8 };
        Integer[] point2 = { 4, 1 };
        Integer[] point3 = { 6, 9 };
        Integer[] point4 = { 5, 5 };
        List<List<Integer>> input = new ArrayList<>();
        input.add(Arrays.asList(point0));
        input.add(Arrays.asList(point1));
        input.add(Arrays.asList(point2));
        input.add(Arrays.asList(point3));
        input.add(Arrays.asList(point4));
        List<Integer> points = find_points(input);
        for (Integer i : points) {
            System.err.println(i);
        }
    }
}
