package com.hrm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hrm.pojo.Notice;
import com.hrm.pojo.User;
import com.hrm.service.NoticeService;
import com.hrm.util.PageModel;
import com.hrm.util.TableCommon;

@Controller
public class NoticeController {
	
	
		/**
		 * 自动注入UserService
		 * */
		@Autowired
		@Qualifier("noticeService")
		private NoticeService noticeService;
		
		/**
		 * 处理/login请求
		 * */
		@RequestMapping(value="/notice/selectNotice")
		 public String selectNotice(Model model,Integer pageIndex,
				 @ModelAttribute Notice notice){
			PageModel pageModel = new PageModel();
			if(pageIndex != null){
				pageModel.setPageIndex(pageIndex);
			}
			/** 查询用户信息     */
			List<Notice> notices = noticeService.findNotice(notice, pageModel);
			model.addAttribute("notices", notices);
			model.addAttribute("pageModel", pageModel);
			return "notice/notice";
			
		}
		
		/**
		 * 处理添加请求
		 * @param Integer id  要显示的公告id
		 * @param Model model
		 * */
		@RequestMapping(value="/notice/previewNotice")
		 public String previewNotice(
				 Integer id,Model model){
			
			Notice notice = noticeService.findNoticeById(id);
			
			model.addAttribute("notice", notice);
			// 返回
			return "notice/previewNotice";
		}
		
		/**
		 * 处理删除公告请求
		 * @param String ids 需要删除的id字符串
		 * @param ModelAndView mv
		 * */
		@RequestMapping(value="/notice/removeNotice")
		 public ModelAndView removeNotice(String ids,ModelAndView mv){
			// 分解id字符串
			String[] idArray = ids.split(",");
			for(String id : idArray){
				// 根据id删除公告
				noticeService.removeNoticeById(Integer.parseInt(id));
			}
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/notice/selectNotice");
			// 返回ModelAndView
			return mv;
		}
		
		/**
		 * 处理添加请求
		 * @param String flag 标记， 1表示跳转到添加页面，2表示执行添加操作
		 * @param Notice notice  要添加的公告对象
		 * @param ModelAndView mv
		 * */
		@RequestMapping(value="/notice/addNotice")
		 public ModelAndView addNotice(
				 String flag,
				 @ModelAttribute Notice notice,
				 ModelAndView mv,
				 HttpSession session){
			if(flag.equals("1")){
				mv.setViewName("notice/showAddNotice");
			}else{
				User user = (User) session.getAttribute(TableCommon.USER_SESSION);
				notice.setUser(user);
				noticeService.addNotice(notice);
				mv.setViewName("redirect:/notice/selectNotice");
			}
			// 返回
			return mv;
		}
		
		/**
		 * 处理添加请求
		 * @param String flag 标记， 1表示跳转到修改页面，2表示执行修改操作
		 * @param Notice notice  要添加的公告对象
		 * @param ModelAndView mv
		 * */
		@RequestMapping(value="/notice/updateNotice")
		 public ModelAndView updateNotice(
				 String flag,
				 @ModelAttribute Notice notice,
				 ModelAndView mv,
				 HttpSession session){
			if(flag.equals("1")){
				Notice target = noticeService.findNoticeById(notice.getId());
				mv.addObject("notice",target);
				mv.setViewName("notice/showUpdateNotice");
			}else{
				noticeService.modifyNotice(notice);
				mv.setViewName("redirect:/notice/selectNotice");
			}
			// 返回
			return mv;
		}
		
		
}
