package engine.module.list;

import coder5560.engine.actor.GroupElement;

import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class AbstractItem extends GroupElement implements Poolable {

	public abstract AbstractItem recreateData(Object object);

	public abstract int getIndex();
}
