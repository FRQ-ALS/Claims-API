package insurance.claims.demo.repository;

import insurance.claims.demo.dto.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ImageRepository extends JpaRepository<Image, Long> {
}
