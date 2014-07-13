package de.bno.jappybird.engine;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JFrame;

/**
 * JFrame zum darstellen Szenenbarsierter Anwendungen. Die Übergebene Clock
 * sorgt für automatisches neuzeichnen.
 * 
 * @author Marvin Bruns
 *
 */
public class GameFrame extends JFrame implements ClockListener {

	private static final long serialVersionUID = -6931296138340412107L;
	private Stage stage;
	private Clock clk;

	public GameFrame(String s) {
		this();
		setTitle(s);
	}

	public GameFrame() {
		iniFrame();
	}

	private void iniFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationByPlatform(true);
		setTitle("GameFrame");
		setIconImage(null);
		setLayout(new BorderLayout());

		stage = new Stage();
		add(stage);
		stage.requestFocusInWindow();
	}

	@Override
	public void setIconImage(Image image) {
		if (image == null) {
			super.setIconImage(new EmptyImage());
		} else {
			super.setIconImage(image);
		}
	}

	public void setStage(Stage stage) {
		removeAll();

		this.stage = stage;
		add(this.stage);
	}

	public Stage getStage() {
		return stage;
	}

	public void setScene(Scene scene) {
		stage.setScene(scene);
	}

	public Clock getClk() {
		return clk;
	}

	public void setClk(Clock clk) {
		if (this.clk != null) {
			this.clk.removeClockListener(this);
		}

		this.clk = clk;
		this.clk.addClockListener(this);
	}

	@Override
	public void tick() {
		stage.repaint();
	}
}
