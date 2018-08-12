package com.hrm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import com.hrm.dao.DocumentDao;
import com.hrm.pojo.Document;
import com.hrm.service.DocumentService;
import com.hrm.util.PageModel;


@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("documentService")
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentDao documentDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Document> findDocument(Document document, PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("document", document);
		int recordCount = documentDao.count(params);
		pageModel.setRecordCount(recordCount);
		
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		
		List<Document> documents = documentDao.selectByPage(params);
		 
		return documents;
	}
	
	@Override
	public void addDocument(Document document) {
		documentDao.save(document);
		
	}
	
	@Override
	public void removeDocumentById(Integer id) {
		documentDao.deleteById(id);
		
	}
	
	@Override
	public void modifyDocument(Document document) {
		documentDao.update(document);
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public Document findDocumentById(Integer id) {
		
		return documentDao.selectById(id);
	}


}
