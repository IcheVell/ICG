package models.matrices;

import enums.MatrixType;

public class MatrixFactory {
    public static double[][] create(MatrixType type, double rotateRadiansAngle) {
        switch (type) {
            case ROTATE_X -> {
                return new double[][] {
                        {1, 0, 0},
                        {0, Math.cos(rotateRadiansAngle), -Math.sin(rotateRadiansAngle)},
                        {0, Math.sin(rotateRadiansAngle), Math.cos(rotateRadiansAngle)},
                };
            }

            case ROTATE_Y -> {
                return new double[][] {
                        {Math.cos(rotateRadiansAngle), 0, Math.sin(rotateRadiansAngle)},
                        {0, 1, 0},
                        {-Math.sin(rotateRadiansAngle), 0, Math.cos(rotateRadiansAngle)},
                };
            }

            case ROTATE_Z -> {
                return new double[][] {
                        {Math.cos(rotateRadiansAngle), -Math.sin(rotateRadiansAngle), 0},
                        {Math.sin(rotateRadiansAngle), Math.cos(rotateRadiansAngle), 0},
                        {0, 0, 1}
                };
            }
        }

        return null;
    }
}
