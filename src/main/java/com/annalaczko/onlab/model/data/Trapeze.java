package com.annalaczko.onlab.model.data;

public class Trapeze extends Polygon {

    //Sarkok jobb fentről óramutató iránya szerint sorban

    public boolean isEnd = false;

    public double[] xpoints, ypoints;

    public Trapeze(Coordinate[] cs) {
        xpoints = new double[4];
        ypoints = new double[4];
        for (int i = 0; i < 4; i++) {
            xpoints[i] = cs[i].getX();
            ypoints[i] = cs[i].getY();
        }
    }


    public Trapeze(double[] _xpoints, double[] _ypoints) throws Exception {
        if (_xpoints.length != 4 || _ypoints.length != 4)
            throw new Exception("The number of coordinates doesn't equals to four");
        xpoints = _xpoints;
        ypoints = _ypoints;
    }

    private int getStartingCornerIndex() { //???
        int index = 0;
        for (int i = 1; i < 4; i++) {
            if (xpoints[index] <= xpoints[i] && ypoints[index] >= ypoints[i]) {
                index = i;
            }
        }
        return index;
    }

    public Coordinate getCornerForRobot(int cornerIndex) {
        //double a=(y1-y0),b=(x0-x1),c=x0*y1-x1*y0;

        int leftcorner, rightcorner;

        if (cornerIndex > 0 && cornerIndex < 3) {
            leftcorner = cornerIndex + 1;
            rightcorner = cornerIndex - 1;
        } else if (cornerIndex == 0) {
            leftcorner = 1;
            rightcorner = 3;
        } else {
            leftcorner = 0;
            rightcorner = 2;
        }

        double angle1 = Math.atan2(ypoints[cornerIndex] - ypoints[leftcorner], xpoints[cornerIndex] - xpoints[leftcorner]);
        double angle2 = Math.atan2(ypoints[cornerIndex] - ypoints[rightcorner], xpoints[cornerIndex] - xpoints[rightcorner]);
        double desiredAngle = (angle1 - angle2) / 2;
        double c = RobotModel.getRadius() / Math.sin(desiredAngle);

        double bisectorAngle = desiredAngle + angle2;

        double x = Math.cos(bisectorAngle) * c;
        double y = Math.sin(bisectorAngle) * c;

        //szögfelező


        return new Coordinate(x + xpoints[cornerIndex], y + ypoints[cornerIndex], null);
    }

    @Override
    public boolean contains(double x, double y) {

        super.xpoints = new int[4];
        super.ypoints = new int[4];

        for (int i = 0; i < 4; i++) {
            super.xpoints[i] = (int) xpoints[i];
            super.ypoints[i] = (int) ypoints[i];
        }

        return super.contains(x, y);
    }

    public void writeTrapeze() {
        for (int i = 0; i < 4; i++) {
            System.out.println(xpoints[i] + "-" + ypoints[i]);
        }
    }

    public double findCommonCoordinateY(Trapeze other) throws Exception {
        double t1c1, t1c2, t2c1, t2c2;

        for (int icorners = 0; icorners < 4; icorners++) {
            for (int jcorners = 0; jcorners < 4; jcorners++) {
                if (this.xpoints[icorners] == other.xpoints[jcorners]) {
                    if ((icorners == 0 || icorners == 3) && (jcorners == 1 || jcorners == 2)) {
                        t1c1 = this.ypoints[0];
                        t1c2 = this.ypoints[3];
                        t2c1 = other.ypoints[1];
                        t2c2 = other.ypoints[2];
                        double minY = Math.min(t1c2, t2c2);
                        double maxY = Math.max(t1c1, t2c1);

                        return (minY - maxY) / 2 + maxY;
                    }
                    if ((icorners == 1 || icorners == 2) && (jcorners == 3 || jcorners == 0)) {
                        t1c1 = this.ypoints[1];
                        t1c2 = this.ypoints[2];
                        t2c1 = other.ypoints[0];
                        t2c2 = other.ypoints[3];
                        double minY = Math.min(t1c2, t2c2);
                        double maxY = Math.max(t1c1, t2c1);

                        return (minY - maxY) / 2 + maxY;
                    }
                }
            }
        }
        throw new Exception("FUCK");

    }
}
