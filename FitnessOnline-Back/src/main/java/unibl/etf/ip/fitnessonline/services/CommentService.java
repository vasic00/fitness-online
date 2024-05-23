package unibl.etf.ip.fitnessonline.services;

import unibl.etf.ip.fitnessonline.model.dto.CommentDTO;

public interface CommentService {
    CommentDTO add(CommentDTO commentDTO);
    void delete(long id);
}
