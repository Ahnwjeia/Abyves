package Ahnwjeia.abyves.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import org.apache.log4j.Logger;

public class AbyvesPeople {
    public static String AbyvesContact1 = "abyvescontact1";
    public static String AbyvesContact2 = "abyvescontact2";
    public static String AbyvesContact3 = "abyvescontact3";
    public static Logger log = Global.getLogger(AbyvesPeople.class);

    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }
    public static void AbyvesCreateContact() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        MarketAPI market = null;

        market = Global.getSector().getEconomy().getMarket("corruptedsleepermarket");
		    if (market != null) {
                PersonAPI abyvescontact1 = Global.getFactory().createPerson();
                abyvescontact1.setId(AbyvesContact1);
                abyvescontact1.setFaction(Factions.THREAT);
                abyvescontact1.setGender(FullName.Gender.FEMALE);
                abyvescontact1.setRankId(Ranks.AGENT);
                abyvescontact1.setPostId(Ranks.POST_ADMINISTRATOR);
                abyvescontact1.setImportance(PersonImportance.HIGH);
                abyvescontact1.getName().setFirst("Unit");
                abyvescontact1.getName().setLast("Ananke");
                abyvescontact1.setPortraitSprite(Global.getSettings().getSpriteName("characters", "AbyvesContact1"));
                if (!ip.containsPerson(abyvescontact1)) {
                    log.info("Abyves_RETROGEN_PEOPLE: Unit Ananke did not exist. Has been generated retroactively");
                    market.setAdmin(abyvescontact1);
                    market.getCommDirectory().addPerson(abyvescontact1, 0);
                    market.addPerson(abyvescontact1);
                    ip.addPerson(abyvescontact1);
                } else
                    log.info("Abyves_RETROGEN_PEOPLE: Unit Ananke already exists. No action taken");

                PersonAPI abyvescontact2 = Global.getFactory().createPerson();
                abyvescontact2.setId(AbyvesContact2);
                abyvescontact2.setFaction(Factions.THREAT);
                abyvescontact2.setGender(FullName.Gender.MALE);
                abyvescontact2.setRankId(Ranks.AGENT);
                abyvescontact2.setPostId(Ranks.POST_PORTMASTER);
                abyvescontact2.setImportance(PersonImportance.MEDIUM);
                abyvescontact2.getName().setFirst("Unit");
                abyvescontact2.getName().setLast("Protogonos");
                abyvescontact2.setPortraitSprite(Global.getSettings().getSpriteName("characters", "AbyvesContact2"));
                if (!ip.containsPerson(abyvescontact2)) {
                    log.info("Abyves_RETROGEN_PEOPLE: Unit Protogonos did not exist. Has been generated retroactively");
                    market.getCommDirectory().addPerson(abyvescontact2, 1);
                    market.addPerson(abyvescontact2);
                    ip.addPerson(abyvescontact2);
                } else
                    log.info("Abyves_RETROGEN_PEOPLE: Unit Protogonos already exists. No action taken");

                PersonAPI abyvescontact3 = Global.getFactory().createPerson();
                abyvescontact3.setId(AbyvesContact3);
                abyvescontact3.setFaction(Factions.THREAT);
                abyvescontact3.setGender(FullName.Gender.FEMALE);
                abyvescontact3.setRankId(Ranks.AGENT);
                abyvescontact3.setPostId(Ranks.POST_SUPPLY_OFFICER);
                abyvescontact3.setImportance(PersonImportance.MEDIUM);
                abyvescontact3.getName().setFirst("Unit");
                abyvescontact3.getName().setLast("Ouros");
                abyvescontact3.setPortraitSprite(Global.getSettings().getSpriteName("characters", "AbyvesContact3"));
                if (!ip.containsPerson(abyvescontact3)) {
                    log.info("Abyves_RETROGEN_PEOPLE: Unit Ouros did not exist. Has been generated retroactively");
                    market.getCommDirectory().addPerson(abyvescontact3, 2);
                    market.addPerson(abyvescontact3);
                    ip.addPerson(abyvescontact3);
                } else
                    log.info("Abyves_RETROGEN_PEOPLE: Unit Ouros already exists. No action taken");
            }
    }
}