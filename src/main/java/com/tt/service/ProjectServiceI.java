package com.tt.service;

import com.tt.model.Project;

import java.util.List;

/**
 * Created by tt on 2016/9/29.
 */
public interface ProjectServiceI extends BaseService<Project>{
    List<String> listStzh(String prg);
}
