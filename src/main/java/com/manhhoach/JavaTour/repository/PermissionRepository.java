package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
