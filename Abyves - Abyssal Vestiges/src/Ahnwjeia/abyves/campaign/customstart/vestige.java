package Ahnwjeia.abyves.campaign.customstart;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.CharacterCreationData;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddRemoveCommodity;
import com.fs.starfarer.api.impl.campaign.rulecmd.FireBest;
import com.fs.starfarer.api.impl.campaign.rulecmd.NGCAddStandardStartingScript;
import exerelin.campaign.ExerelinSetupData;
import exerelin.campaign.PlayerFactionStore;
import exerelin.campaign.customstart.CustomStart;
import exerelin.utilities.StringHelper;

import java.util.Map;

import static exerelin.campaign.StartSetupPostTimePass.sendPlayerFleetToLocation;

public class vestige extends CustomStart {

    @Override
    public void execute(InteractionDialogAPI dialog, Map<String, MemoryAPI> memoryMap) {
        CharacterCreationData data = (CharacterCreationData) memoryMap.get(MemKeys.LOCAL).get("$characterData");
        data.addScriptBeforeTimePass(new Script() {
            public void run() {
                Global.getSector().getMemoryWithoutUpdate().set("$vestige", true);
                //Global.getSector().getMemoryWithoutUpdate().set("$nex_startLocation", "nomios");
            }
        });
        PlayerFactionStore.setPlayerFactionIdNGC(Factions.PLAYER);


        String vid = "Facilitation_Unit_Type810";
        data.addStartingFleetMember(vid, FleetMemberType.SHIP);
        FleetMemberAPI temp = Global.getFactory().createFleetMember(FleetMemberType.SHIP, vid);

        data.getStartingCargo().getCredits().add(15000);
        AddRemoveCommodity.addCreditsGainText(15000, dialog.getTextPanel());

        int fuel = (int)temp.getFuelCapacity();
        AddRemoveCommodity.addFleetMemberGainText(temp.getVariant(), dialog.getTextPanel());
        data.getStartingCargo().addItems(CargoAPI.CargoItemType.RESOURCES, Commodities.CREW, 5);
        data.getStartingCargo().addItems(CargoAPI.CargoItemType.RESOURCES, Commodities.CREW, 5);
        data.getStartingCargo().addItems(CargoAPI.CargoItemType.RESOURCES, Commodities.SUPPLIES, 100);
        AddRemoveCommodity.addCommodityGainText(Commodities.SUPPLIES, 100, dialog.getTextPanel());
        data.getStartingCargo().addItems(CargoAPI.CargoItemType.RESOURCES, Commodities.FUEL, fuel);
        AddRemoveCommodity.addCommodityGainText(Commodities.FUEL, fuel, dialog.getTextPanel());
        MutableCharacterStatsAPI stats = data.getPerson().getStats();
        stats.addPoints(1);
        CampaignFleetAPI tempFleet = FleetFactoryV3.createEmptyFleet(PlayerFactionStore.getPlayerFactionIdNGC(), "patrolSmall", (MarketAPI)null);
        tempFleet.getFleetData().addFleetMember(temp);
        tempFleet.getFleetData().setFlagship(temp);
        temp.setCaptain(data.getPerson());
        temp.getRepairTracker().setCR(0.7F);
        tempFleet.getFleetData().setSyncNeeded();
        tempFleet.getFleetData().syncIfNeeded();
        tempFleet.forceSync();
        data.setDifficulty("normal");
        ExerelinSetupData.getInstance().easyMode = false;
        ExerelinSetupData.getInstance().freeStart = true;

        data.addScript(new Script() {
            public void run() {
                SectorAPI sector = Global.getSector();
                CampaignFleetAPI playerFleet = sector.getPlayerFleet();
                CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
                NGCAddStandardStartingScript.adjustStartingHulls(fleet);
                fleet.getFleetData().ensureHasFlagship();

                for (FleetMemberAPI member : fleet.getFleetData().getMembersListCopy()) {
                    float max = member.getRepairTracker().getMaxCR();
                    member.getRepairTracker().setCR(max);
                }
                fleet.getFleetData().setSyncNeeded();


                Global.getSector().getFaction(Factions.INDEPENDENT).setRelationship(Factions.PLAYER, RepLevel.INHOSPITABLE);
                Global.getSector().getFaction(Factions.HEGEMONY).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.PERSEAN).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.LUDDIC_CHURCH).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.LUDDIC_PATH).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.TRITACHYON).setRelationship(Factions.PLAYER, RepLevel.INHOSPITABLE);
                Global.getSector().getFaction(Factions.PIRATES).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.DIKTAT).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.THREAT).setRelationship(Factions.PLAYER, RepLevel.WELCOMING);
                Global.getSector().getFaction(Factions.KOL).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);
                Global.getSector().getFaction(Factions.LIONS_GUARD).setRelationship(Factions.PLAYER, RepLevel.HOSTILE);


                SectorEntityToken entity = sector.getEntityById("corruptedsleeper");
                if (entity != null)
                {
                    sendPlayerFleetToLocation(playerFleet, entity);
                }

                StarSystemAPI system = Global.getSector().getStarSystem("Eos");
                system.addScript(new vestigehook(system));
            }
        });

        dialog.getVisualPanel().showFleetInfo(StringHelper.getString("exerelin_ngc", "playerFleet", true),
                tempFleet, null, null);

        FireBest.fire(null, dialog, memoryMap, "ExerelinNGCStep4");
    }
}

