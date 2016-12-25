package com.tt.service.impl;

import com.tt.dao.InspectPlanDaoI;
import com.tt.model.InspectPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.InspectDataDaoI;
import com.tt.model.InspectData;
import com.tt.service.InspectDataServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("inspectDataService")
@Transactional
public class InspectDataServiceImpl implements InspectDataServiceI {
    @Autowired
    private InspectDataDaoI inspectDataDao;

    @Autowired
    private InspectPlanDaoI planDao;
    @Override
    public List<InspectData> list(String PRG,String STZH,boolean loadFlag) {
        String hql = "from InspectData WHERE PRG=:PRG and STZH=:STZH and loadFlag=:loadFlag order by SETprs ASC,totalTime ASC";
        Map<String,Object> params = new HashMap<>();

        params.put("PRG",PRG);
        params.put("STZH",STZH);
        params.put("loadFlag",loadFlag);
        List<InspectData> ret = inspectDataDao.find(hql, params);
//        ret.stream().forEach(item->{
//            inspectDataDao.saveOrUpdate(item);
//            System.out.println(item.getAvgWyjc());
//        });
        return calcDurationAndCurWyjc(ret);
    }

    /**
     * 计算数据之间的时间间隔和本级位移
     * @param list
     * @return
     */
    private List<InspectData> calcDurationAndCurWyjc(List<InspectData> list) {
        if(list==null||list.isEmpty()){
            return null;
        }
        InspectData firstItem = list.get(0);
        firstItem.setInterval(0);
        if(list.size()>1){
            String current_SETprs = firstItem.getSetprs();
            Double pre_avg_wyjc = 0.0;
            for(int i=1;i<list.size();i++){
                InspectData cur = list.get(i);
                InspectData pre = list.get(i-1);
                Integer interval = cur.getSetprs().equals(pre.getSetprs())?cur.getTotalTime()-pre.getTotalTime():0;
                cur.setInterval(interval);
                //当当前数据和上一条数据的基准压力值一致时,说明当前数据还是和上一条数据属于同一组,直接计算本级位移即可,
                // 否则的话说明数据进入下一基准压力值,需要重新设置当前基准值和上一级的最终位移,再进行计算
                if(!cur.getSetprs().equals(current_SETprs)){
                    current_SETprs = cur.getSetprs();
                    pre_avg_wyjc = pre.getAvgWyjc();
                }
                cur.setCur_wyjc(cur.getAvgWyjc()-pre_avg_wyjc);
            }
        }

        return list;
    }

    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<>();

        params.put("ids", ids);
        return inspectDataDao.executeHql("delete from InspectData where id in (:ids)", params);
    }

    @Override
    public List<Map<String, Object>> loadKeys(Integer dept_id) {
        Map<String, Object> params = new HashMap<>();

        return inspectDataDao.findList("select distinct new map(d.prg as prg,d.stzh as stzh) from InspectData d where d.dept_id=:dept_id",params);
    }
    @Override
    public List<Map<String, Object>> loadUnLinkedKeys() {
        Map<String, Object> params = new HashMap<>();

        return inspectDataDao.findList("select distinct new map(d.prg as prg,d.stzh as stzh,d.devnb as devnb) from InspectData d where d.plan_id is null and d.dept_id=:dept_id",params);
    }
    public List<Map<String, Object>> loadLinkedKeys(Integer plan_id) {
        Map<String,Object> params = new HashMap<>();

        params.put("plan_id",plan_id);
        return inspectDataDao.findList("select distinct new map(d.prg as prg,d.stzh as stzh) from InspectData d where d.plan_id=:plan_id and d.dept_id=:dept_id",params);
    }

    @Override
    public int linkData(Integer plan_id,Map<String, Object> data) {
        InspectPlan plan = planDao.getById(plan_id);
        String sql = "update b_inspect_data set plan_id=:plan_id,prg=:new_prg,stzh=:new_stzh where prg=:prg and stzh=:stzh and devnb=:devnb and dept_id=:dept_id";
        data.put("plan_id",plan_id);
        data.put("new_prg",plan.getProject().getCode());
        data.put("new_stzh",plan.getStzh());
        int ret = inspectDataDao.executeSql(sql,data);
        return ret;
    }

    @Override
    public InspectData loadLatestData(String PRG, String STZH) {
        return inspectDataDao.loadLatestData(PRG,STZH);
    }

    @Override
    public Map<String, Object> calcChart0(List<InspectData> list) {
        Map<String,Object> ret = new HashMap<>();
        Map<Object,Object> tick = new HashMap<>();
        List<Map<String,Object>> data = new LinkedList<>();
        if(list==null||list.isEmpty()){
            return null;
        }
        InspectData firstItem = list.get(0);
        firstItem.setInterval(0);
        if(list.size()>1){
            String current_SETprs = firstItem.getSetprs();
            for(int i=1;i<list.size();i++){
                InspectData cur = list.get(i);
                //当当前数据和上一条数据的基准压力值一致时,说明当前数据还是和上一条数据属于同一组,直接计算本级位移即可,
                // 否则的话说明数据进入下一基准压力值,需要重新设置当前基准值和上一级的最终位移,再进行计算
                if(!cur.getSetprs().equals(current_SETprs)){
                    InspectData pre = list.get(i-1);

                    Map<String,Object> point = getChart0Point(current_SETprs,cur.getAvgWyjc());
                    data.add(point);
                    Object key = point.get("x");
                    if(!tick.containsKey(key)){
                        tick.put(key,key);
                    }
                    current_SETprs = cur.getSetprs();
                }
            }

        }
        InspectData lastItem = list.get(list.size()-1);
        Map<String,Object> point = getChart0Point(lastItem.getSetprs(),lastItem.getAvgWyjc());
        data.add(point);
        Object key = point.get("x");
        if(!tick.containsKey(key)){
            tick.put(key,key);
        }

        ret.put("data",data);
        ret.put("tick",tick);
        return ret;
    }

    @Override
    public Map<String, Object> calcChart1(List<InspectData> list) {
        Map<String,Object> ret = new HashMap<>();
        Map<Double,Integer> tick = new HashMap<>();
        List<Map<String,Object>> data = new LinkedList<>();
        if(list==null||list.isEmpty()){
            return null;
        }
        InspectData firstItem = list.get(0);
        firstItem.setInterval(0);
        if(list.size()>1){
            String current_SETprs = firstItem.getSetprs();

            Map<String,Object> firstGroup = new HashMap<>();
            firstGroup.put("name",current_SETprs);
            firstGroup.put("data",new LinkedList<Map<String,Object>>());
            if(firstItem.getTotalTime()>0){
                Map<String,Double> firstPoint = getChart1Point(firstItem.getTotalTime(),firstItem.getAvgWyjc());
                ((LinkedList)firstGroup.get("data")).add(firstPoint);
                Double key = firstPoint.get("x");
                if(!tick.containsKey(key)){
                    tick.put(key,firstItem.getTotalTime());
                }
            }

            data.add(firstGroup);
            for(int i=1;i<list.size();i++){
                InspectData cur = list.get(i);
                Map<String,Object> group;
                if(!cur.getSetprs().equals(current_SETprs)){
                    current_SETprs = cur.getSetprs();
                    group = new HashMap<>();
                    group.put("name",current_SETprs);
                    group.put("data",new LinkedList<Map<String,Object>>());
                    data.add(group);
                }else{
                    group = data.get(data.size()-1);
                }
                if (cur.getTotalTime()>0) {
                    Map<String,Double> point = getChart1Point(cur.getTotalTime(),cur.getAvgWyjc());
                    ((LinkedList)group.get("data")).add(point);
                    Double key = point.get("x");
                    if(!tick.containsKey(key)){
                        tick.put(key,cur.getTotalTime());
                    }
                }
            }
        }
        ret.put("data",data);
        ret.put("tick",tick);
        return ret;
    }


    private Map<String,Object> getPoint(Object x,Object y){
        Map<String,Object> ret = new HashMap<>();
        ret.put("x",x);
        ret.put("y",y);
        return ret;
    }
    private Map<String,Object> getChart0Point(String x,Double y){
        Map<String,Object> ret = new HashMap<>();
        ret.put("x",Double.parseDouble(x));
        ret.put("y",y);
        return ret;
    }
    private Map<String,Double> getChart1Point(Integer x,Double y){
        Map<String,Double> ret = new HashMap<>();
        ret.put("x",Math.log10(x)-Math.log10(5));
        ret.put("y",y);
        return ret;
    }
    @Override
    public Map<String, Object> calcChart2(List<InspectData> list) {
        Map<String,Object> ret = new HashMap<>();
        Map<Double,String> tick = new HashMap<>();
        List<Map<String,Double>> data = new LinkedList<>();
        if(list==null||list.isEmpty()){
            return null;
        }
        InspectData firstItem = list.get(0);
        firstItem.setInterval(0);

        if(list.size()>1){
            String current_SETprs = firstItem.getSetprs();
            for(int i=1;i<list.size();i++){
                InspectData cur = list.get(i);
                //当当前数据和上一条数据的基准压力值一致时,说明当前数据还是和上一条数据属于同一组,直接计算本级位移即可,
                // 否则的话说明数据进入下一基准压力值,需要重新设置当前基准值和上一级的最终位移,再进行计算
                if(!cur.getSetprs().equals(current_SETprs)){
                    InspectData pre = list.get(i-1);
                    Map<String,Double> point = getChart2Point(current_SETprs,pre.getAvgWyjc());
                    data.add(point);
                    Double key = point.get("x");
                    if(!tick.containsKey(key)){
                        tick.put(key,current_SETprs);
                    }
                    current_SETprs = cur.getSetprs();
                }
            }

        }
        InspectData lastItem = list.get(list.size()-1);

        Map<String,Double> point = getChart2Point(lastItem.getSetprs(),lastItem.getAvgWyjc());
        data.add(point);
        Double key = point.get("x");
        if(!tick.containsKey(key)){
            tick.put(key,lastItem.getSetprs());
        }
        ret.put("data",data);
        ret.put("tick",tick);
        return ret;
    }

    private Map<String,Double> getChart2Point(String x,Double y){
        Map<String,Double> ret = new HashMap<>();
        ret.put("x",Math.log10(Double.parseDouble(x)));
        ret.put("y",y);
        return ret;
    }

    @Override
    public Map<Integer, Double> getMaxLoad(List<Integer> plan_ids) {
        if(plan_ids==null||plan_ids.isEmpty()){
            return null;
        }
        Map<Integer,Double> ret = new HashMap<>();
        inspectDataDao.getMaxLoadByPlanIds(plan_ids).stream().forEach(row->{
            ret.put((Integer) row[0],(Double) row[1]);
        });
        return ret;
    }

    @Override
    public Map<Integer, Double> getMaxOffset(List<Integer> plan_ids) {
        if(plan_ids==null||plan_ids.isEmpty()){
            return null;
        }
        Map<Integer,Double> ret = new HashMap<>();
        inspectDataDao.getMaxOffsetByPlanIds(plan_ids).stream().forEach(row->{
            ret.put((Integer) row[0],(Double) row[1]);
        });
        return ret;
    }
}
