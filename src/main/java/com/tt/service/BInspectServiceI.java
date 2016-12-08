package com.tt.service;


import com.tt.model.InspectData;

public interface BInspectServiceI
{
    int add(InspectData ins);
    int updateStatus(String PRG, String STZH, String DevNB);
}
