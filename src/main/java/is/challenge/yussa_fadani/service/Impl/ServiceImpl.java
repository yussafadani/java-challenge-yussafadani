package is.challenge.yussa_fadani.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.challenge.yussa_fadani.dao.Dao;
import is.challenge.yussa_fadani.entity.UserEntity;
import is.challenge.yussa_fadani.service.ServiceInterface;

@Service
public class ServiceImpl implements ServiceInterface{

	@Autowired
	private Dao dao;
	
	@Override
	public List<UserEntity> testGetApi() {
		List<UserEntity> list = new ArrayList<UserEntity>();
		Long test = Long.valueOf(9);
		list.add(new UserEntity(test, "test", "123"));
		return list;
	}


	@Override
	public List<UserEntity> ListUser() throws Exception {
		List <UserEntity> list = new ArrayList<UserEntity>(){};
		try {
			list = dao.findAll();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return list;
	}
	
	@Override
	public Map<String, Object> Registrasi(UserEntity entity) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserEntity> list = new ArrayList<UserEntity>();
			boolean validasi = true;
			list = dao.findAll();
			for (int i = 0; i < list.size(); i++) {
				if (entity.getUsername().equalsIgnoreCase(list.get(i).getUsername())) {
					validasi = false;
					break;
				}
			}
			if (validasi) {
				map.put("status", true);
				map.put("message", "Sukses registrasi!");
				dao.save(entity);
			} else {
				map.put("status", false);
				map.put("message", "Username sudah terpakai");
			} 
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return map;
	}


	@Override
	public Map<String, Object> login(UserEntity entity) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserEntity> list = new ArrayList<UserEntity>();
			boolean info = false;
			
			if((entity.getUsername().equals("") || entity.getUsername() == null) ||
					entity.getPassword().equals("") || entity.getPassword() == null) {
				map.put("msg_id", 1);
				map.put("message", "Username dan / atau password kosong");
			}else {
				list = dao.findAll();
				for (int i = 0; i < list.size(); i++) {
					//sukses login
					if (entity.getUsername().equals(list.get(i).getUsername()) && 
							entity.getPassword().equals(list.get(i).getPassword()) ) {
						info = true;
						map.put("msg_id", 2);
						map.put("message", "Sukses Login");
						break;
					}
				}
				if(!info) {
					map.put("msg_id", 3);
					map.put("message", "Username / Password Salah!");
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return map;
	}


	@Override
	public Map<String, Object> editUser(UserEntity entity) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserEntity> list = new ArrayList<UserEntity>();
		boolean info = true;
		try {
			list = dao.findAll();
			for (int i = 0; i < list.size(); i++) {
				if (entity.getUsername().equalsIgnoreCase(list.get(i).getUsername())) {
					info = false;
					map.put("msg_id", 1);
					map.put("message", "Username sudah terpakai");
					break;
				}
				if (entity.getPassword().equals(list.get(i).getPassword())) {
					info = false;
					map.put("msg_id", 2);
					map.put("message", "Password tidak boleh sama dengan password sebelumnya");
					break;
				}
			}
			if (info) {
				dao.save(entity);
				map.put("msg_id", 3);
				map.put("message", "sukses");
			} 
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return map;
	}


	@Override
	public UserEntity findById(Long id) {
		Optional<UserEntity> result = dao.findById(id);
		if(result.isPresent()) {
			return result.get();
		}else {
			return null;	
		}
	}

}
