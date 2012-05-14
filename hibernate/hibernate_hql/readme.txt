hibernate查询语言hql

在hql中关键字不区分大小写，通常小写，类的名称和属性名称必须区分大小写


1、简单属性查询【重要】
	* 单一属性查询，返会属性结果集列表，元素类型和实体类中相应的类型一致
	* 多个属性查询，多个属性查询返会对象数组，对象数组的长度取决于属性的个数
	  对象数组中元素的类型取决于属性在实体类中的类型
	* 如果认为返会数组不够对象化，可以使用hql动态实例化Student对象
	参见：SimplePropertyQueryTest.java
	   
2、实体对象查询【重要】
    * N + 1问题，就是发出了N+1条sql语句
    1：首先发出查询对象id列表的语句
    N：根据id到缓存中查询，如果缓存中不存在与之匹配的数据，那么会根据id发出相应的sql语句
    
    *list和iterate的区别？
     list： 默认情况下list每次都会发出sql语句，list会将数据放到缓存中，而不利用缓存
     iterate：默认情况下iterate利用缓存，如果缓存中不存在会出现N+1问题
	参见：SimpleObjectQueryTest1.java,SimpleObjectQueryTest2.java     
         
3、条件查询【重要】
	* 可以采用拼字符串的方式传递参数
	* 可以采用 ？来传递参数（索引从0开始）
	* 可以采用 :参数名 来传递参数
	* 如果传递多个参数，可以采用setParamterList方法
	* 在hql中可以使用数据库的函数，如：date_format
	参见：SimpleConditionQueryTest.java
	
4、hibernate直接使用sql语句查询
	参见：SqlQueryTest.java
	
5、外置命名查询
    * 在映射文件中使用<query>标签来定义hql
    * 在程序中使用session.getNamedQuery()方法得到hql查询串
    参见：NameQueryTest.java,Student.hbm.xml
    
6、查询过滤器
	* 在映射文件中定义过滤器参数
	* 在类的映射中使用过滤器参数
	* 在程序中必须显示的启用过滤器，并且为过滤器参数赋值
	参见：FilterQueryTest.java,Student.hbm.xml
	
7、分页查询【重要】
	* setFirstResult(),从0开始
	* setMaxResults(),每页显示的记录数
	参见：PageQueryTest.java
	
8、对象导航查询【重要】
	参见：ObjectNavQueryTest.java
	
9、连接查询【重要】
	* 内连接
	* 外连接（左连接/右连接）
	参见：JoinQueryTest.java
	
10、统计查询【重要】
	参见：StatQueryTest.java
	
11、DML风格的操作（尽量少用，因为和缓存不同步）	
	参见：DMLQueryTest.java