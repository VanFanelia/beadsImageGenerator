package de.vanfanel.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.Test;

public class HTTPUtilsTest {

  @Test
  public void testIsContentTypeOfConnectionAnImageFalseOnApplicationJSONType() throws Exception
  {
    HttpUrlConnectionImplementation con = new HttpUrlConnectionImplementation(new URL("http://localhost/"));
    con.setHeaderResponse("application/json");

    assertThat(HTTPUtils.isContentTypeOfConnectionAnImage(con), Matchers.is(false));
  }

  @Test
  public void testIsContentTypeOfConnectionAnImageTrueOnTypeImagePng() throws Exception
  {
    HttpUrlConnectionImplementation con = new HttpUrlConnectionImplementation(new URL("http://localhost/"));
    con.setHeaderResponse("image/png");

    assertThat(HTTPUtils.isContentTypeOfConnectionAnImage(con), Matchers.is(true));
  }


  @Test
  public void testIsContentTypeOfConnectionAnImageTrueOnTypeImageGifWithCharsetInfo() throws Exception
  {
    HttpUrlConnectionImplementation con = new HttpUrlConnectionImplementation(new URL("http://localhost/"));
    con.setHeaderResponse("image/gif;charset=UTF-8");

    assertThat(HTTPUtils.isContentTypeOfConnectionAnImage(con), Matchers.is(true));
  }

  class HttpUrlConnectionImplementation extends HttpURLConnection{

    private String headerResponse = "";

    /**
     * Constructor for the HttpURLConnection.
     *
     * @param u the URL
     */
    protected HttpUrlConnectionImplementation(URL u) {
      super(u);
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean usingProxy() {
      return false;
    }

    @Override
    public void connect() throws IOException {}

    @Override
    public String getHeaderField(String field)
    {
      return this.headerResponse;
    }

    public void setHeaderResponse(String headerResponse) {
      this.headerResponse = headerResponse;
    }
  }
}