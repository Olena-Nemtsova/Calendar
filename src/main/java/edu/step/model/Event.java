package edu.step.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Event {
    @Id
    private int id;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;
    private int userId;
}
