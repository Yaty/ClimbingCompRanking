/* 
 * Copyright (C) 2017
 * Mail : Hugo Da Roit - contact@hdaroit.fr
 * GitHub : https://github.com/Yaty
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
        BoulderingRankingTest.class,
        LeadBoulderingRankingTest.class,
        SpeedBoulderingRankingTest.class,
        SpeedLeadRankingTest.class,
        SpeedRankingTest.class,
        CombinedRankingTest.class,
        LeadRankingTest.class,
        RankingWithMultipleCategories.class
    }
)
public class TestLauncher {}
