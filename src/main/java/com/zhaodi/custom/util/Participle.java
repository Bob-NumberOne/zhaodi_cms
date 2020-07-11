package com.zhaodi.custom.util;
import com.zhaodi.custom.entity.UfPatentdataWord;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;

import java.util.*;

/**
 * @author WangBo
 * @Describe 公共方法
 * @date 2020/4/1 9:13
 */
public class Participle {
    /**
     * @Describe 根据词以及词性找出对应数据
     * @Describe 获取分词
     * maps:top==1升序排序==0降序排列；total==截取词数
     * @author WangBo
     */
    public static List<Map<String, String>> extractParticiple(String string,Set<String> expectedNature,Map<String,String> maps) {
        List<Map<String, String>> datas=new ArrayList<>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        DicLibrary.insert(DicLibrary.DEFAULT, "本实用新型公开", "n", 1000);

        Result result = DicAnalysis.parse(string); //分词结果的一个封装，主要是一个List<Term>的terms
        //System.out.println(result.getTerms());

        List<Term> terms = result.getTerms(); //拿到terms
        //System.out.println(terms.size());

        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
                //System.out.println(word+"===="+natureStr);
                //统计
                if (map.get(word) == null) {//map.put()将键值存储在map集合中，如果存在，那么就覆盖该键值，如果不存在就新建一个。
                    map.put(word, 1);
                } else {
                    int frequency = map.get(word);
                    map.put(word, ++frequency);
                }
            }
        }

        //利用TreeMap实现Comparator接口
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(maps.get("top").equals("1")){
                    return o1.getValue()-o2.getValue();//升序排序
                }else{
                    return o2.getValue() - o1.getValue();//降序排序
                }
            }
        };

        // map转换成list进行排序，Entry是Map中的一个静态内部类，用来表示Map中的每个键值对，
        //除非使用了静态导入import static java.util.Map.*，否则Map不可以省略。
        // map.EntrySet(),实现了Set接口，里面存放的是键值对.
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        // 排序
        Collections.sort(list, valueComparator);
        // 默认情况下，TreeMap对key进行升序排序 
        /*System.out.println("------------map按照value降序排序--------------------");*/
        int i=0;
        Integer num=Integer.valueOf(maps.get("total"));//截取数目
        for (Map.Entry<String, Integer> entry : list) {
            if(i==num){
                break;
            }
            if(entry.getKey().equals("本发明")||entry.getKey().equals("所述")|| entry.getKey().equals("包括") || entry.getKey().equals("连接") || entry.getKey().equals("发明") || entry.getKey().equals("有")|| entry.getKey().equals("装置")|| entry.getKey().equals("设置")){

            }else{
                Map<String,String> m=new HashMap<>();
                m.put("name",entry.getKey());
                m.put("value",entry.getValue().toString());
                /*            System.out.println(entry.getKey()+":"+entry.getValue().toString());*/
                datas.add(m);
            }
            i++;
        }
        return datas;
    }

    public static List<Map<String, String>> executeParticiple(String string, Set<String> expectedNature, Map<String, String> maps, List<UfPatentdataWord> partWords, List<String> filterWord) {
        List<Map<String, String>> datas=new ArrayList<>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if(partWords.size()>0){
            for(UfPatentdataWord uf:partWords){
                DicLibrary.insert(DicLibrary.DEFAULT, uf.getWord(), uf.getNature(),uf.getId().intValue());
            }
        }

        Result result = DicAnalysis.parse(string); //分词结果的一个封装，主要是一个List<Term>的terms
        List<Term> terms = result.getTerms(); //拿到terms

        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
                //System.out.println(word+"===="+natureStr);
                //统计
                if (map.get(word) == null) {//map.put()将键值存储在map集合中，如果存在，那么就覆盖该键值，如果不存在就新建一个。
                    map.put(word, 1);
                } else {
                    int frequency = map.get(word);
                    map.put(word, ++frequency);
                }
            }
        }

        //利用TreeMap实现Comparator接口
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(maps.get("top").equals("1")){
                    return o1.getValue()-o2.getValue();//升序排序
                }else{
                    return o2.getValue() - o1.getValue();//降序排序
                }
            }
        };

        // map转换成list进行排序，Entry是Map中的一个静态内部类，用来表示Map中的每个键值对，
        //除非使用了静态导入import static java.util.Map.*，否则Map不可以省略。
        // map.EntrySet(),实现了Set接口，里面存放的是键值对.
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        // 排序
        Collections.sort(list, valueComparator);
        // 默认情况下，TreeMap对key进行升序排序 
        /*System.out.println("------------map按照value降序排序--------------------");*/
        int i=0;
        Integer num=Integer.valueOf(maps.get("total"));//截取数目
        for (Map.Entry<String, Integer> entry : list) {
            if(i==num){
                break;
            }
            String key=entry.getKey();
            if(!filterWord.contains(key)){
                Map<String,String> m=new HashMap<>();
                m.put("name",entry.getKey());
                m.put("value",entry.getValue().toString());
                datas.add(m);
            }
            i++;
        }
        return datas;
    }

}
