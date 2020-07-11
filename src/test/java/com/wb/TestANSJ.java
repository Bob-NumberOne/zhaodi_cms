package com.wb;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.*;
public class TestANSJ {
    public static void test1() {
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        String str = "本/发明/公开/一种/装配式/建筑/墙板/用/模具/组件/，/包括/第一/挡边/及/第二/挡边/，/所述/第一/挡边/位于/所述/第二/挡边/上部/，/所述/第二/挡边/的/内侧/设有/第一/台阶部/，/所述/第一/挡边/位于/所述/第二/挡边/远离/所述/第一/台阶部/的/一端/，/所述/第一/台阶部/的/台阶面/与/所述/第一/挡边/形成/翼部/。/本/发明/公开/的/装配式/建筑/墙板/用/模具/，/包括/第一/挡边/及/第二/挡边/，/第一/挡边/位于/第二/挡边/上部/，/在/装模/与/脱模/时/，/第二/挡边/不会/干涉/翼部/的/装模/与/脱模/，/装模/、/脱模/简单/及/墙板/生产/周期/短。/本/发明/还/公开/一种/包括/上述/装配式/建筑/墙板/用/模具/组件/的/装配式/建筑/墙板/用/模具/。";
        Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
        System.out.println(result.getTerms());

        List<Term> terms = result.getTerms(); //拿到terms
        System.out.println(terms.size());

        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
                System.out.println(word + ":" + natureStr);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("ddddddddddddddd");
        test1();
    }
}
