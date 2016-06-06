package de.vanfanel;


import de.vanfanel.exceptions.HTTPNotFoundException;
import static de.vanfanel.utils.BeadsColorUtils.getIntFromColor;
import static de.vanfanel.utils.BeadsColorUtils.getNearestColor;
import de.vanfanel.utils.HTTPUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
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
  public @ResponseBody ImageResponse process(@RequestBody ImageRequest request) throws HTTPNotFoundException
  {
    if(StringUtils.defaultIfEmpty(request.getLink(),"").isEmpty() || ! HTTPUtils.checkURLIsImage(request.getLink())){
      throw new HTTPNotFoundException();
    }

    try {
      BufferedImage img = ImageIO.read(new URL(request.getLink()));
      ImageResponse response = new ImageResponse();

      fillResponseWithImgData(response, img);


      System.out.println(response);
      return response;

    } catch (IOException e) {
      e.printStackTrace();
      throw new HTTPNotFoundException();
    }

  }

  private void fillResponseWithImgData(ImageResponse response, BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();

    response.setHeight(height);
    response.setWidth(width);

    System.out.println("height: "+height+"width" +width);

    int[] imagePixelValues = new int[width*height];
    BufferedImage base64Image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

    IntStream.range(0, height*width).parallel().forEach(i -> {
      int x = (int) Math.floor(i / height);
      int y = i % height;
      imagePixelValues[i] = getIntFromColor(getNearestColor(img.getRGB(x, y)));
      base64Image.setRGB(x,y,imagePixelValues[i]);
    });

    response.setPixelValues(imagePixelValues);

    response.setImgBase64(convertToBase64(base64Image));

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
