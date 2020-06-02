package ru.burlakov.dshkazan.utill;

public class DistanceCalculator {

    public static enum Unit {
        K,
        N
    }

    // Kilometers
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        return distance(lat1, lon1, lat2, lon2, Unit.K);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, Unit unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == Unit.K) {
                dist = dist * 1.609344;
            } else if (unit == Unit.N) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

}
