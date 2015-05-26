package de.bno.jappybird.dao;

import java.util.LinkedList;
import java.util.List;

import de.bno.jappybird.settings.HSettings;
import de.kvwl.n8dA.infrastructure.commons.entity.GamePerson;
import de.kvwl.n8dA.infrastructure.rewards.client.CreditAccessClient;
import de.kvwl.n8dA.infrastructure.rewards.client.ExceptionDialog;

public class BestenlisteRewardsDAO implements BestenlisteDAO
{

	@Override
	public List<Score> getBestenliste()
	{

		List<Score> highscore = new LinkedList<Score>();

		String gameName = HSettings.getString(HSettings.KEY_GAME_NAME, "JappyBird");
		CreditAccessClient accessClient = null;
		try
		{
			accessClient = new CreditAccessClient(
				HSettings.getString(HSettings.KEY_REWARD_SERVER_ADDRESS, "localhost"), HSettings.getBoolean(
					HSettings.KEY_INSTALL_SECURITY_MANAGER, false));
			accessClient.initConnectionToServer();

			List<GamePerson> first10Scores = accessClient.getFirst10GamePersonsForGame(gameName);

			int count = 0;
			for (GamePerson person : first10Scores)
			{
				if (count >= 10)
				{
					break;
				}

				highscore.add(new Score(person.getPerson().getName(), person.getPoints()
					* HSettings.getInt(HSettings.KEY_REWARD_MULTIPLIER, 1)));

				count++;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return highscore;
	}

	@Override
	public boolean insertScore(Score score)
	{
		boolean isInHighscore = false;

		String gameName = HSettings.getString(HSettings.KEY_GAME_NAME, "JappyBird");

		CreditAccessClient accessClient = null;
		try
		{
			accessClient = new CreditAccessClient(
				HSettings.getString(HSettings.KEY_REWARD_SERVER_ADDRESS, "localhost"), HSettings.getBoolean(
					HSettings.KEY_INSTALL_SECURITY_MANAGER, false));
			accessClient.initConnectionToServer();

			List<GamePerson> first10Scores = accessClient.getFirst10GamePersonsForGame(gameName);
			GamePerson lastScore = first10Scores.get(Math.min(first10Scores.size() - 1, 9));
			Integer lastPoints = lastScore.getPoints();

			if (first10Scores.size() < 10 || score.getScore() > lastPoints)
			{

				isInHighscore = true;
			}

			accessClient.persistConfigurationPointsForPerson(score.getName(), gameName, score.getScore());
		}
		catch (Exception e)
		{
			ExceptionDialog.showExceptionDialog(null, "Verbindungsfehler",
				"Es konnte keien Verbindung zum Punkteserver aufgebaut werden.", e);
			return false;
		}

		return isInHighscore;
	}

	@Override
	public boolean isHighscore(int score)
	{

		return true;
	}

}
