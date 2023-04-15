package mzc.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Bill")
@Getter @Setter @NoArgsConstructor
public class Bill extends BaseModel {

    @OneToMany(mappedBy = "bill")
    private Set<ProductBill> products = new HashSet<>();

    @Column
    private Long memberId;

    @ManyToOne()
    @JoinColumn(name = "memberId", nullable = false)
    @MapsId("memberId")
    private Member member;
}
