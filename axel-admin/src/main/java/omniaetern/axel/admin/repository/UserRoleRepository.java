package omniaetern.axel.admin.repository;

import omniaetern.axel.model.UserRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleRelation, Long>, JpaSpecificationExecutor<UserRoleRelation> {
}
