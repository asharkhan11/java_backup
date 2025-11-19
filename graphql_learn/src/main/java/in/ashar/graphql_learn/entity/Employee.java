package in.ashar.graphql_learn.entity;

import in.ashar.graphql_learn.constant.Department;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;

    private String name;

    private Department dept;

    @Builder.Default
    private LocalDate joined = LocalDate.now();


}
