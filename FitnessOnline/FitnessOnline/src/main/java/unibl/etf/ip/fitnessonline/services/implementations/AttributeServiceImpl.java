package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.AttributeDAO;
import unibl.etf.ip.fitnessonline.dao.ProgramDAO;
import unibl.etf.ip.fitnessonline.dao.SpecificAttributeDAO;
import unibl.etf.ip.fitnessonline.model.Attribute;
import unibl.etf.ip.fitnessonline.model.AttributeEmbeddedId;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.SpecificAttribute;
import unibl.etf.ip.fitnessonline.model.dto.AttributeDTO;
import unibl.etf.ip.fitnessonline.services.AttributeService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;


@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {

    private final SpecificAttributeDAO specificAttributeDAO;
    private final AttributeDAO attributeDAO;
    private final ProgramDAO programDAO;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;

    public AttributeServiceImpl(SpecificAttributeDAO specificAttributeDAO, AttributeDAO attributeDAO, ProgramDAO programDAO, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.specificAttributeDAO = specificAttributeDAO;
        this.attributeDAO = attributeDAO;
        this.programDAO = programDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }


    @Override
    public AttributeDTO add(AttributeDTO attributeDTO) {
        Attribute attribute = new Attribute();
        try{
            Program program=programDAO.findById(attributeDTO.getProgramId()).get();
            boolean flag=true;
            for(SpecificAttribute sa : program.getCategory().getSpecificAttributes())
                if(sa.getId() == attributeDTO.getSpecificAttributeId()){
                    flag=false;
                    attribute.setSpecificAttribute(sa);
                    break;
                }
            if(flag){
                System.out.println("Forbidden attribute");
                return null;
            }
            attribute.setProgram(program);
            attribute.setValue(attributeDTO.getValue());
            AttributeEmbeddedId attributeEmbeddedId = new AttributeEmbeddedId();
            attributeEmbeddedId.setProgramId(attribute.getProgram().getId());
            attributeEmbeddedId.setSpecificAttributeId(attribute.getSpecificAttribute().getId());
            attribute.setId(attributeEmbeddedId);
            attributeDAO.save(attribute);
            AttributeDTO resultDTO = new AttributeDTO();
            resultDTO.setSpecificAttributeId(attribute.getSpecificAttribute().getId());
            resultDTO.setProgramId(attribute.getProgram().getId());
            resultDTO.setValue(attribute.getValue());
            return resultDTO;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    @Override
    public void delete(long programId, long specificAttributeId) {
        try {
            SpecificAttribute sa = specificAttributeDAO.findById(specificAttributeId).get();
            Program program = programDAO.findById(programId).get();
            attributeDAO.deleteByProgramAndSpecificAttribute(program, sa);

        }
        catch (Exception ex) {
            loggerBean.logError(ex);
        }
    }
}
