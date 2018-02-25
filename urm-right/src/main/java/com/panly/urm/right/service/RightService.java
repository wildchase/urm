package com.panly.urm.right.service;


import java.util.List;

import com.panly.urm.right.domain.Application;
import com.panly.urm.right.domain.Right;
import com.panly.urm.tran.auth.TreeDTO;

/**
 * 获取right的接口
 * @author lipan
 */
public interface RightService {
	
	public Application checkApplication(String appCode);
	
	/**
	 * 鉴定用户是否有该操作的权限，若是有则返回对应的right，若是没有则返回null
	 * @param acctId
	 * @param operCode
	 * @return
	 */
	public Right checkRight(Long acctId,String operCode,String appCode);
	
	/**
	 * 获取应用的功能树
	 * @return
	 */
	public List<TreeDTO> getAppFuncTree(String appCode);
	
	/**
	 * 获取应用下该用户的功能数
	 * @return
	 */
	public List<TreeDTO> getAcctFuncTree(Long acctId,String appCode);
	
}
