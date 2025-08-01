package Ahnwjeia.abyves.hullmods;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;

public class RefacilitationModule extends BaseLogisticsHullMod {


    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + 50 + "%";
        if (index == 1) return "" + 5;
        return null;
    }
}

