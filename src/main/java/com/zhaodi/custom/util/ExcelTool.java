package com.zhaodi.custom.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelTool {
    private static final String XLSX = ".xlsx";
    private static final String XLS = ".xls";
    public static final short IMG_HEIGTH = 30; // 导出图片高度
    public static final short IMG_WIDTH = 30; // 导出图片宽度

    /**
     * 读取文件数据
     * @author WangBo
     * @param xlsx文件或者xls文件
     * @return 文件数据
     * @return m:
     * key uploadRoot代表根目录
     * key uploadSubDir代表文件目录
     * key isImage代表加载图片 value:"0"==>不加载图片；value:"1"==>需加载图片
     * key imageFiled 代表excel中的图片列名称
     */
    public static JSONArray readExcel(File file,Map<String,String> m,List<String> col_list) {
        JSONArray array = null;
        try {
            String fileName = file.getName().toLowerCase();
            Workbook book = null;
            if (fileName.endsWith(XLSX)) {
                book = new XSSFWorkbook(new FileInputStream(file));
            } else if (fileName.endsWith(XLS)) {
                try {
                    book = new XSSFWorkbook(new FileInputStream(file));
                }catch (Exception e) {
                    POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
                    book = new HSSFWorkbook(poifsFileSystem);
                }
            } else {
                return array;
            }
            array = read(book,col_list);
            if(m.get("isImage").equals("1")){//当需要加载excel中图片时
                String imgName=m.get("imageFiled");
                Map<Integer, String> imgData=getImage(book,m.get("uploadRoot"),m.get("uploadSubDir"));
                for(Map.Entry<Integer, String> values:imgData.entrySet()){
                    Integer mapKey=values.getKey();
                    array.getJSONObject(mapKey-1).put(imgName,values.getValue());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // POI 3.9乃自动关闭，故而无book.close()方法
        return array;
    }
    /**
     * @Describe 获取excel图片
     * @author WangBo
     * @param book
     * @param uploadRoot:根目录
     * @param uploadSubDir：文件目录
     */
    private static Map<Integer, String> getImage(Workbook book, String uploadRoot, String uploadSubDir) throws IOException {
        Sheet sheet = book.getSheetAt(0);
        Map<Integer, String> sheetIndexPicMap = new HashMap<Integer, String>();
        try{
            //读取sheet的图片放入Map
            for (POIXMLDocumentPart dr : ((XSSFSheet) sheet).getRelations()) {
                if (dr instanceof XSSFDrawing) {
                    XSSFDrawing drawing = (XSSFDrawing) dr;
                    List<XSSFShape> shapes = drawing.getShapes();
                    for (XSSFShape shape : shapes) {
                        XSSFPicture pic = (XSSFPicture) shape;
                        XSSFClientAnchor anchor = pic.getPreferredSize();
                        CTMarker ctMarker = anchor.getFrom();
                        //picMap.put(ctMarker.getRow(),pic.getPictureData());
                        //获取图片格式
                        String ext = pic.getPictureData().suggestFileExtension();
                        //保存的文件名
                        String fileName = UUID.randomUUID().toString() + "." + ext;
                        String url=uploadSubDir + "/" + fileName;
                        sheetIndexPicMap.put(ctMarker.getRow(),url);
                        //System.out.println(url);
                        XSSFPictureData data = pic.getPictureData();
                        //输出全路径
                        String outPath = uploadRoot+url;
                        // 判断文件夹是否存在
                        isExistDir(outPath);
                        FileOutputStream out = new FileOutputStream(outPath);
                        out.write(data.getData());
                        out.close();
                    }
                }
            }

        }catch (Exception e){
            //读取sheet的图片放入Map
            List<HSSFPictureData> pictures = (List<HSSFPictureData>) book.getAllPictures();
            if (pictures.size() != 0) {
                for (HSSFShape shape : ((HSSFSheet) sheet).getDrawingPatriarch().getChildren()) {
                    HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                    if (shape instanceof HSSFPicture) {
                        HSSFPicture pic = (HSSFPicture) shape;
                        int pictureIndex = pic.getPictureIndex() - 1;
                        HSSFPictureData data = pictures.get(pictureIndex);
                        //获取图片格式
                        String ext = data.suggestFileExtension();
                        //保存的文件名
                        String fileName = UUID.randomUUID().toString() + "." + ext;
                        String url=uploadSubDir + "/" + fileName;
                        sheetIndexPicMap.put(anchor.getRow1(),url);
                        String outPath = uploadRoot+url;
                        // 判断文件夹是否存在
                        isExistDir(outPath);
                        FileOutputStream out = new FileOutputStream(outPath);
                        out.write(data.getData());
                        out.close();

                    }
                }
            }


        }

        return sheetIndexPicMap;
    }
    /**
     * 将文件的数据解析为JSON
     */
    private static JSONArray read(Workbook book,List<String> col_list) throws IOException {
        Sheet sheet = book.getSheetAt(0);

        int rowStart = sheet.getFirstRowNum(); // 首行下标
        int rowEnd = sheet.getLastRowNum(); // 尾行下标
        // 获取第一行JSON对象键
        Row firstRow = sheet.getRow(rowStart);
        int cellStart = firstRow.getFirstCellNum();
        int cellEnd = firstRow.getLastCellNum();
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        for (int j = cellStart; j < cellEnd; j++) {
            // 表头遇到空格停止解析
            //String val = getValue(firstRow.getCell(j));//获取列名
            String val = col_list.get(j);//赋予指定列名
            if (val == null || val.trim().length() == 0) {
                cellEnd = j;
                break;
            }
            keyMap.put(j,val);
        }
        if (keyMap.isEmpty()) {
            return (JSONArray) Collections.emptyList();
        }
        // 获取每行JSON对象的值
        JSONArray array = new JSONArray();
        // 如果首行与尾行相同，表明只有一行，返回表头数据
        if (rowStart == rowEnd) {
            JSONObject object = new JSONObject();
            for (int i : keyMap.keySet()) {
                object.put(keyMap.get(i), "");
            }
            array.add(object);
            return array;
        }

        for(int i = rowStart+1; i <= rowEnd ; i++) {
            Row eachRow = sheet.getRow(i);
            JSONObject obj = new JSONObject();
            StringBuffer sb = new StringBuffer();
            for (int k = cellStart; k < cellEnd; k++) {
                if (eachRow != null) {
                    String val = getValue(eachRow.getCell(k));
                    sb.append(val); // 所有数据添加到里面，用于判断该行是否为空
                    obj.put(keyMap.get(k),val);
                }
            }
            if (sb.toString().length() > 0) {
                array.add(obj);
            }
        }
        return array;
    }

    /**
     * 获取表格单元格数据
     */
    private static String getValue(Cell cell) throws IOException {
        // 空白或空
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
            return "";
        }
        // 0. 数字 类型
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format(date);
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String val = cell.getStringCellValue()+"";
            val = val.toUpperCase();
            if (val.contains("E")) {
                val = val.split("E")[0].replace(".", "");
            }
            return val;
        }
        // 1. String类型
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String val = cell.getStringCellValue();
            if (val == null || val.trim().length() == 0) {
                return "";
            }
            return val.trim();
        }
        // 2. 公式 CELL_TYPE_FORMULA
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        }
        // 4. 布尔值 CELL_TYPE_BOOLEAN
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue()+"";
        }
        // 5. 错误 CELL_TYPE_ERROR
        return "";
    }

    /**
     * excel导出
     * @param title 表名称
     * @param rowList 导出每行数据
     */
    public static void export(String title, List<List<Object>> rowList,String PATH) {
        if (rowList == null) {
            rowList = Collections.emptyList();
        }
        SXSSFWorkbook book = new SXSSFWorkbook();
        Sheet sheet = book.createSheet(title);
        Drawing patriarch = sheet.createDrawingPatriarch();
        CellStyle style = book.createCellStyle();
        // 数据居左
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        //style.setAlignment(HorizontalAlignment.LEFT);
        // 写数据
        for (int i = 0; i < rowList.size(); i++) {
            List<Object> row = rowList.get(i);
            Row sr = sheet.createRow(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) != null && row.get(j) instanceof URL) {
                    URL url = (URL)row.get(j);
                    sr.setHeight((short) (IMG_WIDTH * IMG_HEIGTH));
                    setExcelImg(book, patriarch, i, j, url);
                } else {
                    setExcelValue(sr.createCell(j), row.get(j), style);
                }
            }
        }
        try {
            if (PATH.length() > 0) {
                File dir = new File(PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            File file = new File(PATH + title + XLSX);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayOutputStream ops = new ByteArrayOutputStream();
            book.write(ops);
            fos.write(ops.toByteArray());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出写图片
     */
    private static void setExcelImg(SXSSFWorkbook wb,Drawing patriarch, int rowIndex, int cloumIndex, URL url) {
        // （jdk1.7版本try中定义流可自动关闭）
        try (InputStream is = url.openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = is.read(buff, 0, 1024)) > 0) {
                outputStream.write(buff, 0, rc);
            }
            // 设置图片位置
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,
                    cloumIndex, rowIndex, cloumIndex + 1, rowIndex + 1);
            //anchor.setAnchorType(0);
            anchor.setAnchorType(0);

            patriarch.createPicture(anchor, wb.addPicture(
                    outputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出写数据
     */
    public static void setExcelValue(Cell cell,  Object value, CellStyle style){
        // 写数据
        if (value == null) {
            cell.setCellValue("");
        }else {
            if (value instanceof Integer || value instanceof Long) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(Long.valueOf(value.toString()));
            } else if (value instanceof BigDecimal) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(((BigDecimal)value).setScale(3, RoundingMode.HALF_UP).doubleValue());
            } else {
                cell.setCellValue(value.toString());
            }
            cell.setCellStyle(style);
        }
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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