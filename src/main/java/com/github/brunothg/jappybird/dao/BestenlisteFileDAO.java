package com.github.brunothg.jappybird.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.brunothg.stream.autokey.AutoKey;
import com.github.brunothg.stream.manipulate.ManipulatorInputStream;
import com.github.brunothg.stream.manipulate.ManipulatorOutputStream;

public class BestenlisteFileDAO implements BestenlisteDAO {

	private static final Path db = Paths.get("./data/highscore.ak");
	private static List<Score> scores;

	public BestenlisteFileDAO() {
	}

	@Override
	public List<Score> getBestenliste() {

		if (scores != null) {
			return scores;
		}

		List<Score> ret = new ArrayList<Score>(10);

		if (!Files.exists(db)) {
			scores = ret;
			return ret;
		}

		try {
			readIntoList(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.sort(ret, Collections.reverseOrder());

		scores = ret;

		return ret;
	}

	private void readIntoList(List<Score> ret) throws IOException {

		if (!Files.exists(db)) {
			return;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(
				new ManipulatorInputStream(Files.newInputStream(db),
						new AutoKey(this.getClass().getSimpleName(),
								AutoKey.MODUS_ENCODE)),
				Charset.forName("UTF-8")));

		String line = null;

		while ((line = in.readLine()) != null) {

			try {
				String name = line;

				String score = (line = in.readLine());
				int scoreI = Integer.valueOf(score);

				ret.add(new Score(name, scoreI));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		in.close();
	}

	private static synchronized void writeFromList(List<Score> list)
			throws IOException {

		if (!Files.exists(db)) {

			Path dir = db.getParent();
			if (!Files.exists(dir)) {
				Files.createDirectories(dir);
			}

			Files.createFile(db);
		}

		scores = list;

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new ManipulatorOutputStream(Files.newOutputStream(db),
						new AutoKey(BestenlisteFileDAO.class.getSimpleName(),
								AutoKey.MODUS_DECODE)),
				Charset.forName("UTF-8")));

		for (Score s : list) {

			out.write(s.getName());
			out.newLine();

			out.write(s.getScore() + "");
			out.newLine();
		}

		out.close();
	}

	@Override
	public boolean insertScore(Score score) {

		List<Score> list = getBestenliste();

		if (list.size() >= 10 && list.get(9).getScore() >= score.getScore()) {
			return false;
		}

		list.add(score);
		Collections.sort(list, Collections.reverseOrder());

		list = list.subList(0, Math.min(list.size(), 10));

		scores = list;

		return true;
	}

	@Override
	public boolean isHighscore(int score) {
		List<Score> list = getBestenliste();

		if (list.size() < 10) {
			return true;
		}

		if (list.get(9).getScore() < score) {
			return true;
		}

		return false;
	}

	static {
		registerShutdownHook();
	}

	private static void registerShutdownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {

				try {
					writeFromList(scores);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
