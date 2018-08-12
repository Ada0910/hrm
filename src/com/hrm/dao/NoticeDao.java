package com.hrm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType;

import static  com.hrm.util.TableCommon.NOTICETABLE;
import com.hrm.dao.provider.NocticeDynaSqlProvider;
import com.hrm.pojo.Notice;

public interface NoticeDao {
	
	@SelectProvider(type = NocticeDynaSqlProvider.class,method = "selectWhitParam")
	@Results({
		@Result(id = true,column = "id" ,property = "id"),
		@Result(column="CREATE_DATE" ,property= "createDate",javaType=java.util.Date.class),
		@Result(column = "USER_ID" ,property = "user",one=@One(select = "com.hrm.dao.UserDao.selectById",fetchType = FetchType.EAGER))
	})
	List<Notice> selectByPage(Map<String,Object> params);
	
	@SelectProvider(type = NocticeDynaSqlProvider.class,method= "count")
	Integer count(Map<String, Object> params);
	
	@Select("select * from "+ NOTICETABLE+"  where id = #{id}")
	Notice selectById(int id);
	
	@Delete("delete from " + NOTICETABLE+" where id = #{id}")
    void deleteById(Integer id);
	
	@SelectProvider(type=NocticeDynaSqlProvider.class,method="updateNotice")
	void update(Notice notice);
	
	@SelectProvider(type=NocticeDynaSqlProvider.class,method="insertNotice")
	void save(Notice notice);
	
}
