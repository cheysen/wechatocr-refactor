package wechatocr.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Author Cheysen
 * @Description 校验signature参数
 * 加密/校验流程如下:
 * 1.将token、timestamp、nonce三个参数进行字典序排序
 * 2.将三个参数字符串拼接成一个字符串进行sha1加密
 * 3.获得加密后的字符串与signature比较,标识该请求来源于微信
 * @Date 2019/7/24 11:36
 * @Version 1.0
 */
public class CheckSignatureUtils {
    private static final String TOKEN = "ocr2019";
    public static boolean check(String signature,String timestamp,String nonce) throws NoSuchAlgorithmException {
        String[] args = new String[]{TOKEN,timestamp,nonce};
        //将三个参数进行字典排序
        Arrays.sort(args);
        StringBuilder argStr = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            argStr.append(args[i]);
        }
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(argStr.toString().getBytes());
        String  tmpStr = byteToStr(digest);
        argStr = null;
        return  tmpStr.equals(signature.toUpperCase());
    }
    /**
     * @Description 将字节数组转换为十六进制字符串
     * @Date 2019/7/25 22:26
     * @param byteArray :
     * @return : java.lang.String
     */
    private static String byteToStr(byte[] byteArray){
        StringBuilder strDigest = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            strDigest.append(byteToHexStr(byteArray[i]));
        }
        return strDigest.toString();
    }
    /**
     * @Description 将字节转换为十六进制字符串
     * @Date 2019/7/25 22:25
     * @param b :
     * @return : java.lang.String
     */
    private static String byteToHexStr(byte b){
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = digit[(b >>> 4) &0X0F];
        tempArr[1] = digit[b & 0X0F];
        return new String(tempArr);
    }

}
