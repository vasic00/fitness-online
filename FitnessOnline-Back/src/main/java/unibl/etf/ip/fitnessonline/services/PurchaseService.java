package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.dto.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    List<PurchaseDTO> getByBuyer(long id);
    PurchaseDTO add(PurchaseDTO purchaseDTO);
    void delete(long id);
}