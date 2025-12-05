package omniaetern.axel.admin.repository;

import omniaetern.axel.model.DepartmentDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentDO, Long>, JpaSpecificationExecutor<DepartmentDO> {
    DepartmentDO findDepartmentDOByName(String name);
}
