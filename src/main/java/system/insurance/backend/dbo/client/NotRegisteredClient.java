package system.insurance.backend.dbo.client;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("NOT_REGISTERED")
public class NotRegisteredClient extends Client {
    @JoinColumn(name="gift",referencedColumnName = "id")
    @OneToOne
    private ClientGift gift;


    @Builder
    public NotRegisteredClient(String contact, int age, Sex sex, String name, String email,ClientGift gift) {
        this.contact = contact;
        this.age = age;
        this.sex = sex;
        this.name = name;
        this.email = email;
        this.gift=gift;
    }
}
