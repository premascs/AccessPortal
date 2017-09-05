package com.scs.accessportal.service;
import java.util.Optional;

import com.scs.accessportal.model.ApproverModel;

public  interface UserService {
    public Optional<ApproverModel> findUserByEmail(String email);
    public Optional<ApproverModel> findUserByResetToken(String resetToken);
    public void save(ApproverModel user);
}
