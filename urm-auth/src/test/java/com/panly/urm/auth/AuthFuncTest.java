//package com.panly.urm.auth;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.panly.urm.auth.service.AuthService;
//import com.panly.urm.tran.auth.TreeDTO;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest(properties="spring.profiles.active=dev",classes=AuthMain.class,webEnvironment=WebEnvironment.NONE)
//public class AuthFuncTest  {
//	
//	@Autowired
//	private AuthService authService;
//	
////	@Test
//	public void funcTest(){
//		String appCode = "app-test";
//		Long acctId = 100008L;
//		List<TreeDTO> list =authService.getAppFuncTree(appCode);
//		System.out.println(JSON.toJSONString(list));
//		
//		List<TreeDTO> result =authService.getAcctFuncTreeForApp(appCode, acctId);
//		System.out.println(JSON.toJSONString(result));
//	}
//
//}
