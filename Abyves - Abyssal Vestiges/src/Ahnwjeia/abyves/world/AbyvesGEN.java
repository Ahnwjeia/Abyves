package Ahnwjeia.abyves.world;

import Ahnwjeia.abyves.world.systems.Eos;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;

public class AbyvesGEN implements SectorGeneratorPlugin {
    @Override
    public void generate(SectorAPI sector) {
        new Eos().generate(sector);
       }
}

