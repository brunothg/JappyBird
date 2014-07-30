package de.bno.jappybird.dao;

import java.util.List;

public interface BestenlisteDAO {

	public List<Score> getBestenliste();

	public List<Score> getBestScores(int number);

	public List<Score> getLastScores(int number);

	public boolean insertScore(Score score);
}
