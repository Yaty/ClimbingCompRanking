/*
 * Copyright 2017 Hugo Da Roit - contact@hdaroit.fr.
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
