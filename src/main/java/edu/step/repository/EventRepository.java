package edu.step.repository;

import edu.step.model.Event;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDateTime date);

    @Query(
        "SELECT * FROM event "
        + "WHERE EXTRACT(MONTH FROM date) = :month "
        + "AND EXTRACT(YEAR FROM date) = :year "
        + "AND user_id=:userId"
    )
    List<Event> findByUserIdAndDate(
        @Param("userId") int userId, @Param("month") int month, @Param("year") int year
    );

}
