package com.zhaodi.custom.entity;
/**
 * @author WangBo
 * @version 1.0
 * @Description 暂时是用一个表，后期数据量大了肯定要分表
 * @date 2020/3/31 10:21
 */
public class InnovationJournal {
    private long id;
    private String gkh;//公开(公告)号
    private String sqh;//申请号
    private String bt;//标题
    private String dqsqzqr;//[标]当前申请(专利权)人
    private String fmr;//发明人
    private String sqny;//申请年月
    private String fk;//分块
    private String bz;//备注
    private String ipcflh;//IPC分类号
    private String locflh;//LOC分类号
    private String zy;//摘要
    private String sqr;//申请日
    private String gkr;//公开(公告)日
    private String sqrr;//授权日
    private String yyzl;//引用专利
    private String yxqgj;//优先权国家
    private String yxqh;//优先权号
    private String yxqr;//优先权日
    private String dqsq;//当前申请(专利权)人地址
    private String dljg;//代理机构
    private String dlr;//代理人
    private String qlyq;//权利要求
    private String flztsj;//法律状态/事件
    private String flztgx;//法律状态更新时间
    private String dqsqzlq;//当前申请(专利权)人州/省
    private String dlql;//独立权利要求
    private String kztzyy;//扩展同族被引用专利总数
    private String kztzcy;//扩展同族成员数量
    private String kztz;//扩展同族
    private String yssq;//[标]原始申请(专利权)人
    private String zllx;//专利类型
    private String byyzl;//被引用专利
    private String slj;//受理局
    private String ipczflh;//IPC主分类号
    private String byyzls;//被引用专利数量
    private String yyzlsl;//简单同族成员数量
    private String jdtzbyy;//简单同族被引用专利总数
    private String jdtzcy;//简单同族成员数量
    private String jdtz;//简单同族
    private String ssfl;//所属分类id(树)
    private String img_url;
    private String file_url;
    private String creatTime;
    private long creater;
    private String updateTime;
    private long updateTer;
    private Integer status;
    private String creatName;
    private Integer total;//条数
    private String belongToBlock;//分块字符串数组如：1，2，3，34

    private String bigClass;//大类
    private String jswt;//技术问题
    private String jssd;//技术手段
    private String jsxg;//技术效果

    public String getBelongToBlock() {
        return belongToBlock;
    }

    public void setBelongToBlock(String belongToBlock) {
        this.belongToBlock = belongToBlock;
    }

    public String getBigClass() {
        return bigClass;
    }

    public void setBigClass(String bigClass) {
        this.bigClass = bigClass;
    }

    public String getJswt() {
        return jswt;
    }

    public void setJswt(String jswt) {
        this.jswt = jswt;
    }

    public String getJssd() {
        return jssd;
    }

    public void setJssd(String jssd) {
        this.jssd = jssd;
    }

    public String getJsxg() {
        return jsxg;
    }

    public void setJsxg(String jsxg) {
        this.jsxg = jsxg;
    }

    public String getCreatName() {
        return creatName;
    }

    public void setCreatName(String creatName) {
        this.creatName = creatName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public long getCreater() {
        return creater;
    }

    public void setCreater(long creater) {
        this.creater = creater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTer() {
        return updateTer;
    }

    public void setUpdateTer(long updateTer) {
        this.updateTer = updateTer;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGkh() {
        return gkh;
    }

    public void setGkh(String gkh) {
        this.gkh = gkh;
    }

    public String getSqh() {
        return sqh;
    }

    public void setSqh(String sqh) {
        this.sqh = sqh;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getDqsqzqr() {
        return dqsqzqr;
    }

    public void setDqsqzqr(String dqsqzqr) {
        this.dqsqzqr = dqsqzqr;
    }

    public String getFmr() {
        return fmr;
    }

    public void setFmr(String fmr) {
        this.fmr = fmr;
    }

    public String getSqny() {
        return sqny;
    }

    public void setSqny(String sqny) {
        this.sqny = sqny;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getIpcflh() {
        return ipcflh;
    }

    public void setIpcflh(String ipcflh) {
        this.ipcflh = ipcflh;
    }

    public String getLocflh() {
        return locflh;
    }

    public void setLocflh(String locflh) {
        this.locflh = locflh;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getGkr() {
        return gkr;
    }

    public void setGkr(String gkr) {
        this.gkr = gkr;
    }

    public String getSqrr() {
        return sqrr;
    }

    public void setSqrr(String sqrr) {
        this.sqrr = sqrr;
    }

    public String getYyzl() {
        return yyzl;
    }

    public void setYyzl(String yyzl) {
        this.yyzl = yyzl;
    }

    public String getYxqgj() {
        return yxqgj;
    }

    public void setYxqgj(String yxqgj) {
        this.yxqgj = yxqgj;
    }

    public String getYxqh() {
        return yxqh;
    }

    public void setYxqh(String yxqh) {
        this.yxqh = yxqh;
    }

    public String getYxqr() {
        return yxqr;
    }

    public void setYxqr(String yxqr) {
        this.yxqr = yxqr;
    }

    public String getDqsq() {
        return dqsq;
    }

    public void setDqsq(String dqsq) {
        this.dqsq = dqsq;
    }

    public String getDljg() {
        return dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg;
    }

    public String getDlr() {
        return dlr;
    }

    public void setDlr(String dlr) {
        this.dlr = dlr;
    }

    public String getQlyq() {
        return qlyq;
    }

    public void setQlyq(String qlyq) {
        this.qlyq = qlyq;
    }

    public String getFlztsj() {
        return flztsj;
    }

    public void setFlztsj(String flztsj) {
        this.flztsj = flztsj;
    }

    public String getFlztgx() {
        return flztgx;
    }

    public void setFlztgx(String flztgx) {
        this.flztgx = flztgx;
    }

    public String getDqsqzlq() {
        return dqsqzlq;
    }

    public void setDqsqzlq(String dqsqzlq) {
        this.dqsqzlq = dqsqzlq;
    }

    public String getDlql() {
        return dlql;
    }

    public void setDlql(String dlql) {
        this.dlql = dlql;
    }

    public String getKztzyy() {
        return kztzyy;
    }

    public void setKztzyy(String kztzyy) {
        this.kztzyy = kztzyy;
    }

    public String getKztzcy() {
        return kztzcy;
    }

    public void setKztzcy(String kztzcy) {
        this.kztzcy = kztzcy;
    }

    public String getKztz() {
        return kztz;
    }

    public void setKztz(String kztz) {
        this.kztz = kztz;
    }

    public String getYssq() {
        return yssq;
    }

    public void setYssq(String yssq) {
        this.yssq = yssq;
    }

    public String getZllx() {
        return zllx;
    }

    public void setZllx(String zllx) {
        this.zllx = zllx;
    }

    public String getByyzl() {
        return byyzl;
    }

    public void setByyzl(String byyzl) {
        this.byyzl = byyzl;
    }

    public String getSlj() {
        return slj;
    }

    public void setSlj(String slj) {
        this.slj = slj;
    }

    public String getIpczflh() {
        return ipczflh;
    }

    public void setIpczflh(String ipczflh) {
        this.ipczflh = ipczflh;
    }

    public String getByyzls() {
        return byyzls;
    }

    public void setByyzls(String byyzls) {
        this.byyzls = byyzls;
    }

    public String getYyzlsl() {
        return yyzlsl;
    }

    public void setYyzlsl(String yyzlsl) {
        this.yyzlsl = yyzlsl;
    }

    public String getJdtzbyy() {
        return jdtzbyy;
    }

    public void setJdtzbyy(String jdtzbyy) {
        this.jdtzbyy = jdtzbyy;
    }

    public String getJdtzcy() {
        return jdtzcy;
    }

    public void setJdtzcy(String jdtzcy) {
        this.jdtzcy = jdtzcy;
    }

    public String getJdtz() {
        return jdtz;
    }

    public void setJdtz(String jdtz) {
        this.jdtz = jdtz;
    }

    public String getSsfl() {
        return ssfl;
    }

    public void setSsfl(String ssfl) {
        this.ssfl = ssfl;
    }
}
