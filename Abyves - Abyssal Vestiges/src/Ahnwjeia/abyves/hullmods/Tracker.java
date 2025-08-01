package Ahnwjeia.abyves.hullmods;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.util.IntervalUtil;

public class Tracker implements EveryFrameScript {
    public static float SUPPLY_USE_REDUCTION = 0.5f;
    private IntervalUtil interval = new IntervalUtil(0.2f, 0.3f);
    public void advance(float amount) {
        interval.advance(amount);
        if (interval.intervalElapsed()) {
            //first pass over the data to figure out if the ship is there
            var shipPresent = false;

            for (FleetMemberAPI fmapi : Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy()) {
                if (fmapi.getHullId().equals("Factory_Unit")) {
                    shipPresent = true;
                    break;
                }
            }

            //second pass to apply (or unapply) the modifiers
            for (FleetMemberAPI fmapi : Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy()) {
                if (!shipPresent) {
                    fmapi.getStats().getSuppliesPerMonth().unmodify("Refac1");
                } else {
                    fmapi.getStats().getSuppliesPerMonth().modifyMult("Refac1", SUPPLY_USE_REDUCTION);
                }
            }
        }
    }
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return true;
    }
}
