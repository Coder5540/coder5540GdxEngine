package boardgame.elements;

import coder5560.engine.actor.GroupElement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class NumberManager extends GroupElement {

	Color[]		colors			= {
			new Color(217 / 255f, 68 / 255f, 48 / 255f, 1f),
			new Color(0 / 255f, 153 / 255f, 86 / 255f, 1f),
			new Color(28 / 255f, 113 / 255f, 239 / 255f, 1f),
			new Color(255 / 255f, 222 / 255f, 66 / 255f, 1f) };

	String[]	alphabetTable	= { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "Y", "Z"	};

	String		goal			= "SQUARE";

	enum State {
		STATIC, ANIMATION;
	}

	enum Slide {
		UP, RIGHT, DOWN, LEFT;
	}

	NumberElement[][]	board;

	boolean				win		= false;

	float				box_size;

	int					board_size;

	State				_State	= State.STATIC;

	protected boolean	show	= true;

	Pool<NumberElement>	pool	= new Pool<NumberElement>() {

									@Override
									protected NumberElement newObject() {
										return new NumberElement();
									}
								};

	public NumberManager setGoal(String goal) {
		this.goal = goal;
		return this;
	}

	public NumberManager setBoardSize(int board_size) {
		this.board_size = board_size;
		return this;
	}

	public NumberManager setBoxSize(float box_size) {
		this.box_size = box_size;
		return this;
	}

	public NumberManager build(NumberManager in) {
		this.board_size = in.board_size;
		this.goal = in.goal;
		this.box_size = in.box_size;
		this.win = in.win;
		this.show = in.show;

		board = new NumberElement[board_size][board_size];

		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				NumberElement in_element = in.board[i][j];

				NumberElement element = pool.obtain();
				element.setColor(in_element.base_color, in_element.number_color)
						.setContent(in_element.content)
						.setNumber(in_element.number)
						.setPadding(in_element.padding).buildText();
				element.setSize(box_size, box_size);
				element.setPosition(j * box_size, i * box_size);
				element.setOrigin(element.getWidth() / 2,
						element.getHeight() / 2);
				addActor(element);

				board[i][j] = element;
			}
		}

		setSize(box_size * board_size, box_size * board_size);

		hint(-1);

		return this;
	}

	public NumberManager build() {

		Array<Integer> arr = new Array<Integer>();
		for (int i = 1, size = board_size * board_size; i <= size; i++)
			arr.add(i);

		Array<String> alphabet = new Array<String>();
		alphabet.addAll(alphabetTable);

		Array<String> goalarr = new Array<String>();
		char[] g = goal.toCharArray();
		for (char c : g)
			goalarr.add(c + "");

		int n = board_size * board_size - g.length;
		while (n > 0) {
			alphabet.shuffle();
			goalarr.add(alphabet.peek());
			n--;
		}

		board = new NumberElement[board_size][board_size];

		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				arr.shuffle();
				goalarr.shuffle();
				NumberElement element = pool.obtain();
				element.setColor(Color.DARK_GRAY, Color.WHITE)
						.setContent(goalarr.pop()).setNumber(arr.pop())
						.setPadding(1.5f).buildText();
				element.setSize(box_size, box_size);
				element.setPosition(j * box_size, i * box_size);
				element.setOrigin(element.getWidth() / 2,
						element.getHeight() / 2);
				addActor(element);

				board[i][j] = element;
			}
		}

		setSize(box_size * board_size, box_size * board_size);

		hint(-1);

		return this;
	}

	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			batch.flush();
			boolean flag = clipBegin(getX(), getY(), getWidth(), getHeight());
			if (flag) {
				super.draw(batch, parentAlpha);
				batch.flush();
				clipEnd();
			}
		}
	}

	public abstract boolean slide(float x, float y, Slide type);

	public void show(boolean show) {
		show(show, -1);
	}

	public void show(boolean show, int count) {
		this.show = show;
		if (show) {
			hint(count);
		} else {
			stophint();
		}
	}

	public void stophint() {
		Actor[] childrens = getChildren().begin();
		for (int i = 0, size = getChildren().size; i < size; i++) {
			NumberElement child = (NumberElement) childrens[i];
			if (!child.immutable)
				child.setColor(Color.DARK_GRAY, Color.WHITE);
		}
		getChildren().end();
	}

	public boolean hint(int count) {
		if (_State == State.ANIMATION)
			return false;
		stophint();
		char[] goal_chars = goal.toCharArray();
		Array<Node> list = new Array<Node>();
		Array<Node> queue = new Array<Node>();
		Array<Node> temp = new Array<Node>();
		Array<NumberElement> showlist = new Array<NumberElement>();

		Actor[] childrens = getChildren().begin();
		for (int i = 0, size = getChildren().size; i < size; i++) {
			NumberElement child = (NumberElement) childrens[i];
			if (child.content.equalsIgnoreCase(goal_chars[0] + "")) {

				float x = child.getX() + child.getWidth() / 2, y = child.getY()
						+ child.getHeight() / 2;
				int row = (int) Math.floor(y / box_size);
				int col = (int) Math.floor(x / box_size);

				Node node = node_pool.obtain();
				node.row = row;
				node.col = col;
				node.depth = 0;
				queue.add(node);
				list.add(node);
			}
		}

		int index = 1;

		while (queue.size > 0) {
			for (int i = 0; i < queue.size; i++) {
				Node node = queue.get(i);

				int row = node.row;
				int col = node.col;

				int[] r = { row - 1, row, row + 1, row };
				int[] c = { col, col + 1, col, col - 1 };

				for (int j = 0; j < r.length; j++) {
					int pr = r[j];
					int pc = c[j];
					NumberElement next = getElementByRowCol(pr, pc);
					if (next != null) {
						if (next.content.equalsIgnoreCase(goal_chars[index]
								+ "")) {
							Node pend = node_pool.obtain();
							pend.row = pr;
							pend.col = pc;
							pend.depth = node.depth + 1;

							if (node.sameParent(pend)) {
								node_pool.free(pend);
							} else {
								temp.add(pend);
								node.addNode(pend);
							}
						}
					}
				}
			}
			queue.clear();
			queue.addAll(temp);
			list.addAll(temp);
			temp.clear();
			index++;
			if (index >= goal_chars.length)
				break;
		}

		Node deep = list.get(0);

		for (int i = 1; i < list.size; i++) {
			Node node = list.get(i);
			if (node.depth > deep.depth)
				deep = node;
		}

		boolean win = deep.depth == (goal_chars.length - 1);

		if (show) {
			int max_deep = deep.depth + 1;
			int show_num = count == -1 ? (max_deep) : count;
			show_num = show_num > max_deep ? max_deep : show_num;

			while (deep != null) {
				NumberElement element = getElementByRowCol(deep.row, deep.col);
				if (element != null) {
					showlist.insert(0, element);
				}
				deep = deep.parent;
			}
			for (int i = 0; i < show_num; i++) {
				showlist.get(i).setColor(Color.ORANGE, Color.WHITE);
			}
		}

		for (int i = 1; i < list.size; i++) {
			Node node = list.get(i);
			node_pool.free(node);
		}

		list.clear();
		temp.clear();
		queue.clear();
		showlist.clear();
		return win;
	}

	NumberElement getElement(float x, float y) {
		int row = (int) Math.floor(y / box_size);
		int col = (int) Math.floor(x / box_size);
		if (row >= 0 && col >= 0 && row < board_size && col < board_size) {
			return board[row][col];
		}
		return null;
	}

	NumberElement getElementByRowCol(int row, int col) {
		if (row >= 0 && col >= 0 && row < board_size && col < board_size) {
			return board[row][col];
		}
		return null;
	}

	public NumberManager reset() {
		Actor[] childrens = getChildren().begin();
		for (int i = 0, size = getChildren().size; i < size; i++) {
			NumberElement child = (NumberElement) childrens[i];
			pool.free(child);
		}
		getChildren().end();
		clearChildren();
		board = null;
		_State = State.STATIC;
		win = false;
		return this;
	}

	Pool<Node>	node_pool	= new Pool<Node>() {

								@Override
								protected Node newObject() {
									return new Node();
								}
							};

	class Node implements Poolable {

		Node		parent;
		Array<Node>	child	= new Array<Node>();

		int			row		= -1, col = -1;

		int			depth	= -1;

		public void remove(Node node) {
			child.removeValue(node, true);
		}

		public void addNode(Node node) {
			if (node.parent != null) {
				node.parent.remove(node);
			}
			if (!child.contains(node, true)) {
				child.add(node);
			}
			node.parent = this;
		}

		public boolean sameParent(Node node) {
			Node t = parent;
			while (t != null) {
				if (t.row == node.row && t.col == node.col)
					return true;
				t = t.parent;
			}
			return false;
		}

		public Node get(int index) {
			return child.get(index);
		}

		@Override
		public void reset() {
			if (parent != null)
				parent.remove(this);
			parent = null;
			depth = -1;
			row = col = -1;
			child.clear();
		}

	}

}
