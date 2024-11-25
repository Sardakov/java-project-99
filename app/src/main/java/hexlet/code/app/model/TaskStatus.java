package hexlet.code.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
@Table(name = "task_status")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskStatus implements BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 1)
    @Column(unique = true)
    @ToString.Include
    @EqualsAndHashCode.Include
    private String slug;

    @NotBlank
    @Size(min = 1)
    @Column(unique = true)
    @ToString.Include
    @EqualsAndHashCode.Include
    private String name;

    @CreatedDate
    private LocalDate createdAt;

}

//@Entity
//@Table(name = "task_status", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"name"}),
//        @UniqueConstraint(columnNames = {"slug"})
//})
//@EntityListeners(AuditingEntityListener.class)
//@Getter
//@Setter
//public class TaskStatus implements BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank
//    @Size(min = 1)
//    @Column(unique = true, nullable = false)
//    private String name;
//
//    @NotBlank
//    @Size(min = 1)
//    @Column(unique = true, nullable = false)
//    private String slug;
//
//    @CreatedDate
//    @Column(nullable = false, updatable = false)
//    private LocalDate createdAt;
//}

