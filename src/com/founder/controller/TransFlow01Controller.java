package com.founder.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.founder.beans.FeedBack;
import com.founder.service.TransFlow01Service;

@Controller
public class TransFlow01Controller {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="transflowService")
	private TransFlow01Service tranService;
	
	/**
	 * 将交易信息生成XLS文档
	 */
	@ResponseBody
	@RequestMapping(value = "/transReport")
	public Object transReport(@RequestBody String params){
		log.info("[URL=/transReport,params="+params+"]");
		FeedBack back = new FeedBack();
		back.setSuc(false);
		
		if(params == null || "".equals(params)){
			back.setMsg("请求参数为空，请按要求正确操作！");
			return back;
		}
		
		JSONObject jbj = null;
		try{
			jbj = JSONObject.fromObject(params);
		}catch (Exception e) {
			back.setMsg("请求参数不是JSON格式，请按要求正确操作！");
			return back;
		}

		int type = jbj.optInt("type",0);
		String account = jbj.optString("account");
		String start = jbj.optString("start");
		String end = jbj.optString("end");
		
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("type", type);
		maps.put("khzh", account==null?"":account.trim());
		//去除所有非数字的字符
		maps.put("start", start.replaceAll("[^\\d]",""));
		maps.put("end", end.replaceAll("[^\\d]",""));
		
		String filepath = null;
		try{
			if(type==0){		//个人
				filepath = tranService.reportgrTrans01(maps);
			} else if(type==1){	//对公
				filepath = tranService.reportdgTrans01(maps);
			}
			
			if(filepath == null){
				back.setMsg("没有查到数据!");
				return back;
			}
			
			back.setSuc(true);
			back.setMsg("文件已生成，请下载！");
			back.setData(filepath);
		}catch(RowsExceededException e){
			back.setMsg(e.getMessage());
			log.error(e.getMessage());
		} catch (WriteException e) {
			back.setMsg(e.getMessage());
			log.error(e.getMessage());
		} catch (IOException e) {
			back.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		
		return back;
	}
	
	/**
	 * 文件下载
	 * @param filepath 服务器上的待下载文件路径
	 */
	@RequestMapping(value = "/filedown")
	public ResponseEntity<byte[]> fileDownload(String filepath) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", filepath.substring(filepath.lastIndexOf(File.separator)+1));

		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filepath)),
				headers, HttpStatus.OK);
	}
	
	/**
	 * 用户文件上传
	 * @param multfile 上传的文件信息
	 * @param request 获取其他参数
	 */
	@ResponseBody
	@RequestMapping(value = "/fileupload",method=RequestMethod.POST )
	public Object fileupload(@RequestParam MultipartFile multfile,HttpServletRequest request){
		log.info("[URL=/fileupload,filepath="+multfile.getOriginalFilename()+"]");
		FeedBack back = new FeedBack();
		back.setSuc(false);
		
		int type = Integer.parseInt(request.getParameter("type"));
		int start = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));

		log.info("[type="+type+",start="+start+",end="+end+"]");
		
		File upfile = null;
		if(multfile.isEmpty()){
			back.setMsg("上传文件路径为空，请按要求正确操作！");
			return back;
		} else {
			try {
				upfile = tranService.makeFilePath();
				// 存储文件
				FileUtils.copyInputStreamToFile(multfile.getInputStream(),upfile);
			} catch (IOException e) {
				back.setMsg("接收上传文件出错["+e.getMessage()+"]，请联系开发人员！");
				return back;
			}
		}

		try{
			int tmp = -1;
			if(type==0){		//个人
				tmp = tranService.reportgrTrans02(upfile,start,end);
			} else if(type==1){	//对公
				tmp = tranService.reportdgTrans02(upfile,start,end);
			}
			
			if(tmp == -1){
				back.setMsg("读取文件错误，或文件内容为空！");
				return back;
			}else if(tmp == 0){
				back.setMsg("没有查到数据！");
				return back;
			}
			
			back.setSuc(true);
			back.setMsg("查询到{"+tmp+"}个客户，文件已生成请下载！");
			back.setData(upfile.getAbsolutePath());
		} catch (Exception e) {
			back.setMsg(e.getMessage());
			e.printStackTrace();
		}
		
		return back;
	}
}
