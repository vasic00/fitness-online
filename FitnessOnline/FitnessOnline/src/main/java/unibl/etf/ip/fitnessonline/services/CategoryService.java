package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO addCategory(CategoryDTO category);
    CategoryDTO updateCategory(CategoryDTO category);
    boolean deleteCategory(long category);
    CategoryDTO findById(long id);
    CategoryDTO findByName(String name);
    List<CategoryDTO> getAllSubscribedCategories(long userId);
    CategoryDTO addSubscription(long userId, long categoryId);
    boolean deleteSubscription(long userId, long categoryId);
}
