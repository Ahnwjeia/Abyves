package Ahnwjeia.abyves.helpers;

import Ahnwjeia.abyves.ModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.intel.deciv.DecivTracker;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarketHelpers {
    // Copied from Nexerelin / Histidine

    public static PersonAPI getPerson(MarketAPI market, String postId) {

        for (CommDirectoryEntryAPI dir : market.getCommDirectory().getEntriesCopy()) {
            if (dir.getType() == CommDirectoryEntryAPI.EntryType.PERSON) {
                PersonAPI person = (PersonAPI) dir.getEntryData();
                if (person.getPostId().equals(postId)) {
                    return person;
                }
            }
        }

        return null;
    }

    public static boolean hasPersonSpecific(MarketAPI market, PersonAPI person) {

        for (CommDirectoryEntryAPI dir : market.getCommDirectory().getEntriesCopy()) {
            if (dir.getType() == CommDirectoryEntryAPI.EntryType.PERSON) {
                PersonAPI dirP = (PersonAPI) dir.getEntryData();
                if (dirP.equals(person)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasPerson(MarketAPI market, String postId)
    {
        return getPerson(market, postId) != null;
    }

    public static boolean removePerson(MarketAPI market, String postId)
    {
        PersonAPI person = getPerson(market, postId);
        if (person == null) return false;

        market.getCommDirectory().removePerson(person);
        market.removePerson(person);
        Global.getSector().getImportantPeople().removePerson(person);
        return true;
    }

    public static PersonAPI addPerson(ImportantPeopleAPI ip, MarketAPI market,
                                      String rankId, String postId, boolean noDuplicate)
    {
        if (noDuplicate && hasPerson(market, postId))
            return null;

        PersonAPI person = market.getFaction().createRandomPerson();
        if (rankId != null) person.setRankId(rankId);
        person.setPostId(postId);

        market.getCommDirectory().addPerson(person);
        market.addPerson(person);
        ip.addPerson(person);
        ip.getData(person).getLocation().setMarket(market);
        ip.checkOutPerson(person, "permanent_staff");

        if (postId.equals(Ranks.POST_BASE_COMMANDER) || postId.equals(Ranks.POST_STATION_COMMANDER)
                || postId.equals(Ranks.POST_ADMINISTRATOR))
        {
            if (market.getSize() >= 8) {
                person.setImportanceAndVoice(PersonImportance.VERY_HIGH, StarSystemGenerator.random);
            } else if (market.getSize() >= 6) {
                person.setImportanceAndVoice(PersonImportance.HIGH, StarSystemGenerator.random);
            } else {
                person.setImportanceAndVoice(PersonImportance.MEDIUM, StarSystemGenerator.random);
            }
        } else if (postId.equals(Ranks.POST_PORTMASTER)) {
            if (market.getSize() >= 8) {
                person.setImportanceAndVoice(PersonImportance.HIGH, StarSystemGenerator.random);
            } else if (market.getSize() >= 6) {
                person.setImportanceAndVoice(PersonImportance.MEDIUM, StarSystemGenerator.random);
            } else if (market.getSize() >= 4) {
                person.setImportanceAndVoice(PersonImportance.LOW, StarSystemGenerator.random);
            } else {
                person.setImportanceAndVoice(PersonImportance.VERY_LOW, StarSystemGenerator.random);
            }
        } else if (postId.equals(Ranks.POST_SUPPLY_OFFICER)) {
            if (market.getSize() >= 6) {
                person.setImportanceAndVoice(PersonImportance.MEDIUM, StarSystemGenerator.random);
            } else if (market.getSize() >= 4) {
                person.setImportanceAndVoice(PersonImportance.LOW, StarSystemGenerator.random);
            } else {
                person.setImportanceAndVoice(PersonImportance.VERY_LOW, StarSystemGenerator.random);
            }
        }

        return person;
    }

    public static boolean addPersonSpecific(ImportantPeopleAPI ip, PersonAPI person, MarketAPI market,
                                            String rankId, String postId, boolean noDuplicate)
    {
        if (noDuplicate && hasPersonSpecific(market, person))
            return false;

        if (rankId != null) person.setRankId(rankId);
        person.setPostId(postId);

        market.getCommDirectory().addPerson(person);
        market.addPerson(person);
        ip.addPerson(person);
        ip.getData(person).getLocation().setMarket(market);
        ip.checkOutPerson(person, "permanent_staff");

        if (postId.equals(Ranks.POST_BASE_COMMANDER) || postId.equals(Ranks.POST_STATION_COMMANDER)
                || postId.equals(Ranks.POST_ADMINISTRATOR))
        {
            if (market.getSize() >= 8) {
                person.setImportanceAndVoice(PersonImportance.VERY_HIGH, StarSystemGenerator.random);
            } else if (market.getSize() >= 6) {
                person.setImportanceAndVoice(PersonImportance.HIGH, StarSystemGenerator.random);
            } else {
                person.setImportanceAndVoice(PersonImportance.MEDIUM, StarSystemGenerator.random);
            }
        } else if (postId.equals(Ranks.POST_PORTMASTER)) {
            if (market.getSize() >= 8) {
                person.setImportanceAndVoice(PersonImportance.HIGH, StarSystemGenerator.random);
            } else if (market.getSize() >= 6) {
                person.setImportanceAndVoice(PersonImportance.MEDIUM, StarSystemGenerator.random);
            } else if (market.getSize() >= 4) {
                person.setImportanceAndVoice(PersonImportance.LOW, StarSystemGenerator.random);
            } else {
                person.setImportanceAndVoice(PersonImportance.VERY_LOW, StarSystemGenerator.random);
            }
        } else if (postId.equals(Ranks.POST_SUPPLY_OFFICER)) {
            if (market.getSize() >= 6) {
                person.setImportanceAndVoice(PersonImportance.MEDIUM, StarSystemGenerator.random);
            } else if (market.getSize() >= 4) {
                person.setImportanceAndVoice(PersonImportance.LOW, StarSystemGenerator.random);
            } else {
                person.setImportanceAndVoice(PersonImportance.VERY_LOW, StarSystemGenerator.random);
            }
        }

        return true;
    }


    public static void addMarketPeople(MarketAPI market)
    {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();

        if (market.getMemoryWithoutUpdate().getBoolean(MemFlags.MARKET_DO_NOT_INIT_COMM_LISTINGS)) return;
        if (ModPlugin.nexerelinEnabled) return;

        boolean addedPerson = false;
        if (market.hasIndustry(Industries.MILITARYBASE) || market.hasIndustry(Industries.HIGHCOMMAND)) {
            String rankId = Ranks.GROUND_MAJOR;
            if (market.getSize() >= 6) {
                rankId = Ranks.GROUND_GENERAL;
            } else if (market.getSize() >= 4) {
                rankId = Ranks.GROUND_COLONEL;
            }

            addPerson(ip, market, rankId, Ranks.POST_BASE_COMMANDER, true);
            addedPerson = true;
        }

        boolean hasStation = false;
        for (Industry curr : market.getIndustries()) {
            if (curr.getSpec().hasTag(Industries.TAG_STATION)) {
                hasStation = true;
            }
        }
        if (hasStation) {
            String rankId = Ranks.SPACE_COMMANDER;
            if (market.getSize() >= 6) {
                rankId = Ranks.SPACE_ADMIRAL;
            } else if (market.getSize() >= 4) {
                rankId = Ranks.SPACE_CAPTAIN;
            }

            addPerson(ip, market, rankId, Ranks.POST_STATION_COMMANDER, true);
            addedPerson = true;
        }

//			if (market.hasIndustry(Industries.WAYSTATION)) {
//				// kept here as a reminder to check core plugin again when needed
//			}

        if (market.hasSpaceport()) {
            //person.setRankId(Ranks.SPACE_CAPTAIN);

            addPerson(ip, market, null, Ranks.POST_PORTMASTER, true);
            addedPerson = true;
        }

        if (addedPerson) {
            addPerson(ip, market, Ranks.SPACE_COMMANDER, Ranks.POST_SUPPLY_OFFICER, true);
        }

        if (!addedPerson) {
            addPerson(ip, market, Ranks.CITIZEN, Ranks.POST_ADMINISTRATOR, true);
        }
    }

    public static MarketAPI addMarketplace(
            String factionID,
            SectorEntityToken primaryEntity,
            ArrayList<SectorEntityToken> connectedEntities,
            String name,
            int size,
            ArrayList<String> marketConditions,
            ArrayList<String> submarkets,
            ArrayList<String> industries,
            Boolean WithJunkAndChatter,
            Boolean PirateMode) {
        EconomyAPI globalEconomy = Global.getSector().getEconomy();
        String planetID = primaryEntity.getId();
        String marketID = planetID + "market";

        MarketAPI newMarket = Global.getFactory().createMarket(marketID, name, size);
        newMarket.setFactionId(factionID);
        newMarket.setPrimaryEntity(primaryEntity);
        newMarket.getTariff().modifyFlat("generator", newMarket.getFaction().getTariffFraction());

        if (submarkets != null) {
            for (String market : submarkets) {
                newMarket.addSubmarket(market);
            }
        }

        for (String condition : marketConditions) {
            try {
                newMarket.addCondition(condition);
            } catch (RuntimeException e) {
                newMarket.addIndustry(condition);
            }
        }
        if (industries != null) {
            for (String industry : industries) {
                newMarket.addIndustry(industry);
            }

        }

        if (connectedEntities != null) {
            for (SectorEntityToken entity : connectedEntities) {
                newMarket.getConnectedEntities().add(entity);
            }
        }

        globalEconomy.addMarket(newMarket, WithJunkAndChatter);
        primaryEntity.setMarket(newMarket);
        primaryEntity.setFaction(factionID);

        if (connectedEntities != null) {
            for (SectorEntityToken entity : connectedEntities) {
                entity.setMarket(newMarket);
                entity.setFaction(factionID);
            }
        }

        if (PirateMode) {
            newMarket.setEconGroup(newMarket.getId());
            newMarket.setHidden(true);
            primaryEntity.setSensorProfile(1f);
            primaryEntity.setDiscoverable(true);
            primaryEntity.getDetectedRangeMod().modifyFlat("gen", 5000f);
            newMarket.getMemoryWithoutUpdate().set(DecivTracker.NO_DECIV_KEY, true);
        } else {
            for (MarketConditionAPI mc : newMarket.getConditions()) {
                mc.setSurveyed(true);
            }
            newMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        }

        newMarket.reapplyIndustries();
        return newMarket;
    }

    public static Industry getStationIndustry(MarketAPI market) {
        for (Industry curr : market.getIndustries()) {
            if (curr.getSpec().hasTag(Industries.TAG_STATION)) {
                return curr;
            }
        }
        return null;
    }

    public static void updateStationIfNeeded(MarketAPI market, Industry curr, String goalIndID) {
        if (curr == null) return;

        String currIndId = (Objects.requireNonNull(getStationIndustry(market)).getId());

        if (currIndId.equals(goalIndID)) return;

        market.removeIndustry(curr.getId(), null, false);

        market.addIndustry(goalIndID);
        curr = getStationIndustry(market);
        if (curr == null) return;

        curr.finishBuildingOrUpgrading();


        CampaignFleetAPI fleet = Misc.getStationFleet(market.getPrimaryEntity());
        if (fleet == null) return;

        List<FleetMemberAPI> members = fleet.getFleetData().getMembersListCopy();
        if (members.isEmpty()) return;

        fleet.inflateIfNeeded();

        FleetMemberAPI station = members.get(0);

        WeightedRandomPicker<Integer> picker = new WeightedRandomPicker<>();
        int index = 1; // index 0 is station body
        for (String slotId : station.getVariant().getModuleSlots()) {
            ShipVariantAPI mv = station.getVariant().getModuleVariant(slotId);
            if (Misc.isActiveModule(mv)) {
                picker.add(index, 1f);
            }
            index++;
        }
    }
}