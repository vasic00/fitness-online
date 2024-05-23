package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.dto.AttributeDTO;

public interface AttributeService {
    AttributeDTO add(AttributeDTO attributeDTO);
    void delete(long programId, long specificAttributeId);
}