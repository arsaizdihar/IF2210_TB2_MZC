package mzc.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@MappedSuperclass
@Getter @Setter
public class BaseModel implements Serializable {
    @Id
    @GeneratedValue
    protected long id;
}
