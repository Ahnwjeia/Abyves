package Ahnwjeia.abyves.campaign.customstart;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.StarSystemAPI;

public class vestigehook implements EveryFrameScript {

    public static enum vestigehookstage {
        SHOW_WELCOME_DIALOG,
        DONE,

    }
    protected float elapsed = 0f;
    protected StarSystemAPI system;
    protected vestigehookstage stage = vestigehookstage.SHOW_WELCOME_DIALOG;

    public vestigehook(StarSystemAPI system) {
    }

    public void advance(float amount) {
        if (Global.getSector().isInFastAdvance()) return;
        elapsed += amount;
        if (stage == vestigehookstage.SHOW_WELCOME_DIALOG && elapsed > 1f) {
            if (Global.getSector().getCampaignUI().showInteractionDialog(new vestigedialog(), null)) {
                stage = vestigehookstage.DONE;
                elapsed = 0f;
            }
            return;
        }
    }
    public boolean isDone() {
        return stage == vestigehookstage.DONE;
    }
    public boolean runWhilePaused() {
        return true;
    }

}
