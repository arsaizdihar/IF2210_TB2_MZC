package mzc.plugin_charge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;
import mzc.app.model.BaseModel;
import mzc.app.modules.pricing.pipelines.PricePipelineType;

import java.math.BigDecimal;

@Entity(name = "charge")
@Table(name = "charge")
@Getter
@Setter
@NoArgsConstructor
public class Charge extends BaseModel {
    @EqualCheck
    @Column
    private PricePipelineType type;

    @EqualCheck
    @Column
    private BigDecimal value;

    @EqualCheck
    @Column
    private String identifier;

    @EqualCheck
    @Column
    private String name;

    public Charge(PricePipelineType type, BigDecimal value, String identifier, String name) {
        this.type = type;
        this.value = value;
        this.identifier = identifier;
        this.name = name;
    }
}
