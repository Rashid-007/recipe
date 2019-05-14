package throne.springreacto.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import throne.springreacto.recipe.domain.Category;
import throne.springreacto.recipe.domain.Recipe;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
