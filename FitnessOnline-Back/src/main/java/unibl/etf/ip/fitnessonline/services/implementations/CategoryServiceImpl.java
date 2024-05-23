package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.CategoryDAO;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.SpecificAttribute;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.CategoryDTO;
import unibl.etf.ip.fitnessonline.model.dto.SpecificAttributeDTO;
import unibl.etf.ip.fitnessonline.services.CategoryService;
import unibl.etf.ip.fitnessonline.services.ProgramService;
import unibl.etf.ip.fitnessonline.services.SpecificAttributeService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;
    private final SpecificAttributeService specificAttributeService;
    private final ProgramService programService;
    private final ModelMapper  modelMapper;
    private final LoggerBean loggerBean;
    private final UserDAO userDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO, SpecificAttributeService specificAttributeService, ProgramService programService, ModelMapper modelMapper, LoggerBean loggerBean, UserDAO userDAO) {
        this.categoryDAO = categoryDAO;
        this.specificAttributeService = specificAttributeService;
        this.programService = programService;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
        this.userDAO = userDAO;
    }

    public List<CategoryDTO> getAllCategories() {

            List<Category> categories=categoryDAO.findAll();
            return categories.stream().map(c-> {
                CategoryDTO temp=new CategoryDTO();
                temp.setId(c.getId());
                temp.setName(c.getName());
                temp.setSpecificAttributes(c.getSpecificAttributes().stream().map(a -> {
                    SpecificAttributeDTO spec=new SpecificAttributeDTO();
                    spec.setCategoryId(a.getCategory().getId());
                    spec.setId(a.getId());
                    spec.setName(a.getName());
                    return spec;
                }).collect(Collectors.toList()));
                return temp;
            }).collect(Collectors.toList());
    }

    public CategoryDTO addCategory(CategoryDTO category) {
        try {
            Category entity=modelMapper.map(category,Category.class);
            return modelMapper.map(categoryDAO.save(entity),CategoryDTO.class);
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }
    public CategoryDTO updateCategory(CategoryDTO category){
        try{
            Category temp=categoryDAO.findById(category.getId()).get();
            temp.setName(category.getName());
            Category other = categoryDAO.findByName(category.getName());
            if (other != null && other.getId() != category.getId())
                return null;
            return modelMapper.map(categoryDAO.save(temp),CategoryDTO.class);
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    public boolean deleteCategory(long category){
        try{
            Category temp=categoryDAO.findById(category).get();
            for(SpecificAttribute s: temp.getSpecificAttributes())
                specificAttributeService.delete(s.getId());
            for(Program p: temp.getPrograms())
                programService.deleteAdmin(p.getId());
            categoryDAO.deleteById(category);
            return true;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return false;
        }

    }
    public CategoryDTO findById(long id){
        try{
            Category temp=categoryDAO.findById(id).get();
            return modelMapper.map(temp,CategoryDTO.class);
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }
    public CategoryDTO findByName(String name){
        try{
            Category temp=categoryDAO.findByName(name);
            if (temp != null) {
                return modelMapper.map(temp,CategoryDTO.class);
            }
            else return null;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    @Override
    public List<CategoryDTO> getAllSubscribedCategories(long userId) {
        try {
            User user = userDAO.findById(userId).get();
            List<Category> subscribedCategories = user.getSubscribedCategories();
            return subscribedCategories.stream().map(c-> {
                CategoryDTO temp=new CategoryDTO();
                temp.setId(c.getId());
                temp.setName(c.getName());
                temp.setSpecificAttributes(c.getSpecificAttributes().stream().map(a -> {
                    SpecificAttributeDTO spec=new SpecificAttributeDTO();
                    spec.setCategoryId(a.getCategory().getId());
                    spec.setId(a.getId());
                    spec.setName(a.getName());
                    return spec;
                }).collect(Collectors.toList()));
                return temp;
            }).collect(Collectors.toList());

        }
        catch (Exception ex) {
            loggerBean.logError(ex);
            return new ArrayList<CategoryDTO>();
        }
    }

    @Override
    public CategoryDTO addSubscription(long userId, long categoryId) {
        try {
            Category category = categoryDAO.findById(categoryId).get();
            User user = userDAO.findById(userId).get();
            if (!user.getSubscribedCategories().contains(category)) {
                user.getSubscribedCategories().add(category);
                userDAO.save(user);
                return modelMapper.map(category, CategoryDTO.class);
            }
            else return null;
        }
        catch (Exception ex) {
            loggerBean.logError(ex);
            return null;
        }
    }

    @Override
    public boolean deleteSubscription(long userId, long categoryId) {
        try {
            Category category = categoryDAO.findById(categoryId).get();
            User user = userDAO.findById(userId).get();
            if (user.getSubscribedCategories().contains(category)) {
                user.getSubscribedCategories().remove(category);
                userDAO.save(user);
                return true;
            }
            else return false;
        }
        catch (Exception ex) {
            loggerBean.logError(ex);
            return false;
        }
    }
}
