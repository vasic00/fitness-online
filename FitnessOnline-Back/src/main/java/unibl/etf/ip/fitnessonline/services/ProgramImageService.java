package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.ProgramImage;

import java.util.List;

public interface ProgramImageService {
    ProgramImage findById(long id);
    void delete(long id);
    byte[] getImage(long id);
    boolean saveImage(byte[] data, String id);
    List<String> storeImages(String id, long programId);
    void add(ProgramImage programImage);
}
