package Ahnwjeia.abyves;

import Ahnwjeia.abyves.hullmods.Tracker;
import Ahnwjeia.abyves.world.AbyvesGEN;
import Ahnwjeia.abyves.world.AbyvesPeople;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

public class ModPlugin extends BaseModPlugin {
    public static boolean nexerelinEnabled = false;

    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();

        nexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");
    }

    public void onNewGameAfterEconomyLoad() {
        new AbyvesGEN().generate(Global.getSector());
        AbyvesPeople.AbyvesCreateContact();
        Global.getSector().addScript(new Tracker());
    }
}