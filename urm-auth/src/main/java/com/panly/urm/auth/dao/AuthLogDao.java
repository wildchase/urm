package com.panly.urm.auth.dao;

import com.panly.urm.auth.model.AuthLog;

/**
 * dal Interface:UrmAuthLog
 * @author a@panly.me
 */
public interface AuthLogDao {

	Integer insert(AuthLog record);

    Integer insertSelective(AuthLog record);
    

}