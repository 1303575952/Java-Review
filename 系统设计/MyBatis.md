# MyBatis

## #{}和${}的区别是什么？

#{}是预编译处理，${}是字符串替换。Mybatis在处理#{}时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；Mybatis在处理时，就是把替换成变量的值。使用#{}可以有效的防止SQL注入，提高系统安全性。

```sql
--Mybatis在处理#{}时
select id,name,age from student where id =#{id}
当前端把id值1传入到后台的时候，就相当于:
select id,name,age from student where id ='1'

--Mybatis在处理${}时
select id,name,age from student where id =${id}
当前端把id值1传入到后台的时候，就相当于：
select id,name,age from student where id = 1
```

PS:使用#{}可以有效的防止SQL注入，提高系统安全性(语句的拼接)，如果使用在order by 中就需要使用 ${}。

最大区别在于：#{} 传入值时，sql解析参数是带引号的，而${}传入值时，sql解析参数是不带引号的。

## Mybatis 中一级缓存与二级缓存的区别

缓存：合理使用缓存是优化中最常见的方法之一，将从数据库中查询出来的数据放入缓存中，下次使用时不必从数据库查询，而是直接从缓存中读取，避免频繁操作数据库，减轻数据库的压力，同时提高系统性能。

* 一级缓存是SqlSession级别的缓存：
  Mybatis对缓存提供支持，但是在没有配置的默认情况下，它只开启一级缓存。一级缓存在操作数据库时需要构造sqlSession对象，在对象中有一个数据结构用于存储缓存数据。不同的sqlSession之间的缓存数据区域是互相不影响的。也就是他只能作用在同一个sqlSession中，不同的sqlSession中的缓存是互相不能读取的。

- 二级缓存是mapper级别的缓存：
  MyBatis的二级缓存是mapper级别的缓存，它可以提高对数据库查询的效率，以提高应用的性能。多个SqlSession去操作同一个Mapper的sql语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。