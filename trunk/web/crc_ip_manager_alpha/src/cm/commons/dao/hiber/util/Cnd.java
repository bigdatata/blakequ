package cm.commons.dao.hiber.util;

/**
 * 起组合拼接作用的类
 * @author yangyi
 *
 * 2011-11-15
 * 
 * Cnd
 */
public class Cnd {

	private StringBuffer sb;
	private boolean hasWhere = false;
	private boolean hasOrder = false;

	/**
	 * @return the sb
	 */
	public StringBuffer getSb() {
		return sb;
	}

	public Cnd() {
		this.sb = new StringBuffer();
	}

	public Cnd where(String name, OP op, Object value) {
		if (this.hasOrder) {
			throw new IllegalStateException("已经order!");
		}
		sb.append(" where ");
		sb.append(op.toHql(name, value));
		this.hasWhere = true;
		return this;
	}

	public Cnd and(String name, OP op, Object value) {
//		 if (this.hasOrder) {
//		 throw new IllegalStateException("已经order!");
//		 }
//		 if (!this.hasWhere) {
//		 throw new IllegalStateException("没有where!");
//		 }
		sb.append(" and ");
		sb.append(op.toHql(name, value));
		return this;
	}

	public Cnd or(String name, OP op, Object value) {
		// if (this.hasOrder) {
		// throw new IllegalStateException("已经order!");
		// }
		// if (!this.hasWhere) {
		// throw new IllegalStateException("没有where!");
		// }
		sb.append(" or ");
		sb.append(op.toHql(name, value));
		return this;
	}

	public Cnd between(String name, OP op, Object value, Object value2) {
		sb.append(op.toHql(name, value, value2));
		return this;
	}

	public Cnd in(String name, OP op, Object value) {
		sb.append(op.toHql(name, value));
		return this;
	}

	public Cnd orderBy(String name, boolean asc) {
		if (!this.hasOrder) {
			sb.append(" order by ");
		} else {
			sb.append(",");
		}
		sb.append(name);
		if (asc) {
			sb.append(" asc");
		} else {
			sb.append(" desc");
		}
		this.hasOrder = true;
		return this;
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}

}
