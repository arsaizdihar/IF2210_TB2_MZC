package mzc.app.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter @Setter
public class BaseModel implements Serializable {
    @Id
    @GeneratedValue
    protected long id;
}
