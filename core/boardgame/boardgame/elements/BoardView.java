package boardgame.elements;

import java.util.ArrayList;

import utils.factory.Log;
import coder5560.engine.actor.GroupElement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.coder5560.game.assets.Assets;

public class BoardView extends GroupElement {

	int[][]				elements;
	ArrayList<NodeWord>	nodes;

	public BoardView(Vector2 dimesion, int col, int row, float elementSize) {
		super();
		this.setSize(dimesion.x, dimesion.y);
		elements = new int[col][row];
		nodes = new ArrayList<NodeWord>();
		for (int i = 0; i < col; i++) {
			for (int k = 0; k < row; k++) {
				NodeWord node = new NodeWord(i, k);
				node.buidSize(elementSize, elementSize)
						.buildBackgroud(Assets.instance.ui.reg_ninepatch4)
						.buildText("A").validateElements().getBg().setColor(Color.GRAY);;
				node.setPosition(i * elementSize, k * elementSize);
				nodes.add(node);
			}
		}
	}

	public void validatePosition() {

	}

	@Override
	public void onDraw(Batch batch) {
		for (NodeWord nodeWord : nodes) {
			nodeWord.act(Gdx.graphics.getDeltaTime());
			nodeWord.draw(batch, 1f);
		}
	}
}
