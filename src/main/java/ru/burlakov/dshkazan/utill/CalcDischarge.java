package ru.burlakov.dshkazan.utill;

import ru.burlakov.dshkazan.dto.IndustryDTO;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;

public class CalcDischarge {

    private final static Double P = 0.25;

    private final static Double A = 33.7598;
    private final static Double B = 0.699036;
    private final static Double C = 8.3330;
    private final static Double D = 0.72382;

    private final static Double G = 9.8;

    public static Double calc(MetricParameterDTO metric, IndustryDTO industry, Integer count) {
        Double total;

        Double windSpeed = metric.getWindSpeed();
        Double windSpeedHeigth = 10D;

        Double y = getY(metric.getCoord(), industry.getCoord(), metric.getWindDeg());
        Double x = getX(metric.getCoord(), industry.getCoord(), y);

        Double Fb = getFb(industry.getOutSpeed(), industry.getOutDiameter(), industry.getOutTemperature(), metric.getAirTemperature());

        Double Xf = getXf(Fb);

        Double Us = getUs(industry.getHeight(), windSpeed, windSpeedHeigth);

        Double He = getHe(x, industry.getHeight(), Fb, Xf, Us);

        Double sigmY = getSigmY(x);
        Double sigmZ = getSigmZ(x);

        Double Q = getQ(20L * 60L, industry.getAvgDisch());
        Double K = getK();
        Double V = getV(industry.getHeight(), He, sigmZ, windSpeed, 1);

        total = Q * K * V;

        total /= 2 * Math.PI * Us * sigmY * sigmZ;

        total *= Math.exp(-0.5 * Math.pow(y, 2) / Math.pow(sigmY, 2));

//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(total);

        return total;
    }

    public static Double getX(Double[] metricCoord, Double[] industryCoord, Double h) {
        Double lengthA = DistanceCalculator.distance(metricCoord[0], metricCoord[1], industryCoord[0], industryCoord[1]) * 1000;
        return Math.sqrt( Math.abs(Math.pow(lengthA, 2) - Math.pow(h, 2)) );
    }

    public static Double getY(Double[] metricCoord, Double[] industryCoord, Double windDeg) {
//        Double windDeg_f = windDeg < 0 ? 180 - windDeg : windDeg;
//
//        Double[] genVector = new Double[]{industryCoord[0] - metricCoord[0], industryCoord[1] - metricCoord[1]};
//        Double[] baseVector = new Double[]{industryCoord[0] - metricCoord[0], 0D};
//
//        Double baseDeg = getVectorsDeg(genVector[0], genVector[1], baseVector[0], baseVector[1]);
//
//        Double rotateDeg = 180D;
//
//        Double[] rotatedPoint = getRotatedPoint(industryCoord, rotateDeg);
//
//        Double lengthA = DistanceCalculator.distance(metricCoord[0], metricCoord[1], industryCoord[0], industryCoord[1]);
//        Double lengthB = DistanceCalculator.distance(rotatedPoint[0], rotatedPoint[1], industryCoord[0], industryCoord[1]);
//
//        System.out.println(industryCoord[0] + "," + industryCoord[1]);
//        System.out.println(rotatedPoint[0] + "," + rotatedPoint[1]);
//
//        Double S = lengthA * lengthB / 2;
//
//        return S / lengthA / 2;

        Double lengthA = DistanceCalculator.distance(metricCoord[0], metricCoord[1], industryCoord[0], industryCoord[1]) * 1000;

        Double[] newPoint = movePointTo(industryCoord, windDeg, getVectorLength(metricCoord, industryCoord));

        Double lengthB = DistanceCalculator.distance(metricCoord[0], metricCoord[1], newPoint[0], newPoint[1]) * 1000;

        Double total = Math.sqrt(Math.pow(Math.max(lengthA, lengthB), 2) - Math.pow(Math.min(lengthA, lengthB), 2));

//        System.out.println(newPoint[0] + "," + newPoint[1]);
//        System.out.println(lengthA);
//        System.out.println(lengthB);
//        System.out.println(total);

        return total;

    }

    public static Double[] movePointTo(Double[] point, Double windDeg, Double distance) {
//        где x1,y1 - координаты точки A
//        a - угол в градусах
//        d - расстояние
//
//        где B = (x1+d cos a, y1+d sin a)
        Double x = point[0] + distance * Math.cos(windDeg);
        Double y = point[1] + distance * Math.sin(windDeg);


        return new Double[]{x,y};
    }

    public static Double[] getRotatedPoint(Double[] point, Double deg) {
        Double[] rotatedPoint = new Double[2];
        Double rotateDeg = deg / (180 * 100 * 2.8);
        Double param = 1.0011;

        if(rotateDeg < 0) {
            rotatedPoint[0] = point[0] * Math.cos(rotateDeg) - point[1] * Math.sin(rotateDeg);
            rotatedPoint[1] = point[0] * Math.sin(rotateDeg) + point[1] * Math.cos(rotateDeg);
        } else {
            rotatedPoint[0] = point[0] * Math.cos(rotateDeg) + point[1] * Math.sin(rotateDeg);
            rotatedPoint[1] = -point[0] * Math.sin(rotateDeg) + point[1] * Math.cos(rotateDeg);
        }

        rotatedPoint[0] = rotatedPoint[0] * param;
        rotatedPoint[1] = rotatedPoint[1] * param;

        return rotatedPoint;
    }

    public static Double getVectorsDeg(Double x1, Double y1, Double x2, Double y2) {
        Double module1 = getVectorModule(x1,y1);
        Double module2 = getVectorModule(x2,y2);

        Double scalar = getScalarVoctors(x1, y1, x2, y2);

        return scalar / (module1 * module2);
    }

    public static Double getScalarVoctors(Double x1, Double y1, Double x2, Double y2) {
        return x1 * x2 + y1 * y2;
    }

    public static Double getVectorModule(Double x, Double y) {
        return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
    }

    public static Double getVectorLength(Double[] pointA, Double[] pointB) {
        Double[] vector = getVectorCoord(pointA, pointB);
        return getVectorModule(vector[0], vector[1]);
    }

    public static Double[] getVectorCoord(Double[] a, Double[] b) {
        return new Double[]{b[0] - a[0], b[1] - a[1]};
    }

    public static Double getQ(Long seconds, Double avgDisch) {
        return seconds * avgDisch;
    }

    public static Double getK() {
        return 1 * Math.pow(10, 6);
    }

    public static Double getV(Double z, Double He, Double sigmZ, Double windSpeed, Integer count) {
        Double L = 320 * windSpeed;
        Double H1 = z - (2 * count * windSpeed - He);
        Double H2 = z + (2 * count * windSpeed - He);
        Double H3 = z - (2 * count * windSpeed + He);
        Double H4 = z + (2 * count * windSpeed + He);

        Double total = Math.exp(-0.5 * Math.pow(z - He, 2) / Math.pow(sigmZ, 2));
//        total += Math.exp(-0.5 * Math.pow(z + He, 2) / Math.pow(sigmZ, 2));
        return total;
    }

    public static Double getUs(Double Hs, Double Uref, Double Zref) {
        return Uref * Math.pow(Hs / Zref, P);
    }

    public static Double getSigmY(Double x) {
        return 456.11628 * x * Math.tan(0.017453293 * Math.abs(C - D * Math.log10(x)));
    }

    public static Double getSigmZ(Double x) {
        return A * Math.pow(x, B);
    }

    public static Double getFb(Double outSpeed, Double outDiameter, Double outTemperature, Double streetTemperature) {
        Double total = G * outSpeed * Math.pow(outDiameter, 2);
        total *= (outTemperature - streetTemperature) / (4 * outTemperature);
        return total;
    }

    public static Double getXf(Double Fb) {
        if(Fb < 55)
            return 49 * Math.pow(Fb, (5/8));
        else
            return 119 * Math.pow(Fb, (2/5));
    }

    public static Double getHe(Double x, Double Hs, Double Fb, Double Xf, Double Us) {
        Double total = Hs;
        if (x < Xf) {
            total += 1.6 * Math.pow(Fb, (1/3)) * Math.pow(Xf, (1/3)) / Us;
        } else {
            total += 1.6 * Math.pow(Fb, (1/3)) * Math.pow(x, (1/3)) / Us;
        }
        return total;
    }

}
