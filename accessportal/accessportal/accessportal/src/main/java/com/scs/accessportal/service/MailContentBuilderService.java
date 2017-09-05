package com.scs.accessportal.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.scs.accessportal.model.AccessModel;
import com.scs.accessportal.model.ApproverModel;

@Service

public class MailContentBuilderService {
	private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilderService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(AccessModel user) {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("view", context);
    }
    
    
    public String userEmailBuild(String appEmail,AccessModel model) {
        Context context = new Context();
        context.setVariable("approver", appEmail);
        context.setVariable("user", model);
        return templateEngine.process("umail", context);
    }
    
    public String AutoPassword(String password, ApproverModel approver) {
        Context context = new Context();
        context.setVariable("approver", approver);
        context.setVariable("password", password);
        return templateEngine.process("auto", context);
    }

	public String requestStatusEmailBuild(AccessModel access) {
		// TODO Auto-generated method stub
		Context context = new Context();
        context.setVariable("user", access);
        return templateEngine.process("status", context);
	}
    
}
