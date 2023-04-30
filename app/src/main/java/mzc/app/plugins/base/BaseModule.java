package mzc.app.plugins.base;

import lombok.Getter;
import mzc.app.modules.pricing.PricePipelineResult;

import java.util.ArrayList;
import java.util.List;

public class BaseModule {
    @Getter
    protected List<PricePipelineResult> pricePipelines = new ArrayList<>();

    @Getter
    protected List<IViewModule> viewModules = new ArrayList<>();
}
