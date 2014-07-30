package de.bno.jappybird.dao;

import java.util.ArrayList;
import java.util.List;

public class BestenlisteFileDAO implements BestenlisteDAO {

	public BestenlisteFileDAO() {

	}

	@Override
	public List<Score> getBestenliste() {
		List<Score> ret = new ArrayList<Score>(10);

		return ret;
	}

	@Override
	public boolean insertScore(Score score) {
		return true;
	}

	@Override
	public boolean isHighscore(int score) {
		return false;
	}

}
