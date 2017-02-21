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
