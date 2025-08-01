package Ahnwjeia.abyves.campaign.missions;

import java.awt.Color;

import Ahnwjeia.abyves.world.AbyvesPeople;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.People;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.util.Misc;

public class abyvesIntroQuest extends HubMissionWithSearch {

    private PersonAPI abyvescontact1;
    private PersonAPI baird;

    public static enum Stage {
        GO_TO_ACADEMY,
        COMPLETED,
    }

    @Override
    protected boolean create(MarketAPI createdAt, boolean barEvent) {
        // if already accepted by the player, abort
        if (!setGlobalReference("$abyvesIntro_ref")) {
            return false;
        }

        abyvescontact1 = getImportantPerson(AbyvesPeople.AbyvesContact1);
        baird = getImportantPerson(People.BAIRD);
        if (baird == null) return false;

        setStartingStage(Stage.GO_TO_ACADEMY);
        addSuccessStages(Stage.COMPLETED);

        setStoryMission();

        makeImportant(baird.getMarket(), null, Stage.GO_TO_ACADEMY);
        setStageOnMemoryFlag(Stage.COMPLETED, baird.getMarket(), "$abyvesIntro_completed");

        setRepFactionChangesNone();
        setRepPersonChangesNone();

        return true;
    }

    @Override
    protected void updateInteractionDataImpl() {
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height) {
        float opad = 10f;
        Color h = Misc.getHighlightColor();
        if (currentStage == Stage.GO_TO_ACADEMY) {
            info.addPara("Go to the Galatia Academy in the Galatia system.", opad);
        }
    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad) {
        Color h = Misc.getHighlightColor();
        if (currentStage == Stage.GO_TO_ACADEMY) {
            info.addPara("Go to the Galatia Academy", tc, pad);
            return true;
        }
        return false;
    }

    @Override
    public String getBaseName() {
        return "Visit the Academy";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }
}
