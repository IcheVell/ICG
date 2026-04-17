package dto;

import java.awt.*;
import java.util.List;

public record ModelData(double scale, double ZN, int N, int K, int M, int M1, double rotateX, double rotateY, List<Point> controlPoints) {
}
