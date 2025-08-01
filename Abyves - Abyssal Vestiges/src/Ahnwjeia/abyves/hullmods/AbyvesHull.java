package Ahnwjeia.abyves.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class AbyvesHull extends BaseHullMod {

    public static float AIM_BONUS = 1f;
    public static float MISSILE_GUIDANCE_BONUS = 1f;
    public static float SENSOR_PROFILE_MULT = 0f;

    public static float MODULE_DAMAGE_TAKEN_MULT = 0.5f;
    public static float EMP_DAMAGE_TAKEN_MULT = 0.5f;

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {

        stats.getAutofireAimAccuracy().modifyFlat(id, AIM_BONUS);
        stats.getMissileGuidance().modifyFlat(id, MISSILE_GUIDANCE_BONUS);

        stats.getSensorProfile().modifyMult(id, SENSOR_PROFILE_MULT);

        stats.getEmpDamageTakenMult().modifyMult(id, EMP_DAMAGE_TAKEN_MULT);
        stats.getEngineDamageTakenMult().modifyMult(id, MODULE_DAMAGE_TAKEN_MULT);
        stats.getWeaponDamageTakenMult().modifyMult(id, MODULE_DAMAGE_TAKEN_MULT);
        stats.getDynamic().getMod(Stats.CAN_REPAIR_MODULES_UNDER_FIRE).modifyFlat(id, 1f);

    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        float pad = 3f;
        float opad = 10f;
        Color h = Misc.getHighlightColor();
        Color bad = Misc.getNegativeHighlightColor();
        Color t = Misc.getTextColor();
        Color g = Misc.getGrayColor();

        tooltip.addPara("Threat hulls have a number of shared properties.", opad);

        tooltip.addSectionHeading("Campaign", Alignment.MID, opad);
        tooltip.addPara("Sensor profile reduced to %s.", opad, h, "0");

        tooltip.addSectionHeading("Combat", Alignment.MID, opad);
        tooltip.addPara("Target leading accuracy increased to maximum for all weapons, including missiles.", opad, h);
        tooltip.addPara("Weapon and engine damage taken is reduced by %s. EMP damage taken is reduced by %s. In "
                        + "addition, repairs of damaged but functional weapons and engines can continue while they are under fire.",
                opad, h,
                "" + (int) Math.round((1f - MODULE_DAMAGE_TAKEN_MULT) * 100f) + "%",
                "" + (int) Math.round((1f - EMP_DAMAGE_TAKEN_MULT) * 100f) + "%");
    }

    public float getTooltipWidth() {
        return super.getTooltipWidth();
    }


}
