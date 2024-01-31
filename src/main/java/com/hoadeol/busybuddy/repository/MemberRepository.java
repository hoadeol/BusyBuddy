package com.hoadeol.busybuddy.repository;

import com.hoadeol.busybuddy.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
