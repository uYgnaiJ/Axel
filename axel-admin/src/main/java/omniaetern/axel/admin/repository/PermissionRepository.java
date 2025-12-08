package omniaetern.axel.admin.repository;

import omniaetern.axel.model.PermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionDO, Long>, JpaSpecificationExecutor<PermissionDO> {

}
