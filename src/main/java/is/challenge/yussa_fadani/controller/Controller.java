package is.challenge.yussa_fadani.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import is.challenge.yussa_fadani.entity.UserEntity;
import is.challenge.yussa_fadani.service.ServiceInterface;

@RestController
public class Controller {
	
	@Autowired
	private ServiceInterface service;
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "Koneksi IS Challange Yussa Fadani Berhasil!";
	}
	
	@RequestMapping(value = "/testGetApi", method = RequestMethod.GET)
	public List<UserEntity> testGetApi(){
		return this.service.testGetApi();
	} 
	
	@RequestMapping(value = "/registrasi", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> registrasi(@RequestBody UserEntity entity){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = this.service.Registrasi(entity);
			if((boolean)result.get("status")) {
				map.put("status", true);
				map.put("message", result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
			}else {
				map.put("status", false);
				map.put("message",result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.CONFLICT);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("status", false);
			map.put("message", e.getMessage());
			return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserEntity entity){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = this.service.login(entity);
			if((int)result.get("msg_id") == 1) {
				map.put("status", false);
				map.put("message", result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			}else if ((int)result.get("msg_id") == 2){
				map.put("status", true);
				map.put("message",result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}else {
				map.put("status", false);
				map.put("message", result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("status", false);
			map.put("message", e.getMessage());
			return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/getListUser", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getListUser(){
		List <UserEntity> list = new ArrayList<UserEntity>(){};
		Map<String, Object> map = new HashMap<String, Object>();;
		try {
			list = this.service.ListUser();
			map.put("status", true);
			map.put("data", list);
			return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("status", false);
			map.put("message", e.getMessage());
			return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> editUser(@PathVariable Long id,@RequestBody UserEntity entity){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();;
		try {
			entity.setId(id);
			result = this.service.editUser(entity);
			if((int)result.get("msg_id") == 1) {
				map.put("status", false);
				map.put("message", result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.CONFLICT);
			}else if ((int)result.get("msg_id") == 2){
				map.put("status", false);
				map.put("message",result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			}else {
				map.put("status", true);
				map.put("message", result.get("message"));
				return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("status", false);
			map.put("message", e.getMessage());
			return new  ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
	}
}
