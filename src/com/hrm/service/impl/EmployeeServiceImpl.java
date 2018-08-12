package com.hrm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import com.hrm.dao.EmployeeDao;
import com.hrm.pojo.Employee;
import com.hrm.service.EmployeeService;
import com.hrm.util.PageModel;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	

	@Autowired
	private EmployeeDao employeeDao;
	
	/**
	 * HrmService接口findEmployee方法实现
	 * @see { HrmService }
	 * */
	@Transactional(readOnly=true)
	@Override
	public List<Employee> findEmployee(Employee employee,PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("employee", employee);
		
		int recordCount = employeeDao.count(params);
	    pageModel.setRecordCount(recordCount);
	    
	    if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
	    List<Employee> employees = employeeDao.selectByPage(params);
	    return employees;
	}
	/**
	 * HrmService接口removeEmployeeById方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void removeEmployeeById(Integer id) {
		employeeDao.deleteById(id);
		
	}
	/**
	 * HrmService接口findEmployeeById方法实现
	 * @see { HrmService }
	 * */
	@Transactional(readOnly=true)
	@Override
	public Employee findEmployeeById(Integer id) {
		
		return employeeDao.selectById(id);
	}
	
	/**
	 * HrmService接口addEmployee方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void addEmployee(Employee employee) {
		employeeDao.save(employee);
		
	}
	
	/**
	 * HrmService接口modifyEmployee方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void modifyEmployee(Employee employee) {
		employeeDao.update(employee);
	}
}
