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
package climbingcompranking.controller;

import climbingcompranking.model.Competition;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class CompetitionLoader {
    
    public static Competition loadComp(String compName) {
        StringBuilder compDataJson = new StringBuilder();
        Path compPath = Paths.get("src/climbingcompranking/data/competitions/" + compName + ".json");
        try (Stream<String> lines = Files.lines(compPath)) {
            lines.forEach(s -> compDataJson.append(s));
        } catch (IOException ex) {
            Logger.getLogger(Competition.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        Gson gson = new Gson();
        Competition loadedComp = gson.fromJson(compDataJson.toString(), Competition.class);
        return loadedComp;
    }    
}
