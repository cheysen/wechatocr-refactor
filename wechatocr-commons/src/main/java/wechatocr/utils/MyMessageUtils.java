package wechatocr.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechatocr.message.response.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author Cheysen
 * @Description 解析XML工具类
 * @Date 2019/7/24 14:33
 * @Version 1.0
 */
public class MessageUtils {
    private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);
    /**
     * 扩展XStream,使其支持CDATA块
     */
    private static XStream xStream = new XStream(new XppDriver(){
        @Override
        public HierarchicalStreamWriter createWriter(Writer out){
            return new PrettyPrintWriter(out){
                //对所有XML节点的转换都增加CDATA标记
                boolean cData = true;
                @Override
                protected void writeText(QuickWriter writer, String text){
                    if(cData){
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else {
                        writer.write(text);
                    }
                }
            };
        }
    });
    /**
     * @Description 解析微信发来的XML请求
     * @Date 2019/7/24 14:45
     * @param request :
     * @return : java.util.Map<java.lang.String,java.lang.String>
     */
    public static Map<String, String> praseXML(HttpServletRequest request)  {
        Map<String, String> map = new HashMap<>(16);
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        } catch (IOException i) {
            logger.error("获取XML消息出错");
        } catch (DocumentException d) {
            logger.error("解析XML文档出错");
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            }catch (IOException e) {
                logger.error("关闭输入流出错");
            }
        }

        return map;
    }
   /**
    * @Description 响应文本消息对象转换成XML
    * @Date 2019/7/24 14:49
    * @param textMessage :
    * @return : java.lang.String
    */
    public static String textMessageToXML(TextMessage textMessage){
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }

}
