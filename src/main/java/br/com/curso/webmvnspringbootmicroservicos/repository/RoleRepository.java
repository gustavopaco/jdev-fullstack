package br.com.curso.webmvnspringbootmicroservicos.repository;

import br.com.curso.webmvnspringbootmicroservicos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query(value = "select r from Role r where r.authority = 'ROLE_USER'")
    Role findRoleDefault();
}
