package com.school.schoolstock.domain.auth.service;

import com.school.schoolstock.domain.auth.dto.request.AddMemberRequest;

public interface UserService {
    boolean addMember(AddMemberRequest request);
}
