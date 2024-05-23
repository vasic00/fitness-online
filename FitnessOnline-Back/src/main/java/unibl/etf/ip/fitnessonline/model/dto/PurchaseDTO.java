package unibl.etf.ip.fitnessonline.model.dto;

import lombok.Data;

@Data
public class PurchaseDTO {
    private long id;
    private String paymentType;
    private String time;
    private String programCategory;
    private String programName;
    private double programPrice;
    private long programId;
    private long userId;


    @Override
    public String toString() {
        return "PurchaseDTO{" +
                "id=" + id +
                ", payment='" + paymentType + '\'' +
                ", time='" + time + '\'' +
                ", programCategory='" + programCategory + '\'' +
                ", programTitle='" + programName + '\'' +
                ", programPrice=" + programPrice +
                ", programId=" + programId +
                ", userId=" + userId +
                '}';
    }
}
