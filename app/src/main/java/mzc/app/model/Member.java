package mzc.app.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "Member")
@Getter @Setter @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String phone;

    public Member(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
