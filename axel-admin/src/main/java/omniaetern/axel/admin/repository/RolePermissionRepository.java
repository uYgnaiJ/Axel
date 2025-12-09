package omniaetern.axel.admin.repository;

import omniaetern.axel.model.RolePermissionRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionRelation, Long>, JpaSpecificationExecutor<RolePermissionRelation> {
    List<RolePermissionRelation> findAllByPermission_Id(Long permissionId);

    List<RolePermissionRelation> findAllByRole_Id(Long roleId);

    List<RolePermissionRelation> findAllByRole_IdIn(List<Long> roleIds);
}
