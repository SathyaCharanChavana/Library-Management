package com.example.library_management.service;
import com.example.library_management.model.Member;
import com.example.library_management.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // Add a new member
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    // Get a member by email
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // Update member details
    public Member updateMember(Long id, Member updatedMember) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member existingMember = memberOptional.get();
            existingMember.setFirstName(updatedMember.getFirstName());
            existingMember.setLastName(updatedMember.getLastName());
            existingMember.setEmail(updatedMember.getEmail());
            return memberRepository.save(existingMember);
        }
        return null; // Or throw exception
    }

    // Delete a member
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
