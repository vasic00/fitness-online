package unibl.etf.ip.fitnessonline.dao;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Repository
public class AvatarDAO {

    @Value("${images.repo}")
    private String imagesRepo;
    @Getter
    private final List<String> avatars= Arrays.asList("a1.png","a2.png","a3.png","a4.png","a5.png","a6.png","a7.png","a8.png","a9.png","a10.png","a11.png","a12.png","a13.png","a14.png","a15.png","a16.png");

    public byte[] getAvatarByName(String avatar){
        String path=System.getProperty("user.dir") + imagesRepo + File.separator+avatar;
        try{
            return Files.readAllBytes(Paths.get(path));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
