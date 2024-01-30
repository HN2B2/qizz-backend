package tech.qizz.core.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
}
