package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Comment;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Long> {
}
