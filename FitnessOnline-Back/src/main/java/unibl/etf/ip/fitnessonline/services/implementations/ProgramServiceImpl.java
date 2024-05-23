package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.*;
import unibl.etf.ip.fitnessonline.model.*;
import unibl.etf.ip.fitnessonline.model.dto.*;
import unibl.etf.ip.fitnessonline.services.AttributeService;
import unibl.etf.ip.fitnessonline.services.ProgramImageService;
import unibl.etf.ip.fitnessonline.services.ProgramService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final ProgramDAO programDAO;
    private final UserDAO userDAO;
    private final CategoryDAO categoryDAO;
    private final ModelMapper modelMapper;
    private final AttributeService attributeService;
    private final CommentDAO commentDAO;
    private final AttributeDAO attributeDAO;
    private final ProgramImageDAO programImageDAO;
    private final ProgramImageService programImageService;
    private final LoggerBean loggerBean;

    public ProgramServiceImpl(UserDAO userDAO, ProgramDAO programDAO, CategoryDAO categoryDAO, ModelMapper modelMapper, AttributeService attributeService, CommentDAO commentDAO, AttributeDAO attributeDAO, ProgramImageDAO programImageDAO, ProgramImageService programImageService, LoggerBean loggerBean) {
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.modelMapper = modelMapper;
        this.programDAO = programDAO;
        this.attributeService = attributeService;
        this.commentDAO = commentDAO;
        this.attributeDAO = attributeDAO;
        this.programImageDAO = programImageDAO;
        this.programImageService = programImageService;
        this.loggerBean = loggerBean;
    }

    private ProgramDTO map(Program program) {
        ProgramDTO result = new ProgramDTO();
        result.setId(program.getId());
        result.setContact(program.getContact());
        result.setCategory(program.getCategory().getName());
        result.setDescription(program.getDescription());
        result.setDifficultyLevel(program.getDifficultyLevel());
        result.setInstructorInfo(program.getInstructorInfo());
        result.setLocation(program.getLocation());
        result.setName(program.getName());
        result.setPrice(program.getPrice());
        result.setEnding(program.getEnding());
        if (program.getComments() != null)
            result.setComments(program.getComments().stream().map((Comment c) -> {
                CommentDTO commentDTO = modelMapper.map(c, CommentDTO.class);
                commentDTO.setProgramId(program.getId());
//                CreatorInfo commentCreatorInfo = new CreatorInfo();
//                commentCreatorInfo.setInfo(c.getCreator().getFirstname() + " " + c.getCreator().getLastname());
//                commentCreatorInfo.setId(c.getCreator().getId());
//                System.out.println("COMMENT CREATOR INFO: " + commentCreatorInfo);
                commentDTO.setCreatorId(c.getCreator().getId());
                commentDTO.setCreatorInfo(c.getCreator().getFirstname() + " " + c.getCreator().getLastname());
                commentDTO.setCreatorAvatar(c.getCreator().getAvatar());
                return commentDTO;
            }).collect(Collectors.toList()));
        if (program.getAttributes() != null)
            result.setAttributes(program.getAttributes().stream().map((Attribute a) -> {
                AttributeDTO attributeDTO = modelMapper.map(a, AttributeDTO.class);
                attributeDTO.setProgramId(program.getId());
                return attributeDTO;
            }).collect(Collectors.toList()));
        if (program.getImages() != null)
            result.setImages(program.getImages().stream().map((ProgramImage pi) -> {
                ProgramImageDTO programImageDTO = modelMapper.map(pi, ProgramImageDTO.class);
                programImageDTO.setProgramId(program.getId());
                return programImageDTO;
            }).collect(Collectors.toList()));
     //   System.out.println(program.getCategory());
     //   System.out.println(program.getCategory().getName());
        result.setCategory(program.getCategory().getName());
        CreatorInfo info = new CreatorInfo();
        info.setId(program.getCreator().getId());
        info.setInfo(program.getCreator().getFirstname() + " " + program.getCreator().getLastname());
        result.setCreator(info);
        return result;
    }

    @Override
    public ProgramDTOPage getAllPrograms(Pageable pageable) {
        Page<Program> page = programDAO.findAll(pageable);
        ProgramDTOPage result = new ProgramDTOPage();
        result.setPrograms(page.getContent().stream().map(this::map).collect(Collectors.toList()));
        result.setIndex(page.getNumber());
        result.setTotalPages(page.getTotalPages());
        result.setTotalElements(page.getTotalElements());
        return result;
    }

    @Override
    public ProgramDTOPage getProgramsByCreator(long id, Pageable pageable) {
        try {
            User creator = userDAO.findById(id).get();
            Page<Program> page = programDAO.findByCreator(creator, pageable);
            ProgramDTOPage result = new ProgramDTOPage();
            result.setPrograms(page.getContent().stream().map(this::map).collect(Collectors.toList()));
            result.setIndex(page.getNumber());
            result.setTotalPages(page.getTotalPages());
            result.setTotalElements(page.getTotalElements());
            return result;
        } catch (Exception e) {
            loggerBean.logError(e);
            ProgramDTOPage temp = new ProgramDTOPage();
            temp.setIndex(0);
            temp.setTotalElements(0);
            temp.setTotalPages(1);
            temp.setPrograms(new ArrayList<ProgramDTO>());
            return temp;
        }
    }

    @Override
    public boolean sellProgram(long id) {
        return false;
    }

    @Override
    public ProgramDTO add(ProgramDTO program, String rand) {
        try {
            User creator = userDAO.findById(program.getCreator().getId()).get();
            Category category = categoryDAO.findByName(program.getCategory());
            Program programEntity = modelMapper.map(program, Program.class);
            programEntity.setCategory(category);
            programEntity.setCreator(creator);

            List<Attribute> preAttrs = programEntity.getAttributes();
            programEntity.setAttributes(null);

            Program result = programDAO.save(programEntity);
            if(preAttrs!=null){
                for (Attribute a : preAttrs) {
                    a.setProgram(result);
                    AttributeEmbeddedId embeddedId = new AttributeEmbeddedId();
                    embeddedId.setSpecificAttributeId(a.getSpecificAttribute().getId());
                    embeddedId.setProgramId(result.getId());
                    a.setId(embeddedId);
                    if (result.getAttributes() == null)
                        result.setAttributes(new ArrayList<>());
                    result.getAttributes().add(a);
                    attributeDAO.save(a);
                }
                program.setId(result.getId());
                programDAO.save(result);
            }
            List<String> images=programImageService.storeImages(rand,result.getId());
            images.stream().forEach((String i) -> {
                ProgramImage temp=new ProgramImage();
                temp.setProgram(result);
                temp.setImg(i);
                programImageService.add(temp);
            });
            return map(result);
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    @Override
    public boolean delete(long id, long user) {
        try {
            Program program = programDAO.findById(id).get();
            if (program.getCreator().getId() == user) {
                List<Comment> comments=program.getComments();
                if(comments!=null && !comments.isEmpty())
                    commentDAO.deleteAll(comments);
                List<Attribute> attrs=program.getAttributes();
                if(attrs!=null && !attrs.isEmpty())
                    attributeDAO.deleteAll(attrs);
                List<ProgramImage> images=program.getImages();
                if(images!=null && !images.isEmpty())
                    programImageDAO.deleteAll(images);
                programDAO.deleteById(id);
                return true;
            } else return false;
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return false;
        }
    }

    @Override
    public void deleteAdmin(long id) {
        programDAO.deleteById(id);
    }

    @Override
    public ProgramDTO getById(long id) {
        return null;
    }

    @Override
    public boolean checkIfParticipated(long id, JwtUser jwtUser) {
        User user = userDAO.findById(jwtUser.getId()).orElse(null);
        Program program = programDAO.findById(id).orElse(null);
        if (user != null && program != null) {
            return user.getParticipatedPrograms().contains(program);
        }
        return false;
    }

    @Override
    public List<Long> getParticipations(JwtUser jwtUser) {
        try {
            User user = userDAO.findById(jwtUser.getId()).get();
            ArrayList<Long> participatedProgramsIds = new ArrayList<>();
            user.getParticipatedPrograms().forEach(program -> {
                participatedProgramsIds.add(program.getId());
            });
            return participatedProgramsIds;
        }
        catch (Exception e) {
            //
            return new ArrayList<>();
        }
    }

    @Override
    public ProgramDTOPage getFiltered(double p1, double p2, String valid, String categoryName, String name, int difficultyLevel, JwtUser jwtUser, Pageable pageable) {

        if (valid.equals("true") && jwtUser != null)
            return getFilteredValid(p1, p2, valid, categoryName, name, difficultyLevel, jwtUser, pageable);

        Page<Program> temp = null;
        List<Program> result = new ArrayList<>();
        Category category = categoryDAO.findByName(categoryName);

        if (p1 == -1)
            p1 = 0;
        if (p2 == -1)
            p2 = Double.MAX_VALUE;

        try {
            if ("-".equals(name)) {
                if (difficultyLevel >= 0) {
                    if (category != null) {
                        System.out.println("price, category, difficultyLevel");
                        temp = programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndEndingGreaterThan(p1,p2,category,difficultyLevel, LocalDateTime.now(),pageable);
                    }
                    else {
                        System.out.println("price, difficultyLevel");
                        temp = programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndEndingGreaterThan(p1,p2,difficultyLevel, LocalDateTime.now(),pageable);
                    }
                }
                else {
                    if (category != null) {
                        System.out.println("price, category");
                        temp = programDAO.findByPriceBetweenAndCategoryAndEndingGreaterThan(p1,p2,category,LocalDateTime.now(),pageable);
                    }
                    else {
                        System.out.println("price");
                        temp = programDAO.findByPriceBetweenAndEndingGreaterThan(p1,p2,LocalDateTime.now(),pageable);
                    }
                }
            }
            else {
                if (difficultyLevel >= 0) {
                    if (category != null) {
                        System.out.println("price, category, difficultyLevel, name");
                        temp = programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThan(p1,p2,category,difficultyLevel,name,LocalDateTime.now(), pageable);
                    }
                    else {
                        System.out.println("price, difficultyLevel, name");
                        temp = programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThan(p1,p2,difficultyLevel,name,LocalDateTime.now(),pageable);
                    }
                }
                else {
                    if (category != null) {
                        System.out.println("price, category, name");
                        temp = programDAO.findByPriceBetweenAndCategoryAndNameContainingAndEndingGreaterThan(p1,p2,category,name,LocalDateTime.now(),pageable);
                    }
                    else {
                        System.out.println("price, name");
                        temp = programDAO.findByPriceBetweenAndNameContainingAndEndingGreaterThan(p1,p2,name,LocalDateTime.now(),pageable);
                    }
                }
            }
            result = new ArrayList<>(temp.getContent());

//            if (jwtUser != null && "true".equals(valid)) {
//                    User user = userDAO.findById(jwtUser.getId()).get();
//                    result.removeAll(user.getPrograms());
//                    result.removeAll(user.getParticipatedPrograms());
//            }
        }
        catch (Exception ex) {
            loggerBean.logError(ex);
            ProgramDTOPage tempDTOPage = new ProgramDTOPage();
            tempDTOPage.setIndex(0);
            tempDTOPage.setTotalElements(0);
            tempDTOPage.setTotalPages(1);
            tempDTOPage.setPrograms(new ArrayList<ProgramDTO>());
            return tempDTOPage;
        }

        List<ProgramDTO> resultDTO = result.stream().map(this::map).collect(Collectors.toList());
        ProgramDTOPage resultPage = new ProgramDTOPage();
        resultPage.setPrograms(resultDTO);
        resultPage.setTotalPages(temp.getTotalPages());
        resultPage.setIndex(temp.getNumber());
        resultPage.setTotalElements(result.size());
        return resultPage;
    }

    private ProgramDTOPage getFilteredValid(double p1, double p2, String valid, String categoryName, String name, int difficultyLevel, JwtUser jwtUser, Pageable pageable) {
        Page<Program> temp = null;
        List<Program> result = new ArrayList<>();
        Category category = categoryDAO.findByName(categoryName);

        if (p1 == -1)
            p1 = 0;
        if (p2 == -1)
            p2 = Double.MAX_VALUE;

        User user = userDAO.findById(jwtUser.getId()).get();
        ArrayList<Long> listOfIds = new ArrayList<>();
        user.getParticipatedPrograms().forEach(pp -> listOfIds.add(pp.getId()));
        System.out.println(listOfIds);
        if (listOfIds.isEmpty())
            listOfIds.add((long) -1);

        try {
            if ("-".equals(name)) {
                if (difficultyLevel >= 0) {
                    if (category != null) {
                        System.out.println("price, category, difficultyLevel");
                        temp = programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,category,difficultyLevel,LocalDateTime.now(),user,listOfIds,pageable);
                    }
                    else {
                        System.out.println("price, difficultyLevel");
                        temp = programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,difficultyLevel, LocalDateTime.now(),user, listOfIds,pageable);
                    }
                }
                else {
                    if (category != null) {
                        System.out.println("price, category");
                        temp = programDAO.findByPriceBetweenAndCategoryAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,category,LocalDateTime.now(),user, listOfIds,pageable);
                    }
                    else {
                        System.out.println("price");
                        temp = programDAO.findByPriceBetweenAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,LocalDateTime.now(),user, listOfIds,pageable);
                    }
                }
            }
            else {
                if (difficultyLevel >= 0) {
                    if (category != null) {
                        System.out.println("price, category, difficultyLevel, name");
                        temp = programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,category,difficultyLevel,name,LocalDateTime.now(),user, listOfIds, pageable);
                    }
                    else {
                        System.out.println("price, difficultyLevel, name");
                        temp = programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,difficultyLevel,name,LocalDateTime.now(),user, listOfIds,pageable);
                    }
                }
                else {
                    if (category != null) {
                        System.out.println("price, category, name");
                        temp = programDAO.findByPriceBetweenAndCategoryAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,category,name,LocalDateTime.now(),user, listOfIds,pageable);
                    }
                    else {
                        System.out.println("price, name");
                        temp = programDAO.findByPriceBetweenAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(p1,p2,name,LocalDateTime.now(),user, listOfIds,pageable);
                    }
                }
            }
            result = new ArrayList<>(temp.getContent());

        }
        catch (Exception ex) {
            loggerBean.logError(ex);
            ProgramDTOPage tempDTOPage = new ProgramDTOPage();
            tempDTOPage.setIndex(0);
            tempDTOPage.setTotalElements(0);
            tempDTOPage.setTotalPages(1);
            tempDTOPage.setPrograms(new ArrayList<ProgramDTO>());
            return tempDTOPage;
        }

        List<ProgramDTO> resultDTO = result.stream().map(this::map).collect(Collectors.toList());
        ProgramDTOPage resultPage = new ProgramDTOPage();
        resultPage.setPrograms(resultDTO);
        resultPage.setTotalPages(temp.getTotalPages());
        resultPage.setIndex(temp.getNumber());
        resultPage.setTotalElements(result.size());
        return resultPage;
    }

    @Override
    public ProgramDTOPage getFilteredByCreator(double p1, double p2, String valid, String categoryName, String name, int difficultyLevel, JwtUser jwtUser, Pageable pageable) {
        try {
            User creator = userDAO.findById(jwtUser.getId()).get();
            Page<Program> temp = null;
            List<Program> result = new ArrayList<>();
            Category category = categoryDAO.findByName(categoryName);

            if (p1 == -1)
                p1 = 0;
            if (p2 == -1)
                p2 = Double.MAX_VALUE;

            if ("-".equals(name)) {
                if (difficultyLevel >= 0) {
                    if (category != null) {
                        System.out.println("price, category, difficultyLevel");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndCreatorAndEndingGreaterThan(p1, p2, category, difficultyLevel, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndCreatorAndEndingLessThanEqual(p1, p2, category, difficultyLevel, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndCreator(p1, p2, category, difficultyLevel, creator, pageable);
                            default -> temp;
                        };

                    }
                    else {
                        System.out.println("price, difficultyLevel");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndCreatorAndEndingGreaterThan(p1, p2, difficultyLevel, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndCreatorAndEndingLessThanEqual(p1, p2, difficultyLevel, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndCreator(p1, p2, difficultyLevel, creator, pageable);
                            default -> temp;
                        };
                    }
                }
                else {
                    if (category != null) {
                        System.out.println("price, category");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndCategoryAndCreatorAndEndingGreaterThan(p1, p2, category, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndCategoryAndCreatorAndEndingLessThanEqual(p1, p2, category, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndCategoryAndCreator(p1, p2, category, creator, pageable);
                            default -> temp;
                        };
                    }
                    else {
                        System.out.println("price");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndCreatorAndEndingGreaterThan(p1, p2, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndCreatorAndEndingLessThanEqual(p1, p2, creator, LocalDateTime.now(), pageable);
                            case "-" -> programDAO.findByPriceBetweenAndCreator(p1, p2, creator, pageable);
                            default -> temp;
                        };
                    }
                }
            }
            else {
                if (difficultyLevel >= 0) {
                    if (category != null) {
                        System.out.println("price, category, difficultyLevel, name");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingGreaterThan(p1, p2, category, difficultyLevel, name, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingLessThanEqual(p1, p2, category, difficultyLevel, name, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndCreator(p1, p2, category, difficultyLevel, name, creator, pageable);
                            default -> temp;
                        };
                    }
                    else {
                        System.out.println("price, difficultyLevel, name");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingGreaterThan(p1, p2, difficultyLevel, name, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingLessThanEqual(p1, p2, difficultyLevel, name, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndCreator(p1, p2, difficultyLevel, name, creator, pageable);
                            default -> temp;
                        };
                    }
                }
                else {
                    if (category != null) {
                        System.out.println("price, category, name");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndCategoryAndNameContainingAndCreatorAndEndingGreaterThan(p1, p2, category, name, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndCategoryAndNameContainingAndCreatorAndEndingLessThanEqual(p1, p2, category, name, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndCategoryAndNameContainingAndCreator(p1, p2, category, name, creator, pageable);
                            default -> temp;
                        };
                    }
                    else {
                        System.out.println("price, name");
                        temp = switch (valid) {
                            case "true" ->
                                    programDAO.findByPriceBetweenAndNameContainingAndCreatorAndEndingGreaterThan(p1, p2, name, creator, LocalDateTime.now(), pageable);
                            case "false" ->
                                    programDAO.findByPriceBetweenAndNameContainingAndCreatorAndEndingLessThanEqual(p1, p2, name, creator, LocalDateTime.now(), pageable);
                            case "-" ->
                                    programDAO.findByPriceBetweenAndNameContainingAndCreator(p1, p2, name, creator, pageable);
                            default -> temp;
                        };
                    }
                }
            }
            result = new ArrayList<>(temp.getContent());

//            if (jwtUser != null && "true".equals(valid)) {
//                User user = userDAO.findById(jwtUser.getId()).get();
//                result.removeAll(user.getPrograms());
//                result.removeAll(user.getParticipatedPrograms());
//            }

            List<ProgramDTO> resultDTO = result.stream().map(this::map).collect(Collectors.toList());
            ProgramDTOPage resultPage = new ProgramDTOPage();
            resultPage.setPrograms(resultDTO);
            resultPage.setTotalPages(temp.getTotalPages());
            resultPage.setIndex(temp.getNumber());
            resultPage.setTotalElements(result.size());
            return resultPage;
        }
        catch (Exception ex) {
            loggerBean.logError(ex);
            ProgramDTOPage temp = new ProgramDTOPage();
            temp.setIndex(0);
            temp.setTotalElements(0);
            temp.setTotalPages(1);
            temp.setPrograms(new ArrayList<ProgramDTO>());
            return temp;
        }

    }
}
