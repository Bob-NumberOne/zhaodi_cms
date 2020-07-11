package com.zhaodi.util;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {

    /**
     * 	@Description: 获取excel的文档对象
     * 	@author WangBo
     *	@DateTime 2019年3月5日 上午11:41:27
     *	@param sheetName:表名
     *	@param title:列标题(创建格式如：String[] title = {"书名","作者","出版社","类型","数量"};)
     *	@param data:表里面的数据
     *  @param bean:字段名称
     *  @param styleArr:单元格长度(标准为5000)
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String [] title,List<Map<String ,Object>> data,String [] bean, int [] styleArr){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格,赋予样式
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style1=getStyle(style, sheet, wb,0);
        HSSFCellStyle style2=getStyle(style, sheet, wb,1);


        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            sheet.setColumnWidth(i, styleArr[i]);
            cell.setCellStyle(style1);
        }

        Map<String ,Object> dataMap;
        String b;
        //创建内容
        for(int i = 0;i<data.size();i++) {
            row = sheet.createRow(i + 1);
            dataMap = data.get(i);
            for(int j=0;j<bean.length;j++){
                b = bean[j];
                HSSFCell cell_index=row.createCell(j);
                cell_index.setCellStyle(style2);
//                将内容按顺序赋给对应的列对象

                Object value = dataMap.get(b);

                if (value instanceof Integer) {
                    int intValue = (Integer) value;
                    cell_index.setCellValue(intValue);
                } else if (value instanceof Float) {
                    float fValue = (Float) value;
                    cell_index.setCellValue(fValue);
                } else if (value instanceof Double) {
                    double dValue = (Double) value;
                    cell_index.setCellValue(dValue);
                } else if (value instanceof Long) {
                    long longValue = (Long) value;
                    cell_index.setCellValue(longValue);
                } else if (value == null) {
                    cell_index.setCellValue("");
                } else {
                    // 其它数据类型都当作字符串简单处理
                    String str = (String)value;
                    cell_index.setCellValue(str);
                }
            }
        }
        return wb;

    }

    /**
     * 	@Description: 获取样式
     * 	@author WangBo
     *	@DateTime 2019年3月5日 上午11:41:27
     */
    public static HSSFCellStyle getStyle(HSSFCellStyle style,HSSFSheet sheet,HSSFWorkbook wb,int fontSize){
        HSSFFont font = wb.createFont();
        sheet.setDefaultColumnWidth(18);
        /*style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);*/
        style.setBorderTop(CellStyle.BORDER_THIN);//3.9版本
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        font.setFontName("宋体");
        if(fontSize==0){
            font.setFontHeightInPoints((short) 12);
            //font.setBold(true);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        }else if(fontSize==1){
            font.setFontHeightInPoints((short) 11);
        }
        //style.setAlignment(HorizontalAlignment.CENTER);
        //style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//3.9版本
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        // 自动换行
        style.setWrapText(true);
        return style;
    }


    /**
     * 	@Description:  对excel的文档对象以流方式发送
     * 	@author WangBo
     *	@DateTime 2019年3月5日 上午11:41:27
     *	@param localHttpServletResponse:HttpServletResponse对象
     *	@param localHSSFWorkbook:HSSFWorkbook对象
     *	@param fileName:文件名称
     */
    public static void getExcel(HttpServletResponse localHttpServletResponse,HSSFWorkbook localHSSFWorkbook,String fileName) {
        try {
            fileName=fileName+ ".xls";
            localHttpServletResponse.reset();//清空输出流
            localHttpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
            localHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(new StringBuilder().append(fileName).toString(), "UTF-8"));
            localHttpServletResponse.addHeader("Pargam", "no-cache");
            localHttpServletResponse.addHeader("Cache-Control", "no-cache");
            ServletOutputStream localServletOutputStream = localHttpServletResponse.getOutputStream();
            localHSSFWorkbook.write(localServletOutputStream);
            localServletOutputStream.flush();
            localServletOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 JavaBean对象转化为 Map
     * @param bean 要转化的类型
     * @return Map对象
     */
    public static Map convertBean2Map(Object bean) throws Exception {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将 List<JavaBean>对象转化为List<Map>
     * @param type 要转化的类型
     * @param map
     * @return Object对象
     */
    public static List<Map<String,Object>> convertListBean2ListMap(List<Object> beanList) throws Exception {
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        for(int i=0, n=beanList.size(); i<n; i++){
            Object bean = beanList.get(i);
            Map map = convertBean2Map(bean);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 判断多级路径是否存在，不存在就创建
     *
     * @param filePath 支持带文件名的Path：如：D:\news\2014\12\abc.text，和不带文件名的Path：如：D:\news\2014\12
     */
    public static void isExistDir(String filePath) {
        String paths[] = {""};
        //切割路径
        try {
            String tempPath = new File(filePath).getCanonicalPath();//File对象转换为标准路径并进行切割，有两种windows和linux
            paths = tempPath.split("\\\\");//windows
            if(paths.length==1){paths = tempPath.split("/");}//linux
        } catch (IOException e) {
            System.out.println("切割路径错误");
            e.printStackTrace();
        }
        //判断是否有后缀
        boolean hasType = false;
        if(paths.length>0){
            String tempPath = paths[paths.length-1];
            if(tempPath.length()>0){
                if(tempPath.indexOf(".")>0){
                    hasType=true;
                }
            }
        }
        //创建文件夹
        String dir = paths[0];
        for (int i = 0; i < paths.length - (hasType?2:1); i++) {// 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
            try {
                dir = dir + "/" + paths[i + 1];//采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("成功创建目录：" + dirFile.getCanonicalFile());
                }
            } catch (Exception e) {
                System.err.println("文件夹创建发生异常");
                e.printStackTrace();
            }
        }
    }

}

