package system.insurance.backend.dbo.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class ClientGift {

    @Id
    private int id;
    private String gift;
}
