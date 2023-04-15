package mzc.app.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "Member")
@Getter @Setter @NoArgsConstructor
public class Member extends BaseModel {
    @Column
    private String name;

    @Column
    private String phone;

    @OneToMany(mappedBy = "member")
    private Set<Bill> bills = new HashSet<>();

    @Transient
    private boolean billsLoaded = false;

    public Member(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
