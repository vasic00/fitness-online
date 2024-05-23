package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.AttributeDAO;
import unibl.etf.ip.fitnessonline.dao.CategoryDAO;
import unibl.etf.ip.fitnessonline.dao.ProgramDAO;
import unibl.etf.ip.fitnessonline.dao.SpecificAttributeDAO;
import unibl.etf.ip.fitnessonline.model.Attribute;
import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.SpecificAttribute;
import unibl.etf.ip.fitnessonline.model.dto.AttributeDTO;
import unibl.etf.ip.fitnessonline.model.dto.SpecificAttributeDTO;
import unibl.etf.ip.fitnessonline.services.SpecificAttributeService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SpecificAttributeServiceImpl implements SpecificAttributeService {
    private final SpecificAttributeDAO specificAttributeDAO;
    private final AttributeDAO attributeDAO;
    private final CategoryDAO categoryDAO;
    private final ProgramDAO programDAO;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;

    public SpecificAttributeServiceImpl(SpecificAttributeDAO specificAttributeDAO, CategoryDAO categoryDAO, ProgramDAO programDAO, ModelMapper modelMapper, AttributeDAO attributeDAO, LoggerBean loggerBean) {
        this.specificAttributeDAO = specificAttributeDAO;
        this.categoryDAO = categoryDAO;
        this.programDAO = programDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
        this.attributeDAO = attributeDAO;
    }

    public boolean delete(long id) {
        try {
            SpecificAttribute specificAttribute = specificAttributeDAO.findById(id).get();
            List<Attribute> attributes = attributeDAO.findAllBySpecificAttribute(specificAttribute);
            attributeDAO.deleteAll(attributes);
            specificAttributeDAO.deleteById(id);
            return true;
        } catch (Exception e) {
            loggerBean.logError(e);
            return false;
        }
    }


    @Override
    public SpecificAttributeDTO update(SpecificAttributeDTO specificAttributeDTO) {
        try {
            System.out.println(specificAttributeDTO.getId());
            SpecificAttribute temp = specificAttributeDAO.findById(specificAttributeDTO.getId()).get();
            temp.setName(specificAttributeDTO.getName());
            SpecificAttribute result = specificAttributeDAO.save(temp);
            SpecificAttributeDTO resultDTO=new SpecificAttributeDTO();
            resultDTO.setName(result.getName());
            resultDTO.setId(result.getId());
            resultDTO.setCategoryId(result.getCategory().getId());
            return resultDTO;
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }

    @Override
    public SpecificAttributeDTO add(SpecificAttributeDTO specificAttributeDTO) {
        try {
            SpecificAttribute temp = new SpecificAttribute();
            temp.setName(specificAttributeDTO.getName());
            Category category = categoryDAO.findById(specificAttributeDTO.getCategoryId()).get();
            temp.setCategory(category);
            SpecificAttribute result = specificAttributeDAO.save(temp);
            SpecificAttributeDTO resultDTO = new SpecificAttributeDTO();
            resultDTO.setName(result.getName());
            resultDTO.setId(result.getId());
            resultDTO.setCategoryId(result.getCategory().getId());
            return resultDTO;
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }

    @Override
    public List<SpecificAttributeDTO> getAllSpecificAttributesOfCategory(long id) {
        try {
            Category category = categoryDAO.findById(id).get();
            List<SpecificAttribute> specificAttributes = specificAttributeDAO.findAllByCategory(category);
            List<SpecificAttributeDTO> specificAttributeDTOs = new ArrayList<>();
            specificAttributes.forEach(sa -> specificAttributeDTOs.add(modelMapper.map(sa, SpecificAttributeDTO.class)));
            return specificAttributeDTOs;
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }
}
