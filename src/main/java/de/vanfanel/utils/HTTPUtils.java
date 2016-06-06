package de.vanfanel.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;


public class HTTPUtils {


  public static final List<String> VALID_IMAGE_HEADERS = Arrays.asList("image/gif", "image/jpeg", "image/jpg", "image/png");

  private static HttpURLConnection getConnection(String url)
  {
    try {
      if(url.toLowerCase().startsWith("https"))
      {
        HttpsURLConnection.setFollowRedirects(false);
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
        con.setRequestMethod("HEAD");
        return con;
      }
      else {
          HttpURLConnection.setFollowRedirects(false);
          HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
          con.setRequestMethod("HEAD");
          return con;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean checkURLexists(String url) {
    try {
      HttpURLConnection con = getConnection(url);
      return con != null && con.getResponseCode() == HttpURLConnection.HTTP_OK;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean checkURLIsImage(String url){
    try {
      HttpURLConnection con = getConnection(url);
      return con != null && con.getResponseCode() == HttpURLConnection.HTTP_OK &&
          isContentTypeOfConnectionAnImage(con) ;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  protected static boolean isContentTypeOfConnectionAnImage(URLConnection con)
  {
    String contentType = con.getHeaderField("Content-Type").toLowerCase();
    if(contentType.indexOf(";") > 0) {
      contentType = contentType.substring(0, contentType.indexOf(";"));
    }
    return VALID_IMAGE_HEADERS.contains(contentType);
  }


}
