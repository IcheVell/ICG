package model.filter.interfaces;

import java.awt.image.BufferedImage;

public interface Filter {
    BufferedImage applyFilter(BufferedImage image);
}
