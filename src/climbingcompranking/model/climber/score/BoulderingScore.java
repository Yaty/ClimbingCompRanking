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
package climbingcompranking.model.climber.score;

import climbingcompranking.model.climber.exceptions.InvalidScoreException;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class BoulderingScore {
    private int numberOfTop;
    private int numberOfTryToTop;
    private int numberOfBonus;
    private int numberOfTryToBonus;
    
    public String getFullScore() {
        return String.valueOf(numberOfTop) + 'T' + String.valueOf(numberOfTryToTop) + ':' + String.valueOf(numberOfBonus) + 'B' + String.valueOf(numberOfTryToBonus);
    }

    public int getNumberOfTop() {
        return numberOfTop;
    }

    public void setNumberOfTop(int numberOfTop) {
        this.numberOfTop = numberOfTop;
    }

    public int getNumberOfTryToTop() {
        return numberOfTryToTop;
    }

    public void setNumberOfTryToTop(int numberOfTryToTop) {
        this.numberOfTryToTop = numberOfTryToTop;
    }

    public int getNumberOfBonus() {
        return numberOfBonus;
    }

    public void setNumberOfBonus(int numberOfBonus) {
        this.numberOfBonus = numberOfBonus;
    }

    public int getNumberOfTryToBonus() {
        return numberOfTryToBonus;
    }

    public void setNumberOfTryToBonus(int numberOfTryToBonus) {
        this.numberOfTryToBonus = numberOfTryToBonus;
    }

    public float getFullScoreInFloat() {
        return numberOfTop/numberOfTryToTop + numberOfBonus/numberOfTryToBonus;
    }

    public void reset() {
        numberOfBonus = numberOfTop = numberOfTryToBonus = numberOfTryToTop = 0;
    }
    
    public void setScore(int numberOfTop, int numberOfTryToTop, int numberOfBonus, int numberOfTryToBonus) throws InvalidScoreException {
        // 5 4 1 1
        if(numberOfTop > numberOfTryToTop) throw new InvalidScoreException("A climber can't have more top than top tries !");
        if(numberOfBonus > numberOfTryToBonus) throw new InvalidScoreException("A climber can't have more bonus than bonus tries !");
        if(numberOfTop > numberOfBonus) throw new InvalidScoreException("A climber can't have more tops than bonus");
        this.numberOfBonus = numberOfBonus;
        this.numberOfTryToBonus = numberOfTryToBonus;
        this.numberOfTop = numberOfTop;
        this.numberOfTryToTop = numberOfTryToTop;
    }
    
    public void addTop(int numberOfTries) throws InvalidScoreException {
        if(numberOfTries < 1) throw new InvalidScoreException("You can't add zero or negative tries when adding a top.");
        if(numberOfBonus < numberOfTop + 1) throw new InvalidScoreException("Add bonus tries first.");
        numberOfTryToTop += numberOfTries;
        numberOfTop++;
    }
    
    public void addTopTriesWithoutToping(int numberOfTries) throws InvalidScoreException {
        if(numberOfTries < 1) throw new InvalidScoreException("You can't add zero or negative tries when adding top tries.");
        numberOfTryToTop += numberOfTries;        
    }
    
    public void addBonusTriesWithoutBonusing(int numberOfTries) throws InvalidScoreException {
        if(numberOfTries < 1) throw new InvalidScoreException("You can't add zero or negative tries when adding bonus tries.");
        numberOfTryToBonus += numberOfTries;        
    }
    
    public void addBonus(int numberOfTries) throws InvalidScoreException {
        if(numberOfTries < 1) throw new InvalidScoreException("You can't add zero or negative tries when adding a bonus.");
        numberOfTryToBonus += numberOfTries;
        numberOfBonus++;
    }
}
