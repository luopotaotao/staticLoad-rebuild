package com.tt.service.impl;


import com.tt.dao.BInspectDaoI;
import com.tt.dao.EquipmentDaoI;
import com.tt.dao.InspectPlanDaoI;
import com.tt.model.Equipment;
import com.tt.model.InspectData;
import com.tt.model.InspectPlan;
import com.tt.service.BInspectServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("bInspectServiceI")
@Transactional
public class BInspectServiceImpl implements BInspectServiceI {

    @Autowired
    private BInspectDaoI inspectDao;
    @Autowired
    private InspectPlanDaoI inspectPlanDao;
    @Autowired
    private EquipmentDaoI equipmentDao;
    @Override
    public int updateStatus(String PRG, String STZH, String DevNB) {
        return inspectDao.updateStatus(PRG, STZH, DevNB);
    }

    @Override
    public int add(InspectData ins) {
        InspectPlan plan = inspectPlanDao.getByPrgStzhDevNB(ins.getPrg(), ins.getStzh(), ins.getDevnb());
        if (plan != null) {
            ins.setDept_id(plan.getDept_id());
            ins.setPlan_id(plan.getId());
        }else{
            Equipment equipment = equipmentDao.loadByCodeIgnoreDept(ins.getDevnb());
            ins.setDept_id(equipment.getDept_id());
            ins.setPlan_id(null);
        }
        inspectDao.addIns(ins);
        return ins.getId();
    }
}
