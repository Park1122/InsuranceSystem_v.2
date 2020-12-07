package system.insurance.backend.dbo.client;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public abstract class Client implements Serializable {
    private static final long serialVersionUID = 234980209685049L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(length = 15)
    protected String contact;
    protected int age;
    @Enumerated(EnumType.STRING)
    protected Sex sex;
    @Column(length = 30)
    protected String name;
    @Column(length = 30)
    protected String email;
}
