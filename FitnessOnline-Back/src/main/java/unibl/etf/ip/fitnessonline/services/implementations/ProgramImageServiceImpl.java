package unibl.etf.ip.fitnessonline.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import unibl.etf.ip.fitnessonline.dao.ProgramImageDAO;
import unibl.etf.ip.fitnessonline.model.ProgramImage;
import unibl.etf.ip.fitnessonline.services.ProgramImageService;
import unibl.etf.ip.fitnessonline.util.LoggerBean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProgramImageServiceImpl  implements ProgramImageService {

    @Value("${images.repo}")
    private String imagesRepo;
    private final LoggerBean loggerBean;
    private final ProgramImageDAO programImageDAO;
    private Map<String, List<byte[]>> uploadedImages = new HashMap<String, List<byte[]>>();

    public ProgramImageServiceImpl(LoggerBean loggerBean, ProgramImageDAO programImageDAO) {
        this.loggerBean = loggerBean;
        this.programImageDAO = programImageDAO;
    }

    public ProgramImage findById(long id) {
        try {
            return programImageDAO.findById(id).get();
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }

    public void delete(long id) {
        programImageDAO.deleteById(id);
    }

    public byte[] getImage(long id) {
        ProgramImage image = findById(id);
        if (image == null)
            return null;
        String path = System.getProperty("user.dir") + imagesRepo + File.separator + image.getImg();
        try {
            byte[] result = Files.readAllBytes(Paths.get(path));
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    public void deleteImage(long id) {
        ProgramImage image = findById(id);
        delete(image.getId());
        if (image != null) {
            String path = imagesRepo + File.separator + image.getImg();
            File file = new File(path);
            file.delete();
        }
    }

    public boolean saveImage(byte[] data, String id) {
        try {
            if (!uploadedImages.containsKey(id))
                uploadedImages.put(id, new ArrayList<byte[]>());
            uploadedImages.get(id).add(data);
            System.out.println("saved image for rand" + id);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return false;
        }
    }

    public List<String> storeImages(String id, long programId) {
        List<String> result = new ArrayList<String>();
        try {
            if (uploadedImages.containsKey(id)) {
                List<byte[]> imagesData = uploadedImages.get(id);
                uploadedImages.remove(id);
                int count = 1;
                for (byte[] data : imagesData) {
                    String name = programId + "-" + count++ + ".jpg";
                    String path = System.getProperty("user.dir") + imagesRepo + File.separator + name;
                    result.add(name);
                    File file = new File(path);
                    file.createNewFile();
                    Files.write(Paths.get(path), data);
                    System.out.println("saved");
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
        }
        return result;
    }

    public void add(ProgramImage programImage) {
        this.programImageDAO.save(programImage);
    }
}
