import java.util.*;

class Result {

    public static void findPoints(List<Point> pointList) {
        List<Point> vertices =  new ArrayList<>();
        vertices.add(pointList.get(0));

        int currIndex = 0;
        double relAngle = 0.0;
        do {
            List<Double> angles = getAngles(currIndex, pointList, relAngle);
            double min = Collections.min(angles);
            int minIndex = angles.indexOf(min);
            vertices.add(pointList.get(minIndex));
            currIndex = minIndex;
            if (angles.get(currIndex) > Math.PI * 2)
                relAngle += angles.get(currIndex) - Math.PI * 2;
            else
                relAngle += angles.get(currIndex);
        } while (vertices.get(0) != vertices.get(vertices.size() - 1));
        vertices.forEach(System.err::println);
    }

    public static List<Double> getAngles(int startIndex, List<Point> pointList, double relAngle) {
        List<Double> output = new ArrayList<>();
        Point startPoint = pointList.get(startIndex);
        for (int i = 0; i < pointList.size(); i++) {
            if (i == startIndex)
                output.add(4 * Math.PI);
            Point newPoint = pointList.get(i);
            int x2 = newPoint.x;
            int y2 = newPoint.y;
            int x1 = startPoint.x;
            int y1 = startPoint.y;
            double angle = Math.atan((double) (y2 - y1) / (double) (x2 - x1));
            if (angle < relAngle)
                angle += Math.PI * 2;
            output.add(angle);
        }
        return output;
    }

    public static void main(String[] args) {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(0, 1, 10));
        pointList.add(new Point(1, 2, 8));
        pointList.add(new Point(2, 4, 1));
        pointList.add(new Point(3, 6, 9));
        pointList.add(new Point(4, 5, 5));

        pointList.sort(new XPointComparator());
        pointList.forEach(System.err::println);

        findPoints(pointList);

    }
}

class Point {
    public int index;
    public int x;
    public int y;
    public Point(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "Point{" +
                "index=" + index +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

class XPointComparator implements Comparator<Point> {
    @Override
    public int compare(Point o1, Point o2) {
        return o1.x - o2.x;
    }
}
