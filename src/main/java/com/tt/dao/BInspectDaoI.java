package com.tt.dao;

import com.tt.model.InspectData;


/**
 * 系统操作日志数据库操作类
 * 
 * @author kiky zhang
 */
public interface BInspectDaoI
{
    int addIns(InspectData ins);

    int updateStatus(String PRG, String STZH, String DevNB);
}
