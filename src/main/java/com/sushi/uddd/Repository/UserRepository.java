package com.sushi.uddd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sushi.uddd.Domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save (User user);
    User findByEmail(String email);
}
