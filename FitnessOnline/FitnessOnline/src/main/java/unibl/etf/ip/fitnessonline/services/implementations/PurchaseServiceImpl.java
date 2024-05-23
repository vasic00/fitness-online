package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.ProgramDAO;
import unibl.etf.ip.fitnessonline.dao.PurchaseDAO;
import unibl.etf.ip.fitnessonline.dao.UserDAO;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.Purchase;
import unibl.etf.ip.fitnessonline.model.User;
import unibl.etf.ip.fitnessonline.model.dto.PurchaseDTO;
import unibl.etf.ip.fitnessonline.services.ProgramService;
import unibl.etf.ip.fitnessonline.services.PurchaseService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
    private final ProgramService programService;
    private final ProgramDAO programDAO;
    private final UserDAO userDAO;
    private final PurchaseDAO purchaseDAO;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;

    public PurchaseServiceImpl(ProgramService programService, ProgramDAO programDAO, UserDAO userDAO, PurchaseDAO purchaseDAO, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.programService = programService;
        this.programDAO = programDAO;
        this.userDAO = userDAO;
        this.purchaseDAO = purchaseDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

    public List<PurchaseDTO> getByBuyer(long id){
        try{
            User buyer=userDAO.findById(id).get();
            return purchaseDAO.findByUser(buyer).stream().map((Purchase p) -> {
                PurchaseDTO temp=modelMapper.map(p,PurchaseDTO.class);
                temp.setUserId(id);
                return temp;
            }).collect(Collectors.toList());
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return new ArrayList<>();
        }
    }
    public void delete(long id){
        purchaseDAO.deleteById(id);
    }
    public PurchaseDTO add(PurchaseDTO purchaseDTO){
        try{
            DateFormat df=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            purchaseDTO.setTime(df.format(new Date()));
            System.out.println(purchaseDTO.getUserId());
            User buyer=userDAO.findById(purchaseDTO.getUserId()).get();
            Program program=programDAO.findById(purchaseDTO.getProgramId()).get();
            if(buyer.getId()==program.getCreator().getId() || buyer.getParticipatedPrograms().contains(program))
                throw  new Exception();
            Purchase purchase=modelMapper.map(purchaseDTO,Purchase.class);
            purchase.setProgramName(program.getName());
            purchase.setProgramPrice(program.getPrice());
            purchase.setProgramCategory(program.getCategory().getName());
            Purchase result=purchaseDAO.save(purchase);
            buyer.getParticipatedPrograms().add(program);
            userDAO.save(buyer);
            //ne vraca id od programa
            PurchaseDTO temp=modelMapper.map(result,PurchaseDTO.class);
            return temp;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }
}
