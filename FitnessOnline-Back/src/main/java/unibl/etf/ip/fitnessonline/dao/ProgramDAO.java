package unibl.etf.ip.fitnessonline.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.fitnessonline.model.Category;
import unibl.etf.ip.fitnessonline.model.Program;
import unibl.etf.ip.fitnessonline.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProgramDAO extends JpaRepository<Program, Long> {

    List<Program> findByUsersNotified(boolean usersNotified);
    List<Program> findByCategoryAndUsersNotified(Category category, boolean usersNotified);
    Page<Program> findByCreator(User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndEndingGreaterThan(double p1, double p2, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndEndingGreaterThan(double p1, double p2, Category category, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndEndingGreaterThan(double p1, double p2, int difficultyLevel, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndEndingGreaterThan(double p1, double p2, Category category, int difficultyLevel, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndNameContainingAndEndingGreaterThan(double p1, double p2, String name, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndNameContainingAndEndingGreaterThan(double p1, double p2, Category category, String name, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThan(double p1, double p2, Category category, int difficultyLevel, String name, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThan(double p1, double p2, int difficultyLevel, String name, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCreatorAndEndingGreaterThan(double p1, double p2, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndCreatorAndEndingGreaterThan(double p1, double p2, Category category, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndCreatorAndEndingGreaterThan(double p1, double p2, int difficultyLevel, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndCreatorAndEndingGreaterThan(double p1, double p2, Category category, int difficultyLevel, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndNameContainingAndCreatorAndEndingGreaterThan(double p1, double p2, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndNameContainingAndCreatorAndEndingGreaterThan(double p1, double p2, Category category, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingGreaterThan(double p1, double p2, Category category, int difficultyLevel, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingGreaterThan(double p1, double p2, int difficultyLevel, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCreatorAndEndingLessThanEqual(double p1, double p2, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndCreatorAndEndingLessThanEqual(double p1, double p2, Category category, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndCreatorAndEndingLessThanEqual(double p1, double p2, int difficultyLevel, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndCreatorAndEndingLessThanEqual(double p1, double p2, Category category, int difficultyLevel, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndNameContainingAndCreatorAndEndingLessThanEqual(double p1, double p2, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndNameContainingAndCreatorAndEndingLessThanEqual(double p1, double p2, Category category, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingLessThanEqual(double p1, double p2, Category category, int difficultyLevel, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndCreatorAndEndingLessThanEqual(double p1, double p2, int difficultyLevel, String name, User creator, LocalDateTime ending, Pageable pageable);
    Page<Program> findByPriceBetweenAndCreator(double p1, double p2, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndCreator(double p1, double p2, Category category, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndCreator(double p1, double p2, int difficultyLevel, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndCreator(double p1, double p2, Category category, int difficultyLevel, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndNameContainingAndCreator(double p1, double p2, String name, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndNameContainingAndCreator(double p1, double p2, Category category, String name, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndCreator(double p1, double p2, Category category, int difficultyLevel, String name, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndCreator(double p1, double p2, int difficultyLevel, String name, User creator, Pageable pageable);
    Page<Program> findByPriceBetweenAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, Category category, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, int difficultyLevel, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, Category category, int difficultyLevel, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, String name, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, Category category, String name, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndCategoryAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, Category category, int difficultyLevel, String name, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
    Page<Program> findByPriceBetweenAndDifficultyLevelLessThanEqualAndNameContainingAndEndingGreaterThanAndCreatorNotAndIdNotIn(double p1, double p2, int difficultyLevel, String name, LocalDateTime ending, User creator, Collection<Long> ids, Pageable pageable);
}
