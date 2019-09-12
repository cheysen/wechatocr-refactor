package wechatocr.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * @Author Cheysen
 * @Description URL工具类
 * @Date 2019/8/28 16:23
 * @Version 1.0
 */
public class MyUrlUtils {

    /** 图片URL转base64
     * @param url 图片URL
     * @return base64编码后的字符串
     */
    public static String base64(String url) {
        return inputStreamToBase64(url);
    }
    /**
     * @param desUrl :
     * @return : java.io.InputStream
     * 从URL中读取图片, 转换为流形式,再转为Base64
     *
     */
    private static String inputStreamToBase64(String desUrl) {
        URL url ;
        InputStream in = null ;
        HttpURLConnection urlConnection;
        try {
            url = new URL(desUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(4*1000);
            urlConnection.connect();
            in = urlConnection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len ;
            while((len = in.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }

            byte[] data = outputStream.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
