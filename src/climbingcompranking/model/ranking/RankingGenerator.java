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
package climbingcompranking.model.ranking;

import climbingcompranking.model.climber.Rank;
import climbingcompranking.model.Competition.CompetitionType;
import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.utils.I18n;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class RankingGenerator {
    private final HashMap<Category, ArrayList<Climber>> climbersMap;

    public RankingGenerator(HashMap<Category, ArrayList<Climber>> climbers) {
        this.climbersMap = climbers;
    }
    
    public HashMap<Category, ArrayList<Climber>> getClimbers() {
        return climbersMap;
    }
    
    private void updateLeadScore() {
        for(ArrayList<Climber> climbers : climbersMap.values()) {
            climbers.sort((Climber cell, Climber c2) -> {
                int score = Float.compare(cell.getLeadScore().getFullScoreInFloat(), c2.getLeadScore().getFullScoreInFloat());
                if(score == 0) {
                    cell.getRank().setExaequo(true);
                    c2.getRank().setExaequo(true);
                }
                return -score;
            });

            // Creating a ranking
            int counter = 1, skip = 0;
            for(Climber climber : climbers) {
                if(climber.getRank().isExaequo()) {
                    climber.getRank().setLeadRank(counter);
                    skip++;
                } else {
                    climber.getRank().setLeadRank(counter++ + skip);
                    skip = 0;
                }
                climber.getRank().setExaequo(false);
            }
        }
    }
    
    private void updateSpeedScore() {
        for(ArrayList<Climber> climbers : climbersMap.values()) {
            climbers.sort((Climber cell, Climber c2) -> {
                int score = Float.compare(cell.getSpeedScore().getSpeed(), c2.getSpeedScore().getSpeed());
                 if(score == 0) {
                    cell.getRank().setExaequo(true);
                    c2.getRank().setExaequo(true);
                }
                return score;
            });

            // Creating a ranking
            int counter = 1, skip = 0;
            for(Climber climber : climbers) {
                if(climber.getRank().isExaequo()) {
                    climber.getRank().setSpeedRank(counter);
                    skip++;
                } else {
                    climber.getRank().setSpeedRank(counter++ + skip);
                    skip = 0;
                }
                climber.getRank().setExaequo(false);
            }
        }
    }
    
    private void updateBoulderingScore() {
        for(ArrayList<Climber> climbers : climbersMap.values()) {
            climbers.sort((Climber cell, Climber c2) -> {
                 // first we sort by the number of tops
                 int score = Integer.compare(cell.getBoulderingScore().getNumberOfTop(), c2.getBoulderingScore().getNumberOfTop());
                 if(score != 0) return -score;

                 // if it's equal we look a the number of tries to get those tops
                 score = Integer.compare(cell.getBoulderingScore().getNumberOfTryToTop(), c2.getBoulderingScore().getNumberOfTryToTop());
                 if(score != 0) return score;

                 // if it's again equal we look at the number of bonus
                 score = Integer.compare(cell.getBoulderingScore().getNumberOfBonus(), c2.getBoulderingScore().getNumberOfBonus());
                 if(score != 0) return -score;

                 // and finally we compare the number of tries to get those bonus
                 score = Integer.compare(cell.getBoulderingScore().getNumberOfTryToBonus(), c2.getBoulderingScore().getNumberOfTryToBonus());
                 if(score == 0) { // then those climbersMap are ex-aequo
                    cell.getRank().setExaequo(true);
                    c2.getRank().setExaequo(true);
                 }
                 return score;
            });

             // Update the rank of the climbersMap
            int counter = 1, skip = 0;
            for(Climber climber : climbers) {
                if(climber.getRank().isExaequo()) {
                    climber.getRank().setBoulderingRank(counter);
                    skip++;
                } else {
                    climber.getRank().setBoulderingRank(counter++ + skip);
                    skip = 0;
                }
                climber.getRank().setExaequo(false);
            }
        }
    }
    
    // Beurk, refactor needed
    private void doTheRanking(CompetitionType compType) {     
        // First we have to reset rank exaequo values
        // We will recalculate them after
        for(ArrayList<Climber> climbers : climbersMap.values()) {
            for(Climber climber : climbers) {
                climber.getRank().setExaequo(false);
            }
        }
        
        // Then we update scores
        switch(compType) {
            case BOULDERING:
                updateBoulderingScore();
                break;
            case LEAD:
                updateLeadScore();
                break;
            case SPEED:
                updateSpeedScore();
                break;
            case LEAD_AND_BOULDERING:
                updateLeadScore();
                updateBoulderingScore();
                break;
            case SPEED_AND_BOULDERING:
                updateSpeedScore();
                updateBoulderingScore();
                break;
            case SPEED_AND_LEAD:
                updateSpeedScore();
                updateLeadScore();
                break;
            case COMBINED:
                updateBoulderingScore();
                updateLeadScore();
                updateSpeedScore();
                break;
        }
        
        // Sorting the climbersMap list with the total points
        for(ArrayList<Climber> climbers : climbersMap.values()) {
            climbers.sort((Climber cell, Climber c2) -> {
                int score = Integer.compare(cell.getRank().getTotalPoints(compType), c2.getRank().getTotalPoints(compType));
                if(score == 0) {
                    cell.getRank().setExaequo(true);
                    c2.getRank().setExaequo(true);
                }
                return score;
            });
        }
        
        // And finally update the overall ranking of each climber
        for(ArrayList<Climber> climbers : climbersMap.values()) {
            int skip = 0, counter = 1;
            for(int i = 0 ; i < climbers.size() ; i++) {
                Rank rank = climbers.get(i).getRank();
                if(rank.isExaequo()) {
                    rank.setOverallRank(counter);
                    skip++;
                } else {
                    rank.setOverallRank(counter++ + skip);
                    skip = 0;
                }
            }
        }
    }

    public String getRankingInString(CompetitionType compType) {
        if(climbersMap.isEmpty()) return I18n.MODEL.getString("NoClimbers");
        StringBuilder str = new StringBuilder();
        doTheRanking(compType);
        for(Map.Entry<Category, ArrayList<Climber>> climbers : climbersMap.entrySet()) {
            str.append(climbers.getKey().getCategoryName()).append("\n");
            for(Climber climber : climbers.getValue())
                str.append(climber.getFullName()).append(' ').append(climber.getRank().getOverallRank()).append("\n");
        }
        return str.toString();
    }
    
    public void createRankingPDF(String competitionName, CompetitionType compType) {
        doTheRanking(compType);
        try {
            Document document = new Document(PageSize.A4.rotate()); // Landscape
            Date date = new Date();
            String dateStr = "";
            if(Locale.getDefault().equals(Locale.FRANCE)) {
                dateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
            } else {
                dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            PdfWriter.getInstance(document, new FileOutputStream("data/pdf/" + competitionName + '-' + dateStr + ".pdf"));               

            document.open();
                // Meta data
                document.addTitle(competitionName);
                document.addSubject(competitionName + " ranking.");
                document.addKeywords(competitionName + ", ranking, climbingcompetition, climbcompranking");
                document.addAuthor("ClimbingCompRanking - https://github.com/Yaty/ClimbingCompRanking");
                document.addCreator("ClimbingCompRanking - https://github.com/Yaty/ClimbingCompRanking");
                document.addCreationDate();
                document.setMargins(0, 0, 0, 0);
                
                PdfPCell club = new PdfPCell(new Phrase(I18n.MODEL.getString("Club")));
                PdfPCell climberName = new PdfPCell(new Phrase(I18n.MODEL.getString("ClimberName")));
                PdfPCell ranking = new PdfPCell(new Phrase(I18n.MODEL.getString("Ranking")));
                

                for(Map.Entry<Category, ArrayList<Climber>> climbersCategory : climbersMap.entrySet()) {
                    document.newPage();
                    // Title
                    Paragraph title = new Paragraph(competitionName + " : " + climbersCategory.getKey().getCategoryName());
                    title.setAlignment(Element.ALIGN_CENTER);
                    title.setFont(new Font(Font.FontFamily.HELVETICA, 36));
                    title.setSpacingAfter(20);
                    
                    // Table
                    PdfPTable table = null;
                    switch(compType) {
                        case BOULDERING:
                        case LEAD:
                        case SPEED:
                            table = new PdfPTable(3); // Club | Full name | Ranking
                            table.addCell(club);
                            table.addCell(climberName);
                            table.addCell(ranking);
                            for(Climber climber : climbersCategory.getValue()) {
                                table.addCell(new PdfPCell(new Phrase(climber.getClubName())));
                                table.addCell(new PdfPCell(new Phrase(climber.getFullName())));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getOverallRank()))));
                            }
                            break;
                        case LEAD_AND_BOULDERING:
                            table = new PdfPTable(6); // Club | Full name | Ranking
                            table.addCell(club);
                            table.addCell(climberName);
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("LeadRanking"))));
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("BoulderingRanking"))));
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("Sum"))));    
                            table.addCell(ranking);
                            for(Climber climber : climbersCategory.getValue()) {
                                table.addCell(new PdfPCell(new Phrase(climber.getClubName())));
                                table.addCell(new PdfPCell(new Phrase(climber.getFullName())));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getLeadRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getBoulderingRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getTotalPoints(compType)))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getOverallRank()))));
                            }
                            break;
                        case SPEED_AND_BOULDERING:
                            table = new PdfPTable(6); // Club | Full name | Ranking
                            table.addCell(club);
                            table.addCell(climberName);
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("SpeedRanking")))); 
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("BoulderingRanking"))));
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("Sum"))));    
                            table.addCell(ranking);
                            for(Climber climber : climbersCategory.getValue()) {
                                table.addCell(new PdfPCell(new Phrase(climber.getClubName())));
                                table.addCell(new PdfPCell(new Phrase(climber.getFullName())));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getSpeedRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getBoulderingRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getTotalPoints(compType)))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getOverallRank()))));
                            }
                            break;
                        case SPEED_AND_LEAD:
                            table = new PdfPTable(6); // Club | Full name | Ranking
                            table.addCell(club);
                            table.addCell(climberName);
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("SpeedRanking")))); 
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("LeadRanking"))));
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("Sum"))));    
                            table.addCell(ranking);
                            for(Climber climber : climbersCategory.getValue()) {
                                table.addCell(new PdfPCell(new Phrase(climber.getClubName())));
                                table.addCell(new PdfPCell(new Phrase(climber.getFullName())));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getSpeedRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getLeadRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getTotalPoints(compType)))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getOverallRank()))));
                            }
                            break;
                        case COMBINED:
                            table = new PdfPTable(7); // Club | Full name | Ranking
                            table.addCell(club);
                            table.addCell(climberName);
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("LeadRanking")))); 
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("BoulderingRanking"))));
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("SpeedRanking"))));
                            table.addCell(new PdfPCell(new Phrase(I18n.MODEL.getString("Sum"))));    
                            table.addCell(ranking);
                            for(Climber climber : climbersCategory.getValue()) {
                                table.addCell(new PdfPCell(new Phrase(climber.getClubName())));
                                table.addCell(new PdfPCell(new Phrase(climber.getFullName())));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getLeadRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getBoulderingRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getSpeedRank()))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getTotalPoints(compType)))));
                                table.addCell(new PdfPCell(new Phrase(String.valueOf(climber.getRank().getOverallRank()))));
                            }
                            break;
                    }
                    
                    // Alignment
                    for(PdfPRow row : table.getRows()) {
                        PdfPCell[] cells = row.getCells();
                        for(PdfPCell cellToAlign : cells) {
                            cellToAlign.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cellToAlign.setVerticalAlignment(Element.ALIGN_CENTER);                            
                        }
                    }
                    
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.setHeaderRows(1);              

                    document.add(title);
                    document.add(table);
                }
            document.close();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(RankingGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() {
        String str = "";
        for(Map.Entry<Category, ArrayList<Climber>> entry : climbersMap.entrySet()) {
            str += entry.getKey().getCategoryName() + "\n";
            for(Climber climber : entry.getValue())
                str += climber.getFullName() + "\n";
        }
        return str;
    }
    
}
