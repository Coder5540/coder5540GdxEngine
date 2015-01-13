package engine.module.list;
//package coder5560.engine.list;
//
//import utils.factory.Factory;
//import utils.factory.FontFactory.fontType;
//import utils.factory.Log;
//import utils.listener.OnSelectListener;
//import coder5560.engine.actor.GroupElement;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
//import com.badlogic.gdx.scenes.scene2d.utils.Align;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.Pool;
//import com.coder5560.game.assets.Assets;
//
//public class CopyOfListVertical extends GroupElement {
//
//	public Array<DataItemList>	datas			= new Array<DataItemList>();
//	public Array<ItemList>		items			= new Array<ItemList>();
//
//	float						scrollPosition	= 0;
//
//	private float				flingTime		= 0.1f;
//	private float				flingTimer		= 0;
//	private float				amountY			= 0;
//	private float				velocityY		= 0;
//
//	/* how much expand of the scrollView */
//	float						expandViewportY;
//
//	Table						root;
//	Pool<ItemList>				pool;
//
//	public CopyOfListVertical(float width, float height) {
//		super();
//		{
//			this.setSize(width, height);
//			scrollPosition = height;
//			root = new Table();
//			root.setSize(getWidth(), getHeight());
//			root.setTouchable(Touchable.enabled);
//			root.setClip(true);
//			expandViewportY = getHeight() / 2;
//		}
//
//		for (int i = 0; i < 500000; i++) {
//			datas.add(new DataItemList(i, "Element " + i, "Description : " + i,
//					width, 60 + i, onSelectListener));
//		}
//
//		createPool();
//
//		createList(datas, getHeight());
//
//		buildListener();
//
//		addActor(root);
//
//		validateElement();
//	}
//
//	/* Return start index and last index of the list */
//	public Vector2 createList(Array<DataItemList> listData, float height) {
//		Vector2 indexes = new Vector2();
//		float totalHeight = 0;
//		int index = 0;
//		while (totalHeight < (height + expandViewportY)
//				&& indexes.y <= listData.size) {
//			DataItemList data = listData.get(index++);
//			totalHeight += data.height;
//			ItemList item = pool.obtain();
//			item.recreateData(data);
//			items.add(item);
//			if (!item.hasParent() || item.getParent() != root) {
//				root.addActor(item);
//			}
//		}
//		return indexes;
//	}
//
//	void createPool() {
//		pool = new Pool<ItemList>(10, 20) {
//			@Override
//			protected ItemList newObject() {
//				return new ItemList(new DataItemList(-1, "", "", getWidth(),
//						60, onSelectListener));
//			}
//		};
//	}
//
//	@Override
//	public void act(float deltatime) {
//		update(deltatime);
//		super.act(deltatime);
//	}
//
//	public void update(float delta) {
//		float alpha = flingTimer / flingTime;
//		amountY = velocityY * alpha * delta;
//		flingTimer -= delta * 0.09f;
//		if (flingTimer <= 0) {
//			velocityY = 0;
//		} else {
//			scrollPosition += amountY;
//			validateElement(amountY);
//		}
//
//		while (items.first().getY() >= getHeight() + expandViewportY) {
//			ItemList item = items.first();
//			item.remove();
//			pool.free(item);
//			items.removeIndex(0);
//		}
//
//		while (items.peek().getY() <= -getHeight() + expandViewportY) {
//			ItemList item = items.peek();
//			item.remove();
//			pool.free(item);
//			items.removeValue(item, false);
//		}
//
//		while (items.first().getY() < getHeight() + expandViewportY) {
//			Log.d("" + items.first().dataItemList.index);
//			ItemList item = pool.obtain();
//			item.recreateData(datas.get(Factory.getPrevious(
//					items.first().dataItemList.index, 0, datas.size - 1)));
//			item.setY(items.first().getY(Align.top));
//			items.insert(0, item);
//			root.addActor(item);
//		}
//
//		while (items.peek().getY() > -getHeight() + expandViewportY) {
//			ItemList item = pool.obtain();
//			item.recreateData(datas.get(Factory.getNext(
//					items.peek().dataItemList.index, 0, datas.size - 1)));
//			item.setY(items.peek().getY(Align.bottom) - item.getHeight());
//			items.add(item);
//			root.addActor(item);
//		}
//	}
//
//	public void buildListener() {
//
//		root.addListener(new ActorGestureListener() {
//			@Override
//			public void touchDown(InputEvent event, float x, float y,
//					int pointer, int button) {
//				flingTimer = 0;
//				velocityY = 0;
//			}
//
//			@Override
//			public void pan(InputEvent event, float x, float y, float deltaX,
//					float deltaY) {
//				if (getTotalChildrenHeight() > getHeight()) {
//					scrollPosition += deltaY;
//					validateElement(deltaY);
//				}
//			}
//
//			@Override
//			public void fling(InputEvent event, float velocityX,
//					float velocityY, int button) {
//				CopyOfListVertical.this.velocityY = velocityY;
//				if (getTotalChildrenHeight() > getHeight()) {
//					flingTimer = flingTime;
//				}
//			}
//		});
//	}
//
//	public void validateElement() {
//		if (scrollPosition <= getHeight()) {
//			scrollPosition = getHeight();
//			flingTimer = 0;
//		} else if (getTotalChildrenHeight() - scrollPosition <= 0) {
//			scrollPosition = getTotalChildrenHeight();
//		}
//		float currentPosition = 0;
//		for (int index = 0; index < items.size; index++) {
//			ItemList itemView = items.get(index);
//			currentPosition += itemView.getHeight();
//			itemView.setY(scrollPosition - currentPosition);
//		}
//	}
//
//	// call when the scroll type is in type of pool
//	public void validateElement(float amount) {
//		for (int index = 0; index < items.size; index++) {
//			ItemList itemView = items.get(index);
//			itemView.setY(itemView.getY() + amount);
//		}
//	}
//
//	private float getTotalChildrenHeight() {
//		float totalHeight = 0;
//		for (int index = 0; index < items.size; index++) {
//			ItemList itemView = (ItemList) items.get(index);
//			totalHeight += itemView.getHeight();
//		}
//		return totalHeight;
//	}
//
//	OnSelectListener	onSelectListener	= new OnSelectListener() {
//
//												@Override
//												public void onSelect(int i) {
//
//												}
//											};
//
//}
//
//class ItemList extends AbstractItem{
//	private Image	bg;
//	private LabelStyle	styleTitle, styleDesciption;
//	private Label		lbTitle, lbShortDescription;
//	private Color		bgColor, wordColor, desColor;
//	DataItemList		dataItemList;
//
//	public ItemList(DataItemList dataItemList) {
//		this.dataItemList = dataItemList;
//		createColor();
//		{
//			styleTitle = new LabelStyle();
//			styleTitle.font = Assets.instance.fontFactory.getFont(22,
//					fontType.Medium);
//			styleTitle.fontColor = wordColor;
//
//			styleDesciption = new LabelStyle();
//			styleDesciption.font = Assets.instance.fontFactory.getFont(18,
//					fontType.Regular);
//			styleDesciption.fontColor = desColor;
//		}
//
//		{
//			lbTitle = new Label("", styleTitle);
//			lbTitle.setAlignment(Align.left);
//			lbTitle.setTouchable(Touchable.disabled);
//		}
//
//		lbShortDescription = new Label(Factory.getSubString("",
//				2 * getWidth() / 3 - 10, styleDesciption.font), styleDesciption);
//		lbShortDescription.setAlignment(Align.left);
//		lbShortDescription.setTouchable(Touchable.disabled);
//		lbShortDescription.setWrap(true);
//
//		bg = new Image(Assets.instance.ui.reg_ninepatch);
//		bg.setColor(bgColor);
//
//		bg.addListener(new ClickListener() {
//			Vector2	touch	= new Vector2();
//
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y,
//					int pointer, int button) {
//				touch.set(x, y);
//				return true;
//			}
//
//			@Override
//			public void touchUp(InputEvent event, float x, float y,
//					int pointer, int button) {
//				if (!touch.epsilonEquals(x, y, 20))
//					return;
//				final float px = x, py = y;
//				bg.addAction(Actions.sequence(Actions.color(new Color(
//						220 / 255f, 220 / 255f, 220 / 255f, 1f), 0.1f), Actions
//						.run(new Runnable() {
//
//							@Override
//							public void run() {
//								if (ItemList.this.dataItemList != null
//										&& ItemList.this.dataItemList.onSelectListener != null) {
//									ItemList.this.dataItemList.onSelectListener
//											.onSelect(ItemList.this.dataItemList.index);
//								}
//								bg.setColor(bgColor);
//							}
//						})));
//
//			}
//
//			@Override
//			public void touchDragged(InputEvent event, float x, float y,
//					int pointer) {
//				if (!touch.epsilonEquals(x, y, 10))
//					touch.set(0, 0);
//				super.touchDragged(event, x, y, pointer);
//			}
//		});
//		addActor(bg);
//		addActor(lbTitle);
//		addActor(lbShortDescription);
//		recreateData(this.dataItemList);
//	}
//
//	private void createColor() {
//		bgColor = new Color(10 / 255f, 10 / 255f, 10 / 255f, 0.95f);
//		wordColor = new Color(255 / 255f, 255 / 255f, 255 / 255f, 0.95f);
//		desColor = new Color(245 / 255f, 245 / 255f, 245 / 255f, 0.95f);
//	}
//
//	public void valid() {
//		setSize(dataItemList.width, dataItemList.height);
//		float w = this.getWidth();
//		float h = this.getHeight();
//		bg.setSize(w - 10, h - 2);
//		bg.setPosition(5, 1);
//		lbShortDescription.setWidth(2 * w / 3);
//		lbTitle.setPosition(10,
//				bg.getY(Align.top) - styleTitle.font.getCapHeight() - 5);
//		lbShortDescription.setPosition(40, lbTitle.getY()
//				- styleDesciption.font.getCapHeight() - 10);
//	}
//
//	public ItemList recreateData(Object object) {
//		if (object instanceof DataItemList) {
//			DataItemList data = (DataItemList) object;
//			this.dataItemList = data;
//			this.setSize(data.width, data.height);
//			lbTitle.setText(data.title);
//			lbShortDescription
//					.setText(Factory.getSubString(data.description,
//							2 * getWidth() / 3 - 10,
//							lbShortDescription.getStyle().font));
//			valid();
//		}
//
//		return this;
//	}
//
//	@Override
//	public void reset() {
//
//	}
//
//}
//
//class DataItemList {
//	public int				index				= -1;
//	public String			title				= "";
//	public String			description			= "";
//	public float			width;
//	public float			height;
//	public OnSelectListener	onSelectListener	= null;
//
//	public DataItemList(int index, String title, String description,
//			float width, float height, final OnSelectListener onSelectListener) {
//		super();
//		this.width = width;
//		this.height = height;
//		this.index = index;
//		this.title = title;
//		this.description = description;
//		this.onSelectListener = onSelectListener;
//	}
//}
//
