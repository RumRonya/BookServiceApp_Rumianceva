package com.ifortex.bookservice.impl;

import com.ifortex.bookservice.dao.MemberDAO;
import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public Member findMember() {
        return memberDAO.getTheYoungestReaderTheOldestRomance();
    }

    @Override
    public List<Member> findMembers() {
       return memberDAO.getMembersFrom2023WithoutBooks();
    }
}
