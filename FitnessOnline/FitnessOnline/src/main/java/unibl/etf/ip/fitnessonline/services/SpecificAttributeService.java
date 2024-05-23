package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.dto.SpecificAttributeDTO;

import java.util.List;

public interface SpecificAttributeService {
    boolean delete(long id);
    SpecificAttributeDTO update(SpecificAttributeDTO specificAttributeDTO);
    SpecificAttributeDTO add(SpecificAttributeDTO specificAttributeDTO);

    List<SpecificAttributeDTO> getAllSpecificAttributesOfCategory(long id);
}
