package wechatocr.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * @Author Cheysen
 * @Description 内容处理工具类
 * @Date 2019/8/28 16:37
 * @Version 1.0
 */
public class ContentUtils {
    /**
     * @Description 长度超限,返回部分结果加完整版链接
     * @Date 2019/7/28 22:33
     * @param str :
     * @return : void
     */
    public static String subStringByByte(String str){
        int len = 0;
        //按UTF-8编码处理
        for (int i = 0; i < str.length(); i++) {
            len += str.charAt(i) > 255 ? 3 : 1;
            if(len > 1898){
                str = str.substring(0,i);
                return str;
            }
        }
        return null;
    }


    /**
     * @Description 创建txt文件，将文字内容写入该文件
     * @Date 2019/7/28 23:16
     * @param fileName : 文件名
     * @param content :  文件内容
     * @return : String : 文件路径
     */
    public static String creatTempFile(String fileName,String content) throws IOException {
        File temp = null;
        BufferedWriter bw = null;
        try {
            temp = new File("." + File.separator + fileName);
            temp.createNewFile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp),"gb2312"));
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
        return temp.getCanonicalPath();
    }

    /** 提取返回json字符串中的words值
     * @param jsonStr 百度返回的识别数据
     * @return 提取后的文字内容
     */
    public static String extract(String jsonStr) {
        int firstIndex = jsonStr.indexOf("{");
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject object = new JSONObject(jsonStr.substring(firstIndex));
        JSONArray jsonArray = object.getJSONArray("words_result");
        int total = jsonArray.length();
        for (int i = 0; i < total; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            stringBuilder.append(obj.getString("words"));
            if (i < total - 1) {
                stringBuilder.append("\n");
            }
        }
        if(0 != object.getInt("direction")){
            stringBuilder.append("\n\n")
                    .append("提示:为了更好的识别顺序,建议发送正向图片(图片中文字横向排版)");
        }
        return stringBuilder.toString();
    }
}
