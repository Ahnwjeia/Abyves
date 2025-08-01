package Ahnwjeia.abyves.world.systems;

import Ahnwjeia.abyves.helpers.MarketHelpers;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.enc.AbyssalRogueStellarObjectEPEC;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator.StarSystemType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Eos implements SectorGeneratorPlugin {
    float width = Global.getSettings().getFloat("sectorWidth");
    float height = Global.getSettings().getFloat("sectorHeight");
    public static Color EOS_AMBIENT_LIGHT_COLOR = new Color(100,100,100,255);

    @Override
    public void generate(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Eos"); //create a new variable called system. this is assigned an instance of the new star system added to the Sector at the same time
        system.setName("Eos");
        system.setType(StarSystemType.DEEP_SPACE);
        system.addTag(Tags.THEME_SPECIAL);
        LocationAPI hyper = Global.getSector().getHyperspace();

        system.getLocation().set((-width/2) * 0.6f, -height/2); //sets location of system in hyperspace. map size is in the order of 100000x100000, and 0, 0 is the center of the map, this will set the location to the east and slightly south of the center
        system.setBackgroundTextureFilename("graphics/backgrounds/background4.jpg"); //sets the background image for when in the system. this is a filepath to an image in the core game files

        Random random = StarSystemGenerator.random;

        SectorEntityToken center = system.initNonStarCenter();
        system.setLightColor(EOS_AMBIENT_LIGHT_COLOR);
        center.addTag(Tags.AMBIENT_LS);

        PlanetAPI planetOne = system.addPlanet(
                "hemeraplanet",
                null,
                "Hemera",
                "rocky_ice",
                0,
                300f,
                0,
                0
        );
        planetOne.getMemoryWithoutUpdate().set("$gateHaulerIceGiant", true);
        planetOne.getMarket().addCondition(Conditions.COLD);
        planetOne.getMarket().addCondition(Conditions.DARK);
        planetOne.getMarket().addCondition(Conditions.ORE_RICH);
        planetOne.getMarket().addCondition(Conditions.RARE_ORE_MODERATE);

        planetOne.setOrbit(null);
        planetOne.setLocation(0, 0);

        SectorEntityToken stationOne = system.addCustomEntity("corruptedsleeper", "Reprocessor Node", "corrupted_sleeper", Factions.THREAT);
        stationOne.setCircularOrbit(planetOne, 15f, 600, 91);
        stationOne.setInteractionImage("illustrations", "corrupted_sleeper_illu");
        stationOne.setCustomDescriptionId("corruptedsleeperdesc");

        MarketAPI corruptedsleepermarket = MarketHelpers.addMarketplace(
                Factions.THREAT,
                stationOne,
                null,
                "Reprocessor Node",
                3, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.POPULATION_3
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE //add a player storage market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        "swarm_launch_center",
                        Industries.SPACEPORT,
                        Industries.HEAVYINDUSTRY,
                        Industries.CRYOSANCTUM
                )),
                true,
                true
        );
        corruptedsleepermarket.addTag(Tags.STATION);
        corruptedsleepermarket.addTag(Tags.MARKET_NO_OFFICER_SPAWN);
        system.autogenerateHyperspaceJumpPoints(true, true);
        AbyssalRogueStellarObjectEPEC.setAbyssalDetectedRanges(system);

        SectorEntityToken eos_gate =system.addCustomEntity(
                "eos_gate",
                "Eos Gate",
                "inactive_gate",
                null
        );
        eos_gate.setCircularOrbit(planetOne,45,9680, 550f );
    }
}
