package Ahnwjeia.abyves.campaign.econ;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;

public class swarm_launch_center extends BaseIndustry {

    public void apply() {
        super.apply(true);

        int size = market.getSize();

        demand(Commodities.HEAVY_MACHINERY, size - 2);

        supply(Commodities.METALS, size);
        supply(Commodities.RARE_METALS, size - 2);
        supply(Commodities.FOOD, size);
        supply(Commodities.ORGANICS, size);
        supply(Commodities.FUEL, size-1);
        supply(Commodities.HEAVY_MACHINERY, size-1);
        supply(Commodities.DOMESTIC_GOODS, size-2);
        supply(Commodities.SUPPLIES, size+1);

        if (!isFunctional()) {
            supply.clear();
        }
    }
}

