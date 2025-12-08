package omniaetern.axel.admin.repository;

import omniaetern.axel.model.RolePermissionRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionRelation, Long>, JpaSpecificationExecutor<RolePermissionRelation> {
}
