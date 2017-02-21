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

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class LeadScore {
    private int score;
    private boolean bonus = false;
    
    public int getScore() {
        return score;
    }
    
    public boolean isBonus() {
        return bonus;
    }
    
    public String getFullScorenInString() {
        return bonus ? String.valueOf(score) + '+' : String.valueOf(score);
    }
    
    public float getFullScoreInFloat() {
        float fullscore = score;
        if(bonus) fullscore += 0.5f;
        return fullscore;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    public void reset() {
        bonus = false;
        score = 0;
    }
    
    public void setFullScore(int score, boolean bonus) {
        this.score = score;
        this.bonus = bonus;
    }
}
