package cm.commons.dao.hiber.util;
/**
 * 封装查询条件，其中Link为连接词，可以为where，and，order，or
 * OP为操作符，为=，>,<等
 * @author yangyi
 *
 * 2011-6-1
 * 
 * Element
 */
public class Element {
	Link link;
	OP op;
	String name;
	Object object;
	boolean asc;
	
	public Element(){}
	
	/**
	 * 常用操作构造element
	 * @param link
	 * @param op
	 * @param name
	 * @param object
	 */
	public Element(Link link,OP op,String name,Object object){
		this.link=link;
		this.op=op;
		this.name=name;
		this.object=object;
	}
	
	/**
	 *  专门针对order的构造方式
	 * @param link
	 * @param name
	 * @param asc
	 */
	public Element(Link link,String name,boolean asc){
		this.link=link;
		this.name=name;
		this.asc=asc;
	}
	
	public Element(Link link,OP op,String name,Object object,boolean asc){
		this.link=link;
		this.op=op;
		this.name=name;
		this.object=object;
		this.asc=asc;
	}
	/**
	 * @return the link
	 */
	public Link getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(Link link) {
		this.link = link;
	}
	/**
	 * @return the op
	 */
	public OP getOp() {
		return op;
	}
	/**
	 * @param op the op to set
	 */
	public void setOp(OP op) {
		this.op = op;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * @return the asc
	 */
	public boolean isAsc() {
		return asc;
	}
	/**
	 * @param asc the asc to set
	 */
	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
