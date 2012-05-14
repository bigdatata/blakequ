hibernate的lazy策略可以使用在：
	* <class>标签上，可以取值：true/false
	* <property>标签上，可以取值：true/false,需要类增强工具，对字节码进行修改
	* <set>/<list>标签上,可以取值：true/false/extra
	* <many-to-one>/<one-to-one>单端关联标签上，可以取值：false/proxy/noproxy
	
lazy的概念：在正真使用某个对象的时候才正真的去创建，对于hibernate才会正真的发出sql语句
            去加载该对象
            
hibernate的lazy策略必须在session打开状态下有效

<class>上的lazy只影响普通属性            