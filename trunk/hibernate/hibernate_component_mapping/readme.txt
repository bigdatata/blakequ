Component映射

在hibernate中Component映射采用<component>标签即可

Component是某个实体的逻辑组成部分，它与实体类的主要差别在于，它没有oid
Component在DDD中成为值类

采用Component的好处：实现对象模型的细粒度划分，复用率高，含义明确，层次分明

对象模型与关系模型的设计恰恰相反，对象模型一般是细粒度的，关系模型一般是粗粒度的

		
