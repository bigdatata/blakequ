package cm.commons.dao.hiber.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public enum OP{

	LIKE("like"), EQ("="), UNEQ("<>"), GREAT(">"), LESS("<"), GREAT_EQ(">="), LESS_EQ(
			"<="), BETWEEN("between"), IN("in"){

		@Override
		protected String afterParam() {
			return ")";
		}

		@Override
		protected String beforParam() {
			return "(";
		}

	},NOTIN("not in"){

		@Override
		protected String afterParam() {
			return ")";
		}

		@Override
		protected String beforParam() {
			return "(";
		}
	}
	;

	protected String v = null;

	private OP(String v) {
		this.v = v;
	}

	//id=1由该语句生成 EQ.toHql("id",1);=
	public String toHql(String name, Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(" ");
		sb.append(this.v);
		sb.append(" ");
		sb.append(this.beforParam());
		sb.append(this.handleParam(value));
		sb.append(this.afterParam());
		return sb.toString();
	}
	
	public String toHql(String name, Object value,Object value2) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(" ");
		sb.append(this.v);
		sb.append(" ");
		sb.append(this.beforParam());
		sb.append(this.handleParam(value));
		sb.append(" and ");
		sb.append(this.handleParam(value2));
		sb.append(this.afterParam());
		return sb.toString();
	}

	private String handleParam(Object o) {
		if (o == null) {
			return "null";
		}
		if (o instanceof Date) {
			return String.format("'%s'", new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format((Date) o));
		} else if (o instanceof Object[]) {
			StringBuffer sb = new StringBuffer();
			Object[] array = (Object[]) o;
			for (Object obj : array) {
				sb.append(this.handleParam(obj));
				sb.append(",");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
				return sb.toString();
			}
		} else if (o instanceof String || o instanceof Character) {
			return String.format("'%s'", String.valueOf(o));
		}
		System.out.println("String.valueOf(o):"+String.valueOf(o));
		return String.valueOf(o);
	}

	protected String beforParam() {
		return "";
	}

	protected String afterParam() {
		return "";
	}

	public static void main(String[] args) {
		Long vaLong=new Long(8);
		System.out.println(String.valueOf(vaLong));
	}
}
