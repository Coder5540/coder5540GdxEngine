package dictionary.elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.factory.Factory;
import utils.factory.FontFactory.fontType;
import utils.factory.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.coder5560.game.assets.Assets;
import com.sun.javafx.collections.SetAdapterChange;

public class WordMean extends Table {

	String				strDirectory	= "/home/dinhanh/Study/English Dictionary/English Sound/";
	ArrayList<String>	lines			= new ArrayList<String>();
	Table				tbScroll;
	ScrollPane			scroll;
	AudioManager		audioManager;

	void readFile() {

		FileHandle file = Gdx.files.internal("data/unit7savingenegy.txt");
		try {
			BufferedReader bufferedReader = new BufferedReader(file.reader());
			String line = bufferedReader.readLine();
			while (line != null) {
				lines.add(line);
				line = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		audioManager = new AudioManager(lines);
	}

	public void load() {
		lines.clear();
		readFile();

		tbScroll = new Table();
		tbScroll.setSize(getWidth(), getHeight());
		tbScroll.defaults().expandX().fillX().height(60).left();
		scroll = new ScrollPane(tbScroll);
		scroll.setScrollingDisabled(true, false);
		scroll.setSmoothScrolling(true);
		scroll.setForceScroll(false, true);
		scroll.setClamp(true);
		scroll.setCancelTouchFocus(false);

//		for (int i = 0; i < lines.size(); i++) {
//			Word itemMail = new Word(i, lines.get(i), "", tbScroll.getWidth(),
//					60, onTextSelect);
//			tbScroll.add(itemMail).row();
//			addLine(tbScroll, 2, 0, 0, 1, 1);
//		}
		for (int i = 0; i < lines.size() - 1; i++) {
			if (i % 2 == 0) {
				Word itemMail = new Word(i, lines.get(i), lines.get(i + 1),
						tbScroll.getWidth(), 60, onTextSelect);
				tbScroll.add(itemMail).row();
				addLine(tbScroll, 2, 0, 0, 1, 1);
			}
		}
		this.add(scroll).expand().fill().top();
	}

	void addLine(Table table, float height, float padLeft, float padRight,
			float padTop, float padBottom) {

		Image line = new Image(Assets.instance.ui.reg_ninepatch);
		line.setColor(new Color(0 / 255f, 255 / 255f, 0 / 255f, 1f));
		line.setHeight(height);
		line.setWidth(table.getWidth());
		table.add(line).expandX().fillX().height(height).padTop(padTop)
				.padLeft(padLeft).padBottom(padBottom).padRight(padRight)
				.colspan(4);
		table.row();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		update(delta);
	}

	int		index		= 0;
	int		countTime	= 0;
	int		maxTime		= 100;
	boolean	isDownload	= false;

	public void update(float delta) {
		if (index > lines.size())
			Gdx.app.exit();
		if (lines == null || lines.size() >= 0)
			return;
		countTime++;
		if (countTime >= maxTime) {
			if (index < lines.size()) {
				try {
					String filePath = strDirectory + lines.get(index) + ".mp3";
					File f = new File(filePath);
					if (!f.exists()) {
						Log.d(index + ". Saving File : " + lines.get(index)
								+ ".mp3");
						Factory.save(lines.get(index));
					} else {
						Log.d("File Already Exist");
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				index += 1;
			} else {
				Gdx.app.exit();
			}
			countTime = 0;
		}
	}

	class Word extends Group {
		Image	bg;
		Image	icon;
		LabelStyle	styleTitle, styleDesciption;
		Label		lbTitle, lbShortDescription;
		Color		bgColor, wordColor, desColor;
		public int	index;

		public Word(final int index, final String title,
				final String description, final float width, float height,
				final OnTextSelect onTextSelect) {
			this.setSize(width, height);
			setTransform(true);
			setOrigin(Align.center, Align.center);
			this.index = index;
			createColor();
			styleTitle = new LabelStyle();
			styleTitle.font = Assets.instance.fontFactory.getFont(22,
					fontType.Medium);
			styleTitle.fontColor = wordColor;

			styleDesciption = new LabelStyle();
			styleDesciption.font = Assets.instance.fontFactory.getFont(18,
					fontType.Regular);
			styleDesciption.fontColor = desColor;

			lbTitle = new Label(title, styleTitle);
			lbTitle.setAlignment(Align.center, Align.left);
			lbTitle.setTouchable(Touchable.disabled);

			lbShortDescription = new Label(Factory.getSubString(description,
					2 * width / 3 - 10, styleDesciption.font), styleDesciption);
			lbShortDescription.setAlignment(Align.left);
			lbShortDescription.setTouchable(Touchable.disabled);
			lbShortDescription.setWrap(true);

			bg = new Image(Assets.instance.ui.reg_ninepatch);
			bg.setColor(bgColor);

			bg.addListener(new ClickListener() {
				boolean	isTouch	= false;
				Vector2	touch	= new Vector2();

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touch.set(x, y);
					isTouch = true;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!isTouch || touch.isZero())
						return;
					addAction(Actions.sequence(Actions
							.scaleTo(1.2f, 1.2f, .05f), Actions.scaleTo(1f, 1f,
							.1f, Interpolation.swingOut), Actions
							.run(new Runnable() {
								@Override
								public void run() {
									if (onTextSelect != null) {
										onTextSelect.onSelect(lbTitle.getText()
												.toString());
									}
								}
							})));

				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touch.epsilonEquals(x, y, 10))
						touch.set(0, 0);
					super.touchDragged(event, x, y, pointer);
				}
			});

			addActor(bg);
			addActor(lbTitle);
			addActor(lbShortDescription);
			valid();
		}

		private void createColor() {
			bgColor = new Color(10 / 255f, 10 / 255f, 10 / 255f, 0.95f);
			wordColor = new Color(255 / 255f, 255 / 255f, 255 / 255f, 0.95f);
			desColor = new Color(245 / 255f, 245 / 255f, 245 / 255f, 0.95f);
		}

		public void valid() {
			float w = this.getWidth();
			float h = this.getHeight();
			bg.setSize(w - 40, h - 2);
			bg.setPosition(35, 1);
			lbShortDescription.setWidth(2 * getWidth() / 3);
			lbTitle.setPosition(40, h - lbTitle.getHeight());
			lbShortDescription.setPosition(60,
					lbTitle.getHeight() - 20);
		}
	}

	public interface OnTextSelect {
		public void onSelect(String text);
	}

	OnTextSelect	onTextSelect	= new OnTextSelect() {

										@Override
										public void onSelect(String text) {
											if (audioManager != null)
												audioManager.play(text);
										}
									};

	class AudioManager {
		HashMap<String, Sound>	sounds		= new HashMap<String, Sound>();
		public boolean			init		= false;
		// public String assetFolder = "enviroment/";
		public String			assetFolder	= "savingenergy/";

		public AudioManager(ArrayList<String> words) {
			super();
			init(words);
		}

		private void init(ArrayList<String> words) {
			init = true;
			try {
				for (int i = 0; i < words.size(); i++) {
					String filePath = assetFolder + words.get(i).toLowerCase()
							+ ".mp3";
					FileHandle fileHandle = Gdx.files.internal(filePath);
					if (fileHandle.exists()) {
						Sound sound = Gdx.audio.newSound(fileHandle);
						if (sound != null) {
							sounds.put(words.get(i), sound);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void play(String word) {
			if (sounds.containsKey(word))
				sounds.get(word).play(1f);
		}

	}
}
