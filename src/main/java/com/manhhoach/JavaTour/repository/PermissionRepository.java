package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
            SELECT DISTINCT p.code
            FROM user_role ur 
            JOIN role r ON ur.role_id = r.id
            JOIN role_permission rp ON r.id = rp.role_id
            JOIN permission p ON rp.permission_id = p.id
            WHERE ur.user_id = :userId
            """, nativeQuery = true)
    Set<String> getPermissionsByUserId(Long userId);
}
