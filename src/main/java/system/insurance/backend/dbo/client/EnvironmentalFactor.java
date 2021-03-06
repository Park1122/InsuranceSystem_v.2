package system.insurance.backend.dbo.client;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnvironmentalFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Job job;
    private String dangerousHobby;
//    @Column(name="residence",nullable = false, columnDefinition = "point")
//    private Point residence;
    private String dangerousArea;

    @Builder
    public EnvironmentalFactor(Job job, String dangerousHobby,/* Point residence,*/ String dangerousArea) {
        this.job = job;
        this.dangerousHobby = dangerousHobby;
//        this.residence = residence;
        this.dangerousArea = dangerousArea;
    }

}
