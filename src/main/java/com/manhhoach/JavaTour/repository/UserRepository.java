package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            Select u from User u join fetch u.roles r join fetch r.permissions where u.id = :id
            """)
    Optional<User> getDetail(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
