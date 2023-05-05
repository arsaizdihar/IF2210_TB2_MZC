package mzc.app.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@MappedSuperclass
@Getter
@Setter
public class BaseModel implements Serializable, Cloneable {
    @EqualCheck
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public BaseModel clone() {
        try {
            BaseModel clone = (BaseModel) super.clone();
            clone.setId(getId());
            Field[] fields = this.getClass().getDeclaredFields();
            for (var field: fields) {
                if (field.isAnnotationPresent(Transient.class) || Modifier.isTransient(field.getModifiers())) continue;
                field.setAccessible(true);
                field.set(clone, field.get(this));
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
