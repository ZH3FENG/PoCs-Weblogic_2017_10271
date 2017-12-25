package org.zh3feng.poc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: ZH3FENG
 * @Description:
 * @Date: 下午8:55 2017/11/15
 * @Modified By:
 */
@RestController
public class IndexController {

    @RequestMapping(value = "index" ,method = RequestMethod.POST)
    public String index(HttpServletRequest request) {

        try {
            InputStream ins = request.getInputStream();
            testXMLDecoder(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Hello";
    }


    /**
     * 简化版 CVE-2017-10271，漏洞部分细节看：
     *       http://www.oracle.com/technetwork/security-advisory/cpuoct2017-3236626.html
     *       https://erpscan.com/press-center/blog/analyzing-oracle-security-oracle-critical-patch-update-october-2017/
     *
     * 原理非常简单，XMLDecoder反序列化不可信输入。
     * 由于懒得下载安装weblogic，简单地使用springboot模拟一个接口
     *
     *
     * @see https://docs.oracle.com/javase/tutorial/javabeans/advanced/longpersistence.html
     *      http://blog.diniscruz.com/2013/08/using-xmldecoder-to-execute-server-side.html
     * @param ins
     *
     *
     *
     */
    private static void testXMLDecoder(InputStream ins){
        try{

            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(ins));

            decoder.readObject();
            decoder.close();
            ins.close();

        }catch(Exception e){
            //ignore
        }
    }
}
