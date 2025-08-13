package com.daniel.stockpredictorml.repository;




import com.daniel.stockpredictorml.models.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>  {
}
