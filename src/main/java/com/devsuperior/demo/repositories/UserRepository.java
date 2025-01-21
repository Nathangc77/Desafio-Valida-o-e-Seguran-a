package com.devsuperior.demo.repositories;

import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            SELECT u.email AS username, u.password, role.id AS roleId, role.authority
            FROM tb_user AS u
            INNER JOIN tb_user_role AS userRole ON u.id = userRole.user_id
            INNER JOIN tb_role AS role ON userRole.role_id = role.id
            WHERE u.email = :email
            """)
    List<UserDetailsProjection> searchUserRolesByUsername(String email);
}
