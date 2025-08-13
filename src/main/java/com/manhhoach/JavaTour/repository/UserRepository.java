package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            Select u from User u join fetch u.roles r join fetch r.permissions where u.id = :id
            """)
    Optional<User> getDetail(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = """
            SELECT DISTINCT p.code
            FROM user u
            JOIN user_role ur ON u.id = ur.user_id
            JOIN role r ON ur.role_id = r.id
            JOIN role_permission rp ON r.id = rp.role_id
            JOIN permission p ON rp.permission_id = p.id
            WHERE u.username = :username
            """, nativeQuery = true)
    Set<String> getPermissionsByUsername(String username);


}
