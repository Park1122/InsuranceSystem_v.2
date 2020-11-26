package system.insurance.backend.dbo.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("REGISTERED")
public class RegisteredClient extends RegisteringClient{

}
