package de.bno.jappybird.dao;

import java.util.List;

public interface BestenlisteDAO {

	public List<Score> getBestenliste();

	public boolean insertScore(Score score);

	public boolean isHighscore(int score);
}
