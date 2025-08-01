package Ahnwjeia.abyves.campaign.backgrounds;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import exerelin.campaign.backgrounds.BaseCharacterBackground;
import exerelin.utilities.NexFactionConfig;

public class abyvesAug extends BaseCharacterBackground {

    @Override
    public void onNewGameAfterTimePass(FactionSpecAPI factionSpec, NexFactionConfig factionConfig) {
        Global.getSector().getPlayerPerson().getStats().setSkillLevel(Skills.GUNNERY_IMPLANTS, 1f);
        Global.getSector().getPlayerPerson().getStats().setSkillLevel(Skills.AUTOMATED_SHIPS, 1f);
        Global.getSector().addScript(new ExampleScript());
    }

    public static class ExampleScript implements EveryFrameScript {

        /**
         * @return true when the script is finished and can be cleaned up by the engine.
         */
        @Override
        public boolean isDone() {
            return false;
        }

        /**
         * @return whether advance() should be called while the campaign engine is paused.
         */
        @Override
        public boolean runWhilePaused() {
            return true;
        }

        /**
         * Use SectorAPI.getClock() to convert to campaign days.
         *
         * @param amount seconds elapsed during the last frame.
         */
        @Override
        public void advance(float amount) {
            Global.getSector().getPlayerPerson().getStats().getOfficerNumber().modifyMult("abyvesAug", 0.5f);
        }
    }
}