/*
 * Copyright 2019 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.namegenerator.creature.species;

import org.terasology.namegenerator.creature.species.adjective.AnimalAdjectiveProvider;
import org.terasology.namegenerator.region.adjective.RegionAdjectiveProvider;
import org.terasology.utilities.random.MersenneRandom;
import org.terasology.utilities.random.Random;

import java.util.List;

public class AnimalNameProvider {
    private final AnimalNameTheme theme;
    private final Random random;
    private final List<String> prefixes;
    private final AnimalNameAffinityVector defaultAffinity = AnimalNameAffinityVector.create();
    private final RegionAdjectiveProvider regionProvider;
    private final AnimalAdjectiveProvider adjectiveProvider;

    /**
     * Uses the default naming theme
     *
     * @param seed the seed
     */
    public AnimalNameProvider(long seed) {
        this(seed, AnimalNameAssetTheme.BIRD_LARGE);
    }

    /**
     * @param seed  the seed value
     * @param aTheme the naming theme
     */
    public AnimalNameProvider(long seed, AnimalNameTheme aTheme) {

        random = new MersenneRandom(seed);
        theme = aTheme;
        prefixes = theme.getPrefixes();
        regionProvider = new RegionAdjectiveProvider(seed);
        adjectiveProvider = new AnimalAdjectiveProvider(seed);
    }

    /**
     * @return a creature adjective with default affinities
     */
    public String generateName() {
        return generateName(defaultAffinity.type(random.nextDouble(), random.nextDouble()));
    }

    /**
     * @param affinity the list of affinities
     * @return the adjective
     */
    public synchronized String generateName(AnimalNameAffinityVector affinity) {
        List<String> names = theme.getNames();
        String name = names.get((int)(names.size() * random.nextDouble()));

        int prefixIndex = (int) (random.nextDouble() * prefixes.size());
        String prefix = prefixes.get(prefixIndex);
        String region = regionProvider.generateName();
        String adjective = adjectiveProvider.generateName();

        int choice = random.nextInt(14);
        switch(choice)
        {
            case 0:
            case 1:return region + " " + adjective + " " + name;
            case 2: return region + " " + adjective + " " + prefix + name.toLowerCase();
            case 3:
            case 4: return region + " " + name;
            case 5: return region + " " + prefix + name.toLowerCase();
            case 6:
            case 7: return adjective + " " + name;
            case 8: return adjective + " " + prefix + name.toLowerCase();
            case 9: return prefix + name.toLowerCase();
            case 10:
            case 11: return adjective + " " + region + " " + name;
            case 12: return adjective + " " + region + " " + prefix + name.toLowerCase();
            default: return adjective + " " + name;
        }
    }
}
