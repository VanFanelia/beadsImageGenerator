package de.vanfanel;


import de.vanfanel.exceptions.HTTPNotFoundException;
import de.vanfanel.request.ImageRequest;
import de.vanfanel.response.ImageData;
import de.vanfanel.response.ImageResponse;
import de.vanfanel.utils.BeadsColorUtils;
import static de.vanfanel.utils.BeadsColorUtils.getIntFromColor;
import static de.vanfanel.utils.BeadsColorUtils.getNearestColor;
import de.vanfanel.utils.Dimension;
import de.vanfanel.utils.HTTPUtils;
import de.vanfanel.utils.ImageResizeUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/process")
public class ImageController {

  @RequestMapping(value = "", method = RequestMethod.POST)
  public @ResponseBody
  ImageResponse process(@RequestBody ImageRequest request) throws HTTPNotFoundException
  {
    if(StringUtils.defaultIfEmpty(request.getLink(),"").isEmpty() || ! HTTPUtils.checkURLIsImage(request.getLink())){
      throw new HTTPNotFoundException();
    }

    try {
      BufferedImage img = ImageIO.read(new URL(request.getLink()));
      ImageResponse response = new ImageResponse();

      List<Dimension> dimensionList = BeadsColorUtils.calculateDimensionToScale(new Dimension(img.getWidth(), img.getHeight()));

      dimensionList.parallelStream().map(
          d -> ImageResizeUtils.resizeImageWithThumbnailMaker(img,d.getWidth(),d.getHeight())
      ).forEachOrdered(
          i -> response.getImages().add(getImageData(i))
      );

      return response;

    } catch (IOException e) {
      e.printStackTrace();
      throw new HTTPNotFoundException();
    }

  }

  private ImageData getImageData(BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();

    ImageData result = new ImageData();
    result.setHeight(height);
    result.setWidth(width);

    System.out.println("height: "+height+"width" +width);

    int[] imagePixelValues = new int[width*height];
    BufferedImage base64Image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

    IntStream.range(0, height*width).parallel().forEach(i -> {
      int x = (int) Math.floor(i / height);
      int y = i % height;
      imagePixelValues[i] = getIntFromColor(getNearestColor(img.getRGB(x, y)));
      base64Image.setRGB(x,y,imagePixelValues[i]);
    });

    result.setPixelValues(imagePixelValues);

    result.setImgBase64(convertToBase64(base64Image));

    return result;
  }

  public static String convertToBase64(BufferedImage img){
    String imageString = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try {
      ImageIO.write(img, "PNG", bos);
      byte[] imageBytes = bos.toByteArray();

      BASE64Encoder encoder = new BASE64Encoder();
      imageString = encoder.encode(imageBytes);

      bos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imageString;

  }

}
