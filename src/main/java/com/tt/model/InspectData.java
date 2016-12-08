package com.tt.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "b_inspect_data")
public class InspectData  extends BaseModel{

    private Integer id;
    private Integer plan_id;
    @JSONField(name = "PRG")
    private String prg;

    @JSONField(name = "STZH")
    private String stzh;

    @JSONField(name = "DevNB")
    private String devnb;

    @JSONField(name = "PRS")
    private String prs_str;

    private Double avg_prs;

    @JSONField(name = "HZJC")
    private String hzjc_str;

    private Double avg_hzjc;

    @JSONField(name = "WYJC")
    private String wyjc_str;

    private Double avg_wyjc;

    //本级位移
    private Double cur_wyjc;

    @JSONField(name = "lat")
    private Float lat;

    @JSONField(name = "lng")
    private Float lng;

    @JSONField(name = "Devstr")
    private String devstr;

    @JSONField(name = "Time")
    private String time;

    @JSONField(name = "QJX")
    private String qjx_str;

    @JSONField(name = "NDSJ")
    private String ndsj_str;

    @JSONField(name = "DevST")
    private Byte devst;

    @JSONField(name = "SETprs")
    private String setprs;

    @JSONField(name = "LoadFlag")
    private Boolean loadFlag;
    
    @JSONField(name = "TotalTime")
    private Integer totalTime;

    private Integer interval;
    private Double avg_qjx;
    private Double avg_ndsj;

    @Basic
    @Column(name = "totalTime")
    public Integer getTotalTime()
    {
        return totalTime;
    }
    public void setTotalTime(Integer totalTime)
    {
        this.totalTime = totalTime;
    }
    /**
     * 0：未开始检测
     * 1：正在检测
     * 2：检测完成
     */
    private Integer status;
    @Basic
    @Column(name = "status")
    public Integer getStatus()
    {
        return status;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
   
    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "plan_id")
    public Integer getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(Integer plan_id) {
        this.plan_id = plan_id;
    }
    @Basic
    @Column(name = "prg")
    public String getPrg() {
        return prg;
    }


    public void setPrg(String prg) {
        this.prg = prg;
    }

    @Basic
    @Column(name = "stzh")
    public String getStzh() {
        return stzh;
    }

    public void setStzh(String stzh) {
        this.stzh = stzh;
    }

    @Basic
    @Column(name = "devnb")
    public String getDevnb() {
        return devnb;
    }

    public void setDevnb(String devnb) {
        this.devnb = devnb;
    }

    @Basic
    @Column(name = "prs")
    public String getPrs_str() {
        return prs_str;
    }

    @Transient
    public List<Double> getPrs() {
//        if (prs_str != null && prs_str.length() > 0) {
//            return Arrays.asList(prs_str.split(",")).stream().map(item->{
//                return Double.parseDouble(item);
//            });
//        }
        return null;
    }
    @Transient
    public Double getAvgPrs() {
        if(this.avg_prs == null){
            this.avg_prs = calcAvg(getPrs_str());
        }
        return this.avg_prs;
    }

    public void setPrs_str(String prs_str) {
        this.prs_str = prs_str;
    }

    @Basic
    @Column(name = "hzjc")
    public String getHzjc_str() {
        return hzjc_str;
    }

    @Transient
    public String[] getHzjc() {
        if (hzjc_str != null && hzjc_str.length() > 0) {
            return hzjc_str.split(",");
        }
        return null;
    }
    @Transient
    public Double getAvgHzjc() {
        if(this.avg_hzjc == null){
            this.avg_hzjc = calcAvg(getHzjc_str());
        }
        return this.avg_hzjc;
    }

    public void setHzjc_str(String hzjc_str) {
        this.hzjc_str = hzjc_str;
    }

    @Basic
    @Column(name = "wyjc")
    public String getWyjc_str() {
        return wyjc_str;
    }

    @Transient
    public String[] getWyjc() {
        if (wyjc_str != null && wyjc_str.length() > 0) {
            return wyjc_str.split(",");
        }
        return null;
    }
    @Transient
    public Double getAvgWyjc() {
        if(this.avg_wyjc == null){
            this.avg_wyjc = calcAvg(getWyjc_str());
        }
        return this.avg_wyjc;
    }

    public void setWyjc_str(String wyjc_str) {
        this.wyjc_str = wyjc_str;
    }

    @Basic
    @Column(name = "lat")
    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "lng")
    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    @Basic
    @Column(name = "devstr")
    public String getDevstr() {
        return devstr;
    }

    public void setDevstr(String devstr) {
        this.devstr = devstr;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "qjx")
    public String getQjx_str() {
        return qjx_str;
    }

    @Transient
    public String[] getQjx() {
        if (qjx_str != null && qjx_str.length() > 0) {
            return qjx_str.split(",");
        }
        return null;
    }

    @Transient
    public Double getAvgQjx() {
        if(this.avg_qjx == null){
            this.avg_qjx = calcAvg(getQjx_str());
        }
        return this.avg_qjx;
    }

    public void setQjx_str(String qjx_str) {
        this.qjx_str = qjx_str;
    }

    @Basic
    @Column(name = "ndsj")
    public String getNdsj_str() {
        return ndsj_str;
    }

    @Transient
    public String[] getNdsj() {
        if (ndsj_str != null && ndsj_str.length() > 0) {
            return ndsj_str.split(",");
        }
        return null;
    }
    @Transient
    public Double getAvgNdsj() {
        if(this.avg_ndsj == null){
            this.avg_ndsj = calcAvg(getNdsj_str());
        }
        return this.avg_ndsj;
    }
    public void setNdsj_str(String ndsj_str) {
        this.ndsj_str = ndsj_str;
    }

    @Basic
    @Column(name = "devst")
    public Byte getDevst() {
        return devst;
    }

    public void setDevst(Byte devst) {
        this.devst = devst;
    }

    @Basic
    @Column(name = "Setprs")
    public String getSetprs() {
        return setprs;
    }

    public void setSetprs(String setprs) {
        this.setprs = setprs;
    }

    @Basic
    @Column(name = "LoadFlag")
    public Boolean getLoadFlag() {
        return loadFlag;
    }

    public void setLoadFlag(Boolean loadFlag) {
        this.loadFlag = loadFlag;
    }

    @Transient
    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Transient
    public Double getCur_wyjc() {
        return cur_wyjc;
    }

    public void setCur_wyjc(Double cur_wyjc) {
        this.cur_wyjc = cur_wyjc;
    }

    /**
     * 将由逗号分割的数据字符串解析并计算出平均值
     * @param data_str
     * @return
     */
    private Double calcAvg(String data_str){
        if(data_str==null||data_str.isEmpty()){
            return null;
        }
        Double num_div = Math.pow(10,3);
        List<String> data_list = Arrays.asList(data_str.split(","));
        if(data_list!=null&&!data_list.isEmpty()){
            return Math.round(data_list.stream().map(item -> Double.parseDouble(item)).reduce((a, b) -> a + b).get()/data_list.size()*num_div)/num_div;
        }else {
            return null;
        }
    }

}
