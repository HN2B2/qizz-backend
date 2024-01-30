package tech.qizz.core.category;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.qizz.core.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
