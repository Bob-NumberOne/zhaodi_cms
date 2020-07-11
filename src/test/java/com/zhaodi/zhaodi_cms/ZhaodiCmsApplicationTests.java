package com.zhaodi.zhaodi_cms;
import com.alibaba.fastjson.JSONArray;
import com.zhaodi.util.ExcelTool;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ZhaodiCmsApplicationTests {
    public static void main(String[] args) {
       /* Map<String,String> m=new HashMap<>();
        m.put("uploadRoot","E:/");//服务器地址
        m.put("uploadSubDir","pic");//上传的目录
        m.put("isImage","1");//是否要保存图片
        m.put("imageFiled","img");//图片列名
        String filePath="C:/Users/jack/Desktop/专利文案/user.xls";
        File file= new File(filePath);
        //获取列名list(应该数据库取，暂时只能做实例)
        String[] cols=new String[]{"gkh","img_url","sqh","bt","dqsqzqr","fmr","sqny","fk","bz","ipcflh","locflh","zy","sqr","gkr","sqrr","yyzl","yxqgj","yxqh","yxqr","dqsq","dljg","dlr","qlyq","flztsj","flztgx","dqsqzlq","dlql","kztzyy","kztzcy","kztz","yssq","zllx","byyzl","slj","ipczflh","byyzls","yyzlsl","jdtzbyy","jdtzcy","jdtz"};
        List<String> list= Arrays.asList(cols);
        if (!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
            System.out.println("传入的文件不是excel");
        }
        //List<User> userList = ExcelTool.getBeanList(User.class, file);
        JSONArray arr = ExcelTool.readExcel(file,m,list);
        System.out.println("------- user.xls(Excel 2003)解析结果-----"+arr);
*//*        for (User user: userList) {
            System.out.println(user.toString());
        }*/
    }


}
