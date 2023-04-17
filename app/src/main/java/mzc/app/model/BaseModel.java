package mzc.app.model;


import lombok.Getter;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;

@MappedSuperclass
@Getter @Setter
public class BaseModel implements Serializable {
    @EqualCheck
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj.getClass().equals(this.getClass()))) return false;
        Class<? extends BaseModel> reflectedClass = this.getClass();
        Field[] fields = reflectedClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EqualCheck.class)) {
                try {
                    field.setAccessible(true);
                    if (!field.get(this).equals(field.get(obj))) return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
