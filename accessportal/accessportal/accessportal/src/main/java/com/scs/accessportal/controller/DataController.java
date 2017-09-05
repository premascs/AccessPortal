package com.scs.accessportal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.service.AccessTypeService;
import com.scs.accessportal.service.DataService;
import com.scs.accessportal.service.MailClientService;
import com.scs.accessportal.model.AccessTypeModel;



@Controller
public class DataController {
	
	@Autowired
	DataService dataService;

	@Autowired
	AccessTypeService accessTypeService;	
	
	@Autowired
	MailClientService emailSender;


	
@RequestMapping("form")
public ModelAndView getForm(@ModelAttribute AccessModel access) {
	  List<AccessTypeModel> accessTypeList = accessTypeService.getList();
	  List<String> typeList = new ArrayList<String>();
	  for(AccessTypeModel type:accessTypeList){
		  if(type.getApproverId()!=null){
			  typeList.add(type.getType());
		 }
		  
		  }
	  return new ModelAndView("form", "typeList", typeList);
}

@RequestMapping("/")
public String root(){
	return "redirect:/login";
}


@RequestMapping("register")
public ModelAndView registerUser(@ModelAttribute AccessModel access) {
	System.out.println(access.getAccessType()+" "+access.getAccessReqAction());
 int id= dataService.insertRow(access);
 
/* String appEmail = dataService.getApproverEmail(id);
 emailSender.sendEmail(appEmail, "Test mail from Spring", "Prema");*/
 List<AccessModel> auser = dataService.getAccessListById(id);
 AccessModel accessuser= auser.get(0);
 String appEmail = dataService.getApproverEmail(id);
 String userEmail = accessuser.getUserEmail();
 emailSender.sendUserEmail(userEmail,appEmail,id, access);
 emailSender.sendEmail(appEmail, accessuser);
 
	return new ModelAndView("redirect:form");
// return new ModelAndView("view","reqId",id);
}

@RequestMapping("list")
public ModelAndView getList() {
	  List<AccessModel> accessList = dataService.getList();
	  return new ModelAndView("list", "accessList", accessList);
	 }


/*
@RequestMapping("view")
public ModelAndView view(@ModelAttribute AccessModel access) {
	 int id= dataService.insertRow(access);
	 String appEmail = dataService.getApproverEmail(id);
	 emailSender.sendEmail(appEmail, "Test mail from Spring", "Prema");
	 emailSender.sendEmail("scsanalyticsportal@gmail.com");
	 AccessModel viewObject = dataService.getRowById(id);
		return new ModelAndView("view","user",viewObject);
	// return new ModelAndView("view","reqId",id);
	}*/

}
