package com.github.brunothg.jappybird.scene;

import com.github.brunothg.game.engine.d2.scene.Scene;

public interface PausableScene extends Scene {

	public void start();

	public void stop();

	public void pause();
}
