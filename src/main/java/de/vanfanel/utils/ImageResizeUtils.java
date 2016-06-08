package de.vanfanel.utils;

import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.makers.FixedSizeThumbnailMaker;
import net.coobird.thumbnailator.resizers.DefaultResizerFactory;
import net.coobird.thumbnailator.resizers.Resizer;

public class ImageResizeUtils {

  public static BufferedImage resizeImageWithThumbnailMaker(BufferedImage originalImage, int targetWidth, int targetHeight) {

    Resizer resizer = DefaultResizerFactory.getInstance().getResizer(new java.awt.Dimension(originalImage.getWidth(), originalImage.getHeight()), new java.awt.Dimension(targetWidth, targetHeight));
    return new FixedSizeThumbnailMaker(targetWidth, targetHeight, false, true).resizer(resizer).make(originalImage);
  }

}
