package mzc.plugin_system2.models;

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
    private String key;

    @EqualCheck
    @Column
    private String name;

    public Charge(PricePipelineType type, BigDecimal value, String key, String name) {
        this.type = type;
        this.value = value;
        this.key = key;
        this.name = name;
    }
}
