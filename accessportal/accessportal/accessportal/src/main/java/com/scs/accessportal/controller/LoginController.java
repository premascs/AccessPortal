package com.scs.accessportal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.AccessTypeModel;
import com.scs.accessportal.model.ApproverModel;
import com.scs.accessportal.service.AccessTypeService;
import com.scs.accessportal.service.ApproverService;
import com.scs.accessportal.service.DataService;
import com.scs.accessportal.service.MailClientService;
import com.scs.accessportal.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private ApproverService userService;

	@Autowired
	DataService dataService;
	
	@Autowired
	AccessTypeService accessTypeService;

	@Autowired
	MailClientService emailSender;
	
	@Autowired
	ApproverService appService;
	
	@Autowired
	private UserService resetService;

	private String token;

	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/forgotpwdmessage", method = RequestMethod.GET)
	public ModelAndView forgotpwdmessage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgotpwdmessage");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		ApproverModel user = userService.findUserByEmail(auth.getName());
		int appId=user.getId();
		System.out.println(appId);
		 List<AccessModel> accessList = dataService.getListById(appId);
		 List<AccessModel> pendingReqList = new ArrayList<>();
			for(AccessModel a: accessList){
			if(a.getStatus().equals("pending")){
				
				pendingReqList.add(a);
			}
			}
		modelAndView.addObject("role",user.getActive());
		ApproverModel app = new ApproverModel();
		List<AccessTypeModel> accessTypeList = accessTypeService.getList();
		  List<String> typeList = new ArrayList<String>();
		  for(AccessTypeModel type:accessTypeList){
			  //if(type.getApproverId()==null){
				  typeList.add(type.getType());
			// }	  
			  }
		  List<ApproverModel> appList =appService.getApproverList();
		  Map <String, ApproverModel> hm =new HashMap<>();
		
		  if(!appList.isEmpty()){
		  for(ApproverModel a: appList){
			  System.out.println("inside id"+a.getId());
					
					List<AccessTypeModel> at=appService.getAccessTypeByAppId(a.getId());
					if (at.isEmpty()){
						
						System.out.println("+++++++++++No Approver");
					}else{
						//System.out.println("dddddddd"+a.getId());
						String type=at.get(0).getType();
						hm.put(type, a);
				}
					
		  modelAndView.addObject("appList",hm);
		  }
		  }
		
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("aReq", pendingReqList);
		modelAndView.addObject("typeList",typeList);
		
		modelAndView.addObject("approverModel", app);
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView createNewUser(@ModelAttribute ApproverModel approverModel, @RequestParam String type) {
		ModelAndView modelAndView = new ModelAndView();
		//ApproverModel userExists = userService.findUserByEmail(user.getEmail());
	/*	if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("home");
		} else {*/
			//System.out.println(approverModel.getEmail());
			//System.out.println("++++"+type);
		
		
			userService.saveUser(approverModel);
			accessTypeService.setApproverIdByAccessType(approverModel.getId(), type);
		/*****************************Add Administrator ****************************************/
			//appService.saveAdmin(approverModel);
		/***************************************************************************************/
			
		
			//int appId=user.getId();
			//accessType.setApproverId(appId);
			//System.out.println("+++++++++++"+id);
			//userService.saveAccessType(accessType);
			/*userService.saveUser(user);
			
			int appId=user.getId();
			//accessType.setApproverId(appId);
			AccessTypeModel app =new AccessTypeModel();
			app.setApproverId(appId);
			app.setType(accessType.getType());
			//accessTypeService.setAccessTypeByApprover(appId, accessType.getType());
			userService.saveAccessType(app);	*/
			//modelAndView.addObject("successMessage", "User has been registered successfully");
			//modelAndView.addObject("user", new ApproverModel());
			modelAndView.setViewName("home");
			
		/*}*/
		
		return new ModelAndView("redirect:/home");
	}
	
	
	
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.GET)
	public ModelAndView approve(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		List<AccessModel> accessModel= dataService.getAccessListById(id);
		AccessModel acc =accessModel.get(0);
		
		if(acc.getAccessReqAction().equals("Add")){
		acc.setStatus("approved");
		String userEmail=acc.getUserEmail();
		dataService.save(acc);
		emailSender.sendRequestStatusEmail(userEmail, acc);
		}
		
		if(acc.getAccessReqAction().equals("Remove")){
			acc.setStatus("approved");
			String userEmail=acc.getUserEmail();
			emailSender.sendRequestStatusEmail(userEmail, acc);
			dataService.deleteReqById(id);
			
			
			}
		
		if(acc.getAccessReqAction().equals("Change")){
			acc.setStatus("approved");
			String userEmail=acc.getUserEmail();
			dataService.save(acc);
			emailSender.sendRequestStatusEmail(userEmail, acc);
			}
		
		modelAndView.setViewName("home");
		return new ModelAndView("redirect:/home");
		
	}
	
	@RequestMapping(value = "/decline/{id}", method = RequestMethod.GET)
	public ModelAndView decline(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		List<AccessModel> accessModel= dataService.getAccessListById(id);
		AccessModel acc =accessModel.get(0);
		acc.setStatus("declined");
		String userEmail=acc.getUserEmail();
		dataService.save(acc);
		emailSender.sendRequestStatusEmail(userEmail, acc);
		dataService.deleteReqById(id);
		modelAndView.setViewName("home");
		  return new ModelAndView("redirect:/home");
	}  
	

@RequestMapping(value = "/searchview/{id}", method = RequestMethod.POST)
public ResponseEntity<?>  displayApprover(@PathVariable ("id") String id, HttpServletRequest request) {
	ApproverModel app= userService.getApproverById(Integer.parseInt(id));
	List<AccessTypeModel> at = appService.getAccessTypeByAppId(Integer.parseInt(id));
	if (at.isEmpty()){
		String status="No";
		return ResponseEntity.ok(status);
	}else{
	String type=at.get(0).getType();
	//String type=appService.getAccessTypeByApprover(Integer.parseInt(id));	
	Map <String, Object> hm= new HashMap<>();
	hm.put(type,app);
	return ResponseEntity.ok(hm);
	}
}
@RequestMapping(value = "/updateview/{id}", method = RequestMethod.POST)
public ResponseEntity<?>  updateApprover(@PathVariable ("id") String id, HttpServletRequest request) {
	ApproverModel app= userService.getApproverById(Integer.parseInt(id));
	List<AccessTypeModel> at = appService.getAccessTypeByAppId(Integer.parseInt(id));
	if (at.isEmpty()){
		String status="No";
		return ResponseEntity.ok(status);
	}else{
	String type=at.get(0).getType();
	//String type=appService.getAccessTypeByApprover(Integer.parseInt(id));	
	List<AccessTypeModel> accessTypeList = accessTypeService.getList();
	  List<String> typeList = new ArrayList<String>();
	  typeList.add(type);
	  for(AccessTypeModel ty:accessTypeList){
		  if(ty.getApproverId()==null){
			  
			  typeList.add(ty.getType());
		 }
		  
		  }	
	
	
//Map <String, Object> hm= new HashMap<>();
	  Map <String, Object> hm= new HashMap<String, Object>();
hm.put(type, app);
	return ResponseEntity.ok(hm);
	}
}

@RequestMapping(value = "/deleteview/{id}", method = RequestMethod.POST)
public ResponseEntity<?>  deleteApprover(@PathVariable ("id") String id, HttpServletRequest request) {
ApproverModel app= userService.getApproverById(Integer.parseInt(id));
List<AccessTypeModel> at = appService.getAccessTypeByAppId(Integer.parseInt(id));
if (at.isEmpty()){
	String status="No";
	return ResponseEntity.ok(status);
}else{
String type=at.get(0).getType();
//String type=appService.getAccessTypeByApprover(Integer.parseInt(id));	
Map <String, Object> hm= new HashMap<>();
hm.put(type,app);
return ResponseEntity.ok(hm);
}
}

@RequestMapping(value = "/deleteapprover/{id}", method = RequestMethod.POST)
public ResponseEntity<?>  deleteApproverData(@PathVariable ("id") String id, HttpServletRequest request) {
 appService.deleteApproverById(Integer.parseInt(id));
 accessTypeService.updateApproverIdToNull(Integer.parseInt(id));
//String type=appService.getAccessTypeByApprover(Integer.parseInt(id));	
String status ="deleted";
return ResponseEntity.ok(status);
	
}


@RequestMapping(value = "/updateapprover", method = RequestMethod.POST)
public ResponseEntity<?>  updateApproverData(HttpServletRequest request) throws IOException {
	//String arr = request.getParameter("uappname");	
	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String json = "";
    if(br != null){
        json = br.readLine();
    }
	
	ObjectMapper mapper = new ObjectMapper();
	ApproverModel aModel = mapper.readValue(json, ApproverModel.class);
	//AccessTypeModel typeModel = mapper.readValue(json, AccessTypeModel.class);
	
	//AccessTypeModel newtype= userService.
	ApproverModel newapp = userService.getApproverById(aModel.getId());
	if(!newapp.getName().equals(aModel.getName())){
		newapp.setName(aModel.getName());
	}
	if(!newapp.getLastName().equals(aModel.getLastName())){
		newapp.setLastName(aModel.getLastName());
	}
	if(!newapp.getEmail().equals(aModel.getEmail())){
		newapp.setEmail(aModel.getEmail());
	}
	
	userService.saveUser(newapp);
String saved="saved";
	return ResponseEntity.ok(saved);
	
}

@RequestMapping(value = "/updateaccesstype", method = RequestMethod.POST)
public ResponseEntity<?>  updateAccessTypeData(HttpServletRequest request) throws IOException {
	//String arr = request.getParameter("uappname");
	
	
	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String json = "";
    if(br != null){
        json = br.readLine();
    }
	
	ObjectMapper mapper = new ObjectMapper();
	//ApproverModel aModel = mapper.readValue(json, ApproverModel.class);
	AccessTypeModel typeModel = mapper.readValue(json, AccessTypeModel.class);
	
	//System.out.println(typeModel.getApproverId());
	//int appid = typeModel.getApproverId();
	//appService.getAccessTypeByAppId(appid);
//System.out.println("dddddddddddddd"+typeModel.getType());
List<AccessTypeModel> at = appService.getAccessTypeByAppId(typeModel.getApproverId());
int oldTypeId=at.get(0).getId();

System.out.println("olddddd"+oldTypeId);

//List<AccessTypeModel>acc=accessTypeService.findRowByAccessType(typeModel.getType());
//int accessTypeId=acc.get(0).getId();
//System.out.println("ddfd"+acc.get(0));
String saved="saved";
	accessTypeService.updateApproverIdToNull(oldTypeId);
	
	accessTypeService.setApproverIdByAccessType(typeModel.getApproverId(), typeModel.getType());
//String saved="saved";
	return ResponseEntity.ok(saved);
	
}



	//----Reset Password
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// Display forgotPassword page
	
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgotpassword");
		return modelAndView;
    }
    
	
	
    // Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {
		//System.out.println("Hi");
		// Lookup user in database by e-mail
		Optional<ApproverModel> optional = resetService.findUserByEmail(userEmail);

		if (!optional.isPresent()) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} else {
			
			// Generate random 36-character string token for reset password 
			ApproverModel user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			resetService.save(user);

			String appUrl = request.getScheme() + "://" + request.getServerName()+":"+ request.getLocalPort();
			
			String token=user.getResetToken();
			String uto =user.getEmail();
			emailSender.sendResetPassword(uto, token,appUrl);
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
		}

		modelAndView.setViewName("redirect:forgotpwdmessage");
		return modelAndView;

	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, @RequestParam("token") String token) {
		
		Optional<ApproverModel> user = resetService.findUserByResetToken(requestParams.get("token"));
		setToken(requestParams.get("token"));
		if (user.isPresent()) { // Token found in DB
			modelAndView.addObject("resetToken", token);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
		System.out.println("token========================"+requestParams.toString());
		// Find the user associated with the reset token
		Optional<ApproverModel> user = resetService.findUserByResetToken(getToken());

	
		if (user.isPresent()) {
			
			ApproverModel resetUser = user.get();
			System.out.println("user========="+resetUser);
			if(requestParams.get("password").equals(requestParams.get("confirmpassword"))){
				String pswd= bCryptPasswordEncoder.encode(requestParams.get("password"));
				resetUser.setPassword(pswd);
			}
				
			
			resetUser.setResetToken(null);

			// Save user
			resetService.save(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;
			
		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");	
		}
		
		return modelAndView;
   }


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
   
  
}
	
// Going to reset page without a token redirects to login page
	/*@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}*/
	/*@RequestMapping(value="/main/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApproverModel user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("role",user.getActive());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content available Only for approver");
		modelAndView.setViewName("main/home");
		return modelAndView;
	}
	*/

