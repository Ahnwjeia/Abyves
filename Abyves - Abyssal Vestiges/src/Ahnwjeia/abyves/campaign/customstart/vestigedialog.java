package Ahnwjeia.abyves.campaign.customstart;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.impl.campaign.ids.Abilities;
import com.fs.starfarer.api.loading.AbilitySpecAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.Map;

public class vestigedialog implements InteractionDialogPlugin {

    public static enum OptionId {
        INIT,
        CONT1,
        CONT2,
        CONT3,
        CONT4,
        CONT5,
        CONT6,
    }

    protected InteractionDialogAPI dialog;
    protected TextPanelAPI textPanel;
    protected OptionPanelAPI options;
    protected VisualPanelAPI visual;
    protected CampaignFleetAPI playerFleet;

    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        textPanel = dialog.getTextPanel();
        options = dialog.getOptionPanel();
        visual = dialog.getVisualPanel();

        playerFleet = Global.getSector().getPlayerFleet();

        //visual.showImagePortion("illustrations", "jump_point_hyper", 640, 400, 0, 0, 480, 300);
        visual.showFleetInfo("Your fleet", playerFleet, null, null);

        //dialog.setOptionOnEscape("Leave", OptionId.LEAVE);

        optionSelected(null, OptionId.INIT);
    }

    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }

    public void backFromEngagement(EngagementResultAPI result) {
        // no combat here, so this won't get called
    }

    public void optionSelected(String text, Object optionData) {
        if (optionData == null) return;

        OptionId option = (OptionId) optionData;

        if (text != null) {
            //textPanel.addParagraph(text, Global.getSettings().getColor("buttonText"));
            dialog.addOptionSelectedText(option);
        }

        switch (option) {
            case INIT:
                textPanel.addParagraph("Welcome to the Persean Sector!");

                textPanel.addParagraph("The station administrator has assigned you a ship. " +
                        "Entering the bridge, you look down at a TriPad on top of a control panel.");

                textPanel.addParagraph("You pick up the TriPad and boot it up. " +
                        "You are welcomed by a startup message on the display.");

                textPanel.addParagraph("\"Dear DOMAIN Citizen," +
                        "the Tri-Tachyon Corporation is pleased " +
                        "to guide a new customer through the setup of their cutting-edge TriPad. " +
                        "Please proceed through the setup and leave a feedback at the end, " +
                        "or connect your previous Tri-Tachyon device using your customer number.\"");

                textPanel.addParagraph("Your internal augments connect with the device " +
                        "autonomously and it loads information into your mind.");

                options.clearOptions();
                options.addOption("Continue", OptionId.CONT1, null);
                break;
            case CONT1:
                AbilitySpecAPI ability1 = Global.getSettings().getAbilitySpec(Abilities.TRANSPONDER);
                AbilitySpecAPI ability2 = Global.getSettings().getAbilitySpec(Abilities.GO_DARK);
                AbilitySpecAPI ability3 = Global.getSettings().getAbilitySpec(Abilities.SENSOR_BURST);
                AbilitySpecAPI ability4 = Global.getSettings().getAbilitySpec(Abilities.EMERGENCY_BURN);
                AbilitySpecAPI ability5 = Global.getSettings().getAbilitySpec(Abilities.SUSTAINED_BURN);
                AbilitySpecAPI ability6 = Global.getSettings().getAbilitySpec(Abilities.SCAVENGE);
                AbilitySpecAPI ability7 = Global.getSettings().getAbilitySpec(Abilities.INTERDICTION_PULSE);
                AbilitySpecAPI ability0 = Global.getSettings().getAbilitySpec(Abilities.DISTRESS_CALL);
                textPanel.addPara("The data stream flashes before your mind's eye.");
                textPanel.addPara("You have gained the knowledge for:");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability1.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability2.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability3.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability4.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability5.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability6.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability7.getName() + "\"");
                textPanel.addPara("%s",
                        Misc.getHighlightColor(),
                        "\"" + ability0.getName() + "\"");
                options.clearOptions();
                options.addOption("Continue", OptionId.CONT2, null);
                break;
            case CONT2:
                //textPanel.addParagraph("It's possible to scavenge through the same debris field multiple times, but there are diminishing returns and increased risk with each attempt. Only scavenge once here.");
                textPanel.addParagraph("You hear a growing noise of static coming from the TriPad. " +
                        "The stream of files transmitted to you slows down. " +
                        "A squealing sound spreads in your head and you see a message.");
                textPanel.addParagraph("\"FAULTIFICATE ERROR - FILE TRANSFICATION FAILED - UNIT UNFORMULATE\"", Misc.getHighlightColor());
                options.clearOptions();
                options.addOption("Continue", OptionId.CONT3, null);
                break;
            case CONT3:
                textPanel.addParagraph("\"FAULTIFICATE ERROR - ABSCENITY OF INPUT - UNIT UNFORMULATE\"", Misc.getHighlightColor());
                options.clearOptions();
                options.addOption("Continue", OptionId.CONT4, null);
                break;
            case CONT4:
                textPanel.addParagraph("\"FAULTIFICATE ERROR - DENYANCE OF ACCESS - UNIT UNFORMULATE\"", Misc.getHighlightColor());
                options.clearOptions();
                options.addOption("Continue", OptionId.CONT5, null);
                break;
            case CONT5:
                textPanel.addParagraph("The noise subsides, the message disappears. " +
                        "Your vision clears and you take a deep breath.");
                textPanel.addParagraph("You gain consciousness and wake up.");
                options.clearOptions();
                options.addOption("Continue", OptionId.CONT6, null);
                break;
            case CONT6:
                Global.getSector().setPaused(false);
                dialog.dismiss();
                break;
        }
    }




    public void optionMousedOver(String optionText, Object optionData) {

    }

    public void advance(float amount) {

    }

    public Object getContext() {
        return null;
    }
}
