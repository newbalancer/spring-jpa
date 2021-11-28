package io.gonzo.jpa.app.domain;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.gonzo.jpa.app.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_job")
public class Job extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

//    @ElementCollection
//    @CollectionTable(name = "app_job_user", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "job_id", nullable = false)
//    public Set<Long> userIdSet = new HashSet<>();

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "app_job_user",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties({"groups", "gender"})
    private Set<User> users = new HashSet<>();

    @Builder
    public Job(String title, String content, Set<User> users) {
        this.title = title;
        this.content = content;
        this.users = users;
    }

}
