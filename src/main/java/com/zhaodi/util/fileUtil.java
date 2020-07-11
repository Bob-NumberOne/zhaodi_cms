package com.zhaodi.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
public class fileUtil {
	/**
    *
    * @param path
    * @return String
    * @description 将文件转base64字符串
    * @date 2018年3月20日
    * @author changyl
    * File转成编码成BASE64
    */

   public static  String fileToBase64(String path) {
       String base64 = null;
       InputStream in = null;
       try {
           File file = new File(path);
           in = new FileInputStream(file);
           byte[] bytes=new byte[(int)file.length()];
           in.read(bytes);
           base64 = Base64.getEncoder().encodeToString(bytes);
       } catch (Exception e) {
           System.out.println(path+"---系统找不到此路径文件！");
       } finally {
           if (in != null) {
               try {
                   in.close();
               } catch (IOException e) {
                   System.out.println(path+"---文件流关闭失败！");
               }
           }
       }
       return base64;
   }
   
   public static  String fileToBase64(File file) {
       String base64 = null;
       InputStream in = null;
       try {
           in = new FileInputStream(file);
           byte[] bytes=new byte[(int)file.length()];
           in.read(bytes);
           base64 = Base64.getEncoder().encodeToString(bytes);
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (in != null) {
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       return base64;
   }

   //BASE64解码成File文件
   public static void base64ToFile(String destPath,String base64, String fileName) {
       File file = null;
       //创建文件目录
       String filePath=destPath;
       File  dir=new File(filePath);
       if (!dir.exists() && !dir.isDirectory()) {
           dir.mkdirs();
       }
       BufferedOutputStream bos = null;
       FileOutputStream fos = null;
       try {
           byte[] bytes = Base64.getDecoder().decode(base64);
           file=new File(filePath+"/"+fileName);
           fos = new FileOutputStream(file);
           bos = new BufferedOutputStream(fos);
           bos.write(bytes);
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (bos != null) {
               try {
                   bos.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (fos != null) {
               try {
                   fos.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   }
   /**
    * 	清空文件和文件目录
    */
   public static void clean(File f) throws Exception {
       String cs[] = f.list();
       if (cs == null || cs.length <= 0) {
           System.out.println("delFile:[ " + f + " ]");
           boolean isDelete = f.delete();
           if (!isDelete) {
               System.out.println("delFile:[ " + f.getName() + "文件删除失败！" + " ]");
               throw new Exception(f.getName() + "文件删除失败！");
           }
       } else {
           for (int i = 0; i < cs.length; i++) {
               String cn = cs[i];
               String cp = f.getPath() + File.separator + cn;
               File f2 = new File(cp);
               if (f2.exists() && f2.isFile()) {
                   System.out.println("delFile:[ " + f2 + " ]");
                   boolean isDelete = f2.delete();
                   if (!isDelete) {
                       System.out.println("delFile:[ " + f2.getName() + "文件删除失败！" + " ]");
                       throw new Exception(f2.getName() + "文件删除失败！");
                   }
               } else if (f2.exists() && f2.isDirectory()) {
                   clean(f2);
               }
           }
           System.out.println("delFile:[ " + f + " ]");
           boolean isDelete = f.delete();
           if (!isDelete) {
               System.out.println("delFile:[ " + f.getName() + "文件删除失败！" + " ]");
               throw new Exception(f.getName() + "文件删除失败！");
           }
       }
   }
   /**
    * @Describe 根据路径读取图片
    * @author WangBo
    * @param 参数信息
    */
   public static byte[] getImage(String adress) throws IOException {
       byte data[]=null;
       if (adress != null){
           FileInputStream is = null;
           File filePic = new File(adress);
           try {
               is = new FileInputStream(filePic);
           } catch (FileNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           int i = is.available(); // 得到文件大小
           data= new byte[i];
           is.read(data); // 读数据
           is.close();
       }
        return data;
   }
    /**
     * 根据图片的地址，返回图片的缓冲流
     * @param addr
     * @return
     */
    public static BufferedImage getInputStream(String addr){
        try {
            String imgPath = addr;
            BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
            return image;
        } catch (Exception e) {
            System.out.println("获取图片异常:java.awt.image.BufferedImage");
            System.out.println("请检查图片路径是否正确，或者该地址是否为一个图片");
        }
        return null;
    }

    public static HttpServletResponse download(String path, String name,HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            /*String filename = file.getName();*/
            String filename=name;
            // 取得文件的后缀名。
            /*String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();*/

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(new StringBuilder().append(filename).toString(), "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            System.out.println(path+"---系统中找不到此路径文件！");
        }
        return response;
    }
}
