package system.insurance.backend.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableServer.ServantRetentionPolicy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

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
    private int id;
    @Column(length = 15)
    private String contact;
    private int age;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(length = 30)
    private String name;
    @Column(length = 30)
    private String email;
//    @Enumerated(EnumType.STRING)
//private ClientType type;
}
