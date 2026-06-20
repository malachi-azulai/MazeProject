package org.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class NetWorkManager {

  public String fetchRenderConfigJson() throws Exception{
     URL url = new URL("https://backend-qcf9.onrender.com/fm1/get-render-config");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          StringBuilder response = new StringBuilder();
          String inputLine;
          while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();
          return response.toString();
      }
      else {
          throw new Exception("Server returned HTTP response code: " + responseCode);
      }
  }
    public BufferedImage fetchMazeImage(int width, int height) throws Exception {
        // הוספת הפרמטרים לכתובת ה-URL כדי לקבל את התמונה המדויקת
        URL url = new URL("https://backend-qcf9.onrender.com/fm1/get-maze-image?width=" + width + "&height=" + height);
        BufferedImage image = ImageIO.read(url);
        if (image == null) {
            throw new Exception("Failed to download maze image from the server.");
        }
        return image;
    }







}
