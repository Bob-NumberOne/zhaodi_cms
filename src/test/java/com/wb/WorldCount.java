package com.wb;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.util.*;
public class WorldCount {
    public static void main(String[] args) {
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};

        String string = "";
        //通过键值对的方式去分别存储字符串和出现的次数
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            //定义一个文件字节读取流，去读取磁盘中的文件
            FileInputStream fis = new FileInputStream("D:\\王勃专用文件\\data.txt");
            //创建一个BufferedReader的缓冲流，将字符流对象放入进去，提高读取的效率
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String temp = "";
            try {
                //从BufferReader中读取下一行
                while ((temp = br.readLine()) != null) {
                    string = string + temp;//读取到的文件信息
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Result result = ToAnalysis.parse(string); //分词结果的一个封装，主要是一个List<Term>的terms
        System.out.println(result.getTerms());

        List<Term> terms = result.getTerms(); //拿到terms
        System.out.println(terms.size());

        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
                //统计
                if (map.get(word+"-"+natureStr) == null) {//map.put()将键值存储在map集合中，如果存在，那么就覆盖该键值，如果不存在就新建一个。
                    map.put(word+"-"+natureStr, 1);
                } else {
                    int frequency = map.get(word+"-"+natureStr);
                    map.put(word+"-"+natureStr, ++frequency);
                }
            }
        }

        //利用TreeMap实现Comparator接口
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue()-o2.getValue();//升序排序 return o2.getValue() - o1.getValue();//降序排序
            }
        };

        // map转换成list进行排序，Entry是Map中的一个静态内部类，用来表示Map中的每个键值对，
        //除非使用了静态导入import static java.util.Map.*，否则Map不可以省略。
        // map.EntrySet(),实现了Set接口，里面存放的是键值对.
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        // 排序
        Collections.sort(list, valueComparator);
        // 默认情况下，TreeMap对key进行升序排序 
        System.out.println("------------map按照value降序排序--------------------");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}