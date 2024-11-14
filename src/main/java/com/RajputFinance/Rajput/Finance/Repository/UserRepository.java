package com.RajputFinance.Rajput.Finance.Repository;

import com.RajputFinance.Rajput.Finance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    List<User> findAllByIsAdmin(boolean isAdmin);
}
